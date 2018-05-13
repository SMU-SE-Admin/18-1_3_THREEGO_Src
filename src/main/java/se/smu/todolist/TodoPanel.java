package se.smu.todolist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;

public class TodoPanel extends JPanel {
	private JTable tblTodo;
	private String[] columns = {"중요도", "과목명", "마감일", "실제 마감일", "상태", "WHAT TO DO", "변경/삭제/메모"};
	private DefaultTableModel todoModel = new DefaultTableModel(columns, 0);

	/**
	 * Create the panel.
	 */
	public TodoPanel() {
		setLayout(null);
		JLabel lblTitle = new JLabel("To Do List");
		lblTitle.setBounds(500, 5, 100, 30);
		add(lblTitle);
		
		tblTodo = new JTable(todoModel);
		JScrollPane sp = new JScrollPane(tblTodo);
		sp.setBounds(510, 50, 450, 400);
		add(sp);				
		
		JButton btnEnroll = new JButton("등록");
		btnEnroll.setBounds(800, 5, 100, 30);
		btnEnroll.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				todoModel.addRow(new String[] {"낮음", "수학", "00", "00", "모름", "헤헤", "변삭메"});
			}
		});
		add(btnEnroll);
	}
}