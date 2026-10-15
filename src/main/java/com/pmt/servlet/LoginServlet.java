package com.pmt.servlet;
import com.pmt.dao.UserDAO;
import com.pmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO dao=new UserDAO();
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");String email=req.getParameter("email"),password=req.getParameter("password");
        if(email==null||password==null){resp.sendRedirect(req.getContextPath()+"/login.html?error=1");return;}
        try{User user=dao.login(email.trim(),password);
            if(user==null){resp.sendRedirect(req.getContextPath()+"/login.html?error=1");return;}
            HttpSession old=req.getSession(false);if(old!=null)old.invalidate();
            HttpSession session=req.getSession(true);session.setAttribute("user",user);session.setMaxInactiveInterval(1800);
            resp.sendRedirect(req.getContextPath()+"/dashboard");
        }catch(SQLException e){throw new ServletException("Login failed; check database setup.",e);}
    }
}
