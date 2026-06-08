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
import java.util.Locale;
import java.util.ResourceBundle;

@WebFilter("/*")
public class LocalizationFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(LocalizationFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = req.getSession();
        String lang = req.getParameter("lang");

        if (lang != null && (lang.equals("en") || lang.equals("de"))) {
            session.setAttribute("lang", lang);
            logger.debug("Language changed to: {}", lang);
        }

        String currentLang = (String) session.getAttribute("lang");
        if (currentLang == null) {
            currentLang = "en";
            session.setAttribute("lang", currentLang);
        }

        Locale locale = currentLang.equals("de") ? Locale.GERMAN : Locale.ENGLISH;
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        req.setAttribute("lang", currentLang);
        req.setAttribute("bundle", bundle);

        chain.doFilter(req, res);
    }
}