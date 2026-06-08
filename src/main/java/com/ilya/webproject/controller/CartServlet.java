package com.ilya.webproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        session.setAttribute("csrfToken", UUID.randomUUID().toString());

        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        if ("update".equals(action)) {
            String productIdParam = req.getParameter("productId");
            String quantityParam = req.getParameter("quantity");

            if (productIdParam != null && quantityParam != null) {
                try {
                    Long productId = Long.parseLong(productIdParam);
                    int quantity = Integer.parseInt(quantityParam);

                    com.ilya.webproject.model.Cart cart =
                            (com.ilya.webproject.model.Cart) session.getAttribute("cart");

                    if (cart != null) {
                        cart.updateQuantity(productId, quantity);
                        logger.info("Updated cart item productId: {}, quantity: {}", productId, quantity);
                    }

                } catch (NumberFormatException e) {
                    logger.error("Invalid parameters: {}", e.getMessage());
                }
            }

        } else if ("remove".equals(action)) {
            String productIdParam = req.getParameter("productId");

            if (productIdParam != null) {
                try {
                    Long productId = Long.parseLong(productIdParam);

                    com.ilya.webproject.model.Cart cart =
                            (com.ilya.webproject.model.Cart) session.getAttribute("cart");

                    if (cart != null) {
                        cart.removeItem(productId);
                        logger.info("Removed product {} from cart", productId);
                    }

                } catch (NumberFormatException e) {
                    logger.error("Invalid productId: {}", e.getMessage());
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}