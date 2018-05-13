package se.smu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginFrame extends JFrame implements ActionListener {
	private JTextField textField;
	private JTextField textField_1;
	JButton LoginButton;
	
	public LoginFrame() {
		setTitle("로그인");
		setSize(400,400);
		getContentPane().setLayout(null);
	
		JLabel lblTodoManagement = new JLabel("ToDo Management");
		lblTodoManagement.setFont(new Font("굴림체", Font.BOLD, 25));
		lblTodoManagement.setBounds(97, 24, 210, 31);
		getContentPane().add(lblTodoManagement);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("굴림체", Font.PLAIN, 20));
		lblId.setBounds(75, 111, 33, 31);
		getContentPane().add(lblId);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("굴림체", Font.PLAIN, 20));
		lblPassword.setBounds(54, 198, 87, 31);
		getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(176, 116, 145, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(176, 203, 145, 24);
		getContentPane().add(textField_1);
		
		LoginButton = new JButton("로그인");
		LoginButton.setFont(new Font("굴림", Font.PLAIN, 25));
		LoginButton.setBounds(117, 280, 176, 71);
		getContentPane().add(LoginButton);
		LoginButton.addActionListener(this);
			
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new LoginFrame();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == LoginButton) {
			Frame fs = new SubFrame();
			fs.setVisible(true);
			this.setVisible(false);
		}
	}
}


