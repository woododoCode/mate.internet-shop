<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="includes/header.jsp"></jsp:include>
<div class="container">
    <div class="row d-flex justify-content-center mt-5">
        <div class="col-6 card">
            <form class="text-center p-5" method="post" action="${pageContext.request.contextPath}/registration">
                <p class="h4 mb-4">Registration</p>
                <label>Name:</label>
                <input name="name" type="text" class="form-control mb-4" placeholder="Name" value="${name}">
                <label>Login:</label>
                <input name="login" type="text" class="form-control mb-4" placeholder="Login" value="${login}">
                <div class="my-2">
                    <div class="d-inline invalid-feedback">
                        ${message2}
                    </div>
                </div>
                <label>Password:</label>
                <input name="pwd" type="password" class="form-control mb-4" placeholder="Password">
                <label>Password:</label>
                <input name="pwd-confirm" type="password" class="form-control mb-4" placeholder="Confirm password">
                <div class="d-inline invalid-feedback">
                    ${message}
                </div>
                <button class="btn btn-info btn-block my-4" type="submit">Sign in</button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="includes/footer.jsp"></jsp:include>