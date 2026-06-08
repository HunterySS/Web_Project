package com.ilya.webproject.controller;

import com.ilya.webproject.model.User;
import com.ilya.webproject.service.UserService;
import com.ilya.webproject.service.impl.UserServiceImpl;
import com.ilya.webproject.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/profile/edit")
public class EditProfileServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(EditProfileServlet.class);
    private final UserService userService;

    public EditProfileServlet() {
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        String email = req.getParameter("email");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (email != null && !email.trim().isEmpty()) {
            currentUser.setEmail(email);
        }

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            if (newPassword.equals(confirmPassword)) {
                currentUser.setPassword(PasswordUtil.hashPassword(newPassword));
            } else {
                req.setAttribute("error", "Passwords do not match");
                req.getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(req, resp);
                return;
            }
        }

        userService.updateUser(currentUser);
        session.setAttribute("user", currentUser);
        logger.info("Profile updated for user: {}", currentUser.getUsername());

        req.setAttribute("success", "Profile updated successfully");
        req.getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(req, resp);
    }
}