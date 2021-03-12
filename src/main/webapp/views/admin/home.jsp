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

	<%
		response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.addHeader("Pragma", "no-cache");
	response.addDateHeader("Expires", 0);
	%>

	<main>
		<div class="container-fluid">
			<h4 class="mt-4" style="font-size: 18px">Quản Lý Người Dùng</h4>
			<c:if test="">
				<div class="hello" id="alert-success" class="alert alert-primary"
					role="alert" hidden="">Delete User Success</div>
			</c:if>
			<div class="card mb-4">
				<div class="card-body">
					<button class="btn btn-primary" onclick="buttonAdd()"
						style="margin-bottom: 10px; float: right; margin-left: 20px; font-size: 12px">Add
						User</button>
					<div class="table-responsive" style="font-size: 12px">

						<table class="table table-hover" id="dataTable"
							style="position: relative; top: 0; left: 0; bottom: 0; font-size: 13px;"
							cellspacing="0">
							<thead>
								<tr>
									<th data-sortable="false">ID</th>
									<th data-sortable="false">Fullname</th>
									<!-- <th data-sortable="false">Birthday</th>
									<th data-sortable="false">Address</th> -->
									<th data-sortable="false">Telephone</th>
									<th data-sortable="false">Email</th>
									<th data-sortable="false">Password</th>
									<th data-sortable="false">Role Name</th>
									<th data-sortable="false">Deleted</th>
									<!-- <th data-sortable="false">Created Date</th>
									<th data-sortable="false">Created By</th>
									<th data-sortable="false">Modified Date</th>
									<th data-sortable="false">Modified By</th> -->
									<th data-sortable="false">Doing</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>ID</th>
									<th>Fullname</th>
									<!-- <th>Birthday</th>
									<th>Address</th> -->
									<th>Telephone</th>
									<th>Email</th>
									<th>Password</th>
									<th>Role Name</th>
									<th>Deleted</th>
									<!-- <th>Created Date</th>
									<th>Created By</th>
									<th>Modified Date</th>
									<th>Modified By</th> -->
									<th>Doing</th>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach var="item" items="${models}">
									<tr>

										<td>${item.id}</td>
										<td>${item.fullname}</td>
										<%-- <td>${item.birthday}</td>
										<td>${item.address}</td> --%>
										<td>${item.telephone}</td>
										<td>${item.email}</td>
										<td>${item.password}</td>
										<td>${item.roleName}</td>
										<td>${item.deleted}</td>
										<%-- <td>${item.createdDate}</td>
										<td>${item.createdBy}</td>
										<td>${item.modifiedDate}</td>
										<td>${item.modifiedBy}</td> --%>
										<td>
											<div class="btn-group mr-2" role="group"
												aria-label="First group">
												<button id="1" type="button" class="btn btn-info"
													style="font-size: 12px">Details</button>
												<button id="2" type="button" class="btn btn-success"
													style="font-size: 12px">Update</button>
												<button id="1" type="button" class="btn btn-warning"
													style="font-size: 12px">Delete</button>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!-- Script -->
						<script type="text/javascript"
							src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
						<script type="text/javascript">
							function buttonAdd() {
								window.location.href = "admin-insert-user";
							}
							$(".btn-info")
							.click(
									function(e) {
										var $tr = $(this).closest('tr');
										var rowData = $('#dataTable')
												.DataTable().row($tr)
												.data();
										if (rowData != null) {
											window.location.href = "admin-details-user?id="
													+ rowData[0];
										}
									});

							$(".btn-success")
									.click(
											function(e) {
												var $tr = $(this).closest('tr');
												var rowData = $('#dataTable')
														.DataTable().row($tr)
														.data();
												if (rowData != null) {
													window.location.href = "admin-edit-user?id="
															+ rowData[0];
												}
											});
							$(".btn-warning").click(
									function(e) {
										var $tr = $(this).closest('tr');
										var rowData = $('#dataTable')
												.DataTable().row($tr).data();
										var str1 = "true";
										var n = str1.localeCompare(rowData[8]);
										if (n == 0) {
											alert("deleted");
										} else {
											deleteUser(rowData[0]);
										}
									});
							function deleteUser(data) {
								var obj = {
									id : data
								};
								$.ajax({

									url : 'update-user-api',
									type : 'DELETE',
									contentType : 'application/json',
									data : JSON.stringify(obj),
									dataType : 'json',

									success : function(result) {
										//alert('hello');
										window.location.href = "admin-user?deleteUser=success";
									},
									error : function(error) {
										alert('error');
									}
								});
							}
						</script>
					</div>
				</div>
			</div>
		</div>
	</main>
</body>

</html>