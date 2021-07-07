<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
       <div class="location">
           <strong>你现在所在的位置是:</strong>
           <span>订单管理页面</span>
       </div>
    <div style="float: left">
        <form action="${pageContext.request.contextPath }/bill/billExcelInput" method="post" enctype="multipart/form-data" style="text-align: right">
            <input	value="简单导出" type="button" id="outExcel">
            <input	value="完整导出" type="button" id="outTrueExcel">
            <input type="button" value="导入文件" id="fileButton">
            <input type="file" name="excelInput" id="fileInput"> <%--此处的id值，需要与对应的处理 MultipartFile 实例参数名一致--%>
            <input type="submit"  style="display: none" id="fileSubmit">
        </form>
    </div>
       <div class="search" style="float: left">
       <form method="post" action="${pageContext.request.contextPath }/getBillList?currentPageNo=">
		   <input name="method" value="query" class="input-text" type="hidden" >
			<span>商品名称：</span>
			<input name="queryProductName" type="text" value="${queryProductName }"style="width: 100px">
			 
			<span>供应商：</span>
			<select name="queryProviderId">
				<c:if test="${providerList != null }">
				   <option value="0">--请选择--</option>
				   <c:forEach var="provider" items="${providerList}">
				   		<option <c:if test="${provider.id == queryProviderId }">selected="selected"</c:if>
				   		value="${provider.id}">${provider.proName}</option>
				   </c:forEach>
				</c:if>
       		</select>
			 
			<span>是否付款：</span>
			<select name="queryIsPayment">
				<option value="0">--请选择--</option>
				<option value="1" ${queryIsPayment == 1 ? "selected=\"selected\"":"" }>未付款</option>
				<option value="2" ${queryIsPayment == 2 ? "selected=\"selected\"":"" }>已付款</option>
       		</select>

		   <span>起始时间：</span>
		   <input type="text" style="width: 60px;height: 28px" Class="Wdate" id="startday" name="startday" value="${startday }"
		   readonly="readonly" onclick="WdatePicker();">
		   <span>结束时间：</span>
		   <input type="text" style="width: 60px;height: 28px" Class="Wdate" id="endday" name="endday" value="${endday }"
				  readonly="readonly" onclick="WdatePicker();">

			 <input	value="查 询" type="submit" id="searchbutton">
			 <a href="${pageContext.request.contextPath }/jsp/billadd.jsp">添加订单</a>
		</form>
       </div>

       <!--账单表格 样式和供应商公用-->
      <table class="providerTable" cellpadding="0" cellspacing="0">
          <tr class="firstTr">
			  <th><input type="checkbox" id="checkAllInput" width="5%"></th>
              <th width="10%">订单编码</th>
              <th width="20%">商品名称</th>
              <th width="10%">供应商</th>
              <th width="10%">订单金额</th>
              <th width="10%">是否付款</th>
              <th width="10%">创建时间</th>
              <th width="25%">操作</th>
          </tr>
          <c:forEach var="bill" items="${billList }" varStatus="status">
				<tr>
					<td><input type="checkbox" name="billId" value="${bill.id}"></td>
					<td>
					<span>${bill.billCode }</span>
					</td>
					<td>
					<span>${bill.productName }</span>
					</td>
					<td>
					<span>${bill.providerName}</span>
					</td>
					<td>
					<span>${bill.totalPrice}</span>
					</td>
					<td>
					<span>
						<c:if test="${bill.isPayment == 1}">未付款</c:if>
						<c:if test="${bill.isPayment == 2}">已付款</c:if>
					</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${bill.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifyBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a class="deleteBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
      </table>
	<%@include file="/jsp/rollpagebill.jsp" %>
</div>
</section>
<%--用于导出excel--%>
<div style="display:none">
	<form  id="excelForm" method="post" target="_blank" action="" >
		<textarea name="billList" id="jsonText" cols="50" rows="50"></textarea>">
		<input type="submit" id="excelSubmit">
	</form>
</div>
<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rollpage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/billlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/excelOut.js"></script>
<script>
    var message ='<%=request.getParameter("errorInfo")%>'
    if (message!=null&message!=""&message!="null") {
        if (message==="successful"){
            alert("导入成功");
        }
        if (message==="rePick"){
            alert("没有选择文件！");
        }
        if (message==="typeError"){
            alert("请选择xl或xlsx文件！");
        }
        if (message==="fail"){
            alert("出现了意料之外的情况，导入失败");
        }
        if (message==="overMax"){
            alert("出文件大小超出限制");
        }
    }
</script>
