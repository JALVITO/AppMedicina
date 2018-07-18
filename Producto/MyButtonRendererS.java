//Basada en implementacion de http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MyButtonRendererS extends JButton implements TableCellRenderer {
    
  public MyButtonRendererS() {
    setOpaque(true);
  }
  
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    setText(value.toString().substring(0,8)); //Oculta indice de label de boton
    return this;
  }

}