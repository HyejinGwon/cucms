<%--
 /**
  * @Class Name : CumsProgramListRegist.jsp
  * @Description : 프로그램목록 등록 화면
  * @Modification Information
  * @
  * @  수정일       수정자             수정내용
  * @ ----------   --------   ---------------------------
  *	  2023.03.23    권혜진	   all-in-one 커스터마이징
  *
  */
  /* Image Path 설정 */
  //String imagePath_icon   = "/images/egovframework/com/sym/prm/icon/";
 // String imagePath_button = "/images/egovframework/com/sym/prm/button/";
--%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- <%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %> --%>
<%-- <c:set var="ImgUrl" value="/images/egovframework/com/sym/prm/"/> --%>
<%-- <c:set var="CssUrl" value="/css/egovframework/com/sym/prm/"/> --%>
<%-- <link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css"> --%>
<%-- <link href="<c:url value="/css/egovframework/com/button.css"/>" rel="stylesheet" type="text/css"> --%>
<c:set var="vprogrmFileNm"><spring:message code="comSymPrm.programListRegist.progrmFileNm"/></c:set>
<c:set var="vprogrmStrePath"><spring:message code="comSymPrm.programListRegist.progrmStrePath"/></c:set>
<c:set var="vprogrmKoreanNm"><spring:message code="comSymPrm.programListRegist.progrmKoreanNm"/></c:set>
<c:set var="vprogrmDc"><spring:message code="comSymPrm.programListRegist.progrmDc"/></c:set>
<c:set var="vurl"><spring:message code="comSymPrm.programListDetailSelectUpdt.url"/></c:set>

<main class="mt-5 pt-5">
	<div class="container-fluid px-4">
		<h1 class="mt-4"><spring:message code="comSymPrm.programListRegist.pageTop.title"/></h1>
		<div class="card mb-4">
			<div class="card-body">
				<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>
				<form id="progrmInfo" method="post">
				<!-- 검색조건 유지 -->
				    <input type="hidden" name="searchCondition" value="<c:out value='${searchVO.searchCondition}'/>"/>
				    <input type="hidden" name="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>"/>
				    <input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}' default='1' />"/>
				
					<div class="mb-3 mt-3">
						<label for="progrmFileNm" class="form-label"><spring:message code="comSymPrm.programListDetailSelectUpdt.progrmFileNm"/></label> 
						<input type="text" class="form-control" id="progrmFileNm" name="progrmFileNm" maxlength="50"  title="${vprogrmFileNm}" ><!-- 프로그램파일명 -->
					</div>
					<div class="mb-3">
						<label for="progrmStrePath" class="form-label"><spring:message code="comSymPrm.programListDetailSelectUpdt.progrmStrePath"/></label> 
						<input type="text" class="form-control" id="progrmStrePath" name="progrmStrePath" maxlength="60" title="${vprogrmStrePath}" ><!-- 저장경로 -->
					</div>
					<div class="mb-3">
						<label for="content" class="form-label"><spring:message code="comSymPrm.programListDetailSelectUpdt.progrmKoreanNm"/></label>
						<input type="text" class="form-control" id="progrmKoreanNm" name="progrmKoreanNm" maxlength="60" title="${vprogrmKoreanNm}" ><!-- 한글명 -->
					</div>
					<div class="mb-3">
						<label for="progrmUrl" class="form-label"><spring:message code="comSymPrm.programListDetailSelectUpdt.url"/></label>
						<input type="text" class="form-control" id="URL" name="URL" maxlength="60" title="${vurl}" ><!-- URL -->
					</div>
					<div class="mb-3">
						<label for="writer" class="form-label"><spring:message code="comSymPrm.programListDetailSelectUpdt.progrmDc"/></label>
						<input type="textarea" class="form-control" id="progrmDc" name="progrmDc" title="${vprogrmDc}" ><!-- 프로그램설명 -->
						<input name="cmd" type="hidden" value="<c:out value='insert'/>"/>
					</div>
					<button class="btn btn-outline-secondary" id="btn_list"><spring:message code="button.list"/></button>
					<button class="btn btn-outline-success" id="btn_regist"><spring:message code="button.save" /></button>
<%-- 					<button class="btn btn-outline-danger" id="btn_delete"><spring:message code="button.delete" /></button> --%>
				</form>
			</div>
		</div>
	</div>
</main>

<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript">

/* ********************************************************
 * 입력 처리 함수
 ******************************************************** */
 $(document).on('click', '#btn_regist', function(e) {
	 var params = {
			  progrmFileNm   : $("#progrmFileNm").val()
			, progrmStrePath : $("#progrmStrePath").val()
			, progrmKoreanNm : $("#progrmKoreanNm").val()
			, URL            : $("#URL").val()
			, progrmDc       : $("#progrmDc").val()
			, flag            : "insert"
	}
    if (confirm("등록하시겠습니까 ?") == true) {
         $("#progrmInfo").attr("action", "/sym/prm/CumsProgramListRegist.do");
         $("#progrmInfo").submit();
		
		
// 		$.ajax({
// 	        url : "/sym/prm/CumsProgramListRegist.do",
// 	        type : "POST",
// 	        dataType : "json",
// 	        data : JSON.stringify(params),
// 	        contentType : "application/json",
// 	        beforeSend : function() {
// 	        },
// 	        success : function(data) {
// 	            console.log(data);
// 	        },
// 	        error : function(request, status, error) {
// 	            alert("list search fail :: error code: "
// 	            + request.status + "\n" + "error message: "
// 	            + error + "\n");
// 	        }
// 	    });
		
    } else {
        return;
    }
 });
 
 /* ********************************************************
  * 목록조회 함수
  ******************************************************** 
 */
 $("#btn_list").click(function previous() {
 	$("#progrmInfo").attr("action", "/sym/prm/CumsProgramListManageSelect.do?");
     $("#progrmInfo").submit();

 });
 
 <!--
/* ********************************************************
 * 목록조회 함수
 ******************************************************** */
function selectList(){
	location.href = "<c:url value='/sym/prm/CumsProgramListManageSelect.do' />";
}

/* ********************************************************
 * focus 시작점 지정함수
 ******************************************************** */
 function fn_FocusStart(){
		var objFocus = document.getElementById('F1');
		objFocus.focus();
	}


<c:if test="${!empty resultMsg}">alert("${resultMsg}");</c:if>
-->
</script>

