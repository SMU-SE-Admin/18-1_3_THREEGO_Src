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

public class TodoModify extends JFrame implements ActionListener, ItemListener{

	private JPanel contentPane;
	private JTextField tfSubject;
	private JTextArea taWTD;
	private JButton btnComplete;

	private JComboBox cbDeadMonth;
	private JComboBox cbDeadDate;
	private JComboBox cbRDeadMonth;
	private JComboBox cbRDeadDate;

	private Vector<Object> todoData;
	private DefaultTableModel todoModel;
	private int row;
	private String id;
	
	private JLabel lblState;
	private JComboBox cbState;
	private JComboBox cbImportance;

	/**
	 * Create the frame.
	 */
	public TodoModify(String _id, final DefaultTableModel _todoModel, final Vector<Object> _todoData, final int _row) {
		setLocation(800, 300);
		setSize(550, 450);
		getContentPane().setLayout(null);
		
		todoModel = _todoModel;
		todoData = _todoData;
		row = _row;
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
		cbState.setModel(new DefaultComboBoxModel(new String[] {"신규","진행","해결"}));
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
		cbImportance.setModel(new DefaultComboBoxModel(new String[] {"낮음","보통","높음"}));
		getContentPane().add(cbImportance);

		
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
			if (!tfSubject.getText().equals("") && !taWTD.getText().equals("")) {
				Vector<Object> modi = new Vector<Object>();
				modi.add(0, cbImportance.getSelectedIndex());
				modi.add(1, tfSubject.getText());
				modi.add(2, cbDeadMonth.getSelectedItem().toString()  + 
						"." + cbDeadDate.getSelectedItem().toString());
				modi.add(3, cbRDeadMonth.getSelectedItem().toString() +
						"." + cbRDeadDate.getSelectedItem().toString().toString());
				modi.add(4, cbState.getSelectedItem());
				modi.add(5, taWTD.getText());
				modi.add(6, todoData.get(6));
				
				if(!checkDupl(todoModel, modi)) {
					DBConnection db = new DBConnection();
					db.updateTodo(id, todoData, modi);
					db.close();
					
					todoModel.insertRow(row, modi);
					todoModel.removeRow(row+1);
				}else {
					JOptionPane.showMessageDialog(null, "중복된 과목이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				setVisible(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "누락된 곳이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
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
		cbImportance.setSelectedIndex((Integer)data.get(0));
		taWTD.setText(data.get(5).toString());
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
