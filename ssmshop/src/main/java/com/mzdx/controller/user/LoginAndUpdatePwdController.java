package com.mzdx.controller.user;


import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.xdevapi.JsonArray;
import com.mzdx.pojo.User;
import com.mzdx.service.user.UserService;
import com.mzdx.service.user.UserServiceImpl;
import com.mzdx.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginAndUpdatePwdController {
	@Autowired
	private UserService userService;
//登录
	@RequestMapping("/login")
	public String dologin(String userCode, String userPassword,
						  HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		if (userCode != null && userPassword != null){
			User user=userService.getLoginUser(userCode, userPassword);
			if (user!=null){
				request.getSession().setAttribute(Constants.USER_SESSION,user);
				return "/jsp/frame.jsp";
			}else {
				request.setAttribute("error","用户名或密码不正确");
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}

		}
		return null;
	}
	//退出系统
	@RequestMapping("/loginout")
	public String dologinout(HttpServletRequest request,HttpServletResponse response) throws IOException {
		System.out.println("--");
		request.getSession().removeAttribute(Constants.USER_SESSION);
		response.sendRedirect(request.getContextPath()+"/login.jsp");
     return null;
	}
	//修改密码
	@RequestMapping("/jsp/usernewPassword")
	public void updatepwd(HttpServletRequest request,HttpServletResponse response){
		Object object=request.getSession().getAttribute(Constants.USER_SESSION);
		//获取新密码
		String newpassword=request.getParameter("newpassword");
		int updatepwd=0;
		if (object!=null&&!StringUtils.isNullOrEmpty(newpassword)){
			updatepwd=userService.updatePwd(((User)object).getId(),newpassword);
			if (updatepwd>0){
				request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
				// 密码修改成功，移除当前session
				request.getSession().removeAttribute(Constants.USER_SESSION);
			}else {
				request.setAttribute(Constants.SYS_MESSAGE,"修改密码失败！");
			}
		}else {
			//新密码错误
			request.setAttribute(Constants.SYS_MESSAGE,"修改密码失败！");
		}
		try {
			request.getRequestDispatcher("pwdmodify.jsp").forward(request, response);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping("/jsp/user.do")
	public void getPwdByUserId(HttpServletRequest request,HttpServletResponse response){
		Object object=request.getSession().getAttribute(Constants.USER_SESSION);
		//获取旧密码
		String oldpassword=request.getParameter("oldpassword");
		HashMap<String ,String> resultMap=new HashMap<>();
		if (object==null){
			//session过期或失效
			resultMap.put("result", "sessionerror");
		}else if (StringUtils.isNullOrEmpty(oldpassword)){
			resultMap.put("result", "error");
		}else {
			//用户老密码
			String userPassword=((User)object).getUserPassword();
			if (oldpassword.equals(userPassword)){
				resultMap.put("result", "true");
			}else {
				resultMap.put("result", "false");
			}
		}
		try {
			response.setContentType("application/json");
			PrintWriter outPrintWriter = response.getWriter();
			outPrintWriter.write(JSONArray.toJSONString(resultMap));
			outPrintWriter.flush();
			outPrintWriter.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
