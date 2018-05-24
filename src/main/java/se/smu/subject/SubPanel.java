package se.smu.subject;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import se.smu.db.DBConnection;

public class SubPanel extends JPanel implements ActionListener{
	private String subTitle[] = { "년도-학기", "과목명", "요일", "시간", "교수", "변경", "삭제" };
	private DefaultTableModel subtableModel;
	private JTable table;
	private JLabel lblId;
	private JScrollPane sp;
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

		lblId = new JLabel("ID : " + id);
		lblId.setBounds(14, 17, 121, 18);
		add(lblId);
		
		table.getColumnModel().getColumn(5).setCellEditor(new SubTableCell("변경", id, table));
		table.getColumnModel().getColumn(5).setCellRenderer(new SubTableCell("변경", id, table));
		table.getColumnModel().getColumn(6).setCellEditor(new SubTableCell("삭제", id, table));
		table.getColumnModel().getColumn(6).setCellRenderer(new SubTableCell("삭제", id, table));
		
		this.id = id;
		
		this.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enrollButton) {
			Frame fs = new enrollWindow(subtableModel, input_data, id);
			fs.setVisible(true);
		} 
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
	private JTable tbl;
	
	public SubTableCell(final String label, String _id, final JTable table) {
		btn = new JButton(label);
		id = _id;
		tbl = table;
		btn.setText(label);
				
		btn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String type = btn.getText();
				int row = table.getSelectedRow();
				if ("변경".equals(type)) {
					Object input_data[] = new Object[7];
					input_data = getRows(table, row);
					Frame fr = new enrollModifyWindow((DefaultTableModel) table.getModel(), input_data, row, id);
					fr.setVisible(true);
				}else if("삭제".equals(type)) {
					if(JOptionPane.showConfirmDialog(null, "해당 과목을 삭제하시곘습니까?", "삭제", 
							JOptionPane.YES_NO_OPTION , JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						DBConnection db = new DBConnection();
						if(db.deleteSubject(id, getRows(table, row))) {
							db.close();
							int tmRow = table.convertRowIndexToModel(row);
							DefaultTableModel tm = (DefaultTableModel) table.getModel();
							tm.removeRow(tmRow);
						}
					}
				}
			}
		});
		
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
		return btn;
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
