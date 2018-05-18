package se.smu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import se.smu.todolist.TodoEnroll;
import se.smu.todolist.TodoModify;
import se.smu.todolist.TodoPanel;

public class MainFrame extends JFrame {
	/*private String subTitle[] = { "년도-학기", "과목명", "요일", "시간", "교수", "변경", "삭제" };
	private DefaultTableModel subtableModel;
	private JTable table;
	private JLabel lblId;
	private JScrollPane sp;
	private JPanel panel;
	private JButton enrollButton;
	public Object input_data[] = new Object[7];*/
	
	private JPanel subPanel;
	private JPanel todoPanel;
	
	static String id;
	
	/*public Object todoData[] = new Object[9];

	private JButton btnEnroll;
	private JTable tblTodo;
	private String[] columns = { "중요도", "과목명", "마감일", "실제 마감일", "상태", "WHAT TO DO", "변경", "삭제", "메모" };
	private DefaultTableModel todoModel = new DefaultTableModel(columns, 0);*/

	public MainFrame(String id) {
		setTitle("메인화면");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300, 300);
		setSize(1280, 450);
		
		this.id = id;
		
		subPanel = new SubPanel(id);
		this.add(subPanel);
		
		todoPanel = new TodoPanel();
		this.add(todoPanel);

		/*subtableModel = new DefaultTableModel(subTitle, 0);
		getContentPane().setLayout(null);
		table = new JTable(subtableModel);

		sp = new JScrollPane(table);
		sp.setBounds(0, 47, 500, 400);
		getContentPane().add(sp);

		enrollButton = new JButton("등록");
		enrollButton.setBounds(415, 8, 71, 33);
		getContentPane().add(enrollButton);

		enrollButton.addActionListener(this);

		JLabel lblId_1 = new JLabel("ID : 201411205");
		lblId_1.setBounds(14, 17, 121, 18);
		getContentPane().add(lblId_1);
		
		table.getColumnModel().getColumn(5).setCellEditor(new SubTableCell("변경"));
		table.getColumnModel().getColumn(5).setCellRenderer(new SubTableCell("변경"));
		table.getColumnModel().getColumn(6).setCellEditor(new SubTableCell("삭제"));
		table.getColumnModel().getColumn(6).setCellRenderer(new SubTableCell("삭제"));*/
		
		/// !!!

		/*JLabel lblTitle = new JLabel("To Do List");
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
		sp.setBounds(502, 47, 550, 400);
		add(sp);

		btnEnroll = new JButton("등록");
		btnEnroll.setBounds(900, 8, 71, 33);
		btnEnroll.addActionListener(this);
		add(btnEnroll);*/

		setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame(id);
	}

	/*public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enrollButton) {
			Frame fs = new enrollWindow(subtableModel, input_data);
			fs.setVisible(true);
		} else if (e.getSource() == btnEnroll) {
			Frame fs = new TodoEnroll(todoModel, todoData);
			fs.setVisible(true);
		}
		if (e.getSource() == btnEnroll) {
			Frame fs = new TodoEnroll(todoModel, todoData);
			fs.setVisible(true);
		}
	}*/
}

/*class SubTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private JButton btn;
	
	public SubTableCell(final String label) {
		btn = new JButton(label);
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
			Object input_data[] = new Object[7];
			input_data = getRows(table, row);
			Frame fr = new enrollModifyWindow((DefaultTableModel) table.getModel(), input_data, row);
			fr.setVisible(true);
		}else if("삭제".equals(type)) {
			DefaultTableModel tm = (DefaultTableModel) table.getModel();
			tm.removeRow(row);
		}
		return null;
	}
	
	private Object[] getRows(JTable tbl, int row) {
		Object rows[] = new Object[7];
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			Object obj = tbl.getValueAt(row, i);
			rows[i] = obj;
		}
		return rows;
	}
}*/

/*class TodoTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
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
}*/
