<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="includes/header.jsp"></jsp:include>
<h1 class="h2-responsive text-center my-5"><strong>Products in cart</strong></h1>
<div class="container">
    <div class="row d-flex justify-content-center mt-5">
        <div class="col-6 card">
    <table id="dtBasicExample" class="mt-5 table table-striped table-bordered" cellspacing="0" width="10%">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Price</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody class="text-center">
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <c:out value="${product.getId()}"/>
                </td>
                <td>
                    <c:out value="${product.getName()}"/>
                </td>
                <td>
                    <c:out value="${product.getPrice()}"/>
                </td>
                <td>
                    <a  href="${pageContext.request.contextPath}/cart/delete?id=${product.getId()}"
                        class="btn aqua-gradient btn-sm">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
        <a class="btn btn-info btn-block my-4" href="${pageContext.request.contextPath}/order">Create Order</a>
</div>
</div>
</div>
<jsp:include page="includes/footer.jsp"></jsp:include>