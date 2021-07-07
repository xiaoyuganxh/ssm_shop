<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面</span>
        </div>
        <div class="search">
        	<form method="post" action="${pageContext.request.contextPath }/user.do.queryuser?currentPageNo=">
				<%--<input name="currentPageNo" value="${currentPageNo}" type="hidden">--%>
				<span>用户编码：</span>
				<input name="userCode" type="text" value="${userCode }">
				<span>用户名称：</span>
				<input name="queryname" type="text" value="${queryUserName }">
				<input value="查 询" type="submit" id="searchbutton">
					<%--<a href="${pageContext.request.contextPath }/getIsdelete">回收站</a>--%>
				<a href="${pageContext.request.contextPath }/jsp/useradd.jsp">添加用户</a>
			</form>
        </div>
        <!--用户操作表格-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="8%">用户编码</th>
                <th width="10%">用户名称</th>
                <th width="7%">用户密码</th>
                <th width="5%">用户性别</th>
                <th width="10%">用户生日</th>
                <th width="10%">用户电话</th>
                <th width="20%">用户地址</th>
				<th width="15%">头像</th>
                <th width="15%">操作</th>

            </tr>
            <c:forEach var="user" items="${userList}" varStatus="status">
				<tr>
					<td>
					<span>${user.userCode }</span>
					</td>
					<td>
					<span>${user.userName }</span>
					</td>
					<td>
					<span>${user.userPassword}</span>
					</td>
					<td>
                    <span>
            		<c:if test="${user.gender == 1 }">男</c:if>
					<c:if test="${user.gender == 2 }">女</c:if>
				    </span>

					</td>
					<td>
						<span>
					<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
						<span>${user.phone}</span>
					</td>
					<td>
						<span>${user.address }</span>
					</td>
					<div>
						<td>
							<c:if test="${user.pic !=null}">
								<img src="${pageContext.request.contextPath }/pic/${user.pic}" style="width:50px; height:50px; border-radius:50%"/>
								<br/>
							</c:if>
						</td>
					</div>
					<td>
					<span><a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
        </table>
	<%@include file="/jsp/rollpage.jsp"%>
    </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该用户吗？</p>
           <a href="#" id="yes">确定</a>
           <a href="#" id="no">取消</a>
       </div>
   </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userlist.js"></script>
