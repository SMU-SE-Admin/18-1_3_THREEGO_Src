package se.smu.alarm;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JOptionPane;

import se.smu.db.DBConnection;

public class Alarm {
	
	public Alarm(String id) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		int alarmMonth = cal.get(Calendar.MONTH)+1;
		int alarmDay = cal.get(Calendar.DAY_OF_MONTH);
		
		Vector<Object> deadlines = sortData(id);
		
		for(int i=0; i<deadlines.size(); i++) {
			Vector<Object> vec = (Vector<Object>) deadlines.get(i);
			String[] time = vec.get(2).toString().split("\\.");
			int month = Integer.parseInt(time[0]);
			int day = Integer.parseInt(time[1]);
			
			if(alarmMonth == month && alarmDay == day) {
				String title = "마감일 알림 시스템";
				String content = "중요도 : " + getImportance((Integer)vec.get(0)) + "\n"
						+ "과목명 : " + vec.get(1).toString() + "\n"
						+ "마감일 : " + vec.get(2).toString() + "\n"
						+ "실제 마감일 : " + vec.get(3).toString() + "\n"
						+ "상태 : " + vec.get(4).toString() + "\n"
						+ "WHAT TO DO : " + vec.get(5).toString() + "\n";
				JOptionPane.showMessageDialog(null, content, title, JOptionPane.NO_OPTION);
			}
		}
				
		
	}
	
	private String getImportance(int val) {
		switch(val) {
		case 0:
			return "낮음";
		case 1:
			return "보통";
		default:
			return "높음";
		}
	}
	
	@SuppressWarnings("unchecked")
	public Vector<Object> sortData(String id){
		
		DBConnection db = new DBConnection();
		Vector data = db.getTodo(id);
		db.close();
		
		Collections.sort(data, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				Vector<Object> v1 = (Vector<Object>)o1;
				Vector<Object> v2 = (Vector<Object>)o2;
				return v1.get(2).toString().compareTo(v2.get(2).toString()) > 0 ? 1 : -1;
			}
		});
		
		return data;
	}
	
}
