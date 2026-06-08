<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${bundle.getString("login.title")} | WebShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            background: #f5f7fa;
        }

        .glass-container {
            max-width: 500px;
            width: 100%;
            margin: 0 auto;
        }

        .card {
            text-align: center;
        }

        .input-group {
            text-align: left;
        }
    </style>
</head>
<body>
<div class="glass-container">
    <div class="header">
        <h1>WebShop<span>.</span></h1>
        <div class="lang-switch">
            <a href="${pageContext.request.contextPath}/language?lang=en" class="lang-btn ${lang == 'en' ? 'active' : ''}">EN</a>
            <a href="${pageContext.request.contextPath}/language?lang=de" class="lang-btn ${lang == 'de' ? 'active' : ''}">DE</a>
        </div>
    </div>
    <div class="content">
        <div class="card animate">
            <h2 style="font-size: 32px; margin-bottom: 8px;" class="gradient-text">${bundle.getString("login.title")}</h2>
            <p style="color: #718096; margin-bottom: 30px;">Welcome back!</p>

            <c:if test="${not empty error}">
                <div class="alert alert-error">⚠️ ${error}</div>
            </c:if>

            <c:if test="${not empty sessionScope.registerSuccess}">
                <div class="alert alert-success">✅ ${sessionScope.registerSuccess}</div>
                <% session.removeAttribute("registerSuccess"); %>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/login" id="loginForm">
                <div class="input-group">
                    <label>${bundle.getString("login.username")}</label>
                    <input type="text" name="username" id="username" required>
                </div>

                <div class="input-group">
                    <label>${bundle.getString("login.password")}</label>
                    <input type="password" name="password" id="password" required>
                </div>

                <button type="submit" class="btn" style="width: 100%;">${bundle.getString("login.button")}</button>
            </form>

            <p style="margin-top: 24px; color: #718096;">
                ${bundle.getString("login.register")}
                <a href="${pageContext.request.contextPath}/register" style="color: #667eea; text-decoration: none;">→</a>
            </p>
        </div>
    </div>
    <div class="footer">
        <p>&copy; 2026 WebShop. All rights reserved.</p>
    </div>
</div>

<script>
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value;

        if (!username || !password) {
            e.preventDefault();
            alert('Please fill in all fields');
        }
    });
</script>
</body>
</html>