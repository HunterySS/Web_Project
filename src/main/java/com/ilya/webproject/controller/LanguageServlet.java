package com.ilya.webproject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/language")
public class LanguageServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LanguageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String lang = req.getParameter("lang");
        String referer = req.getHeader("Referer");

        if (lang != null) {
            req.getSession().setAttribute("lang", lang);
            logger.info("Language changed to: {}", lang);
        }

        if (referer != null && !referer.isEmpty()) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}