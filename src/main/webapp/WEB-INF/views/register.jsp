<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
</head>
<body>
<h2>Sign Up</h2>

<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>

<c:if test="${not empty success}">
    <div style="color: green;">${success}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/register">
    <label>Username:</label>
    <input type="text" name="username" required><br><br>

    <label>Email:</label>
    <input type="email" name="email" required><br><br>

    <label>Password:</label>
    <input type="password" name="password" required><br><br>

    <label>Confirm Password:</label>
    <input type="password" name="confirmPassword" required><br><br>

    <button type="submit">Sign Up</button>
</form>

<p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign In</a></p>
</body>
</html>