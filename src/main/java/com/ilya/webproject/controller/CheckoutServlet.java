package com.ilya.webproject.controller;

import com.ilya.webproject.dao.OrderDao;
import com.ilya.webproject.dao.impl.OrderDaoImpl;
import com.ilya.webproject.model.Cart;
import com.ilya.webproject.model.CartItem;
import com.ilya.webproject.model.Order;
import com.ilya.webproject.model.OrderItem;
import com.ilya.webproject.model.User;
import com.ilya.webproject.exception.ApplicationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutServlet.class);
    private final OrderDao orderDao;

    public CheckoutServlet() {
        this.orderDao = new OrderDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Cart cart = (session != null) ? (Cart) session.getAttribute("cart") : null;

        if (session == null || cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        User user = (User) session.getAttribute("user");
        req.setAttribute("user", user);
        req.setAttribute("cart", cart);

        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        String token = req.getParameter("csrfToken");
        String sessionToken = (String) session.getAttribute("csrfToken");

        if (token == null || !token.equals(sessionToken)) {
            logger.warn("CSRF token mismatch");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        session.removeAttribute("csrfToken");

        Cart cart = (session != null) ? (Cart) session.getAttribute("cart") : null;

        if (session == null || cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        User user = (User) session.getAttribute("user");

        Order order = new Order();
        order.setUserId(user.getId());
        order.setTotalAmount(cart.getTotalPrice());
        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getItems().values()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProduct().getId());
            orderItem.setProductName(item.getProduct().getName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice());
            orderItems.add(orderItem);
        }

        Order savedOrder = orderDao.save(order, orderItems);

        if (savedOrder == null) {
            logger.error("Order failed for user: {}", user.getUsername());
            throw new ApplicationException("Order failed. Insufficient stock.");
        }
    }
}