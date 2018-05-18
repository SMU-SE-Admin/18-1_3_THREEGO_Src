package se.smu;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import se.smu.todolist.TodoEnroll;

public class SubPanel extends JPanel implements ActionListener{
	private String subTitle[] = { "년도-학기", "과목명", "요일", "시간", "교수", "변경", "삭제" };
	private DefaultTableModel subtableModel;
	private JTable table;
	private JLabel lblId;
	private JScrollPane sp;
	private JPanel panel;
	private JButton enrollButton;
	public Object input_data[] = new Object[7];
	String id;
	
	public SubPanel(String id) {
		setLayout(null);
		setSize(500, 400);
		subtableModel = new DefaultTableModel(subTitle, 0);
		table = new JTable(subtableModel);

		sp = new JScrollPane(table);
		sp.setBounds(0, 47, 500, 350);
		add(sp);

		enrollButton = new JButton("등록");
		enrollButton.setBounds(415, 8, 71, 33);
		add(enrollButton);

		enrollButton.addActionListener(this);

		JLabel lblId_1 = new JLabel("ID : " + id);
		lblId_1.setBounds(14, 17, 121, 18);
		add(lblId_1);
		
		table.getColumnModel().getColumn(5).setCellEditor(new SubTableCell("변경"));
		table.getColumnModel().getColumn(5).setCellRenderer(new SubTableCell("변경"));
		table.getColumnModel().getColumn(6).setCellEditor(new SubTableCell("삭제"));
		table.getColumnModel().getColumn(6).setCellRenderer(new SubTableCell("삭제"));
		
		this.id = id;
		
		sort();
		
		this.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enrollButton) {
			Frame fs = new enrollWindow(subtableModel, input_data, id);
			fs.setVisible(true);
		} 
	}
	
	private void sort() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(subtableModel);
		table.setRowSorter(sorter);
	}
}

class SubTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
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
}
