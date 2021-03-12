<%@include file="/common/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>C.Shop | Admin</title>

</head>

<body>


	<main>
		<div class="container-fluid">
			<h4 class="mt-4">Thêm người dùng</h4>
			<div class="card mb-4">
				<div class="card-body">
					<form enctype="multipart/form-data">
						<div class="form-group">
							<label>Fullname</label> <input type="text" class="form-control"
								id="fullname">
						</div>
						<div class="form-group">
							<label>Avatar</label> <input type="file"
								class="form-control-file" id="avatar" onchange="onChange();">
						</div>
						<div class="form-group">
							<label>Birthday</label> <input type="date" class="form-control"
								id="birthday">
						</div>
						<div class="form-group">
							<label>Address</label> <input type="text" class="form-control"
								id="address">
						</div>
						<div class="form-group">
							<label>Telephone</label> <input type="text" class="form-control"
								id="telephone">
						</div>
						<div class="form-group">
							<label>Email</label> <input type="email" class="form-control"
								id="email" placeholder="name@example.com">
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password"
								class="form-control" id="password">
						</div>
						<div class="form-group">
							<label>Role Name</label> <select id="role" class="form-control"
								style="width: 100%; height: 40px; border-color: #ccc; border-radius: 5px"
								aria-label="Default select example">
								<option>ADMIN</option>
								<option selected>USER</option>

							</select>
						</div>
						<button type="button" class="btn btn-primary">Submit</button>
					</form>
					<script type="text/javascript"
						src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
					<script type="text/javascript">
					const onChange = (event) => {
						  const value = event.target.value;

						  // this will return C:\fakepath\somefile.ext
						  console.log(value);

						  const files = event.target.files;

						  //this will return an ARRAY of File object
						  console.log(files);
						}
						$(".btn-primary").click(
								function(e) {
									
									var fullnameInput = document
											.getElementById("fullname").value;
									var birthdayInput = document
											.getElementById("birthday").value;
									var addressInput = document
											.getElementById("address").value;
									var telephoneInput = document
											.getElementById("telephone").value;
									var passwordInput = document
											.getElementById("password").value;
									var emailInput = document
											.getElementById("email").value;
									var roleName = document
											.getElementById("role").value;
									var roleId = 1;
									if (roleName.localeCompare("ADMIN") == 0) {
										roleId = 1;
									} else {
										roleId = 2;
									}
									var avatar = document
											.getElementById("avatar").value.split("C:\\fakepath\\");
									insertData(fullnameInput, birthdayInput,
											addressInput, telephoneInput,
											emailInput, passwordInput, roleId,avatar[1])
								});
						
						function insertData(fullnameInput, birthdayInput,
								addressInput, telephoneInput, emailInput,
								passwordInput, roleId,avatar) {
							var obj = {
								fullname : fullnameInput,
								birthday : birthdayInput,
								address : addressInput,
								telephone : telephoneInput,
								email : emailInput,
								password : passwordInput,
								roleId : roleId,
								imageFile:avatar
							};
							$.ajax({

								url : 'update-user-api',
								type : 'POST',
								contentType : 'application/json',
								data : JSON.stringify(obj),
								dataType : 'json',
								success : function(result) {
									window.location.href = "admin-users";
								},
								error : function(error) {
									alert(avatar);
									/* alert('error'); */
								}
							});
						}
					</script>
				</div>
			</div>
		</div>
	</main>
</body>

</html>