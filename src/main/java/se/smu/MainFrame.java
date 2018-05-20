package se.smu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import se.smu.todolist.TodoEnroll;
import se.smu.todolist.TodoModify;
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
