<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>回收站</span>
    </div>
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">账户名</th>
            <th width="20%">用户名</th>
            <th width="10%">性别</th>
            <th width="10%">生日</th>
            <th width="15%">电话</th>
            <th width="15%">地址</th>
            <th width="20%">操作</th>
        </tr>
        <c:forEach var="userList" items="${userList}" varStatus="status">
            <tr>
                <td>
                    <span>${userList.userCode}</span>
                </td>
                <td>
                    <span>${userList.userName }</span>
                </td>
                <td>
                    <span>
            		<c:if test="${userList.gender == 1 }">男</c:if>
					<c:if test="${userList.gender == 2 }">女</c:if>
				    </span>

                </td>
                <td>
                    <span>${userList.birthday}</span>
                </td>
                <td>
                    <span>${userList.phone}</span>
                </td>
                <td>
                    <span>${userList.address}</span>
                </td>
                <td>
					<span>
					<fmt:formatDate value="${provider.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
                </td>
                <td>
                    <span><a class="viewUser" href="javascript:;" userid=${userList.id } username=${userList.userName }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyUser" href="javascript:;" userid=${userList.id } username=${userList.userName }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteUser" href="javascript:;" userid=${userList.id } username=${userList.userName }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>

    <c:import url="rollpage.jsp">
        <c:param name="totalCount" value="${totalCount}"/>
        <c:param name="currentPageNo" value="${currentPageNo}"/>
        <c:param name="totalPageCount" value="${totalPageCount}"/>
    </c:import>
    <%--<%@include file="/jsp/rollpage.jsp"%>--%>
</div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain" >
            <p>你确定要永久删除删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/isdelete.js"></script>