package com.pmt.dao;
import com.pmt.model.Project;
import com.pmt.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ProjectDAO {
    public List<Project> findByOwner(int owner) throws SQLException {
        List<Project> out=new ArrayList<>();
        String sql="SELECT p.id,p.name,p.description,p.deadline,p.owner_id,COUNT(t.id) task_count FROM projects p LEFT JOIN tasks t ON t.project_id=p.id WHERE p.owner_id=? GROUP BY p.id ORDER BY p.created_at DESC";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,owner);try(ResultSet r=p.executeQuery()){while(r.next())out.add(new Project(r.getInt("id"),r.getString("name"),r.getString("description"),r.getString("deadline"),r.getInt("owner_id"),r.getInt("task_count")));}
        }return out;
    }
    public void create(String name,String description,String deadline,int owner) throws SQLException {
        String sql="INSERT INTO projects(name,description,deadline,owner_id) VALUES(?,?,?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,name);p.setString(2,description);
            if(deadline==null||deadline.isBlank())p.setNull(3,Types.DATE);else p.setDate(3,java.sql.Date.valueOf(deadline));
            p.setInt(4,owner);p.executeUpdate();
        }
    }
    public void delete(int id,int owner) throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM projects WHERE id=? AND owner_id=?")){
            p.setInt(1,id);p.setInt(2,owner);p.executeUpdate();
        }
    }
    public boolean belongsToOwner(int id,int owner) throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT id FROM projects WHERE id=? AND owner_id=?")){
            p.setInt(1,id);p.setInt(2,owner);try(ResultSet r=p.executeQuery()){return r.next();}
        }
    }
}
