package com.ilya.webproject.dao.impl;

import com.ilya.webproject.dao.OrderDao;
import com.ilya.webproject.model.Order;
import com.ilya.webproject.model.OrderItem;
import com.ilya.webproject.util.DatabaseConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Override
    public Order save(Order order, List<OrderItem> items) {
        Connection conn = null;
        PreparedStatement orderPs = null;
        PreparedStatement itemPs = null;
        ResultSet generatedKeys = null;

        try {
            conn = DatabaseConnectionPool.getConnection();
            conn.setAutoCommit(false);

            String orderSql = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, ?)";
            orderPs = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPs.setLong(1, order.getUserId());
            orderPs.setBigDecimal(2, order.getTotalAmount());
            orderPs.setString(3, order.getStatus());

            int affectedRows = orderPs.executeUpdate();

            if (affectedRows == 0) {
                conn.rollback();
                logger.error("Creating order failed, no rows affected.");
                return null;
            }

            generatedKeys = orderPs.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getLong(1));
            } else {
                conn.rollback();
                logger.error("Creating order failed, no ID obtained.");
                return null;
            }

            String itemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            itemPs = conn.prepareStatement(itemSql);

            for (OrderItem item : items) {
                item.setOrderId(order.getId());
                itemPs.setLong(1, item.getOrderId());
                itemPs.setLong(2, item.getProductId());
                itemPs.setInt(3, item.getQuantity());
                itemPs.setBigDecimal(4, item.getPrice());
                itemPs.addBatch();
            }

            itemPs.executeBatch();

            String updateStockSql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
            PreparedStatement stockPs = conn.prepareStatement(updateStockSql);

            for (OrderItem item : items) {
                stockPs.setInt(1, item.getQuantity());
                stockPs.setLong(2, item.getProductId());
                stockPs.setInt(3, item.getQuantity());
                stockPs.addBatch();
            }

            int[] stockResults = stockPs.executeBatch();

            for (int i = 0; i < stockResults.length; i++) {
                if (stockResults[i] == 0) {
                    conn.rollback();
                    logger.error("Insufficient stock for product: {}", items.get(i).getProductId());
                    return null;
                }
            }

            conn.commit();
            logger.info("Order saved successfully with id: {}", order.getId());
            return order;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                logger.error("Rollback failed: {}", ex.getMessage());
            }
            logger.error("Error saving order: {}", e.getMessage());
            return null;

        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (orderPs != null) orderPs.close();
                if (itemPs != null) itemPs.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error("Error closing resources: {}", e.getMessage());
            }
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToOrder(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding order by id: {}", e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }

            logger.info("Found {} orders for user id: {}", orders.size(), userId);

        } catch (SQLException e) {
            logger.error("Error finding orders by user id: {}", e.getMessage());
        }

        return orders;
    }

    @Override
    public List<OrderItem> findItemsByOrderId(Long orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.*, p.name as product_name FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getLong("id"));
                    item.setOrderId(rs.getLong("order_id"));
                    item.setProductId(rs.getLong("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getBigDecimal("price"));
                    items.add(item);
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding items by order id: {}", e.getMessage());
        }

        return items;
    }

    @Override
    public boolean updateStatus(Long orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, orderId);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Order status updated: id={}, status={}", orderId, status);
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error updating order status: {}", e.getMessage());
        }

        return false;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setUserId(rs.getLong("user_id"));

        Timestamp timestamp = rs.getTimestamp("order_date");
        if (timestamp != null) {
            order.setOrderDate(timestamp.toLocalDateTime());
        }

        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setStatus(rs.getString("status"));
        return order;
    }
}