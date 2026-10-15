package com.pmt.dao;
import com.pmt.model.User;
import com.pmt.util.DBConnection;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    public void register(String name,String email,String password) throws SQLException {
        String sql="INSERT INTO users(name,email,password_hash) VALUES(?,?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,name);p.setString(2,email.toLowerCase());p.setString(3,BCrypt.hashpw(password,BCrypt.gensalt()));p.executeUpdate();
        }
    }
    public User login(String email,String password) throws SQLException {
        String sql="SELECT id,name,email,password_hash FROM users WHERE email=?";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,email.toLowerCase());
            try(ResultSet r=p.executeQuery()){
                if(r.next() && BCrypt.checkpw(password,r.getString("password_hash")))
                    return new User(r.getInt("id"),r.getString("name"),r.getString("email"));
            }
        }
        return null;
    }
}
