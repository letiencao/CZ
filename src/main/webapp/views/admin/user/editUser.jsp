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
			<h4 class="mt-4">Cập nhật thông tin người dùng</h4>
			<div class="card mb-4" style="font-size: 15px">
				<div class="card-body">
					<form>
						<div class="form-group">
							<label>Fullname</label> <input type="text"
								style="font-size: 15px" class="form-control" id="inputFullname">
						</div>
						<div class="form-group">
							<label>Birthday</label> <input type="date"
								style="font-size: 15px" class="form-control" id="inputBirthday">
						</div>
						<div class="form-group">
							<label>Address</label> <input type="text" style="font-size: 15px"
								class="form-control" id="inputAddress">
						</div>
						<div class="form-group">
							<label>Telephone</label> <input type="text"
								style="font-size: 15px" class="form-control" id="inputTelephone">
						</div>
						<div class="form-group">
							<label>Email</label> <input type="email" style="font-size: 15px"
								class="form-control" id="inputEmail"
								placeholder="name@example.com">
						</div>
						<div class="form-group">
							<label>Role Name</label> <select id="inputRoleName"
								class="form-control"
								style="width: 100%; height: 40px; border-color: #ccc; border-radius: 5px"
								aria-label="Default select example">
								<option class="admin" value="1">ADMIN</option>
								<option class="user" value="2">USER</option>

							</select>
						</div>
						<button type="button" class="btn btn-primary"
							onclick="updateUser()">Submit</button>
					</form>
					<script type="text/javascript"
						src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
					<script type="text/javascript">
						var s = window.location.href;
						var myString = s.substr(s.indexOf("=") + 1);
						function updateUser() {
							var inputFullname = document
									.getElementById("inputFullname").value;
							var inputBirthday = document
									.getElementById("inputBirthday").value;
							var inputAddress = document
									.getElementById("inputAddress").value;
							var inputTelephone = document
									.getElementById("inputTelephone").value;
							var inputEmail = document
									.getElementById("inputEmail").value;
							var inputRoleId = $("#inputRoleName").children("option:selected").val();
							//alert(inputRoleId);
							var obj = {
								id : myString,
								fullname : inputFullname,
								birthday : inputBirthday,
								address : inputAddress,
								telephone : inputTelephone,
								email : inputEmail,
								roleId : inputRoleId
							};
						    $.ajax({
								url : 'update-user-api',
								type : 'PUT',
								contentType : 'application/json',
								data : JSON.stringify(obj),
								dataType:'json',
								
								success : function(result) {
									window.location.href = "admin-users";
								},
								error : function(error) {
									alert('error');
								}
							});
						}
						if (s.includes('=')) {
							getData();
						}else{
							window.location.href = "admin-users";
						}

						function getData() {
							var obj = {
								id : myString
							};
							$
									.ajax({

										url : 'user-api',
										type : 'POST',
										contentType : 'application/json',
										data : JSON.stringify(obj),
										dataType : 'json',

										success : function(result) {
											document
													.getElementById("inputFullname").value = result.fullname;
											document
													.getElementById("inputBirthday").value = result.birthday;
											document
													.getElementById("inputAddress").value = result.address;
											document
													.getElementById("inputTelephone").value = result.telephone;
											document
													.getElementById("inputEmail").value = result.email;
											var roleName = result.roleName;
											if (roleName.localeCompare("ADMIN") == 0) {
												$("#inputRoleName option[value='1']").prop('selected',true);

											} else {
												$("#inputRoleName option[value='2']").prop('selected',true);
											}
										},
										error : function(error) {
											alert("fail");
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