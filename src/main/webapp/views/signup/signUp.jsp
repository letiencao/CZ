<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

<head>

</head>

<body>

	<div class="limiter">
		<div class="container-login100"
			style="background-image: url('<c:url value='/template/signup/images/bg-01.jpg'/>');">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
				<form class="login100-form validate-form">
					<span class="login100-form-title p-b-49"> Sign Up </span>
					<%--  <c:if test="${sessionScope.message != null }">
					<div class="alert alert-danger" role="alert">User already
						exists.Try again</div>
					</c:if>--%>
					<div class="row">
						<div class="col-lg-6">
							<div class="wrap-input100 validate-input m-b-23"
								data-validate="required">
								<span class="label-input100">Fullname</span> <input
									class="input100" type="text" name="fullname"
									placeholder="Fullname" id="inputFullname">
							</div>
						</div>
						<div class="col-lg-6">
							<div class="wrap-input100 validate-input m-b-10 "
								data-validate="required">
								<span class="label-input100">Birthday</span> <input
									class="input100 js-datepicker" type="text" name="birthday"
									placeholder="Birthday" id="inputBirthday">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6">
							<div class="wrap-input100 validate-input m-b-10"
								data-validate="required">
								<span class="label-input100">Phone Number</span> <input
									class="input100" type="text" name="phoneNumber"
									placeholder="Phone Number" id="inputPhoneNumber">
							</div>
						</div>
						<div class="col-lg-6">
							<div class="wrap-input100 validate-input m-b-10"
								data-validate="required">
								<span class="label-input100">Address</span> <input
									class="input100" type="text" name="address"
									placeholder="Address" id="inputAddress">
							</div>
						</div>


					</div>



					<div class="wrap-input100 validate-input m-b-10"
						data-validate="required">
						<span class="label-input100">Email</span> <input class="input100"
							type="email" name="email" placeholder="Email" id="inputEmail">
					</div>

					<div class="wrap-input100 validate-input m-b-10"
						data-validate="required">
						<span class="label-input100">Password</span> <input
							class="input100" type="password" name="password"
							placeholder="Password" id="inputPassword">

					</div>

					<div class="wrap-input100 validate-input m-b-10"
						data-validate="required">
						<span class="label-input100">Confirm Password</span> <input
							class="input100" type="password" name="confirmPassword"
							placeholder="Confirm password" id="inputConfirmPassword">

					</div>


					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn" type="button"
								onclick="signUp()">Sign Up</button>
						</div>
					</div>

					<div class="txt1 text-center p-t-22 p-b-20">
						<span> Or Sign Up Using </span>
					</div>

					<div class="flex-c-m">
						<a href="#" class="login100-social-item bg1"> <i
							class="fa fa-facebook"></i>
						</a> <a href="#" class="login100-social-item bg3"> <i
							class="fa fa-google"></i>
						</a>
					</div>
				</form>
			</div>
		</div>
	</div>


	<div id="dropDownSelect1"></div>

	<script type="text/javascript">
		var url = window.location.href;
		var session = '${sessionScope.model}';
		
		if(url.includes("sign-up") == true){
			if(session.length > 0){
				var roleId = '${sessionScope.model.roleId}';
				if(roleId == 1){
					window.location.href = "admin-user";
				}else{
					window.location.href = "trang-chu";
				}
			}
		} 
		function signUp() {
			var inputFullname = document.getElementById("inputFullname").value;
			var inputBirthday = document.getElementById("inputBirthday").value;
			var inputPhoneNumber = document.getElementById("inputPhoneNumber").value;
			var inputAddress = document.getElementById("inputAddress").value;
			var inputEmail = document.getElementById("inputEmail").value;
			var inputPassword = document.getElementById("inputPassword").value;
			var inputConfirmPassword = document
					.getElementById("inputConfirmPassword").value;
			if (inputFullname != null ) {
				
				if (inputPassword.localeCompare(inputConfirmPassword) == 0) {
					var obj = {
						fullname : inputFullname,
						birthday : inputBirthday,
						address : inputAddress,
						telephone : inputPhoneNumber,
						email : inputEmail,
						password : inputPassword
					};
					$.ajax({

						url : 'update-user-api',
						type : 'POST',
						contentType : 'application/json',
						data : JSON.stringify(obj),
						dataType : 'json',
						success : function(result) {
							alert("hello")
							window.location.href = "sign-in";
						},
						error : function(error) {
							alert('error');
							window.location.href = "sign-up";
						}
					});
				}

			}

		}
	</script>


</body>

</html>