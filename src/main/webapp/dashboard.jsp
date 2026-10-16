<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.pmt.model.User,com.pmt.model.Project,com.pmt.model.Task" %>

<%!
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>

<%
    User u = (User) session.getAttribute("user");

    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/login.html");
        return;
    }

    List<Project> projects =
        (List<Project>) request.getAttribute("projects");

    List<Task> tasks =
        (List<Task>) request.getAttribute("tasks");

    if (projects == null) projects = new java.util.ArrayList<>();
    if (tasks == null) tasks = new java.util.ArrayList<>();

    int completed = 0;
    int progress = 0;
    int pending = 0;

    for (Task t : tasks) {
        if ("Completed".equals(t.getStatus())) {
            completed++;
        } else if ("In Progress".equals(t.getStatus())) {
            progress++;
        } else {
            pending++;
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Dashboard | ProjectFlow</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/css/style.css">

    <style>
        .side-link {
            cursor: pointer;
            text-decoration: none;
        }

        .side-link.active {
            background: rgba(255, 255, 255, 0.12);
            border-radius: 8px;
        }

        .dashboard-section {
            min-width: 0;
        }

        .content-grid {
            align-items: start;
        }

        .task-actions {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .task-actions form {
            margin: 0;
        }

        .task-actions select {
            max-width: 145px;
        }

        .icon-btn {
            cursor: pointer;
        }

        .section-heading {
            margin-bottom: 20px;
        }

        @media (max-width: 768px) {
            .content-grid {
                grid-template-columns: 1fr;
            }

            .task-actions {
                flex-wrap: wrap;
            }
        }
    </style>
</head>

<body class="dashboard-page">

<aside class="sidebar">

    <a class="brand"
       href="<%=request.getContextPath()%>/dashboard">
        ProjectFlow<span>.</span>
    </a>

    <a class="side-link active"
       href="#overview"
       data-section="overview">
        ▦ &nbsp; Overview
    </a>

    <a class="side-link"
       href="#projects"
       data-section="projects">
        ▤ &nbsp; Projects
    </a>

    <a class="side-link"
       href="#tasks"
       data-section="tasks">
        ✓ &nbsp; Tasks
    </a>

    <div class="sidebar-bottom">

        <div class="user-chip">
            <div class="user-avatar">
                <%=esc(u.getName().substring(0, 1).toUpperCase())%>
            </div>

            <div>
                <b><%=esc(u.getName())%></b>
                <small><%=esc(u.getEmail())%></small>
            </div>
        </div>

        <form action="<%=request.getContextPath()%>/logout"
              method="post">

            <button class="logout-btn" type="submit">
                ↪ Log out
            </button>

        </form>

    </div>
</aside>

<main class="dashboard-main">

    <header class="dash-header">

        <div>
            <div class="eyebrow">YOUR WORKSPACE</div>

            <h1>
                Welcome, <%=esc(u.getName().split(" ")[0])%> 👋
            </h1>

            <p class="muted">
                Here's what's happening with your work.
            </p>
        </div>

        <span class="date-chip">Project overview</span>

    </header>

    <% if (request.getParameter("success") != null) { %>
        <div class="notice">
            Your changes have been saved.
        </div>
    <% } %>

    <% if (request.getParameter("error") != null) { %>
        <div class="notice error">
            Please check the form and try again.
        </div>
    <% } %>


    <!-- OVERVIEW SECTION -->

    <section class="stats dashboard-section"
             id="overview">

        <div class="dash-stat">
            <span class="stat-icon purple">▤</span>
            <small>Total projects</small>
            <strong><%=projects.size()%></strong>
            <span class="stat-caption">Projects you own</span>
        </div>

        <div class="dash-stat">
            <span class="stat-icon blue">✓</span>
            <small>Total tasks</small>
            <strong><%=tasks.size()%></strong>
            <span class="stat-caption">Across your projects</span>
        </div>

        <div class="dash-stat">
            <span class="stat-icon green">↗</span>
            <small>Completed</small>
            <strong><%=completed%></strong>
            <span class="stat-caption">Tasks finished</span>
        </div>

        <div class="dash-stat">
            <span class="stat-icon orange">◷</span>
            <small>In progress</small>
            <strong><%=progress%></strong>
            <span class="stat-caption"><%=pending%> pending</span>
        </div>

    </section>


    <!-- PROJECTS SECTION -->

    <section class="content-grid dashboard-section"
             id="projects">

        <div class="panel">

            <div class="panel-heading">

                <div>
                    <h2>Your projects</h2>
                    <p class="muted">
                        Organize work by project.
                    </p>
                </div>

                <span class="count-pill">
                    <%=projects.size()%>
                </span>

            </div>

            <form class="inline-create"
                  action="<%=request.getContextPath()%>/projects"
                  method="post">

                <input name="name"
                       placeholder="New project name"
                       maxlength="150"
                       required>

                <input name="deadline"
                       type="date"
                       aria-label="Project deadline">

                <input name="description"
                       placeholder="Short description">

                <button class="btn" type="submit">
                    + Add project
                </button>

            </form>

            <div class="project-list">

                <% if (projects.isEmpty()) { %>

                    <div class="empty-state">
                        <b>No projects yet</b>
                        <p>Create your first project above.</p>
                    </div>

                <% } else {
                    for (Project p : projects) {
                %>

                    <article class="project-item">

                        <div class="project-symbol">
                            <%=esc(p.getName().substring(0, 1).toUpperCase())%>
                        </div>

                        <div class="project-info">

                            <b><%=esc(p.getName())%></b>

                            <p><%=esc(p.getDescription())%></p>

                            <small>
                                <%=p.getTaskCount()%> task(s)
                                · Deadline:
                                <%=p.getDeadline() == null
                                    ? "Not set"
                                    : esc(p.getDeadline())%>
                            </small>

                        </div>

                        <form action="<%=request.getContextPath()%>/projects"
                              method="post"
                              onsubmit="return confirm('Delete this project and its tasks?')">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="id"
                                   value="<%=p.getId()%>">

                            <button class="icon-btn danger-text"
                                    type="submit"
                                    aria-label="Delete project">
                                ×
                            </button>

                        </form>

                    </article>

                <%  }
                   }
                %>

            </div>

        </div>

    </section>


    <!-- TASKS SECTION -->

    <section class="content-grid dashboard-section"
             id="tasks">

        <div class="panel task-panel">

            <div class="panel-heading">

                <div>
                    <h2>Tasks</h2>
                    <p class="muted">
                        Track the next steps.
                    </p>
                </div>

                <span class="count-pill">
                    <%=tasks.size()%>
                </span>

            </div>

            <% if (projects.isEmpty()) { %>

                <div class="notice">
                    Create a project first, then add tasks.
                </div>

            <% } else { %>

                <form class="task-form"
                      action="<%=request.getContextPath()%>/tasks"
                      method="post">

                    <label>Task title</label>

                    <input name="title"
                           maxlength="150"
                           placeholder="e.g. Prepare wireframes"
                           required>

                    <label>Project</label>

                    <select name="projectId" required>

                        <% for (Project p : projects) { %>

                            <option value="<%=p.getId()%>">
                                <%=esc(p.getName())%>
                            </option>

                        <% } %>

                    </select>

                    <div class="two-fields">

                        <div>
                            <label>Priority</label>

                            <select name="priority">
                                <option>Medium</option>
                                <option>High</option>
                                <option>Low</option>
                            </select>
                        </div>

                        <div>
                            <label>Due date</label>

                            <input type="date" name="dueDate">
                        </div>

                    </div>

                    <label>Description</label>

                    <input name="description"
                           placeholder="Optional task details">

                    <button class="btn full" type="submit">
                        + Add task
                    </button>

                </form>

            <% } %>


            <div class="task-list">

                <% if (tasks.isEmpty()) { %>

                    <div class="empty-state compact">
                        <b>No tasks yet</b>
                        <p>Your tasks will appear here.</p>
                    </div>

                <% } else {
                    for (Task t : tasks) {
                %>

                    <article class="task-item">

                        <div class="task-main">

                            <b><%=esc(t.getTitle())%></b>

                            <small>
                                <%=esc(t.getProjectName())%>

                                <% if (t.getDueDate() != null) { %>
                                    · Due <%=esc(t.getDueDate())%>
                                <% } %>
                            </small>

                            <div class="task-tags">

                                <span class="priority <%=esc(t.getPriority().toLowerCase())%>">
                                    <%=esc(t.getPriority())%>
                                </span>

                                <span class="status">
                                    <%=esc(t.getStatus())%>
                                </span>

                            </div>

                        </div>

                        <div class="task-actions">

                            <form action="<%=request.getContextPath()%>/tasks"
                                  method="post">

                                <input type="hidden"
                                       name="action"
                                       value="status">

                                <input type="hidden"
                                       name="id"
                                       value="<%=t.getId()%>">

                                <select name="status"
                                        aria-label="Task status"
                                        onchange="this.form.submit()">

                                    <option value="Pending"
                                        <%= "Pending".equals(t.getStatus())
                                            ? "selected" : "" %>>
                                        Pending
                                    </option>

                                    <option value="In Progress"
                                        <%= "In Progress".equals(t.getStatus())
                                            ? "selected" : "" %>>
                                        In Progress
                                    </option>

                                    <option value="Completed"
                                        <%= "Completed".equals(t.getStatus())
                                            ? "selected" : "" %>>
                                        Completed
                                    </option>

                                </select>

                            </form>

                            <form action="<%=request.getContextPath()%>/tasks"
                                  method="post"
                                  onsubmit="return confirm('Delete this task?')">

                                <input type="hidden"
                                       name="action"
                                       value="delete">

                                <input type="hidden"
                                       name="id"
                                       value="<%=t.getId()%>">

                                <button class="icon-btn danger-text"
                                        type="submit"
                                        aria-label="Delete task">
                                    ×
                                </button>

                            </form>

                        </div>

                    </article>

                <%  }
                   }
                %>

            </div>

        </div>

    </section>

</main>


<!-- SIDEBAR NAVIGATION -->

<script>
document.addEventListener("DOMContentLoaded", function () {

    const links = document.querySelectorAll(
        ".side-link[data-section]"
    );

    const overview = document.getElementById("overview");
    const projects = document.getElementById("projects");
    const tasks = document.getElementById("tasks");

    function showSection(name) {

        overview.style.display =
            name === "overview" ? "" : "none";

        projects.style.display =
            name === "projects" ? "" : "none";

        tasks.style.display =
            name === "tasks" ? "" : "none";

        links.forEach(function (link) {
            link.classList.toggle(
                "active",
                link.dataset.section === name
            );
        });
    }

    links.forEach(function (link) {

        link.addEventListener("click", function (event) {

            event.preventDefault();

            const section = this.dataset.section;

            history.replaceState(null, "", "#" + section);

            showSection(section);
        });

    });

    const initialSection = window.location.hash.substring(1);

    if (["overview", "projects", "tasks"].includes(initialSection)) {
        showSection(initialSection);
    } else {
        showSection("overview");
    }

});
</script>

</body>
</html>