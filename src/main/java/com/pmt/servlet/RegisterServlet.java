package com.pmt.servlet;
import com.pmt.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO dao=new UserDAO();
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");
        String name=clean(req.getParameter("name")),email=clean(req.getParameter("email")),password=req.getParameter("password");
        if(name.isBlank()||email.isBlank()||password==null||password.length()<8){resp.sendRedirect(req.getContextPath()+"/register.html?error=invalid");return;}
        try{dao.register(name,email,password);resp.sendRedirect(req.getContextPath()+"/login.html?registered=1");}
        catch(SQLException e){if("23000".equals(e.getSQLState()))resp.sendRedirect(req.getContextPath()+"/register.html?error=exists");else throw new ServletException("Registration failed; check database setup.",e);}
    }
    private String clean(String s){return s==null?"":s.trim();}
}
