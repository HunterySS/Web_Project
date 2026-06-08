package com.ilya.webproject.filter;

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
import java.util.List;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private static final List<String> PUBLIC_PATHS = List.of(
            "/login", "/register", "/", "/css", "/js", "/images"
    );

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        boolean isPublic = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

        if (isPublic) {
            logger.debug("Public path accessed: {}", path);
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) {
            logger.debug("Authenticated user accessed: {}", path);
            chain.doFilter(req, res);
        } else {
            logger.warn("Unauthorized access attempt to: {}", path);
            res.sendRedirect(contextPath + "/login");
        }
    }
}