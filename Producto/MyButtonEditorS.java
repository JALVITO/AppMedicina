//Basada en implementacion de http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyButtonEditorS extends DefaultCellEditor {
  protected JButton button;
  private String label;
  private boolean isPushed;
  private int reng;

  public MyButtonEditorS(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      fireEditingStopped();
    }
    });
    
  }
      
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    reng = Integer.valueOf(value.toString().substring(8)); //Obtiene indice de boton
    label = value.toString().substring(0,8); //Corta indice, para que no aparezca
    button.setText(label);
    isPushed = true;
    return button;
  }
    
  public Object getCellEditorValue() {
    if (isPushed){
      //Comparar tama�os de listas
      boolean b = FrameMainS.getTable().getModel().getRowCount() != FrameMainS.getMedList().size();
        
      
      FrameAgregarS fa = new FrameAgregarS(reng, b);
      fa.setVisible(true);
    }
    isPushed = false;
    return new String(label);
  }
    
  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }
  
  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}