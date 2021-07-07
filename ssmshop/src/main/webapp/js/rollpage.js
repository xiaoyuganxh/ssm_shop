function page_nav(frm,num){
    // frm.pageIndex.value = num;
    frm.action += num;
    frm.submit();
}

function jump_to(frm,num){
    //alert(num);
	//验证用户的输入
	var regexp=/^[1-9]\d*$/;
	var totalPageCount = document.getElementById("totalPageCount").value;
	/*var queryname = document.getElementsByName("queryname").values();
	var userCode = document.getElementsByName("userCode").values();*/
	//alert(totalPageCount);
	if(!regexp.test(num)){
		alert("请输入大于0的正整数！");
		return false;
	}else if((num-totalPageCount) > 0){
		alert("请输入小于总页数的页码");
		return false;
	}else{
		//var num1=num-1;
        /*var elementById = document.getElementById("form1");

        var elementById2 = document.getElementById("currentPageNo1");
        $("#currentPageNo1").val(num-1);
        elementById.submit();*/
        /*
        */
        /*elementById2.val(elementById.val());
        elementById.submit();*/
        var num1=num-1;
        page_nav(frm,num1);
	}
}