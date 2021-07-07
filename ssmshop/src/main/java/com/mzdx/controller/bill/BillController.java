package com.mzdx.controller.bill;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.mzdx.pojo.Bill;
import com.mzdx.pojo.Provider;
import com.mzdx.pojo.User;
import com.mzdx.service.bill.BillService;
import com.mzdx.service.provider.ProviderService;
import com.mzdx.tools.Constants;
import com.mzdx.tools.PageSupport;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;

    //查询所有订单列表
    @RequestMapping("getBillList")
    public String getList(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        //request.setCharacterEncoding("UTF-8");
        try{
            System.out.println("----获取订单列表----");
            List<Provider> providerList = providerService.getProviderList("","",-1,-1);
            model.addAttribute("providerList",providerList);
            //查询条件
            String queryProductName = request.getParameter("queryProductName");
            String queryProviderId = request.getParameter("queryProviderId");
            String queryIsPayment = request.getParameter("queryIsPayment");
            String startday = request.getParameter("startday");
            String endday = request.getParameter("endday");
            System.out.println("-----------------------"+startday+"1");
            System.out.println("-----------------------"+endday);
            //如果产品名为空或空字符串则替换为空字符串
            if(StringUtils.isNullOrEmpty(queryProductName)){
                queryProductName = "";
            }
            //queryProductName = new String(queryProductName.getBytes("ISO-8859-1"), "utf-8");
            Bill bill = new Bill();
            //不为空或空字符串就解析，否则设置为0
            if(StringUtils.isNullOrEmpty(queryIsPayment)){
                bill.setIsPayment(0);
                System.out.println("----查询条件未选择是否付款");
            }else{
                bill.setIsPayment(Integer.parseInt(queryIsPayment));
            }
            if(StringUtils.isNullOrEmpty(queryProviderId)){
                bill.setProviderId(0);
                System.out.println("----查询条件未选择供应商");
            }else{
                bill.setProviderId(Integer.parseInt(queryProviderId));
            }
            bill.setProductName(queryProductName);
            String currentPageNo1 = request.getParameter("currentPageNo");
            if (currentPageNo1==null || currentPageNo1==""){
                currentPageNo1 = "0";
            }
            int currentPageNo=0;
            try {
                currentPageNo = Integer.parseInt(currentPageNo1);
            } catch (Exception e){
                currentPageNo = Integer.parseInt("0");
            }

            String pageIndex = request.getParameter("pageIndex");
            int pageSize = Constants.pageSize;
            if (pageIndex != null) {
                try {
                    currentPageNo = Integer.valueOf(pageIndex);
                } catch (NumberFormatException e) {
                    return "jsp/error.jsp";
                }
            }
            //总数量（表）
            int totalCount = billService.billCount(bill, startday, endday);
            System.out.println("totalCount:"+totalCount);
            if (currentPageNo>totalCount){
                currentPageNo=totalCount-1;
            }
            //总页数
            PageSupport pages = new PageSupport();
            pages.setCurrentPageNo(currentPageNo);
            pages.setPageSize(pageSize);
            pages.setTotalCount(totalCount);
            int totalPageCount = pages.getTotalPageCount();


            if (currentPageNo < 0) {
                currentPageNo = 0;
            } else if (currentPageNo > totalPageCount) {
                currentPageNo = totalPageCount-1;
            }
            System.out.println("currentPageNo:"+currentPageNo);
            System.out.println("totalPageCount"+totalCount);

            System.out.println("----条件查询"+bill);
/*        System.out.println("----查询条件----");
        System.out.println("----商品名称"+queryProductName);
        System.out.println("----供应商id"+queryProviderId);
        System.out.println("----是否付款"+queryIsPayment);*/
            System.out.println("----起始时间"+startday);
            System.out.println("----结束时间"+endday);
            List<Bill> billList = billService.getBillList(bill,startday,endday, currentPageNo, pageSize);
            model.addAttribute("billList",billList);
            model.addAttribute("queryProductName", queryProductName);
            model.addAttribute("queryProviderId", queryProviderId);
            model.addAttribute("queryIsPayment", queryIsPayment);
            model.addAttribute("startday", startday);
            model.addAttribute("endday", endday);
            model.addAttribute("totalPageCount", totalPageCount);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currentPageNo", currentPageNo);
            if (billList!=null){
                return "jsp/billlist.jsp";
            }else {
                return "jsp/error.jsp";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "jsp/error.jsp";
        }


    }

    //添加订单
    @RequestMapping("addBill")
    public String add(Bill bill,HttpServletRequest request){
        try{
            bill.setCreatedBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
            bill.setCreationDate(new Date());
            System.out.println("----待添加订单"+bill);
            int i = billService.add(bill);
            if (i>0){
                System.out.println("添加成功");
                return "redirect:getBillList";
            }else {
                System.out.println("添加失败");
                return "forward:jsp/billadd.jsp";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "jsp/error.jsp";
        }

    }

    //根据订单id查询订单
    @RequestMapping("getBillById")
    public String getBillById(Model model,Integer billid){
        try{
            System.out.println("----待查看订单id"+billid);
            Bill billById = billService.getBillById(billid);
            if (billById!=null){
                model.addAttribute("bill",billById);
                return "jsp/billview.jsp";
            }else {
                return "jsp/error.jsp";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "jsp/error.jsp";
        }

    }

    //根据订单id删除订单
    @RequestMapping("deleteBillById")
    public void deleteBillById(Integer billid,HttpServletResponse response) throws IOException {
        System.out.println("----待删除订单id"+billid);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(billid!=null){
            int i = billService.deleteBillById(billid);
            if(i>0){//删除成功
                resultMap.put("delResult", "true");
                System.out.println("----删除成功----");
            }else{//删除失败
                resultMap.put("delResult", "false");
                System.out.println("----删除失败----");
            }
        }else{
            resultMap.put("delResult", "notexit");
            System.out.println("----无此订单----");
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //根据id查询要修改的订单信息并回显
    @RequestMapping("modifygetId")
    public String modifygetId(Model model,Integer billid){
        try {
            System.out.println("----待修改订单id"+billid);
            Bill billById = billService.getBillById(billid);
            System.out.println("----待修改订单信息"+billById);
            if (billById!=null){
                model.addAttribute("bill",billById);
                return "jsp/billmodify.jsp";
            }else {
                return "jsp/error.jsp";
            }
        }catch (Exception e){
            return "jsp/error.jsp";
        }

    }

    //修改订单信息
    @RequestMapping("modifyBillById")
    public String modifyBillById(Bill bill,HttpServletRequest request){
        try {
            bill.setModifyBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
            bill.setModifyDate(new Date());
            int i = billService.modify(bill);
            if (i>0){
                System.out.println("----修改后订单信息"+bill);
                return "redirect:getBillList";
            }else {
                System.out.println("----修改失败");
                return "forward:modifygetId";
            }
        }catch (Exception e){
            return "jsp/error.jsp";
        }

    }

    //查询供应商列表
    @RequestMapping("getProviderlist")
    private void getProviderlist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("----获取供应商列表----");
        List<Provider> providerList = providerService.getProviderList("","",-1,-1);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(providerList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }
    @RequestMapping("/bill/outExcel")
    public  void outExcel(String billList , HttpServletResponse response)  {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh：mm：ss");

        SXSSFWorkbook workbook = billService.getSimpleExcel(billList);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.reset();  //清空输出六
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");
            //response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=fileOut.xlsx");
            response.setHeader("Pargam", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @RequestMapping("/bill/outTrueExcel")
    public  void outTrueExcel(String billList , HttpServletResponse response)  {

//        查询订单表
        String[] billStr=billList.split(",");
        List<Bill> bills=billService.getBillByIds(billStr);
        SXSSFWorkbook workbook=billService.getBillExcel(bills);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.reset();  //清空输出
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");
            //response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=fileOut.xlsx");
            response.setHeader("Pargam", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @RequestMapping("/bill/billExcelInput")
    public String billExcelInput(MultipartFile excelInput) throws IOException {
        String errorInfo="successful";
        String excelType=null;
        String suffix=".xls";
        String suffix1=".xlsx";
        if(excelInput.getSize()==0){
            errorInfo="rePick";
            return "redirect:/bill/queryBills?errorInfo="+errorInfo;
        }
        String fileType=excelInput.getOriginalFilename();
        boolean flagxls=false;
        boolean flagxlsx=false;
        boolean flag=false;
        if(flagxls=fileType.endsWith(suffix)){
            excelType=suffix;
        }
        if(flagxlsx=fileType.endsWith(suffix1)){
            excelType=suffix1;
        }
        if (flagxls|flagxlsx){
            flag=true;
        }
        if (!flag){
            errorInfo="typeError";
            return "redirect:/getBillList?errorInfo="+errorInfo;
        }
        try {
            billService.billExcelInput(excelInput,excelType);
        }catch (Exception e){
            e.printStackTrace();
            errorInfo="fail";
            return "redirect:/getBillList?errorInfo="+errorInfo;
        }
        return "redirect:/getBillList?errorInfo="+errorInfo;
    }

}
