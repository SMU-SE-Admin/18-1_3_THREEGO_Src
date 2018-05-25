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
		super(subtableModel, input_data, id);
		
		rb1 = new JRadioButton("월", true);
		rb2 = new JRadioButton("화");
		rb3 = new JRadioButton("수");
		rb4 = new JRadioButton("목");
		rb5 = new JRadioButton("금");
		rb6 = new JRadioButton("토");
		rb7 = new JRadioButton("일");
		
		grouprb = new ButtonGroup();
		
		grouprb.add(rb1);
		rb1.setBounds(139, 172, 50, 20);
		grouprb.add(rb2);
		rb2.setBounds(189, 172, 50, 20);
		grouprb.add(rb3);
		rb3.setBounds(239, 172, 50, 20);
		grouprb.add(rb4);
		rb4.setBounds(289, 172, 50, 20);
		grouprb.add(rb5);
		rb5.setBounds(339, 172, 50, 20);
		grouprb.add(rb6);
		rb6.setBounds(389, 172, 50, 20);
		grouprb.add(rb7);
		rb7.setBounds(439, 172, 50, 20);
	
		getContentPane().add(rb1);
		getContentPane().add(rb2);
		getContentPane().add(rb3);
		getContentPane().add(rb4);
		getContentPane().add(rb5);
		getContentPane().add(rb6);
		getContentPane().add(rb7);
		
		completeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!text_Sem.getText().equals("") && !text_Subname.getText().equals("") && !text_Profname.getText().equals("")) {
					input_data[0] = text_Sem.getText();
					input_data[1] = text_Subname.getText();
					input_data[2] = selectedRadioContents(grouprb);
					input_data[3] = start_comboBox.getSelectedItem() + " ~ " + end_comboBox.getSelectedItem();
					input_data[4] = text_Profname.getText();
					input_data[5] = "변경";
					input_data[6] = "삭제";
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
