package com.ilya.webproject.dao.impl;

import com.ilya.webproject.dao.ProductDao;
import com.ilya.webproject.model.Product;
import com.ilya.webproject.util.DatabaseConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    @Override
    public boolean save(Product product) {
        String sql = "INSERT INTO products (name, description, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setInt(4, product.getStock());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        product.setId(rs.getLong(1));
                    }
                }
                logger.info("Product saved successfully: {}", product.getName());
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error saving product: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToProduct(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding product by id: {}", e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

            logger.info("Found {} products", products.size());

        } catch (SQLException e) {
            logger.error("Error finding all products: {}", e.getMessage());
        }

        return products;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE LOWER(name) LIKE LOWER(?)";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }

            logger.info("Found {} products with name containing: {}", products.size(), name);

        } catch (SQLException e) {
            logger.error("Error finding products by name: {}", e.getMessage());
        }

        return products;
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setLong(5, product.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Product updated: {}", product.getName());
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error updating product: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Product deleted with id: {}", id);
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error deleting product: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateStock(Long id, int quantity) {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setLong(2, id);
            ps.setInt(3, quantity);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Stock updated for product id: {}, quantity: {}", id, quantity);
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error updating stock: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean incrementStock(Long productId, int quantity) {
        String sql = "UPDATE products SET stock = stock + ? WHERE id = ?";

        try (Connection conn = DatabaseConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setLong(2, productId);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Stock incremented for product id: {}, quantity: {}", productId, quantity);
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error incrementing stock: {}", e.getMessage());
        }

        return false;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStock(rs.getInt("stock"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            product.setCreatedAt(timestamp.toLocalDateTime());
        }

        return product;
    }
}