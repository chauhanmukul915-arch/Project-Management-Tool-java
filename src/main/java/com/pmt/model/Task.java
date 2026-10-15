package com.pmt.model;
public class Task {
    private final int id, projectId;
    private final String title, description, status, priority, dueDate, projectName;
    public Task(int id,String title,String description,String status,String priority,String dueDate,int projectId,String projectName){
        this.id=id;this.title=title;this.description=description;this.status=status;this.priority=priority;
        this.dueDate=dueDate;this.projectId=projectId;this.projectName=projectName;
    }
    public int getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;}
    public String getStatus(){return status;} public String getPriority(){return priority;} public String getDueDate(){return dueDate;}
    public int getProjectId(){return projectId;} public String getProjectName(){return projectName;}
}
