<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Wallet</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.5.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/scripts.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css" type="text/css">
</head>
<body class="sign-body">
<div class="sign-wrapper">
    <h1 class="name">Wallet</h1>
    <form method="post">
        <div class="form-row">
            <div class="form-group col-md-12">
                <input class="email  form-control" name="email" type="text" placeholder="Введите email" required>
            </div>
            <div class="form-group col-md-12">
                <input class="password  form-control" name="password" type="password" placeholder="Введите пароль"
                       required>
            </div>
        </div>
        <div>
            <button type="submit" class="btn btn-primary col-md-6">Войти</button>
            <a class="link-to-registration" href="${pageContext.request.contextPath}/registration">Зарегистрироваться!</a>
        </div>
    </form>
    <c:if test="${not empty requestScope.loginErr}">
        <span class="loginErr" style="color:red;"><c:out value="${requestScope.loginErr}"/></span>
    </c:if>
</div>
</body>
</html>