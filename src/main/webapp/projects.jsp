<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.pmt.model.User,com.pmt.model.Project" %>
<%!
private String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;").replace("<","&lt;")
            .replace(">","&gt;").replace("\"","&quot;")
            .replace("'","&#39;");
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
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Projects | ProjectFlow</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body class="dashboard-page">
<aside class="sidebar">
    <a class="brand" href="<%=request.getContextPath()%>/dashboard">ProjectFlow<span>.</span></a>
    <div class="side-label">WORKSPACE</div>
    <a class="side-link" href="<%=request.getContextPath()%>/dashboard">▦ Overview</a>
    <a class="side-link active" href="<%=request.getContextPath()%>/projects-page">▤ Projects</a>
    <a class="side-link" href="<%=request.getContextPath()%>/tasks-page">✓ Tasks</a>
    <div class="sidebar-bottom">
        <div class="user-chip">
            <div class="user-avatar"><%=esc(u.getName().substring(0,1).toUpperCase())%></div>
            <div><b><%=esc(u.getName())%></b><small><%=esc(u.getEmail())%></small></div>
        </div>
        <form action="<%=request.getContextPath()%>/logout" method="post">
            <button class="logout-btn">↪ Log out</button>
        </form>
    </div>
</aside>

<main class="dashboard-main">
    <header class="dash-header">
        <div><div class="eyebrow">YOUR WORKSPACE</div>
        <h1>Projects</h1><p class="muted">Create and manage your projects.</p></div>
    </header>

    <section class="panel" id="projects">
        <div class="panel-heading"><div><h2>Your projects</h2></div>
        <span class="count-pill"><%=projects.size()%></span></div>

        <form class="inline-create" action="<%=request.getContextPath()%>/projects" method="post">
            <input name="name" placeholder="New project name" maxlength="150" required>
            <input name="deadline" type="date" aria-label="Project deadline">
            <input name="description" placeholder="Short description">
            <button class="btn" type="submit">+ Add project</button>
        </form>

        <div class="project-list">
        <% if (projects.isEmpty()) { %>
            <div class="empty-state"><b>No projects yet</b><p>Create your first project above.</p></div>
        <% } else { for (Project p : projects) { %>
            <article class="project-item">
                <div class="project-symbol"><%=esc(p.getName().substring(0,1).toUpperCase())%></div>
                <div class="project-info">
                    <b><%=esc(p.getName())%></b>
                    <p><%=esc(p.getDescription())%></p>
                    <small><%=p.getTaskCount()%> task(s) · Deadline: <%=p.getDeadline()==null?"Not set":esc(p.getDeadline())%></small>
                </div>
                <form action="<%=request.getContextPath()%>/projects" method="post"
                      onsubmit="return confirm('Delete this project and its tasks?')">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%=p.getId()%>">
                    <button class="icon-btn danger-text" aria-label="Delete project">×</button>
                </form>
            </article>
        <% } } %>
        </div>
    </section>
</main>
</body>
</html>