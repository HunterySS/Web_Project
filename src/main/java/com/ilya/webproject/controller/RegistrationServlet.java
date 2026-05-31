package com.ilya.webproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("GET request to /register - showing registration form");
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("POST request to /register - processing registration");

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (password == null || !password.equals(confirmPassword)) {
            logger.warn("Passwords do not match for user {}", username);
            req.setAttribute("error", "Passwords do not match");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        logger.info("User {} successfully registered", username);
        req.setAttribute("success", "Registration successful! Please sign in");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}