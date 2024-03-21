package src;
import java.sql.*;

public class test {

public void addbooks(int Bookid, String Bookname, String Author, String Keyword, int publishyear)
{
	JDBC jdbc=new JDBC();
	Connection con =jdbc.establishConnection();
	try {
		//Statement statement = con.createStatement();
		//ResultSet rst = statement.executeQuery("insert into book values(?,?,?,?,?,?)");

		String query="insert into book(Bookid, BookTitle, Author, Keyword, PublishYear) values(?,?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(query);
		
		ps.setInt(1, Bookid);
		ps.setString(2, Bookname);
		ps.setString(3, Author);
		ps.setString(4, Keyword);
		ps.setInt(5, publishyear);
		//ps.setInt(6, bcount);
		ps.executeUpdate();
		System.out.println("Book added successfully.");

	} catch (Exception e) {
		
		e.printStackTrace();
	}
	
}



}
