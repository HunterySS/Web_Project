package com.ilya.webproject.filter;

import com.ilya.webproject.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.isAdmin()) {
            logger.warn("Access denied for non-admin user");
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(req, res);
    }
}