package se.smu.subject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import se.smu.db.DBConnection;

public class SubModify extends enrollWindow{

	public SubModify(final DefaultTableModel subtableModel, final Object[] input_data, final int row, final String id) {
		super(id);
		
		text_Sem.setText((String)input_data[0]);
		text_Subname.setText((String)input_data[1]);
		text_Profname.setText((String)input_data[4]);
		
		String sc = input_data[3].toString().split(" ~ ")[0];
		String ec = input_data[3].toString().split(" ~ ")[1];
		
		start_comboBox.setSelectedItem(sc);
		end_comboBox.setSelectedItem(ec);
		
		String today[] = {"월", "화", "수", "목", "금", "토", "일"};
		boolean tb[] = {false, false, false, false, false, false, false};
		
		for(int i = 0; i < today.length; i++) {
			if(input_data[2].equals(today[i])) {
				tb[i] = true;
			}
		}
		
		setRB(today, tb);
		
		completeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!text_Sem.getText().trim().equals("") && !text_Subname.getText().trim().equals("") && !text_Profname.getText().trim().equals("")) {			
					Object[] prev = new Object[7];
					System.arraycopy(input_data, 0, prev, 0, input_data.length);
					
					beforeEnroll(input_data, subtableModel);
					
					if(!checkDupl(subtableModel, input_data)) {
						DBConnection db = new DBConnection();
						db.updateSubject(id, prev, input_data);
						db.close();
						subtableModel.insertRow(row, input_data);
						subtableModel.removeRow(row + 1);
					}
					setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "누락된 곳이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}

}
