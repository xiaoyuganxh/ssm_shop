package com.mzdx.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.mzdx.pojo.Role;
import com.mzdx.pojo.User;
import com.mzdx.service.role.RoleService;
import com.mzdx.service.user.UserService;
import com.mzdx.tools.Constants;
import com.mzdx.tools.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Controller
public class UserCtrl {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    //删除回收站数据
    @RequestMapping("isdelete")
    public void deleteAllways(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("uid");
        Integer delId = 0;
        try {
            delId = Integer.parseInt(id);
        } catch (Exception e) {
            // TODO: handle exception
            delId = 0;
        }
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (delId <= 0) {
            resultMap.put("delResult", "notexist");
        } else {
            if (userService.deleteALlways(delId)) {
                resultMap.put("delResult", "true");
            } else {
                resultMap.put("delResult", "false");
            }
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json;charset=utf8");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //回收站
    @RequestMapping("getIsdelete")
    public ModelAndView getIsdelete() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userService.getIsdelete();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("jsp/isdelete.jsp");
        return modelAndView;
    }

    //判断账户是否可用
    @RequestMapping(value = "userCodeExist")
    public void userCodeExist(HttpServletRequest request, HttpServletResponse response
    )
            throws IOException {
        String userCode = request.getParameter("userCode");
        //判断用户账号是否可用
        System.out.println("--");
        System.out.println(userCode);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isNullOrEmpty(userCode)) {
            //userCode == null || userCode.equals("")
            resultMap.put("userCode", "exist");
        } else {
            User user = userService.selectUserCodeExist(userCode);
            if (null != user) {
                resultMap.put("userCode", "exist");
            } else {
                resultMap.put("userCode", "notexist");
            }
        }
        //把resultMap转为json字符串以json的形式输出
        //配置上下文的输出类型
        response.setContentType("application/json");
        //从response对象中获取往外输出的writer对象
        PrintWriter outPrintWriter = response.getWriter();
        //把resultMap转为json字符串 输出
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();//刷新
        outPrintWriter.close();//关闭流
    }

    //获取角色列表
    @RequestMapping("getRoleList")
    public void getRoleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Role> roleList = null;
        roleList = userService.getRoleList();
        //把roleList转换成json对象输出
        response.setContentType("application/json;charset=utf8");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(roleList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * 获取所有用户列表
     *
     * @RequestMapping("getAllUser") public ModelAndView getAllUser(){
     * ModelAndView modelAndView =new ModelAndView();
     * List<User> userList = userService.findAll();
     * modelAndView.addObject("userList",userList);
     * modelAndView.setViewName("jsp/userlist.jsp");
     * return modelAndView;
     */


    //查询
    @RequestMapping("user.do.queryuser")
    public void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //查询用户列表
        String currentPageNo1 = request.getParameter("currentPageNo");
        if (currentPageNo1 == null || currentPageNo1 == "") {
            currentPageNo1 = "0";
        }
        int currentPageNo = Integer.parseInt(currentPageNo1);
        String queryUserName = request.getParameter("queryname");
        String userCode = request.getParameter("userCode");

        //String temp = request.getParameter("queryUserRole");
        String pageIndex = request.getParameter("pageIndex");
        int queryUserRole = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        System.out.println("queryUserName servlet--------" + queryUserName);
        System.out.println("queryUserRole servlet--------" + queryUserRole);
        System.out.println("query pageIndex--------- > " + pageIndex);
        if (queryUserName == null) {
            queryUserName = "";
        }

        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp");
            }
        }


        //总数量（表）
        int totalCount = userService.getUserCount(queryUserName, userCode);
        System.out.println("totalCount:" + totalCount);
        if (currentPageNo > totalCount) {
            currentPageNo = totalCount - 1;
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
            currentPageNo = totalPageCount - 1;
        }
        System.out.println("currentPageNo:" + currentPageNo);
        System.out.println("totalPageCount" + totalCount);
//控制首页和尾页
        request.setAttribute("currentPageNo", currentPageNo);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalCount", totalCount);
        userList = userService.getUserList(queryUserName, userCode, currentPageNo, pageSize);
        request.setAttribute("userList", userList);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        request.setAttribute("param", pages);
        request.setAttribute("userCode", userCode);
        request.setAttribute("roleList", roleList);
        request.setAttribute("queryUserName", queryUserName);
        request.setAttribute("queryUserRole", queryUserRole);
        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        request.getRequestDispatcher("jsp/userlist.jsp").forward(request, response);
    }

    @RequestMapping(value = "user.do.view", method = RequestMethod.GET)
    public void getUserView(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("userid") String id) {
        System.out.println(id);
        if (!StringUtils.isNullOrEmpty(id)) {
            //调用后台方法得到user对象
            User user = userService.getUserById(id);
            System.out.println(user);
            request.setAttribute("user", user);
            try {
                request.getRequestDispatcher("jsp/userview.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("getUserById")
    public void getUserById(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "jsp/userview.jsp";
        String id = request.getParameter("uid");

        if (!StringUtils.isNullOrEmpty(id)) {
            //调用后台方法得到user对象
            User user = userService.getUserById(id);
            //System.out.println(user + "*****************************************!!!");
            request.setAttribute("user", user);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    //添加用户
    @RequestMapping("addUser")
    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("add()================");
        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");
        System.out.println(userCode + "-------------------------++");
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        int i = 0;
        if (((User) request.getSession().getAttribute(Constants.USER_SESSION)) == null) {

        } else {
            i = ((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId();
        }
        user.setCreatedBy(i);

        if (userService.add(user)) {
            response.sendRedirect(request.getContextPath() + "/getAllUser");
        } else {
            request.getRequestDispatcher("useradd.jsp").forward(request, response);
        }
    }

    //修改用户数据回显
    @RequestMapping("updateUser")
    public void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "jsp/usermodify.jsp";
        String id = request.getParameter("uid");
        //   System.out.println(id+"-----------------------------------++");
        if (!StringUtils.isNullOrEmpty(id)) {
            //调用后台方法得到user对象
            User user = userService.getUserById(id);
            request.setAttribute("user", user);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    //修改用户
    @RequestMapping("modify")
    public void modify(HttpServletRequest request, HttpServletResponse response, MultipartFile pictureFile)
            throws ServletException, IOException {
        System.out.println(pictureFile + "****-*---------+**********");
        User user = new User();
        String id = request.getParameter("uid");
        String userName = request.getParameter("userName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");
        //图片上传
        System.out.println("-------------"+birthday);
        try {
            if (pictureFile != null) {
                String originalFilename = pictureFile.getOriginalFilename();
                if (originalFilename != null && originalFilename != "") {
                    System.out.println(originalFilename + "++++++++++++");
                    int index = originalFilename.lastIndexOf(".");
                    String suffName = originalFilename.substring(index);
                    String newFIleName = System.currentTimeMillis() + suffName;
                    //构件上传目录
                    String path = "D:\\IDEA\\workspace\\springdome\\ssmshop\\src\\main\\webapp\\pic\\";
                    //String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"pic/";
                    File pathFile = new File(path + newFIleName);
                    //执行图片文件上传
                    pictureFile.transferTo(pathFile);
                    //设置图片名
                    user.setPic(newFIleName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setGender(Integer.valueOf(gender));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        Date parse=null;
        Date date;
        if (birthday.length()==10){
            try {
                parse = sdf.parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            try {
                date = sdf1.parse(birthday);
                sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                String format = sdf1.format(date);
                 parse = sdf1.parse(format);

                //System.out.println(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        /*try {
            date = sdf.parse(birthday);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);
            Date parse = sdf.parse(format);
            user.setBirthday(parse);

            //System.out.println(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        user.setBirthday(parse);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));
        int i = 0;
        if (((User) request.getSession().getAttribute(Constants.USER_SESSION)) == null) {

        } else {
            i = ((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId();
        }
        user.setCreatedBy(i);

        user.setModifyDate(new Date());
        if (userService.modify(user)) {

            response.sendRedirect(request.getContextPath() + "/user.do.queryuser");
        } else {
            request.getRequestDispatcher("usermodify.jsp").forward(request, response);
        }

    }

    //删除用户数据
    @RequestMapping("delUser")
    public void delUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("uid");
        //存入回收站
        if (!StringUtils.isNullOrEmpty(id)) {
            User user = userService.getUserById(id);
            System.out.println(user);
            userService.isdelete(user);
        }

        Integer delId = 0;
        try {
            delId = Integer.parseInt(id);
        } catch (Exception e) {
            // TODO: handle exception
            delId = 0;
        }
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (delId <= 0) {
            resultMap.put("delResult", "notexist");
        } else {
            if (userService.deleteUserById(delId)) {
                resultMap.put("delResult", "true");
            } else {
                resultMap.put("delResult", "false");
            }
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json;charset=utf8");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * 文件上传
     */
    @RequestMapping("/getFile")
    @ResponseBody
    public String getFile(String username, Part file) {
        //将文件写到d盘根目录
        try {

            String s = file.getHeader("Content-Disposition");
            //分析字符串，获取上传的文件名称
            s = s.substring(0, s.length() - 1);
            String fileName = s.substring(s.lastIndexOf("\"") + 1, s.length());
            file.write("E://TestAll/" + fileName);
            //清楚上传的临时文件
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "文件上传成功！";
    }

    @RequestMapping("testfile")
    public void test(
            HttpServletRequest request,
            @RequestParam(value = "x") String x,
            @RequestParam(value = "y") String y,
            @RequestParam(value = "h") String h,
            @RequestParam(value = "w") String w,
            @RequestParam(value = "uid") String id,
            @RequestParam(value = "imgFile") MultipartFile imageFile){
        System.out.println(x);
        System.out.println(y);
        System.out.println(h);
        System.out.println(w);
        int imageX = Integer.parseInt(x);
        int imageY = Integer.parseInt(y);
        int imageH = Integer.parseInt(h);
        int imageW = Integer.parseInt(w);
    }
}

