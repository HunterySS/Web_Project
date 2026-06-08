<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${bundle.getString("profile.title")}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<script src="${pageContext.request.contextPath}/js/animations.js"></script>
<body>
<div class="container">
  <div class="header">
    <h1>🛒 WebShop</h1>
    <div class="lang-switch">
      <a href="${pageContext.request.contextPath}/language?lang=en">EN</a>
      <a href="${pageContext.request.contextPath}/language?lang=de">DE</a>
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
    <a href="${pageContext.request.contextPath}/logout" style="float: right;">🚪 ${bundle.getString("home.logout")}</a>
  </div>
  <div class="content">
    <div style="max-width: 400px; margin: 0 auto;">
      <h2>${bundle.getString("profile.title")}</h2>

      <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
      </c:if>

      <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
      </c:if>

      <form method="post" action="${pageContext.request.contextPath}/profile/edit">
        <label>${bundle.getString("profile.username")}:</label>
        <input type="text" value="${user.username}" disabled><br><br>

        <label>${bundle.getString("profile.email")}:</label>
        <input type="email" name="email" value="${user.email}" required><br><br>

        <label>${bundle.getString("profile.newPassword")}:</label>
        <input type="password" name="newPassword"><br><br>

        <label>${bundle.getString("profile.confirmPassword")}:</label>
        <input type="password" name="confirmPassword"><br><br>

        <button type="submit">${bundle.getString("profile.save")}</button>
      </form>
    </div>
  </div>
  <div class="footer">
    <p>&copy; 2026 WebShop. All rights reserved.</p>
  </div>
</div>
</body>
</html>