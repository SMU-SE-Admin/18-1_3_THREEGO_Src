package se.smu.subject;

import java.awt.Color;
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

public class SubPanel extends JPanel implements ActionListener {
	// 과목 패널에서의 기본적인 변수를 선정한다.
	private String subTitle[] = { "년도-학기", "과목명", "요일", "시간", "교수", "변경", "삭제" };
	private DefaultTableModel subtableModel;
	private JTable table;
	private JLabel lblId;
	private JScrollPane sp;
	private JButton enrollButton;
	private String id;
	public Object input_data[] = new Object[7];

	// 과목 패널 생성자이며 파라미터로 id를 전달받는다.
	public SubPanel(String id) {
		// 과목 패널의 기본적인 크기 설정
		setLayout(null);
		setSize(500, 400);

		// 테이블과 테이블 모델을 초기화한다.
		subtableModel = new DefaultTableModel(subTitle, 0);
		table = new JTable(subtableModel);

		// 데이터베이스와 연결하여 테이블을 새로고침한다.
		refreshTable(id, subtableModel, subTitle);

		// 테이블을 감쌀 ScrollPanel을 설정한다.
		sp = new JScrollPane(table);
		sp.setBounds(0, 47, 500, 350);
		sp.getViewport().setBackground(Color.WHITE);
		add(sp);

		// 등록 버튼을 설정한다.
		enrollButton = new JButton("등록");
		enrollButton.setBounds(415, 8, 71, 33);
		add(enrollButton);
		enrollButton.addActionListener(this);

		// ID를 표시할 라벨을 설정한다.
		lblId = new JLabel("ID : " + id);
		lblId.setBounds(14, 17, 121, 18);
		add(lblId);

		// 테이블의 5,6번 열에 CellEditor와 CellRenderer 를 설정한다.
		table.getColumnModel().getColumn(5).setCellEditor(new SubTableCell("변경", id, table));
		table.getColumnModel().getColumn(5).setCellRenderer(new SubTableCell("변경", id, table));
		table.getColumnModel().getColumn(6).setCellEditor(new SubTableCell("삭제", id, table));
		table.getColumnModel().getColumn(6).setCellRenderer(new SubTableCell("삭제", id, table));

		this.id = id;
		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		// 등록 버튼이면 해당 작업 실행
		if (e.getSource() == enrollButton) {
			// SubEnroll Frame을 생성하고 보여준다.
			Frame fs = new SubEnroll(subtableModel, input_data, id);
			fs.setVisible(true);
		}
	}

	// 테이블을 데이터베이스와 연결하여 새로고침 하는 기능
	private void refreshTable(String id, DefaultTableModel tm, Object[] headers) {
		// 문자열 배열인 헤더를 벡터로 변환한다.
		Vector<Object> columns = new Vector<Object>();
		for (int i = 0; i < headers.length; i++) {
			columns.add(headers[i]);
		}
		// 데이터베이스를 연결하고 데이터를 가져와서 테이블을 새로고침한다.
		DBConnection db = new DBConnection();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		data = db.getSubject(id);
		tm.setDataVector(data, columns);
		db.close();
	}
}

// 커스텀 TableCell 클래스이다.
class SubTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private JButton btn;
	private String id;

	public SubTableCell(final String label, String _id, final JTable table) {
		// 파라미터로 들어온 라벨로 버튼을 생성한다.
		btn = new JButton(label);
		id = _id;
		btn.setText(label);

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 버튼의 텍스트를 받아온다.
				String type = btn.getText();
				// 테이블에서 선택된 Row값을 받아오고 만약 예외적으로 -1값을 받는다면 0으로 초기화하도록 한다.
				int row = (table.getSelectedRow() == -1 ? 0 : table.getSelectedRow());

				// 변경 버튼일 경우
				if ("변경".equals(type)) {
					// 해당 Row의 데이터를 받아 Modify 프레임을 보여준다.
					Object input_data[] = new Object[7];
					input_data = getRows(table, row);
					Frame fr = new SubModify((DefaultTableModel) table.getModel(), input_data, row, id);
					fr.setVisible(true);
				}
				// 삭제 버튼일 경우
				else if ("삭제".equals(type)) {
					// 해당 과목을 정말 삭제할 것 인지 다시 확인한다.
					if (JOptionPane.showConfirmDialog(null, "해당 과목을 삭제하시곘습니까?", "삭제", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						// 데이터베이스를 연결하여 해당 데이터를 지우고 테이블에서도 해당 Row를 삭제한다.
						DBConnection db = new DBConnection();
						db.deleteSubject(id, getRows(table, row));
						db.close();
						int tmRow = table.convertRowIndexToModel(row);
						DefaultTableModel tm = (DefaultTableModel) table.getModel();
						tm.removeRow(tmRow);
						table.setModel(tm);
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

	// 선택된 열의 모든 데이터를 배열 형태로 반환한다.
	private Object[] getRows(JTable tbl, int row) {
		Object rows[] = new Object[7];
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			Object obj = tbl.getValueAt(row, i);
			rows[i] = obj;
		}
		return rows;
	}
}
