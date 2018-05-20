package se.smu.todolist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import se.smu.alarm.Alarm;
import se.smu.db.DBConnection;
import se.smu.memo.Memo;

import javax.swing.JScrollBar;
import java.awt.Scrollbar;

public class TodoPanel extends JPanel implements ActionListener {
	private Vector<Object> todoData = new Vector<Object>();
	private String id;
	
	private JButton btnEnroll;
	private JTable tblTodo;
	private Vector<Object> columns;
	private DefaultTableModel todoModel;
	public Vector<TodoInfo> rowdata = new Vector<TodoInfo>();
	private JButton hidebutton;
	

	public TodoPanel(String _id) {
		setLayout(null);
		setSize(1255,400);
		JLabel lblTitle = new JLabel("To Do List");
		lblTitle.setBounds(514, 17, 121, 18);
		add(lblTitle);
		this.id = _id;

		columns = initColumn();
		todoModel = new DefaultTableModel(columns, 0);
		
		Alarm alarm = new Alarm(id);
		
		refreshTable(id);
		
		tblTodo = new JTable(todoModel);
		TodoSorting todoSorting = new TodoSorting(tblTodo, todoModel);
		
		tblTodo.getColumnModel().getColumn(0).setCellEditor(new TodoTableCell(id, "중요도", tblTodo));
		tblTodo.getColumnModel().getColumn(0).setCellRenderer(new TodoTableCell(id, "중요도", tblTodo));
		tblTodo.getColumnModel().getColumn(6).setCellEditor(new TodoTableCell(id, "변경", tblTodo));
		tblTodo.getColumnModel().getColumn(6).setCellRenderer(new TodoTableCell(id, "변경", tblTodo));
		tblTodo.getColumnModel().getColumn(7).setCellEditor(new TodoTableCell(id, "삭제", tblTodo));
		tblTodo.getColumnModel().getColumn(7).setCellRenderer(new TodoTableCell(id, "삭제", tblTodo));
		tblTodo.getColumnModel().getColumn(8).setCellEditor(new TodoTableCell(id, "메모", tblTodo));
		tblTodo.getColumnModel().getColumn(8).setCellRenderer(new TodoTableCell(id, "메모", tblTodo));
		JScrollPane sp = new JScrollPane(tblTodo);
		sp.setBounds(502, 47, 750, 350);
		add(sp);

		btnEnroll = new JButton("등록");
		btnEnroll.setBounds(1170, 8, 71, 33);
		btnEnroll.addActionListener(this);
		add(btnEnroll);
		
		hidebutton = new JButton("숨기기");
		hidebutton.setBounds(1081, 8, 75, 33);
		hidebutton.addActionListener(this);
		add(hidebutton);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEnroll) {
			Frame fs = new TodoEnroll(id, todoModel, todoData);
			fs.setVisible(true);
		}
		if(e.getActionCommand() == "숨기기") {
			hidebutton.setText("보이기");
			int k = 0;
			while(true) {
				int row = todoModel.getRowCount();
				if(k >= row)
					break;
				if("해결".equals(todoModel.getValueAt(k, 4).toString())) {
					TodoInfo info = new TodoInfo(todoModel.getValueAt(k, 0), todoModel.getValueAt(k, 1), todoModel.getValueAt(k, 2),
							todoModel.getValueAt(k, 3), todoModel.getValueAt(k, 4), todoModel.getValueAt(k, 5), todoModel.getValueAt(k, 6));
					rowdata.add(info);
					todoModel.removeRow(k);
					k = 0;
				}
				else
					k++;
			}
		}	
		if(e.getActionCommand() == "보이기") {
			hidebutton.setText("숨기기");
			if(rowdata.isEmpty())
				return;
			for(int i = 0; i < rowdata.size(); i++) {
				Vector<Object> row = new Vector<Object>();
				row.add(rowdata.get(i).getImportance());
				row.add(rowdata.get(i).getTfSubject());
				row.add(rowdata.get(i).getCbDeadMonth());
				row.add(rowdata.get(i).getCbRDeadMonth());
				row.add(rowdata.get(i).getCbState());
				row.add(rowdata.get(i).getTfWTD());
				todoModel.addRow(row);
			}
			rowdata.removeAllElements();
		}
	}
	
	private Vector<Object> initColumn() {
		Vector<Object> cols = new Vector<Object>();
		cols.add("중요도");
		cols.add("과목명");
		cols.add("마감일");
		cols.add("실제마감일");
		cols.add("상태");
		cols.add("WHAT TO DO");
		cols.add("변경");
		cols.add("삭제");
		cols.add("메모");
		return cols;
	}

	private void refreshTable(String _id) {
		DBConnection db = new DBConnection();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		data = db.getTodo(_id);
		todoModel.setDataVector(data, columns);
		db.close();
	}
	
}

class TodoTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private JButton btn;
	private String id;
	private JTable tbl;

	public TodoTableCell(String _id, final String label, final JTable table) {
		id = _id;
		tbl = table;
		btn = new JButton(label);
		btn.setToolTipText(label);
		if("중요도".equals(label)) {
			btn.setText("");
			btn.setEnabled(false);
			btn.setFocusable(false);
		}
		
		btn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String type = btn.getToolTipText();
				int row = table.getSelectedRow();
				
				if ("변경".equals(type)) {
					Vector<Object> rowData = new Vector<Object>();
					rowData = getRows(table, row);
					Frame fr = new TodoModify(id, (DefaultTableModel) table.getModel(), rowData, row);
				}else if("삭제".equals(type)) {
					if(JOptionPane.showConfirmDialog(null, "해당 과목을 삭제하시곘습니까?", "삭제", 
							JOptionPane.YES_NO_OPTION , JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						DBConnection db = new DBConnection();
						db.deleteTodo(id, getRows(table, row));
						db.close();
						DefaultTableModel tm = (DefaultTableModel) table.getModel();
						tm.removeRow(row);
					}
				}else if("메모".equals(type)) {
					Vector<Object> rowData = new Vector<Object>();
					rowData = getRows(table, row);
					Frame fr = new Memo(id, rowData, table, row);
				}				
			}
		});
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		int rowNum = tbl.getSelectedRow();
		int color = (Integer)tbl.getValueAt(rowNum, 0);
		return color;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		String label = btn.getToolTipText();
		if("중요도".equals(label)) {
			Color[] colors = {Color.lightGray, Color.YELLOW, Color.RED};
			int color = (Integer)table.getValueAt(row, 0);
			btn.setBackground(colors[color]);
		}
		return btn;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return btn;
	}

	private Vector<Object> getRows(JTable tbl, int row) {
		Vector<Object> rows = new Vector<Object>();
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			Object obj = tbl.getValueAt(row, i);
			rows.add(obj);
		}
		return rows;
	}
	
}