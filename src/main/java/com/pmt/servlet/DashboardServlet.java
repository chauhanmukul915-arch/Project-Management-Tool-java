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

    private final ProjectDAO projects = new ProjectDAO();
    private final TaskDAO tasks = new TaskDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User u = session == null ? null : (User) session.getAttribute("user");

        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }

        try {
            req.setAttribute("projects", projects.findByOwner(u.getId()));
            req.setAttribute("tasks", tasks.findByOwner(u.getId()));

            req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Could not load dashboard.", e);
        }
    }
}