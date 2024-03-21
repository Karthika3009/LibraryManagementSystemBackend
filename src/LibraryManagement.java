package src;

//import java.sql.*;
import java.util.*;
public class LibraryManagement {
	public static void main(String[] args){
		String rol="",name="",pass="",Sname="",S_Status="",columnName="",newValue="";
		int ch,ch1,id,id1,id2,ch2=0;
		Stdmanage sm=new Stdmanage();
		Scanner sc=new Scanner(System.in);
		System.out.println("Library Books Management");
//		System.out.println("1.Admin\t2.Librarian\nEnter your choice: ");
//		ch=sc.nextInt();
//		if(ch==1)
//		rol="Admin";
//		else
//		rol="Librarian";
//		if(ch==1 || ch==2)
//		{
		System.out.println("Enter name: ");
		name=sc.next();
		System.out.println("Enter password: ");
		pass=sc.next();
		//}
		Login log=new Login();
		rol=log.librarylogin(name, pass);
		if(rol=="")
		System.out.println("Invalid Credentials...");
		else
		{
			System.out.println("Logged in as "+rol);
		}
		//System.out.println(rol);
		if(rol.equalsIgnoreCase("Librarian"))
			ch2=1;
		else if(rol.equalsIgnoreCase("Student"))
			ch2=2;
		else if(rol.equalsIgnoreCase("Admin"))
			ch2=3;
		
		switch(ch2) {
		case 1:{
			boolean exit2=false;
			while(!exit2) {
		System.out.println("1.Add books to library\n2.Manage Student\n3.Search Book\n4.See Available Booklist in Library\n5.Check the List of Students not returning books on time\n6.Exit\nEnter your choice: ");
		ch=sc.nextInt();
		switch(ch) {
		case 1:
		{
			System.out.println("Enter Bookid: ");
			int Bid=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Book Title: ");
			String Btitle=sc.nextLine();
			System.out.println("Enter Author name: ");
			String author=sc.nextLine();
			System.out.println("Enter Keyword: ");
			String key=sc.nextLine();
			System.out.println("Enter publish year: ");
			int year=sc.nextInt();
//			System.out.println("Enter Bookstatus: ");
//			String status=sc.nextLine();
			test tes=new test();
			tes.addbooks(Bid, Btitle, author, key, year);
			break;
		}
		case 2:
		{
		boolean exit1=false;
		while(!exit1) {
		System.out.println("1.Add\t2.Modify\t3.Block\t4.Remove\t5.View\t6.Exit\nEnter your choice: ");
		ch1=sc.nextInt();
		Student std=new Student();
		switch(ch1)
		{
		case 1:
		{
			System.out.println("Enter Studentid: ");
			id=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Studentname: ");
			Sname=sc.nextLine();
			System.out.println("Enter StudentStatus: ");
			S_Status=sc.nextLine();
		    std.addstudent(id, Sname, S_Status);
		break;
		}
//		case 2:
//		{
//			System.out.println("Enter the Studentid to modify: ");
//			id1=sc.nextInt();
//			int sid=sm.checkSid(id1);
//			if(sid==0)
//			{
//				System.out.println("Studentid is invalid.Please Enter valid id: ");
//				id=sc.nextInt();
//			}
//			else {
//
//				break;
//			}
//			sc.nextLine();
//			System.out.print("Enter the column name to update (StudentName or StudentStatus): ");
//	        columnName = sc.nextLine();
//	        System.out.print("Enter the new value: ");
//	        newValue = sc.nextLine();
//	        std.modifyStudent(id1,columnName,newValue);
//		break;
//		}
		case 2:
	    {
	
	       
	            System.out.println("Enter the Student ID to modify: ");
	            id1 = sc.nextInt();
	            while (true) {
	            int sid = sm.checkSid(id1);
	            if (sid == 0) {
	                System.out.println("Invalid Student ID. Please enter a valid ID: ");
	                id1=sc.nextInt();
	            } else {
	                break; 
	            }
	        }
	        sc.nextLine(); 

	        System.out.print("Enter the column name to update (StudentName or StudentStatus): ");
	        columnName = sc.nextLine();
	        System.out.print("Enter the new value: ");
	        newValue = sc.nextLine();
	        std.modifyStudent(id1, columnName, newValue);
	        break;
	    }

		case 3:
		{
			System.out.print("Enter the Studentid to block: ");
			id1=sc.nextInt();
			while (true) {
	            int sid = sm.checkSid(id1);
	            if (sid == 0) {
	                System.out.println("Invalid Student ID. Please enter a valid ID: ");
	                id1=sc.nextInt();
	            } else {
	                break; 
	            }
	        }
			std.blockStudent(id1);
		break;
		}
		case 4:
		{
			System.out.println("Enter the Studentid to remove: ");
			id2=sc.nextInt();
			while (true) {
	            int sid = sm.checkSid(id2);
	            if (sid == 0) {
	                System.out.println("Invalid Student ID. Please enter a valid ID: ");
	                id2=sc.nextInt();
	            } else {
	                break; 
	            }
	        }
			std.removeStudent(id2);
		break;
		}
		case 5:
		{
			std.viewStudent();
			break;
		}
		case 6:
			exit1=true;
			break;
		}
		}
		break;
		}
		case 3:
		{
			Student std=new Student();
			std.booksearch();
			break;
		}
		case 4:
		{
			sm.bookListInLibrary();
			break;
			
		}
		case 5:
		{
			sm.notReturnOnTime();
			break;
		}
		case 6:
		{
			exit2=true;
			break;
		}
		}
			}
		break;
		}
		case 2:
		{
			boolean exit3=false;
			int ch3;
			while(!exit3) {
				System.out.println("1.Request Book\n2.Return Book\n3.Renew Book\n4.See Booklist in library\n5.Exit\nEnter your choice: ");
				ch3=sc.nextInt();
				switch(ch3)
				{
				case 1:
				{
					sm.requestbook();
					break;
				}
				case 2:
				{
					sm.returnbook();
					break;
				}
				case 3:
				{
					sm.renewbook();
					break;
				}
				case 4:
				{
					sm.bookListInLibrary();
					break;
				}
				case 5:
				{
					exit3=true;
					break;
				}
				}
			}
			break;
		}
		case 3:
		{
			boolean exit4=false;
			int ch4;
			while(!exit4) {
				System.out.println("1.See BookList\t2.Generate Pdf\t3.Exit\nEnter choice: ");
				ch4=sc.nextInt();
				switch(ch4)
				{
				case 1:
					sm.book();
					break;
				case 2:
				{
					System.out.println("Generating Pdf report.....");
					pdf s=new pdf();
					int a[]=s.pdfgenerate();
					s.generatePdf(a);
					System.out.println("Pdf Generated Successfully");
					
					break;
				}
				case 3:
				{
					exit4=true;
					break;
				}
			}
			}
			break;
		}
		}
		
		
		sc.close();
		
	}
	
}
	    
	    
	    

	   



