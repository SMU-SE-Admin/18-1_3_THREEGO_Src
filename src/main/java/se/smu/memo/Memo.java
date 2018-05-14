package se.smu.memo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class Memo extends JFrame implements ActionListener{

	private JLabel lblMemo;
	private JButton btnEnroll;
	private JTextArea taMemo;
	private JScrollPane spMemo;
	
	private JTable tbl;
	private int row;
	private int column;
	
	public Memo(JTable tbl, int row, int column) {
		setLocation(1300, 300);
		setSize(300,500);
		getContentPane().setLayout(null);
		
		this.tbl = tbl;
		this.row = row;
		this.column = column;
		
		lblMemo = new JLabel("MEMO");
		lblMemo.setBounds(20, 40, 60, 20);
		getContentPane().add(lblMemo);
		
		btnEnroll = new JButton("완료");
		btnEnroll.setBounds(201, 35, 71, 30);
		btnEnroll.addActionListener(this);
		getContentPane().add(btnEnroll);
		
		Object[] memoVal = (Object[]) tbl.getValueAt(row, column);
		String memo = memoVal[1].toString();
		taMemo = new JTextArea(memo);
		spMemo = new JScrollPane(taMemo);
		spMemo.setBounds(20, 70, 252, 381);
		getContentPane().add(spMemo);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnEnroll) {
			String memo = taMemo.getText();
			Object[] memoVal = {"메모", memo};
			tbl.setValueAt(memoVal, row, column);
			setVisible(false);
		}
	}
	
}
