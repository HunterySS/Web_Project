<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${bundle.getString("home.title")} | WebShop</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<script src="${pageContext.request.contextPath}/js/animations.js"></script>
<body>
<div class="glass-container">
  <div class="header">
    <h1>WebShop<span>.</span></h1>
    <div class="lang-switch">
      <a href="${pageContext.request.contextPath}/language?lang=en" class="lang-btn ${lang == 'en' ? 'active' : ''}">EN</a>
      <a href="${pageContext.request.contextPath}/language?lang=de" class="lang-btn ${lang == 'de' ? 'active' : ''}">DE</a>
    </div>
  </div>
  <div class="nav">
    <a href="${pageContext.request.contextPath}/home">🏠 ${bundle.getString("home.title")}</a>
    <a href="${pageContext.request.contextPath}/products">📦 ${bundle.getString("home.products")}</a>
    <a href="${pageContext.request.contextPath}/cart">🛒 ${bundle.getString("home.cart")}</a>
    <a href="${pageContext.request.contextPath}/orders">📋 ${bundle.getString("home.orders")}</a>
    <a href="${pageContext.request.contextPath}/profile/edit">✏️ ${bundle.getString("home.profile")}</a>
    <c:if test="${sessionScope.user.admin}">
      <a href="${pageContext.request.contextPath}/admin/products">🔧 Admin</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/logout" class="logout">🚪 ${bundle.getString("home.logout")}</a>
  </div>
  <div class="content">
    <div class="card animate" style="text-align: center; max-width: 600px; margin: 0 auto;">
      <div style="font-size: 80px; margin-bottom: 20px;">🎉</div>
      <h2 class="gradient-text" style="font-size: 36px; margin-bottom: 10px;">${bundle.getString("home.welcome")}, ${username}!</h2>
      <p style="color: #94a3b8; margin-bottom: 30px;">You have successfully logged in.</p>
      <div style="display: flex; gap: 15px; justify-content: center; flex-wrap: wrap;">
        <a href="${pageContext.request.contextPath}/products" class="btn">🛍️ Start Shopping</a>
        <a href="${pageContext.request.contextPath}/profile/edit" class="btn btn-outline">👤 Edit Profile</a>
      </div>
    </div>
  </div>
  <div class="footer">
    <p>&copy; 2026 WebShop. All rights reserved.</p>
  </div>
</div>
</body>
</html>