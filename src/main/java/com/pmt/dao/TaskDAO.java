package com.pmt.dao;
import com.pmt.model.Task;
import com.pmt.util.DBConnection;
import java.sql.*;
import java.util.*;

public class TaskDAO {
    public List<Task> findByOwner(int owner) throws SQLException {
        List<Task> out=new ArrayList<>();
        String sql="SELECT t.id,t.title,t.description,t.status,t.priority,t.due_date,t.project_id,p.name project_name FROM tasks t JOIN projects p ON p.id=t.project_id WHERE p.owner_id=? ORDER BY t.created_at DESC";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,owner);try(ResultSet r=p.executeQuery()){while(r.next())out.add(new Task(r.getInt("id"),r.getString("title"),r.getString("description"),r.getString("status"),r.getString("priority"),r.getString("due_date"),r.getInt("project_id"),r.getString("project_name")));}
        }return out;
    }
    public void create(String title,String description,String status,String priority,String dueDate,int projectId) throws SQLException {
        String sql="INSERT INTO tasks(title,description,status,priority,due_date,project_id) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,title);p.setString(2,description);p.setString(3,status);p.setString(4,priority);
            if(dueDate==null||dueDate.isBlank())p.setNull(5,Types.DATE);else p.setDate(5,java.sql.Date.valueOf(dueDate));
            p.setInt(6,projectId);p.executeUpdate();
        }
    }
    public void updateStatus(int id,int owner,String status) throws SQLException {
        String sql="UPDATE tasks t JOIN projects p ON p.id=t.project_id SET t.status=? WHERE t.id=? AND p.owner_id=?";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,status);p.setInt(2,id);p.setInt(3,owner);p.executeUpdate();}
    }
    public void delete(int id,int owner) throws SQLException {
        String sql="DELETE t FROM tasks t JOIN projects p ON p.id=t.project_id WHERE t.id=? AND p.owner_id=?";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,id);p.setInt(2,owner);p.executeUpdate();}
    }
}
