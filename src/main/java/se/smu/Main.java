package se.smu;

import java.awt.Color;

import javax.swing.UIManager;

public class Main {
	public static final Color PINK = new Color(253, 199, 199);
	public static final Color HOT_PINK = new Color(255,124,128);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UIManager.put("OptionPane.background", PINK);
		UIManager.put("Panel.background", PINK);
		UIManager.put("Button.background", HOT_PINK);
		new LoginFrame();
	}
}
