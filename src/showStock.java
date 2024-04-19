import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class showStock extends JPanel {
	private JTable stockTable;
	JComboBox<String> comp;
	DefaultTableModel model;
	/*
	 * Create the panel.
	 */
	public showStock() {
		setLayout(null);
		setBounds(100,100,840,619);
		JLabel lblstock = new JLabel("AVAILABLE STOCK");
		lblstock.setBounds(328,26,182,21);
		lblstock.setFont(new Font("Tahoma",Font.PLAIN,17));
		add(lblstock);
		
		model = new DefaultTableModel();
		stockTable = new JTable(model);
		stockTable.setBounds(98,112,645,397);
		add(stockTable);
		model.addColumn("Product ID");
		model.addColumn("Product Detail");
		model.addColumn("Company");
		model.addColumn("Quantity");
		JScrollPane scroll = new JScrollPane(stockTable);
		scroll.setBounds(98,112,645,397);
		add(scroll);
		
		comp = new JComboBox<String>();
		comp.setBackground(Color.WHITE);
		comp.setBounds(583,81,160,20);
		add(comp);
		comp.addItem("All");
		comp.addItem("General");
		comp.addItem("Mats & Rugs");
		comp.addItem("N/S & Electic");
		comp.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				updateTable();
			}
		});
		
		JLabel lblCompany = new JLabel("Company");
		lblCompany.setBounds(582,68,161,14);
		add(lblCompany);
		
		JButton btnExportToExcel = new JButton("Export to Excel");
		btnExportToExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toExcel(stockTable,new File("availableStock.xls"));
				JOptionPane.showMessageDialog(null, "Export file created");
			}
		});
		btnExportToExcel.setBounds(605,525,138,23);
		add(btnExportToExcel);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		btnRefresh.setBounds(457,525,138,23);
		add(btnRefresh);
		updateTable();
		
	}
	
	public void updateTable() {
		model.setRowCount(0);
		ArrayList<String> stock = new ArrayList<>();
		stock = DB.showStock(comp.getSelectedItem().toString());
		for(int x=0;x<stock.size();x+=4) {
			model.addRow(new Object[] {stock.get(x),stock.get(x+1),stock.get(x+2),stock.get(x+3)});
			
		}
	}
	
	
	public void toExcel(JTable table,File file) {
		try {
			TableModel model = table.getModel();
			FileWriter excel = new FileWriter(file);
			
			for(int i=0;i<model.getColumnCount();i++) {
				excel.write(model.getColumnName(i)+"\t");
			}
			
			excel.write("\n");
			
			for(int i=0;i<model.getRowCount();i++) {
				for(int j=0;j<model.getColumnCount();j++) {
					excel.write(model.getValueAt(i, j).toString()+"\t");
				}
				excel.write("\n");
			}
			
			excel.close();
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
