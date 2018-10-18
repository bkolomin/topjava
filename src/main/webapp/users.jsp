<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Users test 222</h2>

<table border="1" cellpadding="8" cellspacing="0">
<c:forEach items="${users}" var="user">
    <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
    <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>${user.enabled}</td>
        <td>${user.caloriesPerDay}</td>
        <td>${user.roles}</td>
        <td><a href="users?action=update&id=${user.id}">Update</a></td>
        <td><a href="users?action=delete&id=${user.id}">Delete</a></td>
    </tr>
</c:forEach>
</table>


</body>
</html>