package se.smu;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import se.smu.db.DBConnection;
import se.smu.todolist.TodoEnroll;

public class SubPanel extends JPanel implements ActionListener{
	private String subTitle[] = { "년도-학기", "과목명", "요일", "시간", "교수", "변경", "삭제" };
	private DefaultTableModel subtableModel;
	private JTable table;
	private JLabel lblId;
	private JScrollPane sp;
	private JPanel panel;
	private JButton enrollButton;
	private String id;
	public Object input_data[] = new Object[7];
	
	public SubPanel(String id) {
		setLayout(null);
		setSize(500, 400);
		subtableModel = new DefaultTableModel(subTitle, 0);
		table = new JTable(subtableModel);
		
		refreshTable(id, subtableModel, subTitle);

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
		
		table.getColumnModel().getColumn(5).setCellEditor(new SubTableCell("변경", id));
		table.getColumnModel().getColumn(5).setCellRenderer(new SubTableCell("변경", id));
		table.getColumnModel().getColumn(6).setCellEditor(new SubTableCell("삭제", id));
		table.getColumnModel().getColumn(6).setCellRenderer(new SubTableCell("삭제", id));
		
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
	
	private void refreshTable(String id, DefaultTableModel tm, Object[] headers) {
		Vector<Object> columns = new Vector<Object>();
		for(int i=0; i<headers.length; i++) {
			columns.add(headers[i]);
		}
		DBConnection db = new DBConnection();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		data = db.getSubject(id);
		tm.setDataVector(data, columns);
		db.close();
	}
}

class SubTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private JButton btn;
	private String id;
	
	public SubTableCell(final String label, String id) {
		btn = new JButton(label);
		this.id = id;
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
			Frame fr = new enrollModifyWindow((DefaultTableModel) table.getModel(), input_data, row, id);
			fr.setVisible(true);
		}else if("삭제".equals(type)) {
			if(JOptionPane.showConfirmDialog(null, "해당 과목을 삭제하시곘습니까?", "삭제", 
					JOptionPane.YES_NO_OPTION , JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				DBConnection db = new DBConnection();
				db.deleteSubject(id, getRows(table, row));
				DefaultTableModel tm = (DefaultTableModel) table.getModel();
				tm.removeRow(row);
			}
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
