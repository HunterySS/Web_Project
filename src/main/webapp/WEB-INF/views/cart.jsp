<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${bundle.getString("cart.title")}</title>
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
    <h2>${bundle.getString("cart.title")}</h2>

    <c:set var="cart" value="${sessionScope.cart}" />
    <c:set var="cartExists" value="${not empty cart and not empty cart.items}" />
    <c:set var="csrfToken" value="${sessionScope.csrfToken}" />

    <c:choose>
      <c:when test="${not cartExists}">
        <p>${bundle.getString("cart.empty")}</p>
      </c:when>
      <c:otherwise>
        <table>
          <thead>
          <tr>
            <th>${bundle.getString("cart.product")}</th>
            <th>${bundle.getString("cart.price")}</th>
            <th>${bundle.getString("cart.quantity")}</th>
            <th>${bundle.getString("cart.total")}</th>
            <th>${bundle.getString("cart.action")}</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${cart.items}" var="entry">
            <tr>
              <td>${entry.value.product.name}</td>
              <td>$${entry.value.product.price}</td>
              <td>
                <form method="post" action="${pageContext.request.contextPath}/cart" style="display: inline;">
                  <input type="hidden" name="action" value="update">
                  <input type="hidden" name="csrfToken" value="${csrfToken}">
                  <input type="hidden" name="productId" value="${entry.value.product.id}">
                  <input type="number" name="quantity" value="${entry.value.quantity}" min="0" style="width: 60px;">
                  <button type="submit">${bundle.getString("cart.update")}</button>
                </form>
              </td>
              <td>$${entry.value.totalPrice}</td>
              <td>
                <form method="post" action="${pageContext.request.contextPath}/cart" style="display: inline;">
                  <input type="hidden" name="action" value="remove">
                  <input type="hidden" name="csrfToken" value="${csrfToken}">
                  <input type="hidden" name="productId" value="${entry.value.product.id}">
                  <button type="submit" class="danger">${bundle.getString("cart.remove")}</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          </tbody>
          <tfoot>
          <tr>
            <td colspan="3"><strong>${bundle.getString("cart.total")}:</strong></td>
            <td colspan="2"><strong>$${cart.totalPrice}</strong></td>
          </tr>
          </tfoot>
        </table>
      </c:otherwise>
    </c:choose>

    <br>
    <a href="${pageContext.request.contextPath}/products">${bundle.getString("cart.continue")}</a>
    <c:if test="${cartExists}">
      | <a href="${pageContext.request.contextPath}/checkout">${bundle.getString("cart.checkout")}</a>
    </c:if>
  </div>
  <div class="footer">
    <p>&copy; 2026 WebShop. All rights reserved.</p>
  </div>
</div>
</body>
</html>