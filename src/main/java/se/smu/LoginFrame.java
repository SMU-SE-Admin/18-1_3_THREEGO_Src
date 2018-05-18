package se.smu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginFrame extends JFrame implements ActionListener {
	private JTextField textField;
	private JPasswordField passwordField;
	JButton LoginButton;
	String id;
	
	public LoginFrame() {
		setTitle("로그인");
		setSize(400,400);
		getContentPane().setBackground(new Color(253, 199, 199));
		setLocation(450, 300);
		getContentPane().setLayout(null);
	
		JLabel smLbl=new JLabel("상명대학교");
		smLbl.setFont(new Font("굴림체", Font.BOLD, 12));
		smLbl.setBounds(160,60,210,31);
		getContentPane().add(smLbl);
		
		JLabel lblTodoManagement = new JLabel("과목별 TO DO LIST 관리 프로그램");
		lblTodoManagement.setFont(new Font("굴림체", Font.BOLD, 12));
		lblTodoManagement.setBounds(97, 84, 210, 31);
		getContentPane().add(lblTodoManagement);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("굴림체", Font.PLAIN, 20));
		lblId.setBounds(57, 137, 33, 31);
		getContentPane().add(lblId);
		
		JLabel lblPassword = new JLabel("P/W");
		lblPassword.setFont(new Font("굴림체", Font.PLAIN, 20));
		lblPassword.setBounds(57,198,85,50);
		getContentPane().add(lblPassword);	
		
		textField = new JTextField();
		textField.setText("");
		textField.setBounds(117, 127, 200, 50);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setText("");
		passwordField.setColumns(10);
		passwordField.setBounds(117,198, 200, 50);
		passwordField.setEchoChar('*');
		getContentPane().add(passwordField);
		
		LoginButton = new JButton("로그인");
		LoginButton.setFont(new Font("im", Font.BOLD, 18));
		LoginButton.setBounds(117, 280, 136, 31);
		LoginButton.setBackground(new Color(255,124,128));
		getContentPane().add(LoginButton);
			
		LoginButton.addActionListener(this);
			
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new LoginFrame();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == LoginButton) {
			if(textField.getText().equals("") || passwordField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "ID 혹은 P/W를 확인해주세요", "ERROR!!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			id = textField.getText();
			Frame fs = new MainFrame(id);
			fs.setVisible(true);
			this.setVisible(false);
		}
	}
}