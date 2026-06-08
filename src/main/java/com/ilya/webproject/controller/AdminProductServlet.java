package com.ilya.webproject.controller;

import com.ilya.webproject.dao.ProductDao;
import com.ilya.webproject.dao.impl.ProductDaoImpl;
import com.ilya.webproject.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AdminProductServlet.class);
    private final ProductDao productDao;

    public AdminProductServlet() {
        this.productDao = new ProductDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Product> products = productDao.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/admin/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            String productIdParam = req.getParameter("productId");
            if (productIdParam != null) {
                try {
                    Long productId = Long.parseLong(productIdParam);
                    productDao.deleteById(productId);
                    logger.info("Product deleted: {}", productId);
                } catch (NumberFormatException e) {
                    logger.error("Invalid product id: {}", e.getMessage());
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }
}