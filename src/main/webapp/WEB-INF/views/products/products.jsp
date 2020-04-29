<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"></jsp:include>
<h1 class="h2-responsive text-center my-5"><strong>List of all products</strong></h1>
<div class="container">
    <table id="dtBasicExample" class="mt-5 table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete</th>
        <th>Add to cart</th>
    </tr>
    </thead>
    <tbody>
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
                    <a href="${pageContext.request.contextPath}/products/delete?id=${product.getId()}">Delete</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/products/add?id=${product.getId()}">
                        <button class="btn btn-sm btn-info btn-block my-4" type="submit">
                            Add to cart
                        </button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>