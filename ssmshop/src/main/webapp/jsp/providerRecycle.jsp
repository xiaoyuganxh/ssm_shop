<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商回收站</span>
        </div>
        <div class="search">
        	<form method="post" action="${pageContext.request.contextPath }/findRecycleList?currentPageNo=">
				<input name="method" value="query" type="hidden">
				<span>供应商编码：</span>
				<input name="queryProCode" type="text" value="${queryProCode }">
				
				<span>供应商名称：</span>
				<input name="queryProName" type="text" value="${queryProName }">
				
				<input value="查 询" type="submit" id="searchbutton">
				<a href="${pageContext.request.contextPath }/jsp/provider.do">回到首页</a>
				<a href="#" id="delSelectBtn">批量删除</a>
			</form>
        </div>
        <!--供应商操作表格-->
	<form id="myForm" action="${pageContext.request.contextPath}/deleteSelect" method="post">
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
				<th><input type="checkbox" id="checkAll"></th>
				<th width="10%">编号</th>
                <th width="10%">供应商编码</th>
                <th width="20%">供应商名称</th>
                <th width="10%">联系人</th>
                <th width="10%">联系电话</th>
                <th width="10%">传真</th>
                <th width="10%">创建时间</th>
                <th width="30%">操作</th>
            </tr>
            <c:forEach var="provider" items="${recycleList }" varStatus="status">
				<tr>
					<td>
					<span><input type="checkbox" name="ids" value="${provider.id}"></span>
					</td>
					<td>
					<span>${status.index+1}</span>
					</td>
					<td>
					<span>${provider.proCode }</span>
					</td>
					<td>
					<span>${provider.proName }</span>
					</td>
					<td>
					<span>${provider.proContact}</span>
					</td>
					<td>
					<span>${provider.proPhone}</span>
					</td>
					<td>
					<span>${provider.proFax}</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${provider.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="recoverProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="恢复" title="恢复"/></a></span>
					<span><a class="deleteRecycle" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
        </table>
	</form>
	<%@include file="/jsp/rollpage.jsp" %>
    </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该供应商吗？</p>
           <a href="#" id="yes">确定</a>
           <a href="#" id="no">取消</a>
       </div>
   </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<%--<script type="text/javascript" src="${pageContext.request.contextPath }/js/providerlist.js"></script>--%>
<script>
    var providerObj;
    function deleteProvider(obj){
        $.ajax({
            type:"GET",
            url:path+"/delRecycle",
            data:{method:"delprovider",proid:obj.attr("proid")},
            dataType:"json",
            success:function(data){
                if(data.delResult == "true"){//删除成功：移除删除行
                    cancleBtn();
                    obj.parents("tr").remove();
                    location.reload();
                }else if(data.delResult == "false"){//删除失败
                    //alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
                    changeDLGContent("对不起，删除供应商【"+obj.attr("proname")+"】失败");
                }else if(data.delResult == "notexist"){
                    //alert("对不起，供应商【"+obj.attr("proname")+"】不存在");
                    changeDLGContent("对不起，供应商【"+obj.attr("proname")+"】不存在");
                }else{
                    //alert("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
                    changeDLGContent("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
                }
            },
            error:function(data){
                //alert("对不起，删除失败");
                changeDLGContent("对不起，删除失败");
            }
        });
    }
    function openYesOrNoDLG(){
        $('.zhezhao').css('display', 'block');
        $('#removeProv').fadeIn();
    }

    function cancleBtn(){
        $('.zhezhao').css('display', 'none');
        $('#removeProv').fadeOut();
    }
    function changeDLGContent(contentStr){
        var p = $(".removeMain").find("p");
        p.html(contentStr);
    }
    $(function(){
        $(".viewProvider").on("click",function(){
//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
            var obj = $(this);
            window.location.href=path+"/getRecycleById?proid="+ obj.attr("proid");
        });

        $(".recoverProvider").on("click",function(){
            var obj = $(this);
            window.location.href=path+"/recover?proid="+ obj.attr("proid");
        });

        $('#no').click(function () {
            cancleBtn();
        });

        $('#yes').click(function () {
            deleteProvider(providerObj);
        });

        $(".deleteRecycle").on("click",function(){
            providerObj = $(this);
            changeDLGContent("你确定要删除供应商【"+providerObj.attr("proname")+"】吗？");
            openYesOrNoDLG();
        });
    });

   //复选框全选功能
    window.onload = function () {
        //1.获得全选chckbox框
        var allCheckBox = document.getElementById("checkAll");
        //2.获得数据列表向的checkbox框
        var checkBoxArrary = document.getElementsByName("ids");
        allCheckBox.onclick = function () {
            //3.遍历列表向checkbox
            for (var i = 0; i < checkBoxArrary.length; i++) {
                checkBoxArrary[i].checked = allCheckBox.checked;
            }
        }
        //1.获得删除选中按钮
        document.getElementById("delSelectBtn").onclick = function () {
            if (confirm("你确定要删除这些记录吗?")) {
                //2.提交表单数据
                //3.获得表单  提交表单 submit() 提交表单方法
                //判断有没有 checkbox框处于选中
                var flag = false;
                for (var i = 0; i < checkBoxArrary.length; i++) {
                    if (checkBoxArrary[i].checked) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    document.getElementById("myForm").submit();
                }else {
                    alert("请至少选择一个再删除！！")
				}

            }

        }




    }
    function batchDeleteUser() {
        //获得表单
        var myForm = document.getElementById("myForm");
        //提交表单
        myForm.submit();
    }
</script>