<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"></jsp:include>
<h1 class="h2-responsive text-center my-5"><strong>Orders list</strong></h1>
<div class="container">
    <table id="dtBasicExample" class="mt-5 table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>ID</th>
            <th>Products</th>
            <th>Details</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>
                    <c:out value="${order.getId()}"/>
                </td>
                <td>
                    <c:out value="${order.getUser().getName()}"/>
                </td>
                <td>
                    <a class="btn" href="${pageContext.request.contextPath}/order/detail?id=${order.getId()}">Details</a>
                </td>
                <td>
                    <a class="btn" href="${pageContext.request.contextPath}/order/delete?id=${order.getId()}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>
