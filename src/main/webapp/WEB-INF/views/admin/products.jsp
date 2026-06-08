<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${bundle.getString("admin.title")}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<script src="${pageContext.request.contextPath}/js/animations.js"></script>
<body>
<div class="container">
  <div class="header">
    <h1>🛒 WebShop - Admin</h1>
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
    <h2>${bundle.getString("admin.title")}</h2>

    <table>
      <thead>
      <tr>
        <th>${bundle.getString("product.id")}</th>
        <th>${bundle.getString("product.name")}</th>
        <th>${bundle.getString("product.price")}</th>
        <th>${bundle.getString("product.stock")}</th>
        <th>${bundle.getString("cart.action")}</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${products}" var="product">
        <tr>
          <td>${product.id}</td>
          <td>${product.name}</td>
          <td>$${product.price}</td>
          <td>${product.stock}</td>
          <td>
            <form method="post" action="${pageContext.request.contextPath}/admin/products" style="display: inline;">
              <input type="hidden" name="action" value="delete">
              <input type="hidden" name="productId" value="${product.id}">
              <button type="submit" class="danger" onclick="return confirm('Delete this product?')">${bundle.getString("admin.delete")}</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
  <div class="footer">
    <p>&copy; 2026 WebShop. All rights reserved.</p>
  </div>
</div>
</body>
</html>