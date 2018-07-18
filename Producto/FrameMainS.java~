import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.filechooser.FileNameExtensionFilter;
public class FrameMainS extends JPanel{
  
  private static ArrayList<MedicinaS> medLst;
  private static JTable t;
  private static TableModelCustomS tableModel;
  private static JButton didUMean;
  private static ArrayList<MedicinaS> encontrados;
  private static ArrayList<String> caducas;
  private static JComboBox diaCB, mesCB, yearCB, fechaCB;
  private static JFrame mostrarCad, mostrarCad2;
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public FrameMainS(){
    
    //Inicializacion y propiedades de ventana
    super();
    setPreferredSize(new Dimension(630,370));
    
    final JTextField buscarTF = new JTextField(34);
    buscarTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try{buscar(buscarTF.getText());}
        catch (IOException ee){}
      }
    }
    );
    add(buscarTF);
    
    JButton buscarMedicinaS = new JButton("Buscar");
    buscarMedicinaS.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try{buscar(buscarTF.getText());}
        catch (IOException ee){}
      }
    }
    );
    add(buscarMedicinaS);
    
    didUMean = new JButton("                                        ");
    didUMean.setForeground(new Color(255,255,255));
    didUMean.setBorderPainted(false);
    didUMean.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try{ 
          if (!(didUMean.getText().equals("                                        "))){
            buscar(buscarTF.getText());
            buscarTF.setText(didUMean.getText().substring(25,didUMean.getText().length()-12));
            didUMean.setText("                                        ");
          }
        }
        catch (IOException ee){}
      }
    }
    );
    add(didUMean);
    
    try{
      medLst = leerMedArchivo(); //Obtener medicinas de archivo
      actualizarCaducidad(medLst); //Actualizar sus medicinas
      Object medMat[][] = transEnMat(medLst); //Convertirlo en data
      addTable(medMat); //Agregar a frame
    }
    catch (IOException e){}
    
    //Agrega boton agregar medicina
    JButton agregarMedicinaS = new JButton("Agregar Medicina");
    agregarMedicinaS.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        FrameAgregarS frameAgregar = new FrameAgregarS(-1, false);
        frameAgregar.setVisible(true);
      }
    }
    );
    add(agregarMedicinaS);
    
    //Agrega boton mostrar caducos
    JButton mostrarCadBoton = new JButton("Mostrar Caducos");
    mostrarCadBoton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //mostrarMedCaducas(medLst, false);
        checarCad();
      }
    }
    );
    add(mostrarCadBoton);
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public void addTable(Object data[][]){
    
    tableModel = new TableModelCustomS(data);
    t = new JTable(tableModel);
    
    //Propiedades
    t.getTableHeader().setReorderingAllowed(false); //drag false
    t.setRowSelectionAllowed(false); //selection false
    for (int col = 0; col < t.getColumnCount(); col++){
      t.getColumnModel().getColumn(col).setResizable(false); //resize false
    }
    
    //Cosmetics Cells
    t.setRowHeight(40); //row height
    t.setFont(new Font("Helvetica", Font.PLAIN, 12)); //font
    t.setGridColor(Color.WHITE); //grid
    t.setBackground(new Color(45, 197,247)); //background
    t.setForeground(Color.WHITE); //font color
    
    //Cosmetics Header
    t.getTableHeader().setBackground(new Color(29, 124, 180));
    t.getTableHeader().setForeground(Color.WHITE);
    t.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 12)); //font
    t.getTableHeader().setForeground(Color.WHITE);
    t.getTableHeader().setPreferredSize(new Dimension(500, 40));
    
    //Centrado de Cells
    DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
    centerRender.setHorizontalAlignment(JLabel.CENTER);
    t.setDefaultRenderer(Object.class, centerRender);
    
    //Centrado de Headers
    TableCellRenderer headerRender = t.getTableHeader().getDefaultRenderer();
    JLabel headerLabel = (JLabel) headerRender;
    headerLabel.setHorizontalAlignment(JLabel.CENTER);
    
    //Columna Mas Info
    t.getColumn("Mas Info").setCellRenderer(new MyButtonRendererS());
    t.getColumn("Mas Info").setCellEditor(new MyButtonEditorS(new JCheckBox()));
    
    //Insercion a scrollpane
    JScrollPane scroll = new JScrollPane(t);
    scroll.setPreferredSize(new Dimension(500, 225));
    add(scroll);
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static ArrayList<MedicinaS> leerMedArchivo() throws IOException{
    RandomAccessFile raf = new RandomAccessFile ("medicinasS.dat","rw");
    ArrayList<MedicinaS> lst = new ArrayList<MedicinaS>();
    int numReg = (int)(raf.length()/609);
    //tamReg 609: Nombre (40, string 20) + Contenido (40, string 20) + Fecha de Caducidad (8, long) + Caduco (1, boolean) + Dosis (20, string 10) + Notas (500, string 250)
    
    for (int x = 0; x < numReg; x++){
      raf.seek(609*x); //Ubica cursor en inicio de registro
      
      //Limpia variables de registros anteriores
      String nombreArr = "";
      String contenidoArr = "";
      long fechaArr = 0;
      boolean caducoArr = false;
      String dosisArr = "";
      String notasArr = "";
      
      for (int y = 0; y < 20; y++) //Lee nombre
        nombreArr = nombreArr + raf.readChar();
      
      for (int z = 0; z < 20; z++) //Lee contenido
        contenidoArr = contenidoArr + raf.readChar();
      
      fechaArr = raf.readLong(); //Lee fecha de caducidad como long
      caducoArr = raf.readBoolean(); //Lee caduco
      
      for (int w = 0; w < 10; w++) //Lee dosis
        dosisArr = dosisArr + raf.readChar();
      
      for (int v = 0; v < 250; v++) //Lee notas
        notasArr = notasArr + raf.readChar();
      
      Calendar fechaTemp = Calendar.getInstance();
      fechaTemp.setTimeInMillis(fechaArr); //Compone fecha como Calendar con TimeInMillis (long)
      
      lst.add(x, new MedicinaS(nombreArr.trim(), contenidoArr.trim(), fechaTemp, caducoArr, dosisArr.trim(), notasArr.trim()));
    }
    raf.close();
    return lst;
    
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static void escribirMedArchivo(ArrayList<MedicinaS> lst) throws IOException{ //Exporta datos a archivo
    RandomAccessFile raf = new RandomAccessFile ("medicinasS.dat","rw");
    raf.setLength(0);
    for(int x = 0; x < lst.size(); x++){
      raf.writeChars(ajustarString(lst.get(x).obtenerNombre(),20));
      raf.writeChars(ajustarString(lst.get(x).obtenerContenido(),20));
      raf.writeLong(lst.get(x).obtenerCaducidad().getTimeInMillis());
      raf.writeBoolean(lst.get(x).obtenerCaduco()); 
      raf.writeChars(ajustarString(lst.get(x).obtenerDosis(),10));
      raf.writeChars(ajustarString(lst.get(x).obtenerNotas(),250));
    }
    raf.close();
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static String ajustarString(String str, int num) throws IOException{ //Construye strings, agregar cortar str.length
    
    //String mayor a limite
    if (str.length() > num)
      str = str.substring(0, num);
    
    //String menor a limite
    else{
      for (int x = (str.length() - 1); x < (num - 1); x++)
        str = str + " ";
    }
    return str;
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static Object[][] transEnMat(ArrayList<MedicinaS> lst) throws IOException{ //Convierte medLst -> data[][] para TableModel 
    Object mat[][] = new Object[lst.size()][6];
    for (int x = 0; x < mat.length; x++){
      mat[x][0] = lst.get(x).obtenerNombre();
      mat[x][1] = lst.get(x).obtenerContenido();
      SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
      mat[x][2] = f.format(lst.get(x).obtenerCaducidad().getTime());
      mat[x][3] = lst.get(x).obtenerCaduco();
      mat[x][4] = lst.get(x).obtenerDosis();
      mat[x][5] = "Mas Info" + x; //Label de boton + indice
    }
    return mat;
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static void actualizarCaducidad(ArrayList<MedicinaS> lst){ //Actualiza caducidad en base a fecha al abrir programa
    Calendar ahora = Calendar.getInstance();
    
    for (int x = 0; x < lst.size(); x++){
      Calendar date = lst.get(x).obtenerCaducidad();
      boolean b = (date.equals(ahora) || date.before(ahora));
      lst.get(x).modificarCaduco(b);
    }
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static String search (String c, String d){
   String result = "";
   boolean e = false;
   int error = 0;
   
   String a = d; //menor
   String b = c; //mayor
   if (c.length() <= d.length()){
     a = c; b = d;
   }
   
   //Contabilizaci—n de errores
   int h = 0; //Desplazamiento
   int x = 0; //Posicion
   
   do {
     error = 0; //limpiar error
     for (x = 0; x < a.length(); x++){
       if (Character.toLowerCase(a.charAt(x)) != Character.toLowerCase(b.charAt(x+h))){
         error++; //diferente
       }
     }
     h++;
   } while ((x+h) != b.length() + 1 && (error != 0 && error != 1)); //Pase sin error
   
   //Similar
   if (error == 1 && h == 1 && a.length() == b.length())
     result = "C" + b;
   
   //Exacto
   else if (error == 0){
     if (a.length() == b.length())
       result = "A" + b;
     else
       result = "B" + b;
   }
   
   //Demasiado
   else
     result = "D";
   
   //A -> se encontro exacto
   //B -> le falta texto inicio/fin
   //C -> se encontro un error
   //D -> nada que ver
   
   //System.out.println(result);
   return result;
   }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static void buscar(String b) throws IOException{
    encontrados = new ArrayList<MedicinaS>();
    boolean found = false;
    boolean casi = false;
    String casiS = "";
    
    for (int x = 0; x<medLst.size(); x++){
      String r = search(medLst.get(x).obtenerNombre(), b);
      if (r.charAt(0) != 'D'){
        encontrados.add(medLst.get(x));
        found = true;
        if (r.charAt(0) != 'A'){
          casi = true;
          casiS = medLst.get(x).obtenerNombre();
        }
      }
    }
    
    if (b.equals("   ") || b.equals("")){
      tableModel.updateData(transEnMat(medLst));
      didUMean.setText("                                        ");
    }
    
    
    else if (!found){
      JOptionPane.showMessageDialog(null, "No se ha encontrado '" + b + "'");
      tableModel.updateData(transEnMat(medLst));
    }
    
    else{
      tableModel.updateData(transEnMat(encontrados));
      if (casi)
        didUMean.setText("<html>Quisiste decir: <b>" + casiS +"</b>?</html>");
    }
      
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static void checarCad(){
    mostrarCad = new JFrame();
    mostrarCad.setSize(300,300);
    mostrarCad.setBackground(Color.DARK_GRAY);
    mostrarCad.setLocationRelativeTo(null);
    mostrarCad.setLayout(new FlowLayout());
    mostrarCad.setTitle("Medicinas Caducas");
    
    mostrarCad.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent we){
        mostrarCad.setVisible(false);
      }
    }
    );
   
    String dia[] = {"DD", "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    diaCB = new JComboBox(dia);
   
    String mes[] = {"MM", "1","2","3","4","5","6","7","8","9","10","11","12"};
    mesCB = new JComboBox(mes);
   
    String year[] = FrameAgregarS.giveYears();
    yearCB = new JComboBox(year);
    
    JButton escogerFecha = new JButton("Escoger Fecha");
    escogerFecha.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          
          Calendar fechaInput = Calendar.getInstance(); //Fecha a modificar
          fechaInput.setLenient(false);
          boolean emergency = false;
   
          try {
            //Composicion de fecha
            int diaInput = Integer.valueOf((String)diaCB.getSelectedItem());
            int mesInput = Integer.valueOf((String)mesCB.getSelectedItem());
            mesInput--; //Resolver Enero -> 0 ...
            int yearInput = Integer.valueOf((String)yearCB.getSelectedItem());
     
            fechaInput.set(yearInput, mesInput, diaInput);
            fechaInput.getTime(); //Asegura que fecha falsa no pase
          }
          
          /////Error #3: ComboBoxes no fueron seleccionados//////////////////////////////////////
          catch (NumberFormatException ee){
            JOptionPane.showMessageDialog(null, "Complete los campos de fecha apropiadamente");
            diaCB.setSelectedIndex(0);
            mesCB.setSelectedIndex(0);
            yearCB.setSelectedIndex(0);
            emergency = true;
          }
          
          /////Error #4: Fecha es invalida (ej: feb 31)//////////////////////////////////////
          catch (IllegalArgumentException eee){
            JOptionPane.showMessageDialog(null, "Fecha ingresada no es vailda. Intente de nuevo.");
            diaCB.setSelectedIndex(0);
            mesCB.setSelectedIndex(0);
            yearCB.setSelectedIndex(0);
            emergency = true;
          }
          
          if (!emergency){
            checarCad2(fechaInput, false);
            mostrarCad.setVisible(false);
          }
        }
    }
    );
    
    JLabel fechaL = new JLabel("Fecha especifica:                                  ");
    fechaL.setForeground(Color.WHITE);
    
    JLabel fechaEn = new JLabel("En: ");
    fechaEn.setForeground(Color.WHITE);
    
    String fechaCBL[] = {"En una semana", "En un mes", "En 3 meses", "En 6 meses", "En un a–o"};
    fechaCB = new JComboBox(fechaCBL);
    
    JButton escogerFecha2 = new JButton("=>");
    escogerFecha2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int num = fechaCB.getSelectedIndex();
          Calendar fechaInput = Calendar.getInstance();
          
          switch (num){
            case 0: fechaInput.add(fechaInput.DAY_OF_YEAR, 7); break;
            case 1: fechaInput.add(fechaInput.MONTH, 1); break;
            case 2: fechaInput.add(fechaInput.MONTH, 3); break;
            case 3: fechaInput.add(fechaInput.MONTH, 6); break;
            case 4: fechaInput.add(fechaInput.YEAR, 1); break;
          }
          checarCad2(fechaInput, false);
          mostrarCad.setVisible(false);
        }
    }
    );
    
    JButton escogerFecha3 = new JButton("Hoy");
    escogerFecha3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          checarCad2(Calendar.getInstance(), false);
          mostrarCad.setVisible(false);
        }
    }
    );
    
    //Fecha especifica
    mostrarCad.add(new JLabel("                                                    "));
    mostrarCad.add(fechaL); 
    mostrarCad.add(diaCB); mostrarCad.add(mesCB); mostrarCad.add(yearCB);
    mostrarCad.add(new JLabel("                              "));
    mostrarCad.add(escogerFecha);
    mostrarCad.add(new JLabel("                                                                    "));
    
    //Fecha en
    mostrarCad.add(fechaEn);
    mostrarCad.add(fechaCB);
    mostrarCad.add(escogerFecha2);
    mostrarCad.add(new JLabel("                                                                    "));
    
    //Hoy
    mostrarCad.add(escogerFecha3);
    
   
    mostrarCad.setVisible(true);
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static void checarCad2(Calendar ahora, boolean firstTime){
    caducas = new ArrayList<String>();
    
    for (int x = 0; x < medLst.size(); x++){
      Calendar date = medLst.get(x).obtenerCaducidad();
      if (date.equals(ahora) || date.before(ahora)){
        caducas.add(medLst.get(x).obtenerNombre());
      }
    }
    
    if (caducas.size() != 0){ //Si existen medicinas caducas
      mostrarCad2 = new JFrame();
      mostrarCad2.setSize(300,300);
      mostrarCad2.setBackground(Color.DARK_GRAY);
      mostrarCad2.setLocationRelativeTo(null);
      mostrarCad2.setLayout(new FlowLayout());
      mostrarCad2.setTitle("Medicinas Caducas");
    
      JLabel titleCad;
      
      if (caducas.size() == 1)
        titleCad = new JLabel("La siguiente medicina esta caduca:"); //Caso gramatical para una medicina
      else
        titleCad = new JLabel("Las siguientes medicinas estan caducas:"); //Para dos
      
      titleCad.setForeground(Color.WHITE);
    
      JList listCad = new JList(caducas.toArray()); //JList con caducas
      listCad.setEnabled(false); //Evitar seleccion
    
      DefaultListCellRenderer centerRender = (DefaultListCellRenderer) listCad.getCellRenderer();
      centerRender.setHorizontalAlignment(SwingConstants.CENTER); //Centrado de celdas
    
      JScrollPane scrollCad = new JScrollPane(listCad);
      scrollCad.setPreferredSize(new Dimension(250, 210)); //Scrollpane
    
      JButton okCaducas = new JButton("OK"); //Boton OK
      okCaducas.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          mostrarCad2.setVisible(false);
        }
      }
      );
      
      JButton fileCaduco = new JButton("Crear archivo"); //Boton Crear archivo
      fileCaduco.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          
          final JFileChooser fc = new JFileChooser(); //FileChooser
          fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int returnVal = fc.showOpenDialog(mostrarCad2);
          
          if (returnVal == 0){
            String path = fc.getSelectedFile().getAbsolutePath().toString();
            try{
              PrintWriter fileOut = new PrintWriter(new FileWriter(path + "/Medicinas.txt", false));
              fileOut.println("Medicinas Caducas:");
              fileOut.println(" ");
              fileOut.println("/////////////////////////////////////////////////");
              for (int x = 0; x<caducas.size(); x++){
                for (int y = 0; y<medLst.size(); y++){
                  if (caducas.get(x).equals(medLst.get(y).obtenerNombre())){
                    fileOut.println(" ");
                    fileOut.println("Nombre: " + medLst.get(x).obtenerNombre());
                    fileOut.println("Contenido: " + medLst.get(x).obtenerContenido());
                    SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
                    fileOut.println("Caducidad: " + f.format(medLst.get(x).obtenerCaducidad().getTime()));
                    fileOut.println("Caduco: " + medLst.get(x).obtenerCaduco());
                    fileOut.println("Dosis: " + medLst.get(x).obtenerDosis());
                    fileOut.println(" ");
                    fileOut.println("/////////////////////////////////////////////////");
                  }
                }
              }
              JOptionPane.showMessageDialog(null, "Archivo guardado con exito");
              fileOut.close();
            }
            catch(IOException ee){};
          }
        }
      }
      );
    
      mostrarCad2.add(titleCad);
      mostrarCad2.add(scrollCad);
      mostrarCad2.add(okCaducas);
      mostrarCad2.add(fileCaduco);
    
      mostrarCad2.setVisible(true);
    }
    
    if (caducas.size() == 0 && !firstTime){
      JOptionPane.showMessageDialog(null, "Ninguna medicina esta caduca");
    }
    
    
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  //METODOS RETURN Y DE ACCESO EN FRAMEAGREGAR
  public static void agregarLst(MedicinaS m, int index){
    if (index == -1)
      medLst.add(m); //Nueva medicina
    else
      medLst.set(index, m); //Sobreescribir medicina
  }
  
  public static JTable getTable(){
    return t;
  }
  
  public static ArrayList<MedicinaS> getMedList(){
    return medLst;
  }
  
  public static ArrayList<MedicinaS> getEncontrados(){
    return encontrados;
  }
  
  public static TableModelCustomS getTableModel(){
    return tableModel;
  }
  
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static void main (String[ ] args) throws IOException {
    MainProyectoS p = new MainProyectoS();
 }
  
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
}