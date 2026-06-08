package com.ilya.webproject.controller;

import com.ilya.webproject.dao.OrderDao;
import com.ilya.webproject.dao.OrderItemDao;
import com.ilya.webproject.dao.ProductDao;
import com.ilya.webproject.dao.impl.OrderDaoImpl;
import com.ilya.webproject.dao.impl.OrderItemDaoImpl;
import com.ilya.webproject.dao.impl.ProductDaoImpl;
import com.ilya.webproject.model.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/cancel-order")
public class CancelOrderServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CancelOrderServlet.class);
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final ProductDao productDao;

    public CancelOrderServlet() {
        this.orderDao = new OrderDaoImpl();
        this.orderItemDao = new OrderItemDaoImpl();
        this.productDao = new ProductDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderIdParam = req.getParameter("orderId");

        if (orderIdParam != null) {
            try {
                Long orderId = Long.parseLong(orderIdParam);

                List<OrderItem> items = orderItemDao.findByOrderId(orderId);

                for (OrderItem item : items) {
                    productDao.incrementStock(item.getProductId(), item.getQuantity());
                }

                orderDao.updateStatus(orderId, "CANCELLED");
                logger.info("Order cancelled and stock restored: {}", orderId);

            } catch (NumberFormatException e) {
                logger.error("Invalid order id: {}", e.getMessage());
            }
        }

        resp.sendRedirect(req.getContextPath() + "/orders");
    }
}