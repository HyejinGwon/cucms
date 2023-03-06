<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Cache-Control" content="No-store, No-cache, Must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="-1" />
	<meta http-equiv="Content-Language" content="ko"/>
	<meta http-equiv="imagetoolbar" content="no"/>
	<meta name="robots" content="index,follow" />
	 
	<link rel="shortcut icon" type="image/x-icon" href="" />
	<link rel="apple-touch-icon-precomposed" href="" />
	 
	 
	 
	<!-- Custom fonts for this template-->
    <link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="/css/cums/sb-admin-2.min.css" rel="stylesheet">
    
    <!-- Custom styles for this page -->
    <link href="/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
	<!-- <script src="/js/common/jquery-3.5.1.js"></script> -->
<!-- 	<script src="/js/cums/jquery-1.9.1.js"></script> -->
	 
	<title>씨유소프트 Admin</title>
	 
</head>
 
<body id="page-top">
	<div id="wrapper">    
	    <div id="left"><tiles:insertAttribute name="gnb" /></div>
  
	    <div id="content-wrapper" class="d-flex flex-column">
	    	<!-- Main Content -->
	            <div id="content">
	            	
	                <div id="header"><tiles:insertAttribute name="top" /></div>
	                <div id="body"><tiles:insertAttribute name="content"/>
	                    
	                </div>
	            </div>
	            
	            <div id="footer"><tiles:insertAttribute name="footer"/></div>  
	            
		</div><!-- End of Content Wrapper --> 
		
	</div><!-- End of Page Wrapper -->    
 	
	<!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.do">Logout</a>
                </div>
            </div>
        </div>
    </div>
	    <!-- Bootstrap core JavaScript-->
    <script src="/vendor/jquery/jquery.min.js"></script>
    <script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/js/cums/sb-admin-2.min.js"></script>
        <!-- Page level plugins -->
    <script src="/vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="/vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="/js/cums/demo/datatables-demo.js"></script>
</body>
</html>
 
 
