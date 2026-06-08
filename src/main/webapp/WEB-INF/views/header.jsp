<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="${pageContext.request.contextPath}/js/animations.js"></script>
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