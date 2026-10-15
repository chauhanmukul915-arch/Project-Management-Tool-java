package com.pmt.servlet;
import com.pmt.dao.ProjectDAO;
import com.pmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private final ProjectDAO dao=new ProjectDAO();
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        User u=(User)req.getSession(false).getAttribute("user");String action=req.getParameter("action");
        try{
            if("delete".equals(action)){dao.delete(Integer.parseInt(req.getParameter("id")),u.getId());}
            else{
                String name=clean(req.getParameter("name")),description=clean(req.getParameter("description")),deadline=clean(req.getParameter("deadline"));
                if(name.isBlank()||name.length()>150){resp.sendRedirect(req.getContextPath()+"/dashboard?error=project");return;}
                dao.create(name,description,deadline,u.getId());
            }
            resp.sendRedirect(req.getContextPath()+"/dashboard?success=1");
        }catch(NumberFormatException e){resp.sendRedirect(req.getContextPath()+"/dashboard?error=project");}
        catch(SQLException e){throw new ServletException("Project operation failed.",e);}
    }
    private String clean(String s){return s==null?"":s.trim();}
}
