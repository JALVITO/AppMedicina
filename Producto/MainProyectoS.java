import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MainProyectoS {
  
 private static BufferedReader stdIn=new
     BufferedReader (new InputStreamReader(System.in));
 private static PrintWriter stdOut=new
     PrintWriter (System.out,true);
 
 //INICIALIZAR PROYECTO
 public MainProyectoS(){
   JFrame f = new JFrame();
   f.setSize(630,395);
   f.setBackground(new Color(23, 127, 179));
   f.setLocationRelativeTo(null);
   f.setLayout(new FlowLayout());
   f.setTitle("Inventario Medicina");
   f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //Permite cerrado seguro
   f.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent we){
        String seguroOpt[] = {"Si", "No"};
        int seguroResp = JOptionPane.showOptionDialog(null, "ÀSeguro que quieres salir?", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, seguroOpt, seguroOpt[1]);
        if (seguroResp == 0){
          try{ FrameMainS.escribirMedArchivo(FrameMainS.getMedList()); }
          catch (IOException e) {};
          System.exit(0);
        }
       }
     }
     );
   
   JTabbedPane jtp = new JTabbedPane();
   
   f.add(jtp);
   jtp.add("Inventario Medicinas", new FrameMainS());
   
   f.setVisible(true);
   FrameMainS.checarCad2(Calendar.getInstance(), true);
 }
  
 public static void main (String[ ] args) throws IOException {
   MainProyectoS p = new MainProyectoS();
 }

}