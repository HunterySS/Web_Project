package com.ilya.webproject.controller;

import com.ilya.webproject.dao.OrderDao;
import com.ilya.webproject.dao.impl.OrderDaoImpl;
import com.ilya.webproject.model.Order;
import com.ilya.webproject.model.OrderItem;
import com.ilya.webproject.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrdersServlet.class);
    private final OrderDao orderDao;

    public OrdersServlet() {
        this.orderDao = new OrderDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<Order> orders = orderDao.findByUserId(user.getId());

        Map<Long, List<OrderItem>> orderItemsMap = new HashMap<>();
        for (Order order : orders) {
            List<OrderItem> items = orderDao.findItemsByOrderId(order.getId());
            orderItemsMap.put(order.getId(), items);
        }

        req.setAttribute("orders", orders);
        req.setAttribute("orderItems", orderItemsMap);

        req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
    }
}