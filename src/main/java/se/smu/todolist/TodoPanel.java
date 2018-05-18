package se.smu.todolist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;

public class TodoPanel extends JPanel implements ActionListener {
	public Object todoData[] = new Object[9];

	private JButton btnEnroll;
	private JTable tblTodo;
	private String[] columns = { "중요도", "과목명", "마감일", "실제 마감일", "상태", "WHAT TO DO", "변경", "삭제", "메모" };
	private DefaultTableModel todoModel = new DefaultTableModel(columns, 0);

	public TodoPanel() {
		setLayout(null);
		setSize(1255,400);
		JLabel lblTitle = new JLabel("To Do List");
		lblTitle.setBounds(514, 17, 121, 18);
		add(lblTitle);

		tblTodo = new JTable(todoModel);
		tblTodo.getColumnModel().getColumn(0).setCellEditor(new TodoTableCell("중요도"));
		tblTodo.getColumnModel().getColumn(0).setCellRenderer(new TodoTableCell("중요도"));
		tblTodo.getColumnModel().getColumn(6).setCellEditor(new TodoTableCell("변경"));
		tblTodo.getColumnModel().getColumn(6).setCellRenderer(new TodoTableCell("변경"));
		tblTodo.getColumnModel().getColumn(7).setCellEditor(new TodoTableCell("삭제"));
		tblTodo.getColumnModel().getColumn(7).setCellRenderer(new TodoTableCell("삭제"));
		tblTodo.getColumnModel().getColumn(8).setCellEditor(new TodoTableCell("메모"));
		tblTodo.getColumnModel().getColumn(8).setCellRenderer(new TodoTableCell("메모"));
		JScrollPane sp = new JScrollPane(tblTodo);
		sp.setBounds(502, 47, 750, 350);
		add(sp);

		btnEnroll = new JButton("등록");
		btnEnroll.setBounds(1170, 8, 71, 33);
		btnEnroll.addActionListener(this);
		add(btnEnroll);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEnroll) {
			Frame fs = new TodoEnroll(todoModel, todoData);
			fs.setVisible(true);
		}
	}

}

class TodoTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private JButton btn;

	public TodoTableCell(final String label) {
		btn = new JButton(label);
		if("중요도".equals(label)) {
			btn = new JButton();
			btn.setBackground(Color.LIGHT_GRAY);
		}
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		return btn;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		String type = value.toString();
		if ("변경".equals(type)) {
			Vector<Object> rowData = new Vector<Object>();
			rowData = getRows(table, row);
			Frame fr = new TodoModify((DefaultTableModel) table.getModel(), rowData, row);
		}else if("삭제".equals(type)) {
			DefaultTableModel tm = (DefaultTableModel) table.getModel();
			tm.removeRow(row);
		}else if("메모".equals(type)) {
			System.out.println("메모");
		}else if("중요도".equals(type)) {
			Color[] colors = {Color.LIGHT_GRAY, Color.YELLOW, Color.RED};
			for(int i=0; i<colors.length; i++) {
				if(btn.getBackground().equals(colors[i])) {
					btn.setBackground(colors[(i+1)%3]);
					break;
				}
			}
		}
		return null;
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