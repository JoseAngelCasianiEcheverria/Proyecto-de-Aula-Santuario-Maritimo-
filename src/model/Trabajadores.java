
package model;

import java.util.Date;

public class Trabajadores extends Persona{
    private String cargo;
    private Date fechaContratacion;
    private String salario;
    private String horario;
    private String correo;
    private int numTelefono;

    public Trabajadores(String cargo, Date fechaContratacion, String salario, String horario, String correo, int numTelefono, String nombre, String apellido, String genero, String edad, int id) {
        super(nombre, apellido, genero, edad, id);
        this.cargo = cargo;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
        this.horario = horario;
        this.correo = correo;
        this.numTelefono = numTelefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(int numTelefono) {
        this.numTelefono = numTelefono;
    }
    
    
    

   
    
    
    

    
    public void mostrarInformacion(){
        
    }
    
    public void actualizarSalario(){
        
    }
    
    public void actualizarHorasTrabajo(){
        
    }
    
    
    
}
