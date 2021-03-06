<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"/>
<h1 class="h2-responsive text-center my-5"><strong>Orders list</strong></h1>
<div class="container">
    <div class="row d-flex justify-content-center mt-5">
        <div class="col-6 card">
        <table id="dtBasicExample" class="my-5 table table-striped table-bordered" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>ID</th>
                <th>User Name</th>
                <th>Details</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${userOrders}">
                <tr>
                    <td>
                        <c:out value="${order.getId()}"/>
                    </td>
                    <td>
                        <c:out value="${userName}"/>
                    </td>
                    <td>
                        <a class="btn btn-sm aqua-gradient btn-block" href="${pageContext.request.contextPath}/user/order/detail?id=${order.getId()}">Details</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</div>
<jsp:include page="../includes/footer.jsp"/>
