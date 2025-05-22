package model;
import java.util.ArrayList;

import model.oConstantes.GeneroEnum;

public class Visitantes extends Persona{
    private String correo;
    private String telefono;
    private Guias guiaSeleccionado;
    private String contraseña;

    public Visitantes(String correo, String telefono,  String contraseña,  String nombre, String apellido, GeneroEnum genero, int edad, int iD) {
        super(nombre, apellido, genero, edad, iD);
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Guias getGuiaSeleccionado() {
        return guiaSeleccionado;
    }

    public void setGuiaSeleccionado(Guias guiaSeleccionado) {
        this.guiaSeleccionado = guiaSeleccionado;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    
    

   
   
    
    public void ConsultarVisitantes(){
        
    }
    
    public void AgendarVisita(){
        
    }
    
    public void guardarRegistroVisitantes() {
        
    }
    
    public void actualizarVisitantes(){
        
    }
    
    public void SeleccionarGuia(){
        
    }
    
    public void EvaluarGuia(){
        
    }
    
    public void EvaluarSantuario(){
        
    }
    
    public void ConsultarZonas(){
        
    }
    
    public void CancelarVisita(){
        
    }
    
    public void ModificarDatosPersonales(){
        
    }
    
}