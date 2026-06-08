<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${bundle.getString("product.title")} | WebShop</title>
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
        <h2 class="gradient-text" style="font-size: 32px; margin-bottom: 30px;">${bundle.getString("product.title")}</h2>

        <div class="product-grid">
            <c:forEach items="${products}" var="product">
                <div class="product-card animate">
                    <div style="width: 50px; height: 50px; background: linear-gradient(135deg, #667eea, #764ba2); border-radius: 12px; display: flex; align-items: center; justify-content: center; margin-bottom: 15px;">
                        <span style="font-size: 24px;">📦</span>
                    </div>
                    <h3>${product.name}</h3>
                    <p style="color: #94a3b8; font-size: 13px; margin-bottom: 12px;">${product.description}</p>
                    <div class="price">$${product.price}</div>
                    <div class="stock">📊 ${bundle.getString("product.stock")}: ${product.stock}</div>
                    <form method="post" action="${pageContext.request.contextPath}/add-to-cart" style="margin-top: 15px;">
                        <input type="hidden" name="productId" value="${product.id}">
                        <div style="display: flex; gap: 10px; align-items: center;">
                            <input type="number" name="quantity" value="1" min="1" max="${product.stock}" style="width: 70px; padding: 8px; background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1); border-radius: 12px; color: white;">
                            <button type="submit" class="btn btn-sm">➕ ${bundle.getString("product.add")}</button>
                        </div>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="footer">
        <p>&copy; 2026 WebShop. All rights reserved.</p>
    </div>
</div>
</body>
</html>