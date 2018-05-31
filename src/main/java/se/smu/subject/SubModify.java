package se.smu.subject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import se.smu.db.DBConnection;

public class SubModify extends enrollWindow {

	public SubModify(final DefaultTableModel subtableModel, final Object[] input_data, final int row, final String id) {
		super(id);

		// 텍스트 필드들을 설정한다.
		text_Sem.setText((String) input_data[0]);
		text_Subname.setText((String) input_data[1]);
		text_Profname.setText((String) input_data[4]);

		String sc = input_data[3].toString().split(" ~ ")[0];
		String ec = input_data[3].toString().split(" ~ ")[1];

		start_comboBox.setSelectedItem(sc);
		end_comboBox.setSelectedItem(ec);

		String today[] = { "월", "화", "수", "목", "금", "토", "일" };
		boolean tb[] = { false, false, false, false, false, false, false };

		// 기존 선택된 요일을 라디오 버튼에서 check 한다.
		for (int i = 0; i < today.length; i++) {
			if (input_data[2].equals(today[i])) {
				tb[i] = true;
			}
		}

		// 라디오버튼 및 라디오 그룹을 초기화한다.
		setRB(today, tb);

		completeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 입력 란에 누락이 있는지 확인한다.
				if (!text_Sem.getText().trim().equals("") && !text_Subname.getText().trim().equals("")
						&& !text_Profname.getText().trim().equals("")) {
					Object[] prev = new Object[7];
					System.arraycopy(input_data, 0, prev, 0, input_data.length);

					// 등록 전 기본 설정을 한다.
					boolean checkEnroll = beforeEnroll(input_data, subtableModel);
					if(checkEnroll == false)
						return;

					// 중복여부를 검사 후 중복이 아니면 업데이트
					if (!checkDupl(subtableModel, input_data)) {
						DBConnection db = new DBConnection();
						db.updateSubject(id, prev, input_data);
						db.close();
						subtableModel.insertRow(row, input_data);
						subtableModel.removeRow(row + 1);
					}
					setVisible(false);
				} else {
					// 오류메시지 출력
					JOptionPane.showMessageDialog(null, "누락된 곳이 있습니다!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

}
