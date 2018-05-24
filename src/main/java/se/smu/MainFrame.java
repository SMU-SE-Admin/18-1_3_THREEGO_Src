package se.smu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import se.smu.subject.SubPanel;
import se.smu.todolist.TodoPanel;

public class MainFrame extends JFrame {
	
	private JPanel subPanel;
	private JPanel todoPanel;
	
	static String id;

	public MainFrame(String id) {
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300, 300);
		setSize(1280, 450);
		this.id = id;
		subPanel = new SubPanel(id);
		this.add(subPanel);
		
		todoPanel = new TodoPanel(id);
		this.add(todoPanel);

		setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame(id);
	}
}
