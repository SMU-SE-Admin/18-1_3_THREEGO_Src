package se.smu.subject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

import se.smu.db.DBConnection;

public class SubModify extends enrollWindow{
	private int row;

	public SubModify(final DefaultTableModel subtableModel, final Object[] input_data, final int row, final String id) {
		super(subtableModel, input_data, id);
		
		this.row = row;
		
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
		
		rb1 = new JRadioButton(today[0], tb[0]);
		rb2 = new JRadioButton(today[1], tb[1]);
		rb3 = new JRadioButton(today[2], tb[2]);
		rb4 = new JRadioButton(today[3], tb[3]);
		rb5 = new JRadioButton(today[4], tb[4]);
		rb6 = new JRadioButton(today[5], tb[5]);
		rb7 = new JRadioButton(today[6], tb[6]);
		
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
				if(!text_Sem.getText().trim().equals("") && !text_Subname.getText().trim().equals("") && !text_Profname.getText().trim().equals("")) {			
					Object[] prev = new Object[7];
					System.arraycopy(input_data, 0, prev, 0, input_data.length);
					input_data[0] = text_Sem.getText();
					input_data[1] = text_Subname.getText();
					input_data[2] = selectedRadioContents(grouprb);
					if(start_comboBox.getSelectedIndex() > end_comboBox.getSelectedIndex()) {
						JOptionPane.showMessageDialog(null, "강의 시작 시간이 끝나는 시간보다 늦습니다", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(start_comboBox.getSelectedIndex() == end_comboBox.getSelectedIndex()) {
						JOptionPane.showMessageDialog(null, "강의 시작 시간과 끝나는 시간이 같습니다", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else
						input_data[3] = start_comboBox.getSelectedItem() + " ~ " + end_comboBox.getSelectedItem();
					input_data[4] = text_Profname.getText();
					input_data[5] = "변경";
					input_data[6] = "삭제";
					
					if(!checkDupl(subtableModel, input_data)) {
						DBConnection db = new DBConnection();
						db.updateSubject(id, prev, input_data);
						db.close();
					}
					subtableModel.insertRow(row, input_data);
					subtableModel.removeRow(row + 1);
					setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "누락된 곳이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}

}
