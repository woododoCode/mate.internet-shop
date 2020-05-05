<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.16.0/css/mdb.min.css" rel="stylesheet">
</head>
<body class="cloudy-knoxville-gradient">
<nav class="navbar navbar-expand-lg navbar-dark aqua-gradient">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Home</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
            aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="basicExampleNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/injectData"> Inject Data
                    <span class="sr-only">(current)</span>
                </a>
            </li>
        <c:choose>
            <c:when test="${role == \"ADMIN\"}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/users">Users</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/products">Products manage</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/product/add">Add new product</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/orders">Orders</a>
                </li>
            </c:when>
            <c:when test="${role == \"USER\"}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/cart">Shopping Cart</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/orders">Orders</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/products">Products List</a>
                </li>
            </c:when>
        </c:choose>
        </ul>
        <ul class="navbar-nav ml-auto nav-flex-icons">
            <c:choose>
                <c:when test="${user_id > 0}">

            <li class="nav-item">
                <span class="navbar-text text-body mr-3 font-weight-bold">Welcome ${user_name}</span>
            </li>
            <li class="nav-item">
                <a class="nav-link text-body font-weight-bold" href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
                </c:when>
                <c:otherwise>
            <li class="nav-item">
                <a class="nav-link text-body font-weight-bold" href="${pageContext.request.contextPath}/login">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-body font-weight-bold" href="${pageContext.request.contextPath}/registration">Registration</a>
            </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
</body>
</html>
