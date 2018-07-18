import javax.swing.table.AbstractTableModel;

class TableModelCustomS extends AbstractTableModel {
  private String columnTitles[] = {"Medicina", "Contenido", "Caducidad", "Caduco", "Dosis", "Mas Info"};
  private Object data[][];
    
  //CONSTRUCTOR
  public TableModelCustomS(Object d[][]) {
    this.data = d;
  }
  
  //ACTUALIZA CONTENIDOS DE JTABLE (PARA FRAMEAGREGAR)
  public void updateData(Object d[][]) {
    this.data = d;
    fireTableDataChanged();
  }
    
  //OBTENER VALUE
  public Object getValueAt(int row, int column) {
    return data[row][column];
  }
    
  //NUM DE COLUMNAS
  public int getColumnCount() {
    return columnTitles.length;
  }
    
  //NUM DE RENGLONES
  public int getRowCount() {
    return data.length;
  }
    
  //NOMBRE DE COLUMNA
  public String getColumnName(int col) {
    return columnTitles[col];
  }
  
  //EDICION DE CELLS
  public boolean isCellEditable(int row, int column){
    boolean b = false;
    
    if (column == 5)
      b = true; //Permite obtener input de botones "Mas Info"
      
    return b;  
  }
}