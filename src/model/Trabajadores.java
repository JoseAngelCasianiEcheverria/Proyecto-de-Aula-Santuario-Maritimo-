
package model;

import java.util.Date;

public class Trabajadores {
    private int idTrabajador;
    private String nombre;
    private String cargo;
    private Date fechaContratacion;
    private double salario;
    private int horasTrabajo;
    private String correo;
    private int numTelefono;

    public Trabajadores(int idTrabajador, String nombre, String cargo, Date fechaContratacion, double salario, int horasTrabajo, String correo, int numTelefono) {
        this.idTrabajador = idTrabajador;
        this.nombre = nombre;
        this.cargo = cargo;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
        this.horasTrabajo = horasTrabajo;
        this.correo = correo;
        this.numTelefono = numTelefono;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getHorasTrabajo() {
        return horasTrabajo;
    }

    public void setHorasTrabajo(int horasTrabajo) {
        this.horasTrabajo = horasTrabajo;
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
