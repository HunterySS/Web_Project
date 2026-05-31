<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
</head>
<body>
<h2>Sign In</h2>

<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
    <label>Username:</label>
    <input type="text" name="username" required><br><br>

    <label>Password:</label>
    <input type="password" name="password" required><br><br>

    <button type="submit">Sign In</button>
</form>

<p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign Up</a></p>
</body>
</html>