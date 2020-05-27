<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
    <div class="row d-flex justify-content-center mt-5">
        <div class="col-6 card">
    <form class="p-5" method="post" action="${pageContext.request.contextPath}/admin/product/add">

        <p class="h4 mb-4 text-center">Add new product</p>
        <div class="my-2">
            <div class="d-inline valid-feedback">
            </div>
        </div>
        <label>Name:</label>
        <input name="name" type="text" class="form-control mb-4" placeholder="Name" value="${name}">
        <div class="my-2">
            <div class="d-inline invalid-feedback">
            </div>
        </div>
        <label>Price:</label>
        <input name="price" type="text" class="form-control mb-4" placeholder="Price" value="${price}">
        <div class="my-2">
            <div class="d-inline invalid-feedback">
            </div>
        </div>


        <button class="btn btn-info btn-block my-4" type="submit">Add</button>


</form>
</div>
</div>
</div>
<jsp:include page="../includes/footer.jsp"/>

