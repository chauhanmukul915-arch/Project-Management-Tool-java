package com.pmt.servlet;
import com.pmt.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns={"/dashboard","/projects","/tasks"})
public class AuthFilter implements Filter {
    @Override public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException{
        HttpServletRequest req=(HttpServletRequest)request;HttpServletResponse resp=(HttpServletResponse)response;
        HttpSession s=req.getSession(false);User u=s==null?null:(User)s.getAttribute("user");
        if(u==null){resp.sendRedirect(req.getContextPath()+"/login.html");return;}
        chain.doFilter(request,response);
    }
}
