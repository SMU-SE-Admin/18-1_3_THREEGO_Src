package se.smu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import se.smu.db.DBConnection;

public class enrollModifyWindow extends JFrame {
	JTextField text_Sem;
	JTextField text_Subname;
	JTextField text_Profname;
	String[] starthour = {"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};		
	String[] endhour = {"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
	JButton completeButton;
	JLabel sem_label;
	JLabel subname_label;
	JLabel today_label;
	JLabel time_label;
	JLabel profname_label;
	JRadioButton rb1;
	JRadioButton rb2;
	JRadioButton rb3;
	JRadioButton rb4;
	JRadioButton rb5;
	JRadioButton rb6;
	JRadioButton rb7;
	JComboBox start_comboBox;
	JComboBox end_comboBox;
	ButtonGroup grouprb;
	private int row;
	
	public enrollModifyWindow(final DefaultTableModel subtableModel, final Object input_data[], final int row, final String id) {
		setLocation(300, 300);
		setSize(520,400);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID : " + id);
		lblNewLabel.setBounds(28, 16, 117, 18);
		getContentPane().add(lblNewLabel);
		
		completeButton = new JButton("완료");
		completeButton.setBounds(384, 12, 105, 27);
		getContentPane().add(completeButton);
		
		sem_label = new JLabel("년도-학기");
		sem_label.setBounds(44, 90, 87, 27);
		getContentPane().add(sem_label);
		
		subname_label = new JLabel("과목명");
		subname_label.setBounds(44, 130, 87, 27);
		getContentPane().add(subname_label);
		
		today_label = new JLabel("요일");
		today_label.setBounds(42, 169, 87, 27);
		getContentPane().add(today_label);
		
		time_label = new JLabel("시간");
		time_label.setBounds(42, 213, 87, 27);
		getContentPane().add(time_label);
		
		profname_label = new JLabel("교수명");
		profname_label.setBounds(42, 252, 87, 27);
		getContentPane().add(profname_label);
		
		text_Sem = new JTextField();
		text_Sem.setBounds(145, 91, 214, 27);
		text_Sem.setText((String)input_data[0]);
		getContentPane().add(text_Sem);
		text_Sem.setColumns(10);
		
		text_Subname = new JTextField();
		text_Subname.setBounds(145, 131, 214, 26);
		text_Subname.setText((String)input_data[1]);
		getContentPane().add(text_Subname);
		text_Subname.setColumns(10);
		
		text_Profname = new JTextField();
		text_Profname.setColumns(10);
		text_Profname.setBounds(145, 252, 214, 26);
		text_Profname.setText((String)input_data[4]);
		getContentPane().add(text_Profname);
		
		String today[] = {"월", "화", "수", "목", "금", "토", "일"};
		boolean tb[] = {false, false, false, false, false, false, false};
		
		for(int i = 0; i < today.length; i++) {
			if(input_data[2] == today[i]) {
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
		
		String sc = input_data[3].toString().split(" ~ ")[0];
		String ec = input_data[3].toString().split(" ~ ")[1];
		
		start_comboBox = new JComboBox(starthour);
		start_comboBox.setSelectedItem(sc);
		start_comboBox.setBounds(156, 214, 67, 24);
		getContentPane().add(start_comboBox);
		
		end_comboBox = new JComboBox(endhour);
		end_comboBox.setSelectedItem(ec);
		end_comboBox.setBounds(289, 214, 65, 24);
		getContentPane().add(end_comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("   ~");
		lblNewLabel_2.setBounds(239, 217, 36, 18);
		getContentPane().add(lblNewLabel_2);
		
		this.row = row;

		completeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!text_Sem.getText().equals("") && !text_Subname.getText().equals("") && !text_Profname.getText().equals("")) {
					Object[] prev = new Object[7];
					System.arraycopy(input_data, 0, prev, 0, input_data.length);
					input_data[0] = text_Sem.getText();
					input_data[1] = text_Subname.getText();
					input_data[2] = selectedRadioContents(grouprb);
					input_data[3] = start_comboBox.getSelectedItem() + " ~ " + end_comboBox.getSelectedItem();
					input_data[4] = text_Profname.getText();
					input_data[5] = "변경";
					input_data[6] = "삭제";
					if(!checkDupl(subtableModel, input_data)) {
						DBConnection db = new DBConnection();
						db.updateSubject(id, prev, input_data);
						db.close();
						subtableModel.insertRow(row, input_data);
						subtableModel.removeRow(row + 1);
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
		
	}
	public String selectedRadioContents(ButtonGroup gr) {
		Enumeration<AbstractButton> enums = gr.getElements();
		String contents = "";
		while(enums.hasMoreElements()) {            
		    AbstractButton ab = enums.nextElement();    
		    JRadioButton jb = (JRadioButton)ab;          
		    if(jb.isSelected())                        
		        contents = jb.getText(); 
		}
		return contents;
	}
	
	private boolean checkDupl(DefaultTableModel tm, Object[] row) {
		Vector data = tm.getDataVector();
		for(int i=0; i<data.size(); i++) {
			Vector tmp = (Vector) data.get(i);
			int count = 0;
			for(int j=0; j<5; j++) {
				if(tmp.get(j).equals(row[j])){
					count++;
				}
			}
			if(count == 5)
				return true;
			
		}
		return false;
	}
	
}
