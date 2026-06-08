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
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProductServlet.class);
    private final ProductDao productDao;

    public ProductServlet() {
        this.productDao = new ProductDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Product> products = productDao.findAll();
        req.setAttribute("products", products);
        logger.info("Displaying {} products", products.size());

        req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
    }
}