
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- <main class="mt-5 pt-5"> -->
	<div class="container-fluid px-4">
		<h1 class="mt-4"><spring:message code="comSymPrm.programListManage.pageTop.title" /></h1>
 		<form name="progrmManageForm" action ="<c:url value='/sym/prm/CumsProgramListManageSelect.do' />" method="post">
		<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
		<input name="checkedProgrmFileNmForDel" type="hidden" />
		
		<div class="card mb-4">
			<div class="card-header">
				<div class="input-group mb-3">
				  <input type="text" id="searchKeyword" name="searchKeyword" class="form-control" placeholder="검색어를 입력하세요." aria-label="검색어를 입력하세요." aria-describedby="button-addon2" value="<c:out value='${searchVO.searchKeyword}'/>">
				  <button class="btn btn-outline-secondary" type="button" id="btn_search" onclick="selectProgramListManage();">조회</button>
				</div>
<!-- 				<input type="text" max-length="50" placeholder="검색어를 입력하세요."> -->
<!-- 				<a class="btn btn-primary float-end" href="register"> <i class="fas fa-table me-1"></i> -->
<!-- 					<i class="fas fa-edit"></i>여기는조회 -->
<!-- 				</a> -->
			</div>  
			<div class="card-body">
				<table id="progrmList" class="table table-hover table-striped">
					<colgroup>
						<col style="width:20px" />
						<col style="width:300px" />
						<col style="width:300px" />
						<col style="" />
						<col style="width:350px" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col"><input type="hidden" name="checkAll" class="check2" onclick="fCheckAll();" title="전체선택"  /></th>
							<th><spring:message code="comSymPrm.programListManage.programFileName" /></th>
							<th><spring:message code="comSymPrm.programListManage.programName" /></th>
							<th>URL</th>
							<th><spring:message code="comSymPrm.programListManage.ProgramDescription" /></th>
						</tr>
					</thead>
					<tbody>
						<%-- 데이터를 없을때 화면에 메세지를 출력해준다 --%>
						<c:if test="${fn:length(list_progrmmanage) == 0}">
						<tr>
						<td colspan="5">
							<spring:message code="common.nodata.msg" />
						</td>
						</tr>
						</c:if>
						
						<c:forEach var="result" items="${list_progrmmanage}" varStatus="status">
							<tr>
							    <td>
<!-- 							       <input type="checkbox" name="checkField" class="check2" title="선택"> -->
							       <input name="checkProgrmFileNm" type="hidden" value="<c:out value='${result.progrmFileNm}'/>"/>
							    </td>
							    
								<td>
									<span class="link"><a href="<c:url value='/sym/prm/CumsProgramListDetailSelect.do'/>?tmp_progrmNm=<c:out value="${result.progrmFileNm}"/>"  onclick="selectUpdtProgramListDetail('<c:out value="${result.progrmFileNm}"/>'); return false;">
						            <c:if test="${fn:length(result.progrmFileNm)> 22}">
								    	<c:out value="${fn:substring(result.progrmFileNm,0, 22)}"/>...
								    </c:if>
								    <c:if test="${fn:length(result.progrmFileNm)<= 22}">
								    	<c:out value="${result.progrmFileNm}"/>
								    </c:if>
						            </a></span>
								</td>
								
								<td>
									<c:if test="${fn:length(result.progrmKoreanNm)> 12}">
								    	<c:out value="${fn:substring(result.progrmKoreanNm,0, 12)}"/>...
								    </c:if>
								    <c:if test="${fn:length(result.progrmKoreanNm)<= 12}">
								    	<c:out value="${result.progrmKoreanNm}"/>
								    </c:if>
								</td>
								<td>
									<c:if test="${fn:length(result.URL)> 35}">
								    	<c:out value="${fn:substring(result.URL,0, 35)}"/>...
								    </c:if>
								    <c:if test="${fn:length(result.URL)<= 35}">
								    	<c:out value="${result.URL}"/>
								    </c:if>
								</td>
								<td>
									<c:out value="${result.progrmDc}"/>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<button class="btn btn-primary" id="btn_regist" onclick="insertProgramListManage();">등록</button>
				<!-- paging navigation -->
<!-- 				<div class="pagination"> -->
					<ul class="pagination">
						<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"/>
					</ul>
<!-- 				</div> -->
				
				<input type="hidden" name="cmd">
				<input type="hidden" name="tmp_progrmNm">
			</div>
		</div>
		</form>
	</div>
	
<!-- 	</main> -->

	
	
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script language="javascript1.2" type="text/javaScript">
<!--
/* ********************************************************
 * 모두선택 처리 함수
 ******************************************************** */
function fCheckAll() {
    var checkField = document.progrmManageForm.checkField;
    if(document.progrmManageForm.checkAll.checked) {
        if(checkField) {
            if(checkField.length > 1) {
                for(var i=0; i < checkField.length; i++) {
                    checkField[i].checked = true;
                }
            } else {
                checkField.checked = true;
            }
        }
    } else {
        if(checkField) {
            if(checkField.length > 1) {
                for(var j=0; j < checkField.length; j++) {
                    checkField[j].checked = false;
                }
            } else {
                checkField.checked = false;
            }
        }
    }
}

/* ********************************************************
 * 멀티삭제 처리 함수
 ******************************************************** */
function fDeleteProgrmManageList() {
    var checkField = document.progrmManageForm.checkField;
    var ProgrmFileNm = document.progrmManageForm.checkProgrmFileNm;
    var checkProgrmFileNms = "";
    var checkedCount = 0;
    if(checkField) {
    	if(checkField.length > 1) {
            for(var i=0; i < checkField.length; i++) {
                if(checkField[i].checked) {
                    checkProgrmFileNms += ((checkedCount==0? "" : ",") + ProgrmFileNm[i].value);
                    checkedCount++;
                }
            }
        } else {
            if(checkField.checked) {
            	checkProgrmFileNms = ProgrmFileNm.value;
            }
        }
    }

    if(checkedCount ==0){
		alert("선택된 메뉴가 없습니다.");
		return false;
    }

    if(confirm("<spring:message code="common.delete.msg" />")){	//삭제하시겠습니까?
	    document.progrmManageForm.checkedProgrmFileNmForDel.value=checkProgrmFileNms;
    	document.progrmManageForm.action = "<c:url value='/sym/prm/CumsProgrmManageListDelete.do'/>";
    	document.progrmManageForm.submit();
    }
}

/* ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function linkPage(pageNo){
//	document.menuManageForm.searchKeyword.value =
	document.progrmManageForm.pageIndex.value = pageNo;
	document.progrmManageForm.action = "<c:url value='/sym/prm/EgovProgramListManageSelect.do'/>";
   	document.progrmManageForm.submit();
}

/* ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function selectProgramListManage() { 
	document.progrmManageForm.pageIndex.value = 1;
	document.progrmManageForm.action = "<c:url value='/sym/prm/CumsProgramListManageSelect.do'/>";
	document.progrmManageForm.submit();
}

/* ********************************************************
 * 상세조회처리 함수
 ******************************************************** */
function selectUpdtProgramListDetail(progrmFileNm) {
	document.progrmManageForm.tmp_progrmNm.value = progrmFileNm;
   	document.progrmManageForm.action = "<c:url value='/sym/prm/CumsProgramListDetailSelect.do'/>";
   	document.progrmManageForm.submit();
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
/* ********************************************************
 * 입력 화면 호출 함수
 ******************************************************** */
function insertProgramListManage() {
   	document.progrmManageForm.action = "<c:url value='/sym/prm/CumsProgramListRegist.do'/>";
   	document.progrmManageForm.submit();
// 	location.href="/sym/prm/CumsProgramListRegist.do";
}

/* ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function selectProgramListManage() { 
	document.progrmManageForm.pageIndex.value = 1;
	document.progrmManageForm.action = "<c:url value='/sym/prm/CumsProgramListManageSelect.do'/>";
	document.progrmManageForm.searchKeyword.value = $("#searchKeyword").val();
	document.progrmManageForm.submit();
	
}


</script>