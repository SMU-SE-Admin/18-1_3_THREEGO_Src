package se.smu.todolist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TodoModify extends JFrame implements ActionListener, ItemListener{

	private JPanel contentPane;
	private JTextField tfSubject;
	private JTextField tfWTD;
	private JButton btnComplete;

	private JComboBox cbDeadMonth;
	private JComboBox cbDeadDate;
	private JComboBox cbRDeadMonth;
	private JComboBox cbRDeadDate;

	private Vector<Object> todoData;
	private DefaultTableModel todoModel;
	private int row;
	
	private JLabel lblState;
	private JComboBox cbState;

	/**
	 * Create the frame.
	 */
	public TodoModify(final DefaultTableModel _todoModel, final Vector<Object> _todoData, final int _row) {
		setLocation(800, 300);
		setSize(550, 450);
		getContentPane().setLayout(null);
		
		todoModel = _todoModel;
		todoData = _todoData;
		row = _row;

		btnComplete = new JButton("완료");
		btnComplete.setBounds(374, 45, 112, 42);
		btnComplete.addActionListener(this);
		getContentPane().add(btnComplete);

		JLabel lblSubject = new JLabel("과목명");
		lblSubject.setBounds(57, 113, 83, 26);
		getContentPane().add(lblSubject);

		JLabel lblDeadine = new JLabel("마감일");
		lblDeadine.setBounds(57, 159, 57, 15);
		getContentPane().add(lblDeadine);

		JLabel lblRDeadline = new JLabel("실제 마감일");
		lblRDeadline.setBounds(57, 201, 83, 15);
		getContentPane().add(lblRDeadline);

		JLabel lblWTD = new JLabel("WHAT TO DO");
		lblWTD.setBounds(57, 286, 83, 15);
		getContentPane().add(lblWTD);

		JLabel lblToDoList = new JLabel("TO DO LIST");
		lblToDoList.setHorizontalAlignment(SwingConstants.CENTER);
		lblToDoList.setBounds(57, 45, 106, 42);
		getContentPane().add(lblToDoList);

		tfSubject = new JTextField();
		tfSubject.setBounds(162, 116, 324, 21);
		getContentPane().add(tfSubject);
		tfSubject.setColumns(10);

		cbDeadMonth = new JComboBox();
		cbDeadMonth.setModel(new DefaultComboBoxModel(getMonth()));
		cbDeadMonth.addItemListener(this);
		cbDeadMonth.setBounds(205, 156, 57, 21);
		getContentPane().add(cbDeadMonth);

		cbDeadDate = new JComboBox();
		cbDeadDate.setModel(new DefaultComboBoxModel(getDate(cbDeadMonth.getSelectedItem().toString())));
		cbDeadDate.setBounds(328, 156, 57, 21);
		getContentPane().add(cbDeadDate);

		cbRDeadMonth = new JComboBox();
		cbRDeadMonth.setModel(new DefaultComboBoxModel(getMonth()));
		cbRDeadMonth.setBounds(205, 198, 57, 21);
		getContentPane().add(cbRDeadMonth);

		cbRDeadDate = new JComboBox();
		cbRDeadDate.setModel(new DefaultComboBoxModel(getDate(cbRDeadMonth.getSelectedItem().toString())));
		cbRDeadDate.setBounds(328, 198, 57, 21);
		getContentPane().add(cbRDeadDate);

		tfWTD = new JTextField();
		tfWTD.setColumns(10);
		tfWTD.setBounds(162, 283, 324, 90);
		getContentPane().add(tfWTD);

		JLabel lblWave = new JLabel("~");
		lblWave.setBounds(289, 159, 27, 15);
		getContentPane().add(lblWave);

		JLabel lblWave2 = new JLabel("~");
		lblWave2.setBounds(289, 201, 27, 15);
		getContentPane().add(lblWave2);
		
		lblState = new JLabel("상태");
		lblState.setBounds(57, 242, 83, 15);
		getContentPane().add(lblState);
		
		cbState = new JComboBox();
		cbState.setModel(new DefaultComboBoxModel(new String[] {"신규","진행","해결"}));
		cbState.setBounds(259, 239, 57, 21);
		getContentPane().add(cbState);

		
		loadData(_todoData);
		setVisible(true);
	}

	private Vector<String> getMonth() {
		Vector<String> month = new Vector<String>();
		for (int i = 1; i <= 12; i++) {
			month.add(String.valueOf(i));
		}
		return month;
	}

	private Vector<String> getDate(String _month) {
		Vector<String> date = new Vector<String>();
		int month = Integer.parseInt(_month);
		switch (month) {
		case 2:
			for (int i = 1; i <= 28; i++) {
				date.add(String.valueOf(i));
			}
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			for (int i = 1; i <= 31; i++) {
				date.add(String.valueOf(i));
			}
			break;
		default:
			for (int i = 1; i <= 30; i++) {
				date.add(String.valueOf(i));
			}
			break;
		}

		return date;
	}

	public void itemStateChanged(ItemEvent e) {
		String month = e.getItem().toString();
		if (e.getSource() == cbDeadMonth) {
			cbDeadDate.setModel(new DefaultComboBoxModel(getDate(month)));
		} else if (e.getSource() == cbRDeadMonth) {
			cbRDeadDate.setModel(new DefaultComboBoxModel(getDate(month)));
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnComplete) {
			if (!tfSubject.getText().equals("") && !tfWTD.getText().equals("")) {
				Vector<Object> modi = new Vector<Object>();
				modi.add(0, todoData.get(0));
				modi.add(1, tfSubject.getText());
				modi.add(2, cbDeadMonth.getSelectedItem().toString()  + 
						"." + cbDeadDate.getSelectedItem().toString());
				modi.add(3, cbRDeadMonth.getSelectedItem().toString() +
						"." + cbRDeadDate.getSelectedItem().toString().toString());
				modi.add(4, cbState.getSelectedItem());
				modi.add(5, tfWTD.getText());
				modi.add(6, "변경");
				modi.add(7, "삭제");
				modi.add(8, "메모");
				todoModel.insertRow(row, modi);
				todoModel.removeRow(row+1);
				setVisible(false);
			}
			else {
				System.out.println("누락");
			}
		}
	}
	
	private void loadData(Vector<Object> data) {
		String[] deadLine = data.get(2).toString().split("\\.");
		String[] rdeadLine = data.get(3).toString().split("\\.");
		
		tfSubject.setText(data.get(1).toString());
		cbDeadMonth.setSelectedItem(deadLine[0]);
		cbDeadDate.setSelectedItem(deadLine[1]);
		cbRDeadMonth.setSelectedItem(rdeadLine[0]);
		cbRDeadDate.setSelectedItem(rdeadLine[1]);
		cbState.setSelectedItem(data.get(4).toString());
		tfWTD.setText(data.get(5).toString());
	}

}
