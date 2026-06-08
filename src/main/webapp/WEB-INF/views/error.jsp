<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Error</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div style="max-width: 500px; margin: 50px auto;">
  <div class="card" style="text-align: center;">
    <h2 class="gradient-text">Oops!</h2>
    <div class="alert alert-error">⚠️ ${error}</div>
    <a href="${pageContext.request.contextPath}/home" class="btn">Go Back Home</a>
  </div>
</div>
</body>
</html>