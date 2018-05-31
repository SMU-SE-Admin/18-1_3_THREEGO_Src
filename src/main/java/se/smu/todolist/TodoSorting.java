package se.smu.todolist;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class TodoSorting {

	// 테이블의 열 클릭 시 정렬기능을 설정한다.
	public TodoSorting(JTable table, DefaultTableModel tm) {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tm);
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortColumns = new ArrayList<RowSorter.SortKey>(5);

		sortColumns.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sortColumns.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
		sortColumns.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		sortColumns.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
		sortColumns.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));

		sorter.setSortable(5, false);
		sorter.setSortable(6, false);
		sorter.setSortable(7, false);
		sorter.setSortable(8, false);
		sorter.setSortKeys(sortColumns);
	}
}
