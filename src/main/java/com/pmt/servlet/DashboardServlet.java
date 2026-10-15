package com.pmt.servlet;
import com.pmt.dao.ProjectDAO;
import com.pmt.dao.TaskDAO;
import com.pmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final ProjectDAO projects=new ProjectDAO();private final TaskDAO tasks=new TaskDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        User u=(User)req.getSession(false).getAttribute("user");
        try{req.setAttribute("projects",projects.findByOwner(u.getId()));req.setAttribute("tasks",tasks.findByOwner(u.getId()));req.getRequestDispatcher("/dashboard.jsp").forward(req,resp);}
        catch(SQLException e){throw new ServletException("Could not load dashboard.",e);}
    }
}
