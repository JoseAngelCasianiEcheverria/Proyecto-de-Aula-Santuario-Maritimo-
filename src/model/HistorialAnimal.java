/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.Date;

/**
 *
 * @author juanp
 */

public class HistorialAnimal {
    private int idAnimal;
    private String tipoAntecedente;
    private Date fechaAntecedente;
    private String descripcion;
    private String responsable;
    
    public HistorialAnimal(){
        
    }

    public HistorialAnimal(int id, String tipoAntecedente, Date fechaAntecedente, String descripcion, String responsable) {
        this.idAnimal = id;
        this.tipoAntecedente = tipoAntecedente;
        this.fechaAntecedente = fechaAntecedente;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getTipoAntecedente() {
        return tipoAntecedente;
    }

    public void setTipoAntecedente(String tipoAntecedente) {
        this.tipoAntecedente = tipoAntecedente;
    }

    public Date getFechaAntecedente() {
        return fechaAntecedente;
    }

    public void setFechaAntecedente(Date fechaAntecedente) {
        this.fechaAntecedente = fechaAntecedente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    
    
    
    
}
