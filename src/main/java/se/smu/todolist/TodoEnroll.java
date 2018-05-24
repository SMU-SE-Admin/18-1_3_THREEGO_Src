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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import se.smu.db.DBConnection;

public class TodoEnroll extends JFrame implements ItemListener, ActionListener {

	private JTextField tfSubject;
	private JTextArea taWTD;
	private JButton btnComplete;

	private JComboBox cbDeadMonth;
	private JComboBox cbDeadDate;
	private JComboBox cbRDeadMonth;
	private JComboBox cbRDeadDate;

	private Vector<Object> todoData;
	private String id;
	
	private DefaultTableModel todoModel;
	private JLabel lblState;
	private JComboBox cbState;
	private JComboBox cbImportance;

	/**
	 * Create the frame.
	 */
	public TodoEnroll(String _id, final DefaultTableModel _todoModel, final Vector<Object> _todoData) {
		setLocation(800, 300);
		setSize(550, 450);
		getContentPane().setLayout(null);

		todoModel = _todoModel;
		todoData = _todoData;
		id = _id;

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
		lblWTD.setBounds(57, 316, 83, 15);
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
		cbState.setModel(new DefaultComboBoxModel(new String[] { "신규", "진행", "해결" }));
		cbState.setBounds(259, 239, 57, 21);
		getContentPane().add(cbState);
		
		taWTD = new JTextArea();
		JScrollPane spWTD = new JScrollPane(taWTD);
		spWTD.setBounds(162, 311, 324, 90);
		getContentPane().add(spWTD);
		
		JLabel lblImportance = new JLabel("중요도");
		lblImportance.setBounds(57, 279, 83, 15);
		getContentPane().add(lblImportance);
		
		cbImportance = new JComboBox();
		cbImportance.setBounds(259, 276, 57, 21);
		cbImportance.setModel(new DefaultComboBoxModel(new String[] { "낮음", "보통", "높음" }));
		getContentPane().add(cbImportance);

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
		
		if(month == 2) {
			for (int i = 1; i <= 28; i++) {
				date.add(String.valueOf(i));
			}
		}else if(month == 1 || month == 3 || month == 5 || month == 7
				|| month == 8 || month == 10 || month == 12) {
			for (int i = 1; i <= 31; i++) {
				date.add(String.valueOf(i));
			}
		}else {
			for (int i = 1; i <= 30; i++) {
				date.add(String.valueOf(i));
			}
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
			if (!tfSubject.getText().equals("") && !taWTD.getText().equals("")) {
				Vector<Object> data = new Vector<Object>(todoData);
				data.add(cbImportance.getSelectedIndex());
				data.add(tfSubject.getText());
				data.add(cbDeadMonth.getSelectedItem().toString()  + 
						"." + cbDeadDate.getSelectedItem().toString());
				data.add(cbRDeadMonth.getSelectedItem().toString() +
						"." + cbRDeadDate.getSelectedItem().toString().toString());
				data.add(cbState.getSelectedItem());
				data.add(taWTD.getText());
				data.add("");
				
				if(!checkDupl(todoModel, data)) {
					todoModel.addRow(data);
					DBConnection con = new DBConnection();
					con.setTodo(id, data);
					con.close();
				}
				else {
					JOptionPane.showMessageDialog(null, "중복된 과목이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				setVisible(false);
			} else {
			}
		}
	}

	
	private boolean checkDupl(DefaultTableModel tm, Vector<Object> row) {
		Vector data = tm.getDataVector();
		for(int i=0; i<data.size(); i++) {
			Vector tmp = (Vector) data.get(i);
			int count = 0;
			for(int j=0; j<row.size()-1; j++) {
				if(tmp.get(j).equals(row.get(j))){
					count++;
				}
			}
			if(count == 6)
				return true;
			
		}
		return false;
	}
}
