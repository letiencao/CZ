<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<!--===============================================================================================-->
<link rel="icon" type="image/png"
	href="<c:url value='/template/signup/images/icons/c.shop.png' />" />
<title>C.Shop - Admin</title>
<link
	href="<c:url value='template/admin/css/styles.css" rel="stylesheet' />" />
<link
	href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" crossorigin="anonymous" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"
	crossorigin="anonymous"></script>
<title>Admin Home</title>
</head>
<body class="sb-nav-fixed">
	<%@ include file="/common/admin/header.jsp"%>
	<div id="layoutSidenav">
		<%@ include file="/common/admin/menu.jsp"%>
		<div id="layoutSidenav_content">
			<dec:body />
			<%@ include file="/common/admin/footer.jsp"%>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="<c:url value='/template/admin/js/scripts.js' />"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
		crossorigin="anonymous"></script>
	<script
		src="<c:url value='/template/admin/assets/demo/chart-area-demo.js' />"></script>
	<script
		src="<c:url value='/template/admin/assets/demo/chart-bar-demo.js' />"></script>
	<script
		src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"
		crossorigin="anonymous"></script>
	<script
		src="<c:url value='/template/admin/assets/demo/datatables-demo.js' />"></script>
</body>
</html>
