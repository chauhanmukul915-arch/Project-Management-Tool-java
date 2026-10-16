package com.pmt.servlet;

import com.pmt.dao.ProjectDAO;
import com.pmt.dao.TaskDAO;
import com.pmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tasks-page")
public class TasksPageServlet extends HttpServlet {

    private final ProjectDAO projects = new ProjectDAO();
    private final TaskDAO tasks = new TaskDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }

        try {
            req.setAttribute("projects", projects.findByOwner(user.getId()));
            req.setAttribute("tasks", tasks.findByOwner(user.getId()));
            req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}