<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../includes/header.jsp"></jsp:include>
<jsp:element name="img"><img src="../123.jpg" alt=""></jsp:element>
<div class="container">
    <div class="row d-flex justify-content-center mt-5">
        <div class="col-6 card">
            <form class="text-center p-5" method="post" action="${pageContext.request.contextPath}/login">
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
        </div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>