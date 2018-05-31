package se.smu;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import se.smu.db.DBConnection;

public class LoginFrame extends JFrame implements ActionListener {
	// 로그인 클래스에서 사용되는 변수들을 정의한다.
	private JTextField textField;
	private JPasswordField passwordField;
	JButton LoginButton;
	String id;

	// 기본 생성자
	public LoginFrame() {
		// Frame의 기본적인 타이틀과 크기, 위치를 설정한다
		setTitle("로그인");
		setSize(400, 400);
		setLocation(450, 300);
		getContentPane().setLayout(null);

		// 상명대학교 Label의 설정
		JLabel smLbl = new JLabel("상명대학교");
		smLbl.setFont(new Font("굴림체", Font.BOLD, 12));
		smLbl.setBounds(160, 60, 210, 31);
		getContentPane().add(smLbl);

		// Todo 관리 Label의 설정
		JLabel lblTodoManagement = new JLabel("과목별 TO DO LIST 관리 프로그램");
		lblTodoManagement.setFont(new Font("굴림체", Font.BOLD, 12));
		lblTodoManagement.setBounds(97, 84, 210, 31);
		getContentPane().add(lblTodoManagement);

		// ID Label의 설정
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("굴림체", Font.PLAIN, 20));
		lblId.setBounds(57, 137, 33, 31);
		getContentPane().add(lblId);

		// PWD Label의 설정
		JLabel lblPassword = new JLabel("P/W");
		lblPassword.setFont(new Font("굴림체", Font.PLAIN, 20));
		lblPassword.setBounds(57, 198, 85, 50);
		getContentPane().add(lblPassword);

		// ID textfield의 설정
		textField = new JTextField();
		textField.setText("");
		textField.setBounds(117, 127, 200, 50);
		getContentPane().add(textField);
		textField.setColumns(10);

		// PWD textfield의 설정
		passwordField = new JPasswordField();
		passwordField.setText("");
		passwordField.setColumns(10);
		passwordField.setBounds(117, 198, 200, 50);
		passwordField.setEchoChar('*');
		getContentPane().add(passwordField);

		// Login button의 설정
		LoginButton = new JButton("로그인");
		LoginButton.setFont(new Font("im", Font.BOLD, 18));
		LoginButton.setBounds(117, 280, 136, 31);
		getContentPane().add(LoginButton);

		// Login button 클릭 시 행동할 리스너를 등록한다.
		LoginButton.addActionListener(this);

		// 화면을 출력한다.
		setVisible(true);
	}

	public static void main(String[] args) {
		// 로그인 프레임 생성
		new LoginFrame();
	}

	public void actionPerformed(ActionEvent e) {
		// 로그인 버튼 클릭시 실행
		if (e.getSource() == LoginButton) {
			// id와 pwd가 공백인지 검사한다
			if (textField.getText().trim().equals("") || passwordField.getText().trim().equals("")) {
				// 빈공간이라면 오류메시지를 출력한다.
				JOptionPane.showMessageDialog(null, "ID 혹은 P/W를 입력해주세요", "ERROR!!", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				String id = textField.getText();
				String pwd = String.valueOf(passwordField.getPassword());

				// 데이터베이스를 연결한다.
				DBConnection db = new DBConnection();
				// 해당 아이디와 비밀번호가 데이터베이스에 존재하는지 검사한다.
				if (db.LogIn(id, pwd)) {
					// 존재한다면 다음 프레임을 실행시킨다.
					id = textField.getText();
					Frame fs = new MainFrame(id);
					fs.setVisible(true);
					this.setVisible(false);
				} else {
					// 존재하지 않는다면 오류메시지를 출력한다.
					JOptionPane.showMessageDialog(null, "ID 혹은 P/W를 확인해주세요", "ERROR!!", JOptionPane.ERROR_MESSAGE);
				}
				// 데이터베이스 연결 종료
				db.close();
			}
		}
	}
}