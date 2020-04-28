<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"></jsp:include>
<div class="container">
    <form class="border border-light p-5" method="post" action="${pageContext.request.contextPath}/newprod">

        <p class="h4 mb-4 text-center">Add new product</p>
        <div class="my-2">
            <div class="d-inline valid-feedback">
                ${message4}
            </div>
        </div>
        <label>Name:</label>
        <input name="name" type="text" class="form-control mb-4" placeholder="Name" value="${name}">
        <div class="my-2">
            <div class="d-inline invalid-feedback">
                ${message}
            </div>
        </div>
        <label>Amount:</label>
        <input name="amount" type="text" class="form-control mb-4" placeholder="Amount" value="${amount}">
        <div class="my-2">
            <div class="d-inline invalid-feedback">
                ${message2}
            </div>
        </div>
        <label>Price:</label>
        <input name="price" type="text" class="form-control mb-4" placeholder="Price" value="${price}">
        <div class="my-2">
            <div class="d-inline invalid-feedback">
                ${message3}
            </div>
        </div>


        <button class="btn btn-info btn-block my-4" type="submit">Add</button>


</form>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>

