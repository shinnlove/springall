<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>默认错误页面!</title>
</head>
<body>
<!-- 这是默认错误页面。 -->
<p>
    ERROR! 这是默认错误页面。<br>
    message: <c:out value="${ex.message}"></c:out>
</p>
</body>
</html>