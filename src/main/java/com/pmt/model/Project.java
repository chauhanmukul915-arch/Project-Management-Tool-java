package com.pmt.model;
public class Project {
    private final int id, ownerId, taskCount;
    private final String name, description, deadline;
    public Project(int id,String name,String description,String deadline,int ownerId,int taskCount){
        this.id=id;this.name=name;this.description=description;this.deadline=deadline;this.ownerId=ownerId;this.taskCount=taskCount;
    }
    public int getId(){return id;} public String getName(){return name;}
    public String getDescription(){return description;} public String getDeadline(){return deadline;}
    public int getOwnerId(){return ownerId;} public int getTaskCount(){return taskCount;}
}
