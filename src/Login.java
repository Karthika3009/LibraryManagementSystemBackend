package src;
import java.sql.*;
public class Login {
	public String librarylogin(String name,String password)
	{
	JDBC jdbc=new JDBC();
	Connection con=jdbc.establishConnection();
	String role="";
	try {
	String query="SELECT role FROM login WHERE name = ? AND password = ?";
	try(PreparedStatement ps=con.prepareStatement(query))
	{
	ps.setString(1,name);
	ps.setString(2,password);
	//ps.setString(3,role);
	try(ResultSet rs=ps.executeQuery()){
	if(rs.next())
	{
		role=rs.getString(1);
	}
	}
	}
	}
	catch(SQLException e)
	{
	e.printStackTrace();
	}
	finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
    return role;
	}
}
