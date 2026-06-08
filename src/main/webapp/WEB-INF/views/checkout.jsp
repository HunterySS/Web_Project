<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
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
        <h2>Order Summary</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <h3>Customer: ${user.username}</h3>

        <table>
            <thead>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cart.items}" var="entry">
                <c:set var="item" value="${entry.value}"/>
                <tr>
                    <td>${item.product.name}</td>
                    <td>$${item.product.price}</td>
                    <td>${item.quantity}</td>
                    <td>$${item.totalPrice}</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="3"><strong>Grand Total:</strong></td>
                <td><strong>$${cart.totalPrice}</strong></td>
            </tr>
            </tfoot>
        </table>

        <form method="post" action="${pageContext.request.contextPath}/checkout" onsubmit="return confirm('Confirm order?')">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
            <button type="submit">Confirm Order</button>
        </form>
    </div>
    <div class="footer">
        <p>&copy; 2026 WebShop. All rights reserved.</p>
    </div>
</div>
</body>
</html>