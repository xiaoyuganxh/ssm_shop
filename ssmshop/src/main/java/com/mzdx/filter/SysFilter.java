package com.mzdx.filter;

import com.mzdx.pojo.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SysFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
//		System.out.println("TestFilter init()===========");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;
		User userSession = (User) rq.getSession().getAttribute("userSession");
		String requestURI = rq.getRequestURI();
		System.out.println(requestURI);
		if (requestURI.contains("login.jsp")||
				requestURI.contains("/error")||
				requestURI.contains("/login")||
				requestURI.contains("/css/")||
				requestURI.contains("/images/")||
				requestURI.contains("/js/")||
				requestURI.contains("/calender/")||
				requestURI.contains("/font/")||
				requestURI.contains("/lang/")) {
			chain.doFilter(request, response);
		} else {
			if (userSession != null) {
				chain.doFilter(request, response);
			} else {
				rp.sendRedirect(rq.getContextPath() + "/error.jsp");
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
//		System.out.println("TestFilter destroy()===========");
	}

}
