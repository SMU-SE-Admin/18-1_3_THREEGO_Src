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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import se.smu.db.DBConnection;

public class TodoModify extends JFrame implements ActionListener, ItemListener{

	private JTextField tfmSubject;
	private JTextArea tamWTD;
	private JButton btnmComplete;

	private JComboBox cbmDeadMonth;
	private JComboBox cbmDeadDate;
	private JComboBox cbmRDeadMonth;
	private JComboBox cbmRDeadDate;

	private Vector<Object> todoData;
	private DefaultTableModel todoModel;
	private int row;
	private String id;
	
	private JLabel lblmState;
	private JComboBox cbmState;
	private JComboBox cbmImportance;
	
	private TodoController mtodoController;

	/**
	 * Create the frame.
	 */
	public TodoModify(String _id, final JTable table, final DefaultTableModel _todoModel, final Vector<Object> _todoData, final int _row) {
		setLocation(800, 300);
		setSize(550, 450);
		getContentPane().setLayout(null);
		
		mtodoController = new TodoController();
		
		todoModel = _todoModel;
		todoData = _todoData;
		row = table.convertRowIndexToModel(_row);
		id = _id;

		btnmComplete = new JButton("완료");
		btnmComplete.setBounds(374, 45, 112, 42);
		btnmComplete.addActionListener(this);
		getContentPane().add(btnmComplete);

		JLabel mlblSubject = new JLabel("과목명");
		mlblSubject.setBounds(57, 113, 83, 26);
		getContentPane().add(mlblSubject);

		JLabel mlblDeadine = new JLabel("마감일");
		mlblDeadine.setBounds(57, 159, 57, 15);
		getContentPane().add(mlblDeadine);

		JLabel mlblRDeadline = new JLabel("실제 마감일");
		mlblRDeadline.setBounds(57, 201, 83, 15);
		getContentPane().add(mlblRDeadline);

		JLabel mlblWTD = new JLabel("WHAT TO DO");
		mlblWTD.setBounds(57, 316, 83, 15);
		getContentPane().add(mlblWTD);

		JLabel mlblToDoList = new JLabel("TO DO LIST");
		mlblToDoList.setHorizontalAlignment(SwingConstants.CENTER);
		mlblToDoList.setBounds(57, 45, 106, 42);
		getContentPane().add(mlblToDoList);

		tfmSubject = new JTextField();
		tfmSubject.setBounds(162, 116, 324, 21);
		getContentPane().add(tfmSubject);
		tfmSubject.setColumns(10);

		cbmDeadMonth = new JComboBox();
		cbmDeadMonth.setModel(new DefaultComboBoxModel(mtodoController.getMonth()));
		cbmDeadMonth.addItemListener(this);
		cbmDeadMonth.setBounds(205, 156, 57, 21);
		getContentPane().add(cbmDeadMonth);

		cbmDeadDate = new JComboBox();
		cbmDeadDate.setModel(new DefaultComboBoxModel(mtodoController.getDate(cbmDeadMonth.getSelectedItem().toString())));
		cbmDeadDate.setBounds(328, 156, 57, 21);
		getContentPane().add(cbmDeadDate);

		cbmRDeadMonth = new JComboBox();
		cbmRDeadMonth.setModel(new DefaultComboBoxModel(mtodoController.getMonth()));
		cbmRDeadMonth.setBounds(205, 198, 57, 21);
		getContentPane().add(cbmRDeadMonth);

		cbmRDeadDate = new JComboBox();
		cbmRDeadDate.setModel(new DefaultComboBoxModel(mtodoController.getDate(cbmRDeadMonth.getSelectedItem().toString())));
		cbmRDeadDate.setBounds(328, 198, 57, 21);
		getContentPane().add(cbmRDeadDate);

		JLabel lblWave = new JLabel("~");
		lblWave.setBounds(289, 159, 27, 15);
		getContentPane().add(lblWave);

		JLabel lblWave2 = new JLabel("~");
		lblWave2.setBounds(289, 201, 27, 15);
		getContentPane().add(lblWave2);
		
		lblmState = new JLabel("상태");
		lblmState.setBounds(57, 242, 83, 15);
		getContentPane().add(lblmState);
		
		cbmState = new JComboBox();
		cbmState.setModel(new DefaultComboBoxModel(new String[] {"신규","진행","해결"}));
		cbmState.setBounds(259, 239, 57, 21);
		getContentPane().add(cbmState);
		
		tamWTD = new JTextArea();
		JScrollPane spWTD = new JScrollPane(tamWTD);
		spWTD.setBounds(162, 311, 324, 90);
		getContentPane().add(spWTD);
		
		JLabel lblImportance = new JLabel("중요도");
		lblImportance.setBounds(57, 279, 83, 15);
		getContentPane().add(lblImportance);
		
		cbmImportance = new JComboBox();
		cbmImportance.setBounds(259, 276, 57, 21);
		cbmImportance.setModel(new DefaultComboBoxModel(new String[] {"낮음","보통","높음"}));
		getContentPane().add(cbmImportance);

		
		loadData(_todoData);
		setVisible(true);
	}

	public void itemStateChanged(ItemEvent e) {
		String month = e.getItem().toString();
		if (e.getSource() == cbmDeadMonth) {
			cbmDeadDate.setModel(new DefaultComboBoxModel(mtodoController.getDate(month)));
		} else if (e.getSource() == cbmRDeadMonth) {
			cbmRDeadDate.setModel(new DefaultComboBoxModel(mtodoController.getDate(month)));
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnmComplete) {
			if (!tfmSubject.getText().trim().equals("") && !tamWTD.getText().trim().equals("")) {
				Vector<Object> modi = new Vector<Object>();
				
				String mdeadline = String.format("%02d", Integer.parseInt(cbmDeadMonth.getSelectedItem().toString())) + 
						"." + String.format("%02d", Integer.parseInt(cbmDeadDate.getSelectedItem().toString()));
				String mrdeadline = String.format("%02d", Integer.parseInt(cbmRDeadMonth.getSelectedItem().toString()))  + 
						"." + String.format("%02d", Integer.parseInt(cbmRDeadDate.getSelectedItem().toString()));
				
				
				modi.add(0, cbmImportance.getSelectedIndex());
				modi.add(1, tfmSubject.getText());
				modi.add(2, cbmDeadMonth.getSelectedItem().toString()  + 
						"." + cbmDeadDate.getSelectedItem().toString());
				modi.add(3, cbmRDeadMonth.getSelectedItem().toString() +
						"." + cbmRDeadDate.getSelectedItem().toString().toString());
				modi.add(4, cbmState.getSelectedItem());
				modi.add(5, tamWTD.getText());
				modi.add(6, todoData.get(6));
				
				if(mtodoController.checkDeadline(mdeadline, mrdeadline)) {
					JOptionPane.showMessageDialog(null, "마감일이 실제 마감일보다 빠릅니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!mtodoController.checkDupl(todoModel, modi)) {
					DBConnection db = new DBConnection();
					db.updateTodo(id, todoData, modi);
					db.close();
				}
					todoModel.insertRow(row, modi);
					todoModel.removeRow(row+1);
				
				
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
		
		tfmSubject.setText(data.get(1).toString());
		cbmDeadMonth.setSelectedItem(deadLine[0]);
		cbmDeadDate.setSelectedItem(deadLine[1]);
		cbmRDeadMonth.setSelectedItem(rdeadLine[0]);
		cbmRDeadDate.setSelectedItem(rdeadLine[1]);
		cbmState.setSelectedItem(data.get(4).toString());
		cbmImportance.setSelectedIndex((Integer)data.get(0));
		tamWTD.setText(data.get(5).toString());
	}

}
