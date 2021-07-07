package com.mzdx.controller.provider;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.mzdx.pojo.Provider;
import com.mzdx.pojo.User;
import com.mzdx.service.provider.ProviderService;
import com.mzdx.tools.Constants;
import com.mzdx.tools.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    //删除
    @RequestMapping("/delProvider")
    public void delProvider(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String id=request.getParameter("proid");
        HashMap<String,String> resultMap=new HashMap<>();
        if(!StringUtils.isNullOrEmpty(id)){
            Provider provider = providerService.getProviderById(id);
            int i = providerService.deleteProviderById(id);

/*            System.out.println("id是："+id);
            System.out.println("查询到的供应商是："+provider);*/
            if(i==0){
                providerService.addToRecycle(provider);
             //删除成功
             resultMap.put("delResult","true");
         }else if(i==1){
             //删除失败
         }else if (i>0){
             //该供应商下有订单不能删除，返回一个订单数目
             resultMap.put("delResult",String.valueOf(i));
         }
        }else{
            resultMap.put("delResult", "notexit");
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }
    //根据id进入修改页面
    @RequestMapping("/tomodify")
    public ModelAndView tomodify(String proid){
        Provider provider = providerService.getProviderById(proid);
        ModelAndView mv=new ModelAndView();
        mv.addObject("provider",provider);
        mv.setViewName("jsp/providermodify.jsp");
        return mv;
    }
    //修改操作
    @RequestMapping("provider.modify")
    public String modify(Provider provider){
        providerService.modify(provider);
        return "redirect:/jsp/provider.do";
    }
    //id查询
    @RequestMapping("/getProviderById")
    public void getProviderById(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("proid");
        if(!StringUtils.isNullOrEmpty(id)){
            Provider provider = providerService.getProviderById(id);
            request.setAttribute("provider", provider);
            request.getRequestDispatcher("jsp/providerview.jsp").forward(request, response);
        }
    }
    //添加供应商
    @RequestMapping("/addProvider")
    public void add(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        String proCode = request.getParameter("proCode");
        String proName = request.getParameter("proName");
        String proContact = request.getParameter("proContact");
        String proPhone = request.getParameter("proPhone");
        String proAddress = request.getParameter("proAddress");
        String proFax = request.getParameter("proFax");
        String proDesc = request.getParameter("proDesc");

        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProFax(proFax);
        provider.setProAddress(proAddress);
        provider.setProDesc(proDesc);
        int creatBy=0;
        if((((User)request.getSession().getAttribute(Constants.USER_SESSION)))==null){

        }else {
           ((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId();
        }
        provider.setCreatedBy(creatBy);
        provider.setCreationDate(new Date());
        boolean flag = false;
        flag = providerService.add(provider);
        if(flag){
            response.sendRedirect(request.getContextPath()+"/jsp/provider.do?method=query");
        }else{
            request.getRequestDispatcher("provideradd.jsp").forward(request, response);
        }
    }
    //查询所有供应商
    @RequestMapping("/jsp/provider.do")
    public void getProviderList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String queryProName = request.getParameter("queryProName");
        String queryProCode = request.getParameter("queryProCode");
        if(StringUtils.isNullOrEmpty(queryProName)){
            queryProName = "";
        }else {
            /*queryProName = new String(queryProName.getBytes("ISO-8859-1"), "utf-8");*/
            System.out.println("查询的名字是："+queryProName);
        }


        if(StringUtils.isNullOrEmpty(queryProCode)){
            queryProCode = "";
        }else {
            System.out.println("查询的编码是："+queryProCode);
        }
        String currentPageNo1 = request.getParameter("currentPageNo");
        if (currentPageNo1==null || currentPageNo1==""){
            currentPageNo1 = "0";
        }
        int currentPageNo=0;
        try {
            currentPageNo = Integer.parseInt(currentPageNo1);
        }catch (Exception e){
            currentPageNo = Integer.parseInt("0");
        }

        String pageIndex = request.getParameter("pageIndex");
        int pageSize = Constants.pageSize;
        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            }
        }
        //总数量（表）
        int totalCount = providerService.getProviderCount(queryProName, queryProCode);
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
        try {
            List<Provider> providerList;
            providerList = providerService.getProviderList(queryProName,queryProCode,currentPageNo,pageSize);
            request.setAttribute("providerList", providerList);
            request.setAttribute("queryProName", queryProName);
            request.setAttribute("queryProCode", queryProCode);
            request.setAttribute("totalPageCount", totalPageCount);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("currentPageNo", currentPageNo);
            request.getRequestDispatcher("providerlist.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //查询回收站
    @RequestMapping("/findRecycleList")
    public void getRecycleList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String queryProName = request.getParameter("queryProName");
        String queryProCode = request.getParameter("queryProCode");
        if(StringUtils.isNullOrEmpty(queryProName)){
            queryProName = "";
        }else {
            /*queryProName = new String(queryProName.getBytes("ISO-8859-1"), "utf-8");*/
            System.out.println("查询的名字是："+queryProName);
        }


        if(StringUtils.isNullOrEmpty(queryProCode)){
            queryProCode = "";
        }else {
            System.out.println("查询的编码是："+queryProCode);
        }
        String currentPageNo1 = request.getParameter("currentPageNo");
        if (currentPageNo1==null || currentPageNo1==""){
            currentPageNo1 = "0";
        }
        int currentPageNo = Integer.parseInt(currentPageNo1);
        String pageIndex = request.getParameter("pageIndex");
        int pageSize = Constants.pageSize;
        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
            }
        }
        //总数量（表）
        int totalCount = providerService.getRecycleCount(queryProName, queryProCode);
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
        try {
            List<Provider> recycleList;
            recycleList = providerService.getRecycleList(queryProName,queryProCode,currentPageNo,pageSize);
            request.setAttribute("recycleList", recycleList);
            request.setAttribute("queryProName", queryProName);
            request.setAttribute("queryProCode", queryProCode);
            request.setAttribute("totalPageCount", totalPageCount);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("currentPageNo", currentPageNo);
            request.getRequestDispatcher("/jsp/providerRecycle.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //删除回收站
      @RequestMapping("/delRecycle")
    public void deleteRecycleById(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String id=request.getParameter("proid");
        HashMap<String,String> resultMap=new HashMap<>();
        if(!StringUtils.isNullOrEmpty(id)){
            int i = providerService.deleteRecycleById(id);
            if(i>0){
                //删除成功
                resultMap.put("delResult","true");
            }else{
                resultMap.put("delResult", "notexit");
            }
            //把resultMap转换成json对象输出
            response.setContentType("application/json");
            PrintWriter outPrintWriter = response.getWriter();
            outPrintWriter.write(JSONArray.toJSONString(resultMap));
            outPrintWriter.flush();
            outPrintWriter.close();
        }
      }
      //在回收站查看供应商信息
      @RequestMapping("/getRecycleById")
      public void getRecycleById(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
          String id = request.getParameter("proid");
          if(!StringUtils.isNullOrEmpty(id)){
              Provider provider = providerService.getRecycleById(id);
              request.setAttribute("provider", provider);
              request.getRequestDispatcher("jsp/providerview.jsp").forward(request, response);
          }
      }
      //恢复回收站的数据
    @RequestMapping("/recover")
    public void recover(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("proid");
        if(!StringUtils.isNullOrEmpty(id)){
            //查询当前id返回一个provider
            Provider provider = providerService.getRecycleById(id);
            System.out.println("要恢复的人是："+provider);
            //根据id删除当前供应商
            providerService.deleteRecycleById(id);
            //根据返回的provider 添加到供应商列表
            providerService.add(provider);
            request.getRequestDispatcher("/findRecycleList").forward(request, response);
        }
    }
    //批量删除回收站
    @RequestMapping("/deleteSelect")
    public void deleteSelect(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //获取所有ids
        String[] ids=request.getParameterValues("ids");
        //调用Service删除
        providerService.delSelectedByIds(ids);
        request.getRequestDispatcher("/findRecycleList").forward(request, response);
    }
}
