<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Home</title>
</head>
<body>
<h2>Welcome, ${username}!</h2>
<p>You have successfully logged in.</p>

<h3>Quick Links</h3>
<ul>
  <li><a href="${pageContext.request.contextPath}/products">View Products</a></li>
  <li><a href="${pageContext.request.contextPath}/cart">View Cart</a></li>
  <li><a href="${pageContext.request.contextPath}/orders">My Orders</a></li>
</ul>

<br>
<a href="${pageContext.request.contextPath}/logout">Sign Out</a>
</body>
</html>