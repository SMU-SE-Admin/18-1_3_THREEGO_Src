package se.smu;

import java.awt.Color;

import javax.swing.UIManager;

public class Main {
	public static final Color PINK = new Color(253, 199, 199);
	public static final Color HOT_PINK = new Color(255, 124, 128);

	public static void main(String[] args) {
		// UI매니저를 통해서 Panel 배경색과 버튼의 기본 색을 변경한다
		UIManager.put("OptionPane.background", PINK);
		UIManager.put("Panel.background", PINK);
		UIManager.put("Button.background", HOT_PINK);

		// LoginFrame을 실행한다
		new LoginFrame();
	}
}
