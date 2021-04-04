<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>

	<%
		response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.addHeader("Pragma", "no-cache");
	response.addDateHeader("Expires", 0);
	%>
	<div id="layoutError">
		<div id="layoutError_content">
			<main>
				<div class="container">
					<div class="row justify-content-center">
						<div class="col-lg-6">
							<div class="text-center mt-4">
								<h1 class="display-1">401</h1>
								<p class="lead">Unauthorized</p>
								<p>Access to this resource is denied.</p>
								<!-- neu co quyen user thi back ve trang-chu
								neu co quyen admin thi back ve dashboard -->
								<c:if test="${sessionScope.model.roleId == 1 }">
									<a href="admin-users"> <i class="fas fa-arrow-left mr-1"></i>
										Return to Main Page
									</a>
								</c:if>
								<c:if test="${sessionScope.model.roleId == 2 }">
									<a href="web-trang-chu"> <i class="fas fa-arrow-left mr-1"></i>
										Return to Main Page
									</a>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
		<div id="layoutError_footer">
			<footer class="py-4 bg-light mt-auto">
				<div class="container-fluid">
					<div
						class="d-flex align-items-center justify-content-between small">
						<div class="text-muted">Copyright &copy; Your Website 2020</div>
						<div>
							<a href="#">Privacy Policy</a> &middot; <a href="#">Terms
								&amp; Conditions</a>
						</div>
					</div>
				</div>
			</footer>
		</div>
	</div>

</body>
</html>