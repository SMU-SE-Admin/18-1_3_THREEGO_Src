package se.smu.subject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import se.smu.db.DBConnection;

public class SubEnroll extends enrollWindow {

	public SubEnroll(final DefaultTableModel subtableModel, final Object[] input_data, final String id) {
		super(id);

		boolean[] selected = { true, false, false, false, false, false, false };

		// 라디오 버튼 및 라디오 그룹을 초기화 한다.
		setRB(today, selected);

		completeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 누락된 공간이 있는지 확인한다.
				if (!text_Sem.getText().trim().equals("") && !text_Subname.getText().trim().equals("")
						&& !text_Profname.getText().trim().equals("")) {

					// 등록 전 기본 설정을 확인한다.
					boolean checkEnroll = beforeEnroll(input_data, subtableModel);
					if(checkEnroll == false)
						return;

					// 중복을 검사하고 중복이 아니면 데이터베이스와 테이블에 등록한다.
					if (!checkDupl(subtableModel, input_data)) {
						DBConnection db = new DBConnection();
						db.setSubject(id, input_data);
						db.close();
						subtableModel.addRow(input_data);
					} else {
						// 중복이 있다면 오류 메시지 출력
						JOptionPane.showMessageDialog(null, "중복된 과목이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					setVisible(false);
				} else {
					// 누락이 있다면 오류메시지 출력
					JOptionPane.showMessageDialog(null, "누락된 곳이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

}
