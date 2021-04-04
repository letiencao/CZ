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
			<h4 class="mt-4">Thông tin chi tiết</h4>
			<div class="card mb-4">
				<div class="card-body">
					<form>
						<img id="avatar" src="" alt="Avatar"
							style="width: 150px; height: 200px">
						<div class="form-group row">
							<label for="id" class="col-sm-2 col-form-label"
								style="font-weight: bold;">ID</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="id">
							</div>
						</div>
						<div class="form-group row">
							<label for="fullname" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Fullname</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="fullname">
							</div>
						</div>
						<div class="form-group row">
							<label for="birthday" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Birthday</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="birthday">
							</div>
						</div>
						<div class="form-group row">
							<label for="address" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Address</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="address">
							</div>
						</div>
						<div class="form-group row">
							<label for="telephone" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Telephone</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="telephone">
							</div>
						</div>
						<div class="form-group row">
							<label for="email" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Email</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="email">
							</div>
						</div>
						<div class="form-group row">
							<label for="password" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Password</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="password">
							</div>
						</div>
						<div class="form-group row">
							<label for="rolename" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Role Name</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="rolename">
							</div>
						</div>
						<div class="form-group row">
							<label for="deleted" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Deleted</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="deleted">
							</div>
						</div>
						<div class="form-group row">
							<label for="createddate" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Created Date</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="createddate">
							</div>
						</div>
						<div class="form-group row">
							<label for="createdby" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Created By</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="createdby">
							</div>
						</div>
						<div class="form-group row">
							<label for="modifieddate" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Modified Date</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="modifieddate">
							</div>
						</div>
						<div class="form-group row">
							<label for="modifiedby" class="col-sm-2 col-form-label"
								style="font-weight: bold;">Modified By</label>
							<div class="col-sm-10">
								<input type="text" readonly="readonly" style="outline: none"
									class="form-control-plaintext" id="modifiedby">
							</div>
						</div>
						<button type="button" class="btn btn-primary"
							style="width: 100px; margin: 0 auto; position: relative;"
							onclick="redirect()">Update</button>
					</form>
					<script type="text/javascript"
						src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
					<script type="text/javascript">
						var s = window.location.href;
						var myString = s.substr(s.indexOf("=") + 1);
						if (s.includes('=')) {
							getData();
						} else {
							window.location.href = "admin-users";
						}
						function redirect() {
							 window.location.href = "admin-edit-user?id="
									+ myString;
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
											document.getElementById("id").value = result.id;
											document.getElementById("fullname").value = result.fullname;
											document.getElementById("birthday").value = result.birthday;
											document.getElementById("address").value = result.address;
											document
													.getElementById("telephone").value = result.telephone;
											document.getElementById("email").value = result.email;
											document.getElementById("password").value = result.password;
											document.getElementById("rolename").value = result.roleName;
											document.getElementById("deleted").value = result.deleted;
											document
													.getElementById("createddate").value = result.createdDate;
											document
													.getElementById("createdby").value = result.createdBy;
											document
													.getElementById("modifieddate").value = result.modifiedDate;
											document
													.getElementById("modifiedby").value = result.modifiedBy;
											/* String imageOutput = result.imageFile;
											var image = new Image();
											image.src = 'data:image/png;base64,';
											document.body.appendChild(image);
										
											 */
											 var base = "data:image/png;base64,";
											 document.getElementById("avatar").src = "data:image/png;base64," + result.imageFile;

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