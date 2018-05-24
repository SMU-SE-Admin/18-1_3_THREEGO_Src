package se.smu.memo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import se.smu.db.DBConnection;

public class Memo extends JFrame implements ActionListener{

	private JLabel lblMemo;
	private JButton btnEnroll;
	private JTextArea taMemo;
	private JScrollPane spMemo;
	
	private String id;
	private Vector<Object> rows;
	private JTable table;
	private int row;
	
	public Memo(String _id, Vector<Object> _rows, JTable _table, int _row) {
		setLocation(1580, 300);
		setSize(300,500);
		getContentPane().setLayout(null);
		
		id = _id;
		table = _table;
		rows = _rows;
		row = _row;
		
		lblMemo = new JLabel("MEMO");
		lblMemo.setBounds(20, 40, 60, 20);
		getContentPane().add(lblMemo);
		
		btnEnroll = new JButton("완료");
		btnEnroll.setBounds(201, 35, 71, 30);
		btnEnroll.addActionListener(this);
		getContentPane().add(btnEnroll);
		
		String memo = rows.get(6).toString();
		
		taMemo = new JTextArea(memo);
		spMemo = new JScrollPane(taMemo);
		spMemo.setBounds(20, 70, 252, 381);
		getContentPane().add(spMemo);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnEnroll) {
			Vector<Object> after = new Vector<Object>(rows);
			String memo = taMemo.getText();
			after.set(6, memo);
			table.setValueAt(memo, row, 6);
			DBConnection db = new DBConnection();
			db.updateTodo(id, rows, after);
			db.close();
			setVisible(false);
		}
	}
}
