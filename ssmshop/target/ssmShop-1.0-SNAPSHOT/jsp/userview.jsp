<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 用户信息查看页面</span>
    </div>
    <div class="providerView">
     <%--   <div>
            <td>用户头像</td>
            <td>
                <c:if test="${user.pic !=null}">
                    <img src="http://localhost:8086/ps/${user.pic}" width=100 height=100/>
                    <br/>
                </c:if>
            </td>
        </div>--%>
        <p><strong>用户头像：</strong><span>
            <c:if test="${user.pic!=null and user.pic!=''}">
             <img src=${pageContext.request.contextPath }/pic/${user.pic} width=100 style="width:100px; height:100px; border-radius:50%"/></span></p>
                </c:if>
        <p><strong>用户编号：</strong><span>${user.userCode }</span></p>
        <p><strong>用户名称：</strong><span>${user.userName }</span></p>
        <p><strong>用户性别：</strong>
            <span>
            		<c:if test="${user.gender == 1 }">男</c:if>
					<c:if test="${user.gender == 2 }">女</c:if>
				</span>
        </p>
        <p><strong>出生日期：</strong><span>${user.birthday }</span></p>
        <p><strong>用户电话：</strong><span>${user.phone }</span></p>
        <p><strong>用户地址：</strong><span>${user.address }</span></p>
        <p><strong>用户角色：</strong><span>${user.userRoleName}</span></p>
        <div class="providerAddBtn">
            <input type="button" id="back" name="back" value="返回" >
        </div>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userview.js"></script>