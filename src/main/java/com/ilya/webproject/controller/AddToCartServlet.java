package com.ilya.webproject.controller;

import com.ilya.webproject.dao.ProductDao;
import com.ilya.webproject.dao.impl.ProductDaoImpl;
import com.ilya.webproject.model.Cart;
import com.ilya.webproject.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AddToCartServlet.class);
    private final ProductDao productDao;

    public AddToCartServlet() {
        this.productDao = new ProductDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String productIdParam = req.getParameter("productId");
        String quantityParam = req.getParameter("quantity");

        if (productIdParam == null || quantityParam == null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        try {
            Long productId = Long.parseLong(productIdParam);
            int quantity = Integer.parseInt(quantityParam);

            Optional<Product> productOpt = productDao.findById(productId);

            if (productOpt.isPresent() && quantity > 0) {
                HttpSession session = req.getSession();
                Cart cart = (Cart) session.getAttribute("cart");

                if (cart == null) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);
                }

                cart.addItem(productOpt.get(), quantity);
                logger.info("Added product {} to cart, quantity: {}", productId, quantity);
            }

        } catch (NumberFormatException e) {
            logger.error("Invalid productId or quantity: {}", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}