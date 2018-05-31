package se.smu.todolist;

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

import se.smu.alarm.Alarm;
import se.smu.db.DBConnection;
import se.smu.memo.Memo;

public class TodoPanel extends JPanel implements ActionListener {
	// 해당 클래스의 기본적인 변수 선언
	private Vector<Object> todoData = new Vector<Object>();
	private String id;

	private JButton btnEnroll;
	private JTable tblTodo;
	private Vector<Object> columns;
	private DefaultTableModel todoModel;
	public Vector<TodoInfo> rowdata = new Vector<TodoInfo>();
	private JButton hidebutton;

	public TodoPanel(String _id) {
		// 해당 패널의 기본 크기 설정
		setLayout(null);
		setSize(1255, 400);

		// 타이틀 라벨 설정
		JLabel lblTitle = new JLabel("To Do List");
		lblTitle.setBounds(514, 17, 121, 18);
		add(lblTitle);
		this.id = _id;

		// 열을 벡터화한다.
		columns = initColumn();
		// 테이블 모델을 선언하고 특정 칼럼을 수정 불가능하게 한다.
		todoModel = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int row, int col) {
				if (col == 0 || col == 6 || col == 7 || col == 8) {
					return true;
				} else {
					return false;
				}
			}
		};

		// 알람을 실행한다.
		new Alarm(id);

		// 테이블을 새로고침 한다.
		refreshTable(id);

		// 테이블을 설정한다.
		tblTodo = new JTable(todoModel);
		tblTodo.getTableHeader().setReorderingAllowed(false);
		// 테이블의 칼럼을 클릭시 정렬되록 한다.
		new TodoSorting(tblTodo, todoModel);

		// 특정 칼럼에 버튼을 추가한다.
		tblTodo.getColumnModel().getColumn(0).setCellEditor(new TodoTableCell(id, "중요도", tblTodo));
		tblTodo.getColumnModel().getColumn(0).setCellRenderer(new TodoTableCell(id, "중요도", tblTodo));
		tblTodo.getColumnModel().getColumn(6).setCellEditor(new TodoTableCell(id, "변경", tblTodo));
		tblTodo.getColumnModel().getColumn(6).setCellRenderer(new TodoTableCell(id, "변경", tblTodo));
		tblTodo.getColumnModel().getColumn(7).setCellEditor(new TodoTableCell(id, "삭제", tblTodo));
		tblTodo.getColumnModel().getColumn(7).setCellRenderer(new TodoTableCell(id, "삭제", tblTodo));
		tblTodo.getColumnModel().getColumn(8).setCellEditor(new TodoTableCell(id, "메모", tblTodo));
		tblTodo.getColumnModel().getColumn(8).setCellRenderer(new TodoTableCell(id, "메모", tblTodo));
		JScrollPane sp = new JScrollPane(tblTodo);
		sp.getViewport().setBackground(Color.WHITE);
		sp.setBounds(502, 47, 750, 350);
		add(sp);

		// 등록 및 숨기기 버튼을 설정한다.
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
		// 등록 버튼일 경우
		if (e.getSource() == btnEnroll) {
			Frame fs = new TodoEnroll(id, todoModel, todoData);
			fs.setVisible(true);
		}
		// 숨기기 버튼일 경우
		if (e.getActionCommand() == "숨기기") {
			// 보이기 버튼으로 변경
			hidebutton.setText("보이기");
			int k = 0;
			while (true) {
				int row = todoModel.getRowCount();
				if (k >= row)
					break;
				// 해결로 선택된 것들을 숨긴다.
				if ("해결".equals(todoModel.getValueAt(k, 4).toString())) {
					TodoInfo info = new TodoInfo(todoModel.getValueAt(k, 0), todoModel.getValueAt(k, 1),
							todoModel.getValueAt(k, 2), todoModel.getValueAt(k, 3), todoModel.getValueAt(k, 4),
							todoModel.getValueAt(k, 5), todoModel.getValueAt(k, 6));
					rowdata.add(info);
					todoModel.removeRow(k);
					k = 0;
				} else
					k++;
			}
		}
		// 보이기 버튼일 경우
		if (e.getActionCommand() == "보이기") {
			// 숨기기 버튼으로 바꾼다.
			hidebutton.setText("숨기기");
			if (rowdata.isEmpty())
				return;
			// 숨겨뒀던 모든 데이터를 보여준다.
			for (int i = 0; i < rowdata.size(); i++) {
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

	// 열 초기화
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

	// 테이블 새로고침
	private void refreshTable(String _id) {
		DBConnection db = new DBConnection();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		data = db.getTodo(_id);
		todoModel.setDataVector(data, columns);
		db.close();
	}

}

// 커스템 테이블 셀
class TodoTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	private JButton btn;
	private String id;
	private JTable tbl;

	public TodoTableCell(String _id, final String label, final JTable table) {
		// 버튼 생성
		id = _id;
		tbl = table;
		btn = new JButton(label);
		btn.setToolTipText(label);
		if ("중요도".equals(label)) {
			btn.setText("");
			btn.setEnabled(false);
			btn.setFocusable(false);
		}

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String type = btn.getToolTipText();
				int row = table.getSelectedRow();

				// 변경 버튼일경우
				if ("변경".equals(type)) {
					Vector<Object> rowData = new Vector<Object>();
					rowData = getRows(table, row);
					new TodoModify(id, table, (DefaultTableModel) table.getModel(), rowData, row);
				}
				// 삭제 버튼일 경우
				else if ("삭제".equals(type)) {
					// 삭제 할지 다시 한 번 확인한다.
					if (JOptionPane.showConfirmDialog(null, "해당 과목을 삭제하시곘습니까?", "삭제", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						DBConnection db = new DBConnection();
						db.deleteTodo(id, getRows(table, row));
						db.close();
						int tmRow = table.convertRowIndexToModel(row);
						DefaultTableModel tm = (DefaultTableModel) table.getModel();
						tm.removeRow(tmRow);
						table.setModel(tm);
					}
				}
				// 메모 버튼일 경우
				else if ("메모".equals(type)) {
					Vector<Object> rowData = new Vector<Object>();
					rowData = getRows(table, row);
					new Memo(id, rowData, table, row);
				}
			}
		});
	}

	public Object getCellEditorValue() {
		// 셀 클릭시 해당 셀의 값을 반환
		int rowNum = tbl.getSelectedRow();
		int colNum = tbl.getSelectedColumn();
		Object value = tbl.getValueAt(rowNum, colNum);
		return value;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// 중요도 버튼의 색을 바꾼다.
		String label = btn.getToolTipText();
		if ("중요도".equals(label)) {
			Color[] colors = { Color.lightGray, Color.YELLOW, Color.RED };
			int color = (Integer) table.getValueAt(row, 0);
			btn.setBackground(colors[color]);
		}
		return btn;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return btn;
	}

	// 해당 줄의 데이터를 반환한다.
	private Vector<Object> getRows(JTable tbl, int row) {
		Vector<Object> rows = new Vector<Object>();
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			Object obj = tbl.getValueAt(row, i);
			rows.add(obj);
		}
		return rows;
	}

}