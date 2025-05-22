
package model;

import java.util.Date;
import Model.oConstantes.SexoEnum;

public class Animales {
    private String nombre;
    private int idAnimal;
    private String peso;
    private String especie;
    private SexoEnum  sexo;
    private String estadoSalud;
    private String ubicacion;
    private String habitat;
    private String cuidadorAsignado;
    private Date fechaIngreso;

    public Animales(String nombre, int idAnimal, String peso, String especie, SexoEnum sexo, String estadoSalud, String ubicacion, String habitat, String cuidadorAsignado, Date fechaIngreso) {
        this.nombre = nombre;
        this.idAnimal = idAnimal;
        this.especie = especie;
        this.sexo = sexo;
        this.estadoSalud = estadoSalud;
        this.ubicacion = ubicacion;
        this.peso = peso;
        this.habitat = habitat;
        this.cuidadorAsignado = cuidadorAsignado;
        this.fechaIngreso = fechaIngreso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }
    

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCuidadorAsignado() {
        return cuidadorAsignado;
    }

    public void setCuidadorAsignado(String cuidadorAsignado) {
        this.cuidadorAsignado = cuidadorAsignado;
    }
    
    
    public String getHabitat(){
        return habitat;
    }
    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    
    
    
    
    public void mostrarInformacion(){
        
    }
    
    public void actualizarEstado(){
    
    }
    
    public void actualizarUbicacion(){
        
    }
    
    public void buscarAnimal(){
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
