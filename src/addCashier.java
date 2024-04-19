import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class addCashier extends JPanel{
	
	JTextField userField;
	JButton btnAddCashier;
	private JPasswordField passwordField;
	private JLabel error;
	String err = "Enter username and password";
	String user,pass;
	/*
	 * Create the panel.
	 */
	public addCashier() {
		setLayout(null);
		setBounds(100,100,840,619);
		JLabel lblAddCashier = new JLabel("ADD CASHIER");
		lblAddCashier.setBounds(382,45,182,21);
		lblAddCashier.setFont(new Font("Tahoma",Font.PLAIN,17));
		add(lblAddCashier);
		
		JLabel lbluserName = new JLabel("User Name");
		lbluserName.setFont(new Font("Tahoma",Font.PLAIN,14));
		lbluserName.setBounds(246,104,124,21);
		add(lbluserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma",Font.PLAIN,14));
		lblPassword.setBounds(246,136,124,21);
		add(lblPassword);
		
		userField = new JTextField();
		userField.setBounds(436,106,147,20);
		add(userField);
		userField.setColumns(10);
		
		btnAddCashier = new JButton ("Add Cashier");
		btnAddCashier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user = userField.getText().trim();
				pass = passwordField.getText().toString().trim().toLowerCase();
				if(user.equals("")|| pass.equals("")) {
					error.setText(err);
				}else {
					error.setText("");
					DB.addCashier(user,pass);
					userField.setText("");
					passwordField.setText("");
				}
			}
		});
		btnAddCashier.setBounds(436,194,147,23);
		add(btnAddCashier);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(436,138,147,19);
		add(passwordField);
		
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(328,241,201,14);
		add(error);
	}
}
