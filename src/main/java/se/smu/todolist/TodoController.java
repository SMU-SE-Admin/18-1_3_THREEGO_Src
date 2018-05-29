package se.smu.todolist;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TodoController {
	
	public Vector<String> getMonth() {
		Vector<String> month = new Vector<String>();
		for (int i = 1; i <= 12; i++) {
			month.add(String.valueOf(i));
		}
		return month;
	}

	public Vector<String> getDate(String _month) {
		Vector<String> date = new Vector<String>();
		int month = Integer.parseInt(_month);
		
		if(month == 2) {
			for (int i = 1; i <= 28; i++) {
				date.add(String.valueOf(i));
			}
		}else if(month == 1 || month == 3 || month == 5 || month == 7
				|| month == 8 || month == 10 || month == 12) {
			for (int i = 1; i <= 31; i++) {
				date.add(String.valueOf(i));
			}
		}else {
			for (int i = 1; i <= 30; i++) {
				date.add(String.valueOf(i));
			}
		}
		return date;
	}
	
	public boolean checkDupl(DefaultTableModel tm, Vector<Object> row) {
		Vector data = tm.getDataVector();
		for(int i=0; i<data.size(); i++) {
			Vector tmp = (Vector) data.get(i);
			int count = 0;
			for(int j=0; j<row.size()-1; j++) {
				if(tmp.get(j).equals(row.get(j))){
					count++;
				}
			}
			if(count == 6)
				return true;
			
		}
		return false;
	}
	
	public boolean checkDeadline(String dead, String rdead) {
		if(dead.compareTo(rdead) > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
