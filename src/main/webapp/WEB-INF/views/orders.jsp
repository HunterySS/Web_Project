<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${bundle.getString("order.title")}</title>
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
        <h2>${bundle.getString("order.title")}</h2>

        <c:if test="${not empty sessionScope.orderSuccess}">
            <div class="alert alert-success">${sessionScope.orderSuccess}</div>
            <% session.removeAttribute("orderSuccess"); %>
        </c:if>

        <c:choose>
            <c:when test="${empty orders}">
                <p>${bundle.getString("order.noOrders")}</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${orders}" var="order">
                    <div class="card">
                        <h3>${bundle.getString("order.number")} #${order.id}</h3>
                        <p>${bundle.getString("order.date")}: ${order.orderDate}</p>
                        <p>${bundle.getString("order.status")}: <strong style="color: ${order.status == 'CANCELLED' ? 'red' : 'green'}">${order.status}</strong></p>
                        <p>${bundle.getString("order.total")}: $${order.totalAmount}</p>

                        <table>
                            <thead>
                            <tr>
                                <th>Product</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${orderItems[order.id]}" var="item">
                                <tr>
                                    <td>${item.productName}</td>
                                    <td>${item.quantity}</td>
                                    <td>$${item.price}</td>
                                    <td>$${item.totalPrice}</td>
                                    <td>
                                        <c:if test="${order.status == 'PENDING'}">
                                            <form method="post" action="${pageContext.request.contextPath}/cancel-order" style="display: inline;">
                                                <input type="hidden" name="orderId" value="${order.id}">
                                                <button type="submit" class="danger" onclick="return confirm('Cancel this order?')">${bundle.getString("order.cancel")}</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${order.status == 'CANCELLED'}">
                                            <span style="color: red;">${bundle.getString("order.cancelled")}</span>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <br>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="footer">
        <p>&copy; 2026 WebShop. All rights reserved.</p>
    </div>
</div>
</body>
</html>