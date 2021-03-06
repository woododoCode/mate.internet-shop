<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"/>
    <head>
    <title>All Users</title>
    </head>
<h1 class="h2-responsive text-center my-5"><strong>List of all users</strong></h1>
<div class="container">
    <div class="row d-flex justify-content-center mt-5">
        <div class="col-6 card">
    <table id="dtBasicExample" class="my-5 table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Login</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>
                    <c:out value="${user.getId()}"/>
                </td>
                <td>
                    <c:out value="${user.name}"/>
                </td>
                <td>
                    <c:out value="${user.login}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/users/delete?id=${user.getId()}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</div>
</div>
<jsp:include page="../includes/footer.jsp"/>
