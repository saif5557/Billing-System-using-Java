import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.swing.text.Document;
import javax.swing.text.html.parser.Element;



public class pdfGenerator {
	
	public static void makePdf(Object[] data,long total,int inv) {
		String invoice="_invoice_.pdf";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		Document document = new Document();
		
		
		PdfWriter writer;
		
		try {
			writer = PdfWriter.getInstance(document,new FileOutputStream(invoice));
			document.open();
			Font f1 = new Font(Font.FontFamily.UNDEFINED,18,Font.BOLD);
			Paragraph p1 = new Paragraph("CADDEY STORE",f1);
			Paragraph p2 = new Paragraph("Main Market C Block Alpha 1 Greater Noida");
			Paragraph p3 = new Paragraph("+91 8887475146\n\n\n");
			Paragraph p4 = new Paragraph("\n\nGrand Total: "+total,f1);
			Paragraph p5 = new Paragraph("\nThank you for visiting us...!!\nReturn/Exchange not possible without bill.");
			
			p1.setAlignment(Element.ALIGN_CENTER);
			p3.setAlignment(Element.ALIGN_CENTER);
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(p2);
			document.add(p3);
			Font f2 = new Font(Font.FontFamily.UNDEFINED,8,Font.PLAIN);
			Phrase phrase = new Phrase("Time/Date: "+dateFormat.format(date),f2);
			PdfContentByte canvas = writer.getDirectContent();
			ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,phrase,40,740,0);
			Phrase invNo = new Phrase("Invoice No. "+inv,f2);
			PdfContentByte canv = writer.getDirtectContent();
			ColumnText.showTextAligned(canv,Element.ALIGN_LEFT,invNo,510,785,0);
			Image image;
				try {
					image = Image.getInstance("C:\\\\Users\\\\saifm\\\\eclipse-workspace\\\\Billing-System\\\\images\\\\logo.png");
					image.setAbsolutePosition(10f,730f);
					document.add(image);
				}catch(MalformedURLException e) {
					e.printStackTrace();
				}catch(IOException e) {
					e.printStackTrace();
				}
				
				PdfPTable table = new PdfPTable(5);
				float[] columnWidths = new float[] {15f,30f,10f,10f,15f};
				table.setWidths(columnWidths);
				table.addCell("Product ID");
				table.addCell("Item Detail");
				table.addCell("Unit Price");
				table.addCell("Quantity");
				table.addCell("Total Price");
				for(int aw=0;aw<data.length;aw++) {
					table.addCell(data[aw]+"");
				}
				document.add(table);
				document.add(p4);
				document.add(p5);
				
				document.close();
				sendIn2printer(invoice);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(DocumentException e) {
			e.printStackTrace();
		}
	}
	public static void makePdf2(Object[] data,long total) {
		String sale="_sale_.pdf";
		Document document = new Document();
		
		PdfWriter writer;
		try {
			writer = PdfWriter.getInstance(document,new FileOutputStream(sale));
			document.open();
			Font f1 = new Font(Font.FontFamily.UNDEFINED,18,Font.BOLD);
			Paragraph p1 = new Paragraph("CADDEY STORE",f1);
			Paragraph p2 = new Paragraph("Main Market C Block Alpha 1 Greater Noida");
			Paragraph p3 = new Paragraph("+91 8887475146\n\n\n");
			Paragraph p4 = new Paragraph("\n\nGrand Total: "+total,f1);
			
			p1.setAlignment(Element.ALIGN_CENTER);
			p3.setAlignment(Element.ALIGN_CENTER);
			p2.setAlignment(Element.ALIGN_CENTER);
			document.add(p1);
			document.add(p2);
			document.add(p3);
			
			PdfPTable table = new PdfPTable(4);
			table.addCell("Date");
			table.addCell("Product ID");
			table.addCell("Company");
			table.addCell("Sale");
			for(int aw=0;aw<data.length;aw++) {
				table.addCell(data[aw]+"");
			}
			document.add(table);
			document.add(p4);
			
			document.close();
			sendIn2printer(sale);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendIn2printer(String file) {
		//The desktop api can help calling other applications in our machine and also many other features..
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.print(new File(file));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String []args) {
		
	}
}
