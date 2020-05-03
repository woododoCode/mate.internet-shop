<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"></jsp:include>
<div class="container">
    <div class="row d-flex justify-content-center mt-5">
        <div class="col-6">
            <form class="text-center border border-light p-5" method="post" action="${pageContext.request.contextPath}/login">
                <p class="h4 mb-4">Login</p>
                <label>Login:</label>
                <input name="login" type="text" class="form-control mb-4" placeholder="Login" value="${login}">
                <label>Password:</label>
                <input name="pwd" type="password" class="form-control mb-4" placeholder="Password">
                <button class="btn btn-info btn-block my-4" type="submit">Login</button>
                <div class="my-2">
                    <div class="d-inline invalid-feedback">
                        ${errorMsg}
                    </div>
                </div>
            </form>
        </div>t
    </div>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>