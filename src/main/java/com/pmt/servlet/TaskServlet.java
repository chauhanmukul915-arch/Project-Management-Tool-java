package com.pmt.servlet;
import com.pmt.dao.ProjectDAO;
import com.pmt.dao.TaskDAO;
import com.pmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    private final TaskDAO tasks=new TaskDAO();private final ProjectDAO projects=new ProjectDAO();
    private static final Set<String> STATUSES=Set.of("Pending","In Progress","Completed");
    private static final Set<String> PRIORITIES=Set.of("Low","Medium","High");
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        User u=(User)req.getSession(false).getAttribute("user");String action=req.getParameter("action");
        try{
            if("status".equals(action)){
                String status=req.getParameter("status");if(STATUSES.contains(status))tasks.updateStatus(Integer.parseInt(req.getParameter("id")),u.getId(),status);
            }else if("delete".equals(action)){tasks.delete(Integer.parseInt(req.getParameter("id")),u.getId());}
            else{
                String title=clean(req.getParameter("title")),description=clean(req.getParameter("description"));
                String status=clean(req.getParameter("status"));if(status.isBlank())status="Pending";
                String priority=clean(req.getParameter("priority"));if(priority.isBlank())priority="Medium";
                String due=clean(req.getParameter("dueDate"));int projectId=Integer.parseInt(req.getParameter("projectId"));
                if(title.isBlank()||title.length()>150||!STATUSES.contains(status)||!PRIORITIES.contains(priority)||!projects.belongsToOwner(projectId,u.getId())){
                    resp.sendRedirect(req.getContextPath()+"/dashboard?error=task");return;
                }
                tasks.create(title,description,status,priority,due,projectId);
            }
            resp.sendRedirect(req.getContextPath()+"/dashboard?success=1");
        }catch(NumberFormatException e){resp.sendRedirect(req.getContextPath()+"/dashboard?error=task");}
        catch(SQLException e){throw new ServletException("Task operation failed.",e);}
    }
    private String clean(String s){return s==null?"":s.trim();}
}
