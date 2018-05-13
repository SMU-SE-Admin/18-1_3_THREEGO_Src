package se.smu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.*;
import java.awt.*;


public class SubFrame extends JFrame implements ActionListener{
	String subTitle[] = {"년도-학기", "과목명", "요일", "시간", "교수", "변경", "삭제"};
	public DefaultTableModel subtableModel;
	JTable table;
	JLabel lblId;
	JScrollPane sp;
	JPanel panel;
	JButton enrollButton;
	public Object input_data[] = new Object[7];
	JButton m_button, d_button;
	int row;
	
	public SubFrame() {
		setLocation(300, 300);
		setSize(1000, 500);
		subtableModel = new DefaultTableModel(subTitle, 0);
		getContentPane().setLayout(null);			
		table = new JTable(subtableModel);
		
		sp = new JScrollPane(table);
		sp.setBounds(0, 47, 500, 400);
		getContentPane().add(sp);
		
		enrollButton = new JButton("등록");
		enrollButton.setBounds(415, 8, 71, 33);
		getContentPane().add(enrollButton);
		
		enrollButton.addActionListener(this);
		
		JLabel lblId_1 = new JLabel("ID : 201411205");
		lblId_1.setBounds(14, 17, 121, 18);
		getContentPane().add(lblId_1);
		
		table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField(), subtableModel, input_data, row));
		table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField(), subtableModel, input_data, row));
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new SubFrame();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == enrollButton) {
			Frame fs = new enrollWindow(subtableModel, input_data);
			fs.setVisible(true);
			row = table.getSelectedRow();
		}
	}
}

class ButtonRenderer extends JButton implements TableCellRenderer {
	 public ButtonRenderer() {
		    setOpaque(true);
	 }
	 public Component getTableCellRendererComponent(JTable table, Object obj,
	      boolean selected, boolean focused, int row, int col) {

	      setText((obj==null) ? "":obj.toString());
	    return this;
	  }	  
}

class ButtonEditor extends DefaultCellEditor
{
  protected JButton btn;
   private String lbl;
   private Boolean clicked;

   public ButtonEditor(JTextField txt, final DefaultTableModel subtableModel, final Object input_data[], final int row) {
    super(txt);

    btn=new JButton();
    btn.setOpaque(true);
    
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  if(e.getActionCommand() == "변경") {
    		
    		  System.out.println("변경");
    		  Frame fs = new enrollModifyWindow(subtableModel, input_data);
    		  fs.setVisible(true);
    	  }    		 
    	  if(e.getActionCommand() == "삭제") {
    		  System.out.println("삭제");
    		  subtableModel.removeRow(row);
    	  }
        fireEditingStopped();
      }
    });
  }

   @Override
  public Component getTableCellEditorComponent(JTable table, Object obj,
      boolean selected, int row, int col) {

     lbl=(obj==null) ? "":obj.toString();
     btn.setText(lbl);
     clicked=true;
    return btn;
  }
  
  @Override
  public Object getCellEditorValue() {

      if(clicked)
       {
         //JOptionPane.showMessageDialog(btn, lbl+" Clicked");
       }
     clicked=false;
     return new String(lbl);
   }

    @Override
   public boolean stopCellEditing() {

       clicked=false;
     return super.stopCellEditing();
   }

    @Override
   protected void fireEditingStopped() {
     super.fireEditingStopped();
   }
}
