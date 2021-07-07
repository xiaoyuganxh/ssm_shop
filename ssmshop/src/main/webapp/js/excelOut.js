$(function(){
    /*全选全不选*/
    $("#checkAllInput").change(function(){
        if ($("#checkAllInput").is(":checked")){
            $("input[name='billId']").each(function(){
                $(this).attr("checked","true");
            });
        }else {
            $("input[name='billId']").each(function(){
           /* $(this).attr("checked","false");*/
           $(this).removeAttr("checked");
            })
        }
    });
    /*获取选中的并把数据转成json*/
    $("#outExcel").on("click",function(){
        var json;
        /*检查是否有选中*/
        var flag=false;
        $("input[name='billId']").each(function () {
            if($(this).is(":checked")){
                flag=true;
            }

        });
        /*有，则开始拼接json字符串*/
        if (flag) {
            json = '[';
            $("input[name='billId']:checked").each(function () {
                json = json + '{'+'"billCode":' + '"' + $(this).parent().nextAll().eq(0).children().eq(0).text() + '"' + ',';
                json = json + '"productName":' + '"' + $(this).parent().nextAll().eq(1).children().eq(0).text() + '"' + ',';
                json = json + '"providerName":' + '"' + $(this).parent().nextAll().eq(2).children().eq(0).text() + '"' + ',';
                json = json + '"totalPrice":' + '"' + $(this).parent().nextAll().eq(3).children().eq(0).text() + '"' + ',';
                var isPay = $.trim($(this).parent().nextAll().eq(4).children().eq(0).text());
                json = json + '"isPayment":' + '"' + isPay + '"' + ',';
                var createDate = $.trim($(this).parent().nextAll().eq(5).children().eq(0).text());
                json = json + '"creationDate":' + '"' + createDate + '"';
                json = json + '},'
            });
            json = json.substring(0, json.length - 1);
            json += ']';
            $("#jsonText").val(json);
            $("#excelForm").attr("action",path+'/bill/outExcel');
            $('input#excelSubmit').trigger('click');
        }else{
            /*没有则提示*/
            alert("至少选择一个");
        }
    });
    $("#outTrueExcel").on("click",function(){
        /*检查是否有选中*/
        var flag=false;
        $("input[name='billId']").each(function () {
            if($(this).is(":checked")){
                flag=true;
            }

        });
        if (flag){
            var idArr="";
            $("input[name='billId']:checked").each(function (){
                idArr=idArr+$(this).val()+',';
            });
            idArr=idArr.substring(0,idArr.length-1);
            $("#jsonText").val(idArr);
            $("#excelForm").attr("action",path+'/bill/outTrueExcel');
            $('input#excelSubmit').trigger('click');
        }else{
            /*没有则提示*/
            alert("至少选择一个");
        }
    });
    $("#fileButton").on("click",function () {
        var fileFlag = false;

        if($("#fileInput").val()!=""){
            fileFlag=true;
        }
        if (fileFlag){
            $('input#fileSubmit').trigger('click');
        }
        if(!fileFlag) {
            alert("请选择好文件!");
            return;
        }
    });

})