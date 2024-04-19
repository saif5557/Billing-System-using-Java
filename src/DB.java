import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class DB {
	
	public static Connection DBConnection() {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/store","root","Saif@2002");
			System.out.println("Database is connected !");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Do not connect to DB - Error:"+e);
		}
		return con;
	}
	public static void addProductToDB(String id,String detail,String comp,int quan) {
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			st.executeUpdate("INSERT INTO stock VALUES('"+id+"','"+detail+"','"+comp+"',"+quan+");");
			JOptionPane.showMessageDialog(null, "Product added to database");
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	public static void updateProductToDB(String id,String detail,String comp,int quan) {
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			int status = st.executeUpdate("UPDATE stock set Detail = '"+detail+"',Company = '"+comp+"',Quantity = "+quan+" WHERE ProductID ='"+id+"';");
			if(status == 1) {
				JOptionPane.showMessageDialog(null, "Product updated");
			}else {
				JOptionPane.showMessageDialog(null, "ProductID not found!");
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	public static void deleteProductToDB(String id) {
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			int status = st.executeUpdate("DELETE from stock WHERE ProductID ='"+id+"';");
			if(status == 1) {
				JOptionPane.showMessageDialog(null, "Product deleted");
				
			}else {
				JOptionPane.showMessageDialog(null, "ProductID not found");
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	public static void searchProduct(String id) {
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from stock WHERE ProductID ='"+id+"';");
			if(!rs.next()) {
				JOptionPane.showMessageDialog(null, "No product found with this id!");
			}else {
				JOptionPane.showMessageDialog(null, "ProductID: "+id+"\nQuantity: "+rs.getString("Quantity"));
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	public static void searchCashier(String email) {
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from users WHERE Email ='"+email+"';");
			if(!rs.next()) {
				JOptionPane.showMessageDialog(null, "No cashier found with this email!");
			}else {
				JOptionPane.showMessageDialog(null, "Email: "+email+"\nPassword: "+rs.getString("Password"));
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	public static boolean varifyLogin(String email,String pass) {
		boolean login = false;
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from users WHERE Email ='"+email+"' and Password = '"+pass+"';");
			if(!rs.next()) {
			     login = false;
			}else {
				login = true;
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			e.printStackTrace();
		}
		return login;
	}
	public static void addCashier(String user,String pass) {
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			st.executeUpdate("INSERT INTO users VALUES('"+user+"','"+pass+"');");
			JOptionPane.showMessageDialog(null, "Cashier added to database");
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	public static void deleteCashier(String user,String pass) {
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			int status = st.executeUpdate("DELETE from users WHERE Email='"+user+"' AND Password ='"+pass+"';");
			if(status == 1) {
				JOptionPane.showMessageDialog(null, "Cashier deleted");
			}else {
				JOptionPane.showMessageDialog(null, "Cashier not found!");
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	public static String searchPDetail(String id,int q) {
		Connection con = DBConnection();
		String rt = "";
		try {
			int quan;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from stock WHERE ProductID = '"+id+"';");
			if(!rs.next()) {
				rt ="nill";
			}else {
				quan = Integer.parseInt(rs.getString("Quantity"))-q;
				if(quan<0) {
					rt ="item is out of stock";
				}else {
					rt = rs.getString("Detail")+"%"+rs.getString("Company");
					st.executeUpdate("UPDATE stock set Quantity ="+quan+"WHERE ProductID ='"+id+"';");
				}
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return rt;
	}
	public static void addSaleToDB(Object data[],ArrayList<String> comp,String name) {
		Connection con = DBConnection();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String d=dateFormat.format(date);
		try {
			Statement st = con.createStatement();
			for(int x=0;x<data.length;x=x+5) {
				st.executeUpdate("INSERT INTO sale VALUES('"+data[x]+"','"+comp.get(0)+"','"+d+"','"+data[x+3]+"',"+data[x+4]+",'"+name+"');");
				comp.remove(0);
			}
			con.close();
			
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	public static ArrayList<String> getSale(String date,String comp){
		String q;
		ArrayList<String> r = new ArrayList<String>();
		
		if(comp.equals("All")) {
			q="Select * from sale WHERE Date = '"+date+"';";
		}else {
			q="Select * from sale WHERE Date ='"+date+"' AND Company ='"+comp+"';";
		}
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q);
			while(rs.next()) {
				r.add(rs.getString("Date"));
				r.add(rs.getString("ProductID"));
				r.add(rs.getString("Company"));
				r.add(rs.getString("Payment"));
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return r;
		
	}
	public static ArrayList<String> showStock(String comp){
		String q;
		ArrayList<String> r = new ArrayList<String>();
		if(comp.equals("All")) {
			q="Select * from stock;";
		}else {
			q = "Select * from stock WHERE Company ='"+comp+"''";
		}
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q);
			while(rs.next()) {
				r.add(rs.getString("ProductID"));
				r.add(rs.getString("Detail"));
				r.add(rs.getString("Company"));
				r.add(rs.getString("Quantity"));
				
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return r;
	}
	
	public static String getPDetail(String id,int q) {
		Connection con = DBConnection();
		String rt="";
		try {
			int quan;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from stock WHERE ProductID ='"+id+"';");
			if(!rs.next()) {
				rt="Nill";
			}else {
				quan = Integer.parseInt(rs.getString("Quantity"))-q;
				if(quan<0) {
					rt="item is out of stock";
				}else {
					rt=rs.getString("Detail")+"%"+rs.getString("Company");
					st.executeUpdate("UPDATE stock set Quantity ="+quan+"WHERE ProductID ='"+id+"';");
				}
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			e.printStackTrace();
		}
		return rt;
	}
	
	public static ArrayList<String> searchP(String id){
		Connection con = DBConnection();
		ArrayList<String> data = new ArrayList<String>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from stock WHERE ProductID = '"+id+"';");
			if(rs.next()) {
				data.add(rs.getString("Detail"));
				data.add(rs.getString("Company"));
				data.add(rs.getString("Quantity"));
			}
			con.close();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	public static void updateProduct(String id ,int quan) {
		Connection con = DBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from stock WHERE ProductID = '"+id+"';");
			int q=0;
			if(rs.next()) {
				q = Integer.parseInt(rs.getString("Quantity"))+quan;
				st.executeUpdate("UPDATE stock set Quantity = "+q+" WHERE ProductID = '"+id+"';");
				
			}
			con.close();
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
