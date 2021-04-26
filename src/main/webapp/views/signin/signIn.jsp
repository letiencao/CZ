<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>

<html>

<head>

</head>

<body>
	<div class="limiter">
		<div class="container-login100"
			style="background-image: url('<c:url value='/template/signup/images/bg-01.jpg'/>');">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
				<form class="login100-form validate-form" action="sign-in"
					method="POST">
					<span class="login100-form-title p-b-49"> Sign In </span>
					<c:if test="${sessionScope.message != null }">
						<div class="alert alert-danger" role="alert">Email or
							Password is not correct</div>
					</c:if>
					<div class="wrap-input100 validate-input m-b-23"
						data-validate="Phone number is reauired">
						<span class="label-input100">Phone Number</span> <input class="input100"
							type="tel" name="phoneNumber" placeholder="Type your phone number">
						<span class="focus-input100" data-symbol="&#xf206;"></span>
					</div>

					<div class="wrap-input100 validate-input"
						data-validate="Password is required">
						<span class="label-input100">Password</span> <input
							class="input100" type="password" name="password"
							placeholder="Type your password"> <span
							class="focus-input100" data-symbol="&#xf190;"></span>
					</div>

					<div class="text-right p-t-8 p-b-31">
						<a href="#"> Forgot password? </a>
					</div>

					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn">Login</button>
						</div>
					</div>

					<div class="txt1 text-center p-t-22 p-b-20">
						<span> Or Sign In Using </span>
					</div>

					<div class="flex-c-m">
						<a href="#" class="login100-social-item bg1"> <i
							class="fa fa-facebook"></i>
						</a> <a href="#" class="login100-social-item bg3"> <i
							class="fa fa-google"></i>
						</a>
					</div>

					<div class="flex-col-c p-t-22">
						<span class="txt1 p-b-17"> Or </span>
						<p>
							Don't have an account ? <a href="sign-up" class="txt2"> Sign
								Up </a>
						</p>
					</div>
				</form>
			</div>
		</div>
	</div>


	<div id="dropDownSelect1"></div>
	<script type="text/javascript">
		window.history.forward();
	</script>

</body>

</html>