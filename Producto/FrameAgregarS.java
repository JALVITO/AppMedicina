import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class FrameAgregarS extends JFrame {
  
   /////Components/////////////////////////////////////////////
  
  //Fields
  static JTextField nombreField, contenidoField, dosisField;
  
  //ComboBoxes
  static JComboBox diaCB, mesCB, yearCB;
  
  //Buttons
  static JButton aceptarButton, cancelarButton, editarButton, eliminarButton;
  
  //Gridbag
  static GridBagLayout gridBag; static GridBagConstraints gbc;
    
  //Others  
  static JTextArea notasTA; 
  static JPanel p;
   
 public FrameAgregarS(final int ind, boolean b) { // (-1) Agregar, (>=0) Mas Info
   
   /////Frame///////////////////////////////////////////////
   super();
   setSize(590,350);
   setLocationRelativeTo(null);
   setBackground(new Color(23, 127, 179));
   setLayout(new GridLayout(1,1));
   setTitle("Agregar Medicinas");
   setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
   addWindowListener(new WindowAdapter(){
       public void windowClosing(WindowEvent we){
        String seguroOpt[] = {"Si", "No"};
        String seguroPregunta;
        int seguroResp = JOptionPane.showOptionDialog(null, "�Seguro que quieres cerrar la ventana? \nCambios no guardados se perderan", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, seguroOpt, seguroOpt[1]);
        if (seguroResp == 0){
          setVisible(false);
        }
       }
     }
     );
   
   /////Gridbag y constraint///////////////////////////////////
   
   gridBag = new GridBagLayout();
   gbc = new GridBagConstraints();
   
   p = new JPanel();   
   p.setLayout(gridBag); //JPanel -> gridbag
   gbc.fill = GridBagConstraints.HORIZONTAL; //Components fill hor
   gbc.insets = new Insets(2,2,2,2); //Border Spacing
   
   /////Components/////////////////////////////////////////////
   
   //JLabels
   JLabel nombreLabel = new JLabel("Medicina"); 
   nombreLabel.setForeground(Color.WHITE); 
   JLabel contenidoLabel = new JLabel("Contenido"); 
   contenidoLabel.setForeground(Color.WHITE);
   JLabel dosisLabel = new JLabel("Dosis"); 
   dosisLabel.setForeground(Color.WHITE); 
   JLabel fechaLabel = new JLabel("Fecha de Caducidad"); 
   fechaLabel.setForeground(Color.WHITE);
   JLabel notasLabel = new JLabel("Notas"); 
   notasLabel.setForeground(Color.WHITE);
   
   //JTextFields
   nombreField = new JTextField(); 
   nombreField.setPreferredSize(new Dimension(300,20));
   
   contenidoField = new JTextField(); 
   contenidoField.setPreferredSize(new Dimension(300,20));
   
   dosisField = new JTextField(); 
   dosisField.setPreferredSize(new Dimension(300,20));
   
   //Arrays + JCombo Boxes (Fecha de Caducidad)
   String dia[] = {"DD", "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
   diaCB = new JComboBox(dia);
   
   String mes[] = {"MM", "1","2","3","4","5","6","7","8","9","10","11","12"};
   mesCB = new JComboBox(mes);
   
   String year[] = giveYears();
   yearCB = new JComboBox(year);
   
   //JTextArea + Scrollable
   notasTA = new JTextArea();
   notasTA.setLineWrap(true); notasTA.setWrapStyleWord(true);
   
   JScrollPane scrollTA = new JScrollPane(notasTA);
   scrollTA.setPreferredSize(new Dimension(240,165));
   
   //Buttons
   aceptarButton = new JButton("Aceptar");
   aceptarButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        MedicinaS temp;
        
        if (ind == -1)
          temp = addMedicine(ind);
        else
           temp = addMedicine(encontrarIndice());

        //MedicinaS esta bien
        if (!temp.obtenerNombre().equals("XXXX")){
          if (ind == -1)
            FrameMainS.agregarLst(temp, ind); //Nueva MedicinaS
          else
            FrameMainS.agregarLst(temp, encontrarIndice()); //Sobreescribir MedicinaS
            
          try{
            FrameMainS.getTableModel().updateData(FrameMainS.transEnMat(FrameMainS.getMedList()));
          }
          catch (IOException ee){};
          setVisible(false);
        }
      }
    }
    );
   
   
   cancelarButton = new JButton("Cancelar");
   cancelarButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String seguroOpt[] = {"Si", "No"};
        int seguroResp = JOptionPane.showOptionDialog(null, "�Seguro que quieres cancelar la accion?", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, seguroOpt, seguroOpt[1]);
        if (seguroResp == 0){
          setVisible(false);
        }
      }
    }
    );
   
   editarButton = new JButton("Editar");
   editarButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        masInfoAEditar();
      }
    }
    );
   
   eliminarButton = new JButton("Eliminar");
   eliminarButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String seguroOpt[] = {"Si", "No"};
        int seguroResp = JOptionPane.showOptionDialog(null, "�Seguro que quieres eliminar la medicina?", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, seguroOpt, seguroOpt[1]);
        if (seguroResp == 0){
          eliminarMed();
          setVisible(false);
        }
      }
    }
    );
   
   /////Adding components//////////////////////////////////////
   
   //Left side
   gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4; //Default values
   addComponent(nombreLabel, gridBag, gbc); //(0,0)
   
   gbc.gridy++; addComponent(nombreField, gridBag, gbc); //(0,1)
   gbc.gridy++; addComponent(contenidoLabel, gridBag, gbc); //(0,2)
   gbc.gridy++; addComponent(contenidoField, gridBag, gbc); //(0,3)
   gbc.gridy++; addComponent(dosisLabel, gridBag, gbc); //(0,4)
   gbc.gridy++; addComponent(dosisField, gridBag, gbc); //(0,5)
   gbc.gridy++; addComponent(fechaLabel, gridBag, gbc); //(0,6)
   gbc.gridy++; gbc.gridwidth = 1; addComponent(diaCB, gridBag, gbc); //(0,7)
   gbc.gridx++; addComponent(mesCB, gridBag, gbc); //(1,7)
   gbc.gridx++; addComponent(yearCB, gridBag, gbc); //(2,7)
   
   //Aceptar & Cancelar Buttons
   if (ind == -1){
     gbc.gridy++; gbc.gridx = 4; addComponent(cancelarButton, gridBag, gbc); //(4,8)
     gbc.gridx++; addComponent(aceptarButton, gridBag, gbc); //(5,8)
   }
   else{
     gbc.gridy++; gbc.gridx = 4; addComponent(editarButton, gridBag, gbc); //(4,8)
     gbc.gridx++; addComponent(eliminarButton, gridBag, gbc); //(5,8)
   }
   
   //Notas
   gbc.gridx = 4; gbc.gridy = 0; gbc.gridwidth = 3; addComponent(notasLabel, gridBag, gbc); //(4,0)
   gbc.gridy++; gbc.gridheight = 7; addComponent(scrollTA, gridBag, gbc); //(4,1)
   
   //Agregar panel a Frame
   add(p);
   
   //Caso Mas Info
   if (ind >= 0){
     if(b){
       nombreField.setText(FrameMainS.getEncontrados().get(ind).obtenerNombre());
       contenidoField.setText(FrameMainS.getEncontrados().get(ind).obtenerContenido());
       dosisField.setText(FrameMainS.getEncontrados().get(ind).obtenerDosis());
       diaCB.setSelectedItem(String.valueOf(FrameMainS.getEncontrados().get(ind).obtenerCaducidad().get(Calendar.DAY_OF_MONTH)));
       mesCB.setSelectedItem(String.valueOf(FrameMainS.getEncontrados().get(ind).obtenerCaducidad().get(Calendar.MONTH) + 1));
       yearCB.setSelectedItem(String.valueOf(FrameMainS.getEncontrados().get(ind).obtenerCaducidad().get(Calendar.YEAR)));
       notasTA.setText(FrameMainS.getEncontrados().get(ind).obtenerNotas());
     }
     else{
       nombreField.setText(FrameMainS.getMedList().get(ind).obtenerNombre());
       contenidoField.setText(FrameMainS.getMedList().get(ind).obtenerContenido());
       dosisField.setText(FrameMainS.getMedList().get(ind).obtenerDosis());
       diaCB.setSelectedItem(String.valueOf(FrameMainS.getMedList().get(ind).obtenerCaducidad().get(Calendar.DAY_OF_MONTH)));
       mesCB.setSelectedItem(String.valueOf(FrameMainS.getMedList().get(ind).obtenerCaducidad().get(Calendar.MONTH) + 1));
       yearCB.setSelectedItem(String.valueOf(FrameMainS.getMedList().get(ind).obtenerCaducidad().get(Calendar.YEAR)));
       notasTA.setText(FrameMainS.getMedList().get(ind).obtenerNotas());
     }
     editarAMasInfo();
   }
 }
 
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 public static void addComponent(Component comp, GridBagLayout gridBag, GridBagConstraints gbc){ //fija constraints y agrega componente al GB
   gridBag.setConstraints(comp, gbc);
   p.add(comp);
 }
 
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 public static String[] giveYears(){ //Obtiene arreglo de a�o actual + 10 para yearCB
   Calendar ahora = Calendar.getInstance();
   int year = ahora.get(Calendar.YEAR);
   
   String arrYears[] = new String[12];
   arrYears[0] = "AAAA";
   
   for (int x = 1; x < arrYears.length; x++){
     arrYears[x] = Integer.toString(year); 
     year++;
   }
   return arrYears;
 }
 
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 public MedicinaS addMedicine(int indice){
   
   boolean emergency = false;
   
   //Obtener input de TextFields
   String nombreInput = nombreField.getText();
   String contenidoInput = contenidoField.getText();
   String dosisInput = dosisField.getText();
   String notasInput = notasTA.getText();
   
   /////Error #1: Mismo nombre que medicina previa//////////////////////////////////////
   if (indice == -1){
     for (int x = 0; x < FrameMainS.getMedList().size(); x++){
       if (FrameMainS.getMedList().get(x).obtenerNombre().equalsIgnoreCase(nombreInput)){
         JOptionPane.showMessageDialog(null, "Dicha medicina ya existe. Por favor elija otro nombre");
         emergency = true;
       }
     }
   }
   
   /////Error #2: Datos largos//////////////////////////////////////
   if (nombreInput.length() > 20 || contenidoInput.length() > 20 || dosisInput.length() > 10 || notasInput.length() > 250){
     String pregOpt[] = {"Si", "No"};
     int pregunta = JOptionPane.showOptionDialog(null, "Por motivos de memoria los campos de Medicina, Contenido, Dosis y Notas \nestan limitados a 20, 20, 10 y 250 caracteres respectivamente. Alguno de los \ncampos excede este limite. �Desea continuar con los datos recortados (Si) o \nvolver a ingresarlos (No)", "Atencion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, pregOpt, pregOpt[1]);
    
     //Cambiar los datos, mandar emergencia
     if (pregunta == 1)
       emergency = true;
   }
   
   Calendar fechaInput = Calendar.getInstance(); //Fecha a modificar
   fechaInput.setLenient(false);
   Calendar ahora = Calendar.getInstance(); //Fecha actual
   
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
   
   catch (NumberFormatException e){
     JOptionPane.showMessageDialog(null, "Complete los campos de fecha apropiadamente");
     diaCB.setSelectedIndex(0);
     mesCB.setSelectedIndex(0);
     yearCB.setSelectedIndex(0);
     emergency = true;
   }
   
   /////Error #4: Fecha es invalida (ej: feb 31)//////////////////////////////////////
   
   catch (IllegalArgumentException e){ //Thx Java6
     JOptionPane.showMessageDialog(null, "Fecha ingresada no es vailda. Intente de nuevo.");
     diaCB.setSelectedIndex(0);
     mesCB.setSelectedIndex(0);
     yearCB.setSelectedIndex(0);
     emergency = true;
   }
   
   //Retorno
   MedicinaS test;
   
   if (emergency)
     test = new MedicinaS(); //Manda medicina vacia, con nombre "XXXX" que no se agrega a la lista
   else
     test = new MedicinaS(nombreInput, contenidoInput, fechaInput, (fechaInput.equals(ahora) || fechaInput.before(ahora)) , dosisInput, notasInput);

   return test;
 }
 
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 public static void masInfoAEditar(){
   
   nombreField.setEditable(true);
   nombreField.setForeground(Color.BLACK);
     
   contenidoField.setEditable(true);
   contenidoField.setForeground(Color.BLACK);
     
   dosisField.setEditable(true);
   dosisField.setForeground(Color.BLACK);
     
   diaCB.setEnabled(true);
   mesCB.setEnabled(true); 
   yearCB.setEnabled(true);
     
   notasTA.setEditable(true);
   notasTA.setForeground(Color.BLACK);
   
   editarButton.setVisible(false);
   eliminarButton.setVisible(false);
   
   gbc.gridy = 8; gbc.gridwidth = 1; gbc.gridheight = 1; addComponent(cancelarButton, gridBag, gbc); //(4,8)
   gbc.gridx++; addComponent(aceptarButton, gridBag, gbc); //(5,8)
 }
 
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 public static void editarAMasInfo(){
   
   nombreField.setEditable(false);
   nombreField.setForeground(Color.GRAY);
     
   contenidoField.setEditable(false);
   contenidoField.setForeground(Color.GRAY);
     
   dosisField.setEditable(false);
   dosisField.setForeground(Color.GRAY);
     
   diaCB.setEnabled(false);
   mesCB.setEnabled(false); 
   yearCB.setEnabled(false);
     
   notasTA.setEditable(false);
   notasTA.setForeground(Color.GRAY);
 }
 
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
 
 public static int encontrarIndice(){
   int r = -1;
   for (int x = 0; x < FrameMainS.getMedList().size(); x++){
     if (nombreField.getText().equals(FrameMainS.getMedList().get(x).obtenerNombre())){
       r = x;
     }
   }
   return r;
 }
 
 public static void eliminarMed (){
   try{
     FrameMainS.getMedList().remove(encontrarIndice());
     FrameMainS.getTableModel().updateData(FrameMainS.transEnMat(FrameMainS.getMedList()));
   }
   catch (IOException e) {}
 }
 
 public static void main(String args[]) {
   MainProyectoS p = new MainProyectoS();
  }
 
 }