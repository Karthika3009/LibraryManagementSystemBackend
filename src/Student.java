package src;

import java.sql.*;
import java.util.*;


public class Student {
	
	public void addstudent(int Studentid, String Stdname, String Stdstatus)
	{
		JDBC jdbc=new JDBC();
		Connection con =jdbc.establishConnection();
		try {
			//Statement statement = con.createStatement();
			//ResultSet rst = statement.executeQuery("insert into book values(?,?,?,?,?,?)");

			String query="insert into student values(?,?,?)";
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setInt(1, Studentid);
			ps.setString(2, Stdname);
			ps.setString(3, Stdstatus);
			ps.executeUpdate();
			System.out.println("Student added successfully.");

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public void modifyStudent(int Studentid, String columnName, String newValue)
	{
		JDBC jdbc=new JDBC();
		Connection con =jdbc.establishConnection();
		try {
			//Statement statement = con.createStatement();
			//ResultSet rst = statement.executeQuery("insert into book values(?,?,?,?,?,?)");

			String query="UPDATE Student SET " + columnName + " = ? WHERE StudentId = ?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, newValue);
			ps.setInt(2, Studentid);
			ps.executeUpdate();
			System.out.println("Student details updated successfully.");

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public void removeStudent(int Studentid)
	{
		JDBC jdbc=new JDBC();
		Connection con =jdbc.establishConnection();
		try {
			//Statement statement = con.createStatement();
			//ResultSet rst = statement.executeQuery("insert into book values(?,?,?,?,?,?)");

			String query="DELETE FROM Student WHERE Studentid = ?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, Studentid);
			ps.executeUpdate();
			System.out.println("Student details removed successfully.");

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public void blockStudent(int Studentid)
	{
		JDBC jdbc=new JDBC();
		Connection con =jdbc.establishConnection();
		try {
			//Statement statement = con.createStatement();
			//ResultSet rst = statement.executeQuery("insert into book values(?,?,?,?,?,?)");

			String query="select * from student where Studentid = ? and StudentStatus != 'Blocked'";
			try(PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1, Studentid);
			try(ResultSet rs=ps.executeQuery()){
				if(rs.next()) {
					String bquery="update student set StudentStatus = 'Blocked' where Studentid = ?";
					try(PreparedStatement bs=con.prepareStatement(bquery)){
						bs.setInt(1, Studentid);
						bs.executeUpdate();
						System.out.println("Student with ID " + Studentid + " has been blocked successfully.");
					}
				}
				else
				{
					System.out.println("Unable to block.Student not found");
				}
						
				  }
				}
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		
	}
	public void viewStudent()
	{
		JDBC jdbc=new JDBC();
		Connection con =jdbc.establishConnection();
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
	public void booksearch() {
		int result;
		Scanner sc=new Scanner(System.in);
		int choice;
		boolean exit=false;
		while(!exit) {
		System.out.println("Search books using\n1.Author\t2.Title\t3.Keyword\t4.publishyear\t5.Exit\nEnter your choice: ");
		choice=sc.nextInt();
		switch(choice)
		{
		case 1:
		{
			searchbyauthor();
			break;
		}
		case 2:
		{
			searchbybookname();
			break;
		}
		case 3:
		{   
			searchbyKeyword();
			break;
		}
		case 4:
		{
			searchbyyear();
			break;
			
		}
		case 5:
		{
			exit=true;
			break;
		}
		}
	
		}
		//sc.close();
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public void searchbyauthor() {
			String author="";
			int count=0;
			Scanner sc=new Scanner(System.in);
			JDBC jdbc=new JDBC();
			Connection con =jdbc.establishConnection();
			System.out.println("Enter Author name: ");
			author=sc.nextLine();
			//sc.next();
			try {
				String query="select * from book where Author=? and Bstatus=?";
				try (PreparedStatement ps = con.prepareStatement(query)) {
	                           ps.setString(1, author);
	                           ps.setString(2, "Available");
	                            try (ResultSet rs= ps.executeQuery()) {
	                            while(rs.next()){
                                    count = rs.getInt(1);
                           }
                         
			             }
                      }
                     }catch (Exception e) {
                    	 e.printStackTrace();
			         }
			if(count>0)
			{
				try{
					String query = "select * from book where Author=? and Bstatus=?";
					try (PreparedStatement ps = con.prepareStatement(query)) {
						ps.setString(1, author);
                        ps.setString(2, "Available");
						try(ResultSet rs=ps.executeQuery()){
						    System.out.println("+----------+-----------------------------+");
					            System.out.println("| BookId   | BookTitle                   |");
					            System.out.println("+----------+-----------------------------+");
							while(rs.next()) {
										int bookId = rs.getInt(1);
					                	String bookTitle = rs.getString(2);
					                	System.out.printf("| %-8d | %-27s |\n", bookId, bookTitle);
							}
						}
					    }
						}catch (Exception e) {
								
								e.printStackTrace();
							}
			}
			else
				System.out.println("Book Unavailable");
		}
		public void searchbybookname() {
			String bname="";
			int count=0;
			Scanner sc=new Scanner(System.in);
			JDBC jdbc=new JDBC();
			Connection con =jdbc.establishConnection();
			System.out.println("Enter Book name: ");
			bname=sc.nextLine();
			//sc.next();
			//int bookId = 0;
			try {
				String query="select * from book where BookTitle=? and Bstatus=?";
				try (PreparedStatement ps = con.prepareStatement(query)) {
	                           ps.setString(1, bname);
	                           ps.setString(2, "Available");
	                            try (ResultSet rs= ps.executeQuery()) {
	                            while(rs.next()){
                                    count = rs.getInt(1);
                                    //bookId = rs.getInt("Bookid");
                           }
                         
			             }
                      }
                     }catch (Exception e) {
                    	 e.printStackTrace();
			         }
			//int id=getBookid(bname);
			//sc.close();
			if(count>0)
			{
				try{
					String query = "select * from book where BookTitle=? and Bstatus=?";
					try (PreparedStatement ps = con.prepareStatement(query)) {
						ps.setString(1, bname);
                        ps.setString(2, "Available");
						try(ResultSet rs=ps.executeQuery()){
						    System.out.println("+----------+-----------------------------+");
					            System.out.println("| BookId   | BookTitle                   |");
					            System.out.println("+----------+-----------------------------+");
							while(rs.next()) {
										int bookId = rs.getInt(1);
					                	String bookTitle = rs.getString(2);
					                	System.out.printf("| %-8d | %-27s |\n", bookId, bookTitle);
							}
						}
					    }
						}catch (Exception e) {
								
								e.printStackTrace();
							}
			}
			else
				System.out.println("Book Unavailable");
			
		}
		public void searchbyKeyword() {
			String key="";
			int count=0;
			Scanner sc=new Scanner(System.in);
			JDBC jdbc=new JDBC();
			Connection con =jdbc.establishConnection();
			System.out.println("Enter Keyword to search: ");
			key=sc.nextLine();
			try {
				String query="select * from book where Keyword=? and Bstatus=?";
				try (PreparedStatement ps = con.prepareStatement(query)) {
	                           ps.setString(1, key);
	                           ps.setString(2, "Available");
	                            try (ResultSet rs= ps.executeQuery()) {
	                            while(rs.next()){
                                    count = rs.getInt(1);
                           }
                         
			             }
                      }
                     }catch (Exception e) {
                    	 e.printStackTrace();
			         }
			if(count>0)
			{
				try{
					String query = "select * from book where Keyword=? and Bstatus=?";
					try (PreparedStatement ps = con.prepareStatement(query)) {
						ps.setString(1, key);
                        ps.setString(2, "Available");
						try(ResultSet rs=ps.executeQuery()){
						    System.out.println("+----------+-----------------------------+");
					            System.out.println("| BookId   | BookTitle                   |");
					            System.out.println("+----------+-----------------------------+");
							while(rs.next()) {
										int bookId = rs.getInt(1);
					                	String bookTitle = rs.getString(2);
					                	System.out.printf("| %-8d | %-27s |\n", bookId, bookTitle);
							}
						}
					    }
						}catch (Exception e) {
								
								e.printStackTrace();
							}
			}
			else
				System.out.println("Book Unavailable");
				
		}
		public void searchbyyear() {
			int year;
			int count=0;
			Scanner sc=new Scanner(System.in);
			JDBC jdbc=new JDBC();
			Connection con =jdbc.establishConnection();
			System.out.println("Enter Publish year: ");
			year=sc.nextInt();
			try {
				String query="select * from book where PublishYear=? and Bstatus=?";
				try (PreparedStatement ps = con.prepareStatement(query)) {
	                           ps.setInt(1, year);
	                           ps.setString(2, "Available");
	                            try (ResultSet rs= ps.executeQuery()) {
	                            while(rs.next()){
                                    count = rs.getInt(1);
                           }
                         
			             }
                      }
                     }catch (Exception e) {
                    	 e.printStackTrace();
			         }
			//sc.close();
			if(count>0)
			{
				try{
					String query = "select * from book where PublishYear=? and Bstatus=?";
					try (PreparedStatement ps = con.prepareStatement(query)) {
						ps.setInt(1, year);
                        ps.setString(2, "Available");
						try(ResultSet rs=ps.executeQuery()){
						    System.out.println("+----------+-----------------------------+");
					            System.out.println("| BookId   | BookTitle                   |");
					            System.out.println("+----------+-----------------------------+");
							while(rs.next()) {
										int bookId = rs.getInt(1);
					                	String bookTitle = rs.getString(2);
					                	System.out.printf("| %-8d | %-27s |\n", bookId, bookTitle);
							}
						}
					    }
						}catch (Exception e) {
								
								e.printStackTrace();
							}
			}
			else
				System.out.println("Book Unavailable");
			
		}
		public int getBookid(String bname) {
			int bookid=0;
			JDBC jdbc=new JDBC();
			Connection con =jdbc.establishConnection();
			try {
		    String Query = "SELECT Bookid FROM book WHERE BookTitle = ?";
		    try(PreparedStatement ps = con.prepareStatement(Query)){
		    ps.setString(1, bname);
		    try(ResultSet rs = ps.executeQuery(Query)){
		    bookid = -1;
		    while(rs.next()) {
		        bookid = rs.getInt(1);
		    }
		    }
		    }
		    }catch (Exception e) {
           	 e.printStackTrace();
	         }
		    return bookid;
		}
		
	

	
	


}
