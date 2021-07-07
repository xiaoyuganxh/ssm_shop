<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	
</script>
</head>
<body>

 		<div class="page-bar">
			<ul class="page-num-ul clearfix">
				<%--${param.currentPageNo }/--%>

				<li>共${totalCount }条记录&nbsp;&nbsp;共 ${totalPageCount }页，当前是第${currentPageNo+1}页</li>&nbsp;&nbsp;
					<c:if test="${currentPageNo > 0}">
						<a href="javascript:page_nav(document.forms[0],0);">首页</a>
						<a href="javascript:page_nav(document.forms[0],${currentPageNo-1});">上一页</a>
					</c:if>
					<c:if test="${currentPageNo < totalPageCount-1 }">
						<a href="javascript:page_nav(document.forms[0],${currentPageNo+1});">下一页</a>
						<a href="javascript:page_nav(document.forms[0],${totalPageCount}-1);">最后一页</a>
					</c:if>
					&nbsp;&nbsp;
			</ul>
			<span class="page-go-form"><label>跳转至</label>
			<input value="${totalPageCount}" type="hidden" id="totalPageCount">
	     <input type="text" name="inputPage" id="inputPage" class="page-key" />页
	     <button type="button" class="page-btn" onClick='jump_to(document.forms[0],document.getElementById("inputPage").value)'>GO</button>
		</span>
			<%--<form action="${pageContext.request.contextPath }/user.do.queryuser" id="form1">
		 <span class="page-go-form"><label>跳转至</label>
			 <input value="${totalPageCount}" type="hidden" id="totalPageCount">
			 <input  type="hidden" name="currentPageNo" id="currentPageNo1">
			 <input value="${totalCount}" type="hidden" id="totalCount">
			 <input value="${userCode}" type="hidden" name="userCode">
			 <input value="${queryUserName}" type="hidden" name="queryname">

	     <input type="text" name="inputPage" id="inputPage" class="page-key" value="${inputPage}"/>页
	     <button type="button"  class="page-btn" onClick='jump_to(document.getElementById("inputPage").value)'>GO</button>
		</span>

			</form>--%>
		</div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rollpage.js"></script>
</html>