package se.smu.subject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

import se.smu.db.DBConnection;

public class SubEnroll extends enrollWindow{

	public SubEnroll(final DefaultTableModel subtableModel, final Object[] input_data, final String id) {
		super(id);
		
		boolean[] selected = {true, false, false, false, false, false, false};
		
		setRB(today, selected);

		completeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!text_Sem.getText().trim().equals("") && !text_Subname.getText().trim().equals("") && !text_Profname.getText().trim().equals("")) {
					
					beforeEnroll(input_data, subtableModel);
					
					if(!checkDupl(subtableModel, input_data)) {
						DBConnection db = new DBConnection();
						db.setSubject(id, input_data);
						db.close();
						subtableModel.addRow(input_data);
					}else {
						JOptionPane.showMessageDialog(null, "중복된 과목이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "누락된 곳이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		// TODO Auto-generated constructor stub
	}
	

}
