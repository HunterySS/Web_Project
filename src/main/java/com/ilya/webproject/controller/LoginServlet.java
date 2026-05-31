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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.debug("GET request to /login - showing login form");

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            logger.info("User already logged in, redirecting to home");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("POST request to /login - processing login");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "123".equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            logger.info("User {} successfully logged in", username);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            logger.warn("Failed login attempt for user {}", username);
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}