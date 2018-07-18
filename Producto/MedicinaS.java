import java.util.*;
public class MedicinaS {
 
 //Variables de instancia
 private String nombre;
 private String contenido; //Sales
 private Calendar caducidad;
 private boolean caduco;
 private String dosis;
 private String notas;
 
 //Constructor nulo
 public MedicinaS(){ 
   this.nombre = "XXXX";
 }
 
 //Constructor con parametros
 public MedicinaS(String nom, String cont, Calendar cad, boolean c, String cant, String not){
   this.nombre = nom;
   this.contenido = cont;
   this.caducidad = cad;
   caducidad.setLenient(false);
   this.caduco = c;
   this.dosis = cant;
   this.notas = not;
 }
 
 //Getters 行行行行行行行行行行行行行行行行|
 public String obtenerNombre(){
   return this.nombre;
 }
 
 public String obtenerContenido(){
   return this.contenido;
 }
 
 public Calendar obtenerCaducidad(){
   return this.caducidad;
 }
 
 public boolean obtenerCaduco(){
   return this.caduco;
 }
 
 public String obtenerDosis(){
   return this.dosis;
 }
 
 public String obtenerNotas(){
   return this.notas;
 }
 
 //Setters 行行行行行行行行行行行行行行行行|
 public void modificarNombre(String n){
   this.nombre = n;
 }
 
 public void modificarContenido(String c){
   this.contenido = c;
 }
 
 public void modificarCaducidad(Calendar c){
   this.caducidad = c;
 }
 
 public void modificarCaduco(boolean c){
   this.caduco = c;
 }
 
 public void modificarDosis(String c){
   this.dosis = c;
 }
 
 public void modificarNotas(String n){
   this.notas = n;
 }
 
}