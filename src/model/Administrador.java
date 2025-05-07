
package model;

import java.util.Date;
import model.oConstantes.GeneroEnum;

public class Administrador extends Persona{
   private String nombreusuario;
   private String contraseña; 
   private Date horarioDeTrabajo;

    public Administrador(String nombreusuario, String contraseña, Date horarioDeTrabajo, String nombre, String apellido, GeneroEnum genero, String edad, int id) {
        super(nombre, apellido, genero, edad, id);
        this.nombreusuario = nombreusuario;
        this.contraseña = contraseña;
        this.horarioDeTrabajo = horarioDeTrabajo;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Date getHorarioDeTrabajo() {
        return horarioDeTrabajo;
    }

    public void setHorarioDeTrabajo(Date horarioDeTrabajo) {
        this.horarioDeTrabajo = horarioDeTrabajo;
    }
    
    public void AgregarAnimales(){
        
    }
    
    public void AgregarCuidadores(){
        
    }
    
    public void AgregarVeterinarios(){
        
    }
    
    public void AgregarVigilantes(){
        
    }
    
    public void AgregarGuias(){
        
    }
    
    public void ReporteEstadoAnimales(){
        
    }
    
    public void ReporteEstadoSantuario(){
        
    }
    
    public void ReporteEstadoPersonal(){
        
    }
    
    public void AsignarAnimalesAZonas(){
        
    }
    
    public void GestionarAnimales(){
        
    }
    
    public void AsignarZonas(){
        
    }
    
    
}
