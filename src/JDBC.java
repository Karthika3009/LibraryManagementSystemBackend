package src;
import java.sql.*;

public class JDBC {
    public Connection establishConnection()  {
    	
        String jdbc_url = "jdbc:mysql://localhost:3306/Library";
        String user = "root";
        String password = "root";
        try {
            return DriverManager.getConnection(jdbc_url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
 
 
    }
 
}