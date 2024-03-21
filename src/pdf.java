package src;

	import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
	 
	public class pdf {
	    private static final String pdfDirectory = "D:/jdk-11.0.2/Library/pdf";
	    private static final String pdfName = "hello.pdf";
	    public void generatePdf(int arr[]) {
	    	Document document = new Document();
	        try {
	            // Ensure that the directory exists
	            File directory = new File(pdfDirectory);
	            if (!directory.exists()) {
	                directory.mkdirs(); // Create directory if it doesn't exist
	            }
	 
	            // Create the PDF file
	            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File(pdfDirectory + pdfName)));
	            document.open(); 
	            Font bigBoldFont = FontFactory.getFont(FontFactory.defaultEncoding, 15);
	            Paragraph text = new Paragraph("Books Availability Report", bigBoldFont);
	            text.setAlignment(Element.ALIGN_CENTER);
	            document.add(text);
	            document.add(new Paragraph("\n"));
	            
	            String[][] tableData = {
	            		{"Total Number of books", "Number of books in rotation", "Number of books available in library"},    
	            		{arr[0]+"", (arr[0]-arr[1])+"", arr[1]+""}         };
	            		
	 
	            PdfPTable table = new PdfPTable(3);             
	            for (String header : tableData[0]) {
	            	PdfPCell headerCell = new PdfPCell(new Paragraph(header));
	            	table.addCell(headerCell);             }             
	            // Add data rows
	            for (int i = 1; i < tableData.length; i++) {
	            	for (String data : tableData[i]) {
	            		PdfPCell dataCell = new PdfPCell(new Paragraph(data));
	            		table.addCell(dataCell);
	            		}
	            	}
	            document.add(table);
	            
	 
	            document.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    public int[] pdfgenerate()
	    {
	    	int[] booksInfo=new int[2];
	    	JDBC jdbc=new JDBC();
	    	Connection con =jdbc.establishConnection();
	    	try {
	    	    String queryTotalBooks = "SELECT COUNT(BookTitle) AS b_count FROM book";
	    	    String queryBooksInLibrary = "SELECT COUNT(BookTitle) FROM book WHERE Bstatus = ?";
	    	    
	    	    PreparedStatement psTotalBooks = con.prepareStatement(queryTotalBooks);
	    	    PreparedStatement psBooksInLibrary = con.prepareStatement(queryBooksInLibrary);
	    	    psBooksInLibrary.setString(1, "Available");
	    	    ResultSet totalBooksResultSet = psTotalBooks.executeQuery();
	    	    ResultSet booksInLibraryResultSet = psBooksInLibrary.executeQuery();
	    	    int totalBooks = 0;
	    	    int booksInLibrary = 0;
	    	    if (totalBooksResultSet.next() && booksInLibraryResultSet.next()) {
	    	        totalBooks = totalBooksResultSet.getInt("b_count");
	    	        booksInLibrary = booksInLibraryResultSet.getInt(1);
	    	    }
	    	    booksInfo[0]=totalBooks;
	    	    booksInfo[1]=booksInLibrary;
	    	    
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}
	    	return booksInfo;
	    	
	    }
	}
