package src;
import java.sql.*;
import java.time.*;
import java.util.*;
public class Stdmanage {
	JDBC jdbc=new JDBC();
	Connection con =jdbc.establishConnection();
	Scanner sc=new Scanner(System.in);
	public void studentmanage() {
	int ch;
	Scanner sc=new Scanner(System.in);
	boolean exit=false;
	while(!exit) {
	System.out.println("1.Request Book\n2.Return book\n3.Renew Book\n4.Exit\nEnter your choice: ");
	ch=sc.nextInt();
	switch(ch)
	{
	case 1:
	{
		requestbook();
		break;
	}
	case 2:
	{
		returnbook();
		break;
	}
	case 3:
	{
		renewbook();
		break;
	}
	case 4:
	{
		exit=true;
		break;
	}
	}
	}
	//sc.close();
	
}
public void requestbook()
{
	int id,id1;
	System.out.println("Enter Studentid: ");
	id=sc.nextInt();
	while (true) {
        int sid = checkSid(id);
        if (sid == 0) {
            System.out.println("Invalid Student ID. Please enter a valid ID: ");
            id=sc.nextInt();
        } else {
            break; 
        }
    }
	if (booksissued(id) >= 5) {
        System.out.println("You have already reached the max limit of issued books");
    return;
    }
	Student std=new Student();
	std.booksearch();
	id1=searchbybookname();
	if(id1!=0)
	{
		transaction(id,id1);
	}
	
	
}
public int searchbybookname() {
	String bname="";
	int count=0;
	Scanner sc=new Scanner(System.in);
	JDBC jdbc=new JDBC();
	Connection con =jdbc.establishConnection();
	System.out.println("Enter Book name to issue: ");
	bname=sc.nextLine();
	//sc.next();
	int bookId = 0;
	try {
		String query="select * from book where BookTitle=? and Bstatus=?";
		try (PreparedStatement ps = con.prepareStatement(query)) {
                       ps.setString(1, bname);
                       ps.setString(2, "Available");
                        try (ResultSet rs= ps.executeQuery()) {
                        while(rs.next()){
                            count = rs.getInt(1);
                            bookId = rs.getInt("Bookid");
                   }
                 
	             }
              }
             }catch (Exception e) {
            	 e.printStackTrace();
	         }
	//int id=getBookid(bname);
	//sc.close();
	return bookId;
	
}
public void returnbook()
{
	int bid;
	System.out.println("Enter the id of the book to return: ");
	bid=sc.nextInt();
	try {
		String query="update transaction set checkout=0 where Bookid=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, bid);
		ps.executeUpdate();
		
	}catch (Exception e) {
		
		e.printStackTrace();
	}
	try {
		String query="update book set Bstatus=? where Bookid=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, "Available");
		ps.setInt(2, bid);
		ps.executeUpdate();
		
	}catch (Exception e) {
		
		e.printStackTrace();
	}
	System.out.println("Book returned and added to library");
	book();
	
	
	
	
}
public int booksissued(int id) {
	int count=0;
	try {
    String Query = "SELECT COUNT(*) FROM transaction WHERE Studentid = ? and checkout=1";
    try(PreparedStatement ps = con.prepareStatement(Query)){
    ps.setInt(1, id);
    try(ResultSet rs = ps.executeQuery()){
    while(rs.next()) {
    count = rs.getInt(1);
    }
    }
    }
	}catch (Exception e) {
		
		e.printStackTrace();
	}
    return count;
	}
public void transaction(int sid, int bid)
{
	int tid;
	System.out.println("Enter transaction id: ");
	tid=sc.nextInt();
	try {
		//Statement statement = con.createStatement();
		//ResultSet rst = statement.executeQuery("insert into book values(?,?,?,?,?,?)");

		String query="insert into transaction(Tid,Bookid,Studentid) values(?,?,?)";
		PreparedStatement ps=con.prepareStatement(query);
		//Date currentDate = new Date(new Date().getTime());
		ps.setInt(1, tid);
		ps.setInt(2, bid);
		ps.setInt(3, sid);
		ps.executeUpdate();
		System.out.println("Book issued successfully.");
		transaction();


	} catch (Exception e) {
		
		e.printStackTrace();
	}
	try {
		String query="update book set Bstatus= ? where Bookid=?";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1,"Unavailble");
		ps.setInt(2, bid);
		ps.executeUpdate();
	}catch (Exception e) {
		
		e.printStackTrace();
	}
		
	}
public void renewbook()
{
	int bid,result;
	//String currentDateTime = LocalDateTime.now().toString().substring(0, 10)+" "+LocalDateTime.now().toString().substring(11,19);
	//Timestamp returnDateTime = currentDateTime.plusDays(45).substring(0,10)+" "+currentDateTime.plusDays(45).substring(11,19);
	LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime returnDateTime = currentDateTime.plusDays(45);
    String currentDate=currentDateTime.toString().substring(0,10)+" "+currentDateTime.toString().substring(11,19);
    String returnDate=returnDateTime.toString().substring(0,10)+" "+returnDateTime.toString().substring(11,19);
    //String currentDate=currentDateTime.toString().substring(0,10);
    //String returnDate=returnDateTime.toString().substring(0,10);
	System.out.println("Enter the id of the book to be renewed: ");
	bid=sc.nextInt();
	try {
		String query = "UPDATE Transaction SET RenewalCount = RenewalCount + 1, BuyDate = ?, ReturnDate = ? WHERE Bookid = ? AND RenewalCount < 3 AND checkout = 1";
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(3, bid);
		//System.out.println(currentDate);
		//System.out.println(returnDate);
		
		ps.setTimestamp(1, Timestamp.valueOf(currentDate));
	    ps.setTimestamp(2, Timestamp.valueOf(returnDate));
		result=ps.executeUpdate();
		if(result>0) {
		System.out.println("Book renewed Successfully");
		transaction();
		}
		
		else
		System.out.println("Renew count exceeded. Cannot renew the book further.");
		}catch (Exception e) {
					
					e.printStackTrace();
				}
	
}
public void student()
{
		try{
			String query = "select * from student";
			try (PreparedStatement ps = con.prepareStatement(query)) {
				try(ResultSet rs=ps.executeQuery()){
				    System.out.println("+----------+-----------------------------+----------------------+");
			            System.out.println("| StudentId   | StudentName                   | StudentStatus");
			            System.out.println("+----------+-----------------------------+----------------------+");
					while(rs.next()) {
								int Sid = rs.getInt(1);
			                	String Sname = rs.getString(2);
			                	String Sstatus = rs.getString(3);
					System.out.printf("| %-8d | %-27s | %-20s |\n", Sid, Sname, Sstatus);
					}
				}
				}
				}catch (Exception e) {
						e.printStackTrace();
					}
}
public void book()
{
	try{
		String query = "select * from book";
		try (PreparedStatement ps = con.prepareStatement(query)) {
			try(ResultSet rs=ps.executeQuery()){
			    System.out.println("+----------+-----------------------------+----------------------+-------------+----------+----------+");
		            System.out.println("| BookId   | BookTitle                   | Author               | Keyword             |PublishYear | BStatus  |");
		            System.out.println("+----------+-----------------------------+----------------------+-------------+----------+---------+");
				while(rs.next()) {
							int bookId = rs.getInt(1);
		                	String bookTitle = rs.getString(2);
		                	String author = rs.getString(3);
		                	String key=rs.getString(4);
		                	int publishYear = rs.getInt(5);
		                	String bStatus = rs.getString(6);
		                	System.out.printf("| %-8d | %-27s | %-20s |%-11s | %-11d | %-8s |\n", bookId, bookTitle, author, key, publishYear, bStatus);
				}
			}
		    }
			}catch (Exception e) {
					
					e.printStackTrace();
				}
}
public void transaction()
{
	try{
		String query = "select * from transaction";
		try (PreparedStatement ps = con.prepareStatement(query)) {
			try(ResultSet rs=ps.executeQuery()){
			    	System.out.println("+----------+-----------+-----------+----------+-------------+----------------+-----------+");
		            System.out.println("| TId      | Bookid    | Studentid | checkout |Buydate      | Returndate     |RenewalCount");
		            System.out.println("+----------+-----------+-----------+----------+-------------+----------------+-----------+");
				while(rs.next()) {
							int tId = rs.getInt(1);
		                	int bid= rs.getInt(2);
		                	int sid = rs.getInt(3);
		                	int checkout=rs.getInt(4);
		                	String buydate = rs.getString(5);
		                	String returndate = rs.getString(6);
		                	int renewal=rs.getInt(7);
		                	System.out.printf("| %-6d | %-8d | %-6d |%-6d | %-11s | %-11s |%-6d |\n", tId, bid, sid, checkout, buydate, returndate, renewal);
				}
			}
		    }
			}catch (Exception e) {
					
					e.printStackTrace();
				}
}
public void notReturnOnTime() {
	try {
		String query="SELECT transaction.Studentid,student.StudentName FROM transaction join student on transaction.Studentid=student.Studentid WHERE DATEDIFF(CURDATE(), BuyDate) > 45";
		PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        boolean res=isNotReturnBooks();
       //if(rs.next()) {
        System.out.println("Students who have not returned books on time:");
        System.out.println("+----------+-----------------------------+");
        System.out.println("| StudentId   | StudentName                   |");
        System.out.println("+----------+-----------------------------+");
        if(res == false) {
        	System.out.println("No one");
        }
        while (rs.next()) {
            int studentId = rs.getInt(1);
            String sname=rs.getString(2);
            System.out.printf("| %-8d | %-27s |\n", studentId, sname);
        }
        //}
//        else
//        {
//        	System.out.println("No one");
//        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
		
}
public boolean isNotReturnBooks() throws SQLException {
    String query = "SELECT transaction.Studentid,student.StudentName FROM transaction join student on transaction.Studentid=student.Studentid WHERE DATEDIFF(CURDATE(), BuyDate) > 45";
    PreparedStatement ps = con.prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    return rs.next();
}
public void bookListInLibrary()
{
	try {
		String query="Select BookTitle from book where bstatus=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1,"Available");
        ResultSet rs = ps.executeQuery();
        System.out.println("BookList in Library: ");
        System.out.println("+--------------+");
        System.out.println("| BookName     |");
        System.out.println("+--------------+");
        while (rs.next()) {
            String bname=rs.getString(1);
            System.out.printf("| %-11s |\n",bname);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        
	}
public int checkSid(int sid)
{
	int id=0;
	try {
		String query="Select Studentid from student where Studentid=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1,sid);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			id=1;
		}
		}catch (SQLException e) {
	        e.printStackTrace();
	    }
	return id;
}
public int[] pdf()
{
	int[] booksInfo=new int[2];
	try {
		String query="select count(BookTitle) from book";
		String query1="select count(BookTitle) from book where Bstatus=?";
		PreparedStatement ps = con.prepareStatement(query1);
		ps.setString(1,"Available");
		ResultSet TotalBooks=ps.executeQuery(query);
		ResultSet NotinLibrary=ps.executeQuery(query1);
		if(TotalBooks.next()&&NotinLibrary.next())
		{
			int totalbooks= TotalBooks.getInt(1);
			int notinlibrary=NotinLibrary.getInt(1);
		booksInfo[0]=totalbooks;
		booksInfo[1]=notinlibrary;
		}
		
	}catch (SQLException e) {
        e.printStackTrace();
    }
	return booksInfo;
	
}
}
	


	





