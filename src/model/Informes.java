/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Gercray
 */
public class Informes {
    private String fecha;
    private String especie;
    private String idAnimal;
    private String veterinario;
    private String edad;
    private String lesion;
    private String estado;
    private String tiempoRecuperacion;
    private String medicamento;
    private String dosis;
    private String administracion;
    private String duracionTratamiento;

    public Informes() {
    }

    public Informes(String fecha, String especie, String idAnimal, String veterinario, String edad, String lesion, String estado, String tiempoRecuperacion, String medicamento, String dosis, String administracion, String duracionTratamiento) {
        this.fecha = fecha;
        this.especie = especie;
        this.idAnimal = idAnimal;
        this.veterinario = veterinario;
        this.edad = edad;
        this.lesion = lesion;
        this.estado = estado;
        this.tiempoRecuperacion = tiempoRecuperacion;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.administracion = administracion;
        this.duracionTratamiento = duracionTratamiento;
    }

    

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getLesion() {
        return lesion;
    }

    public void setLesion(String lesion) {
        this.lesion = lesion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTiempoRecuperacion() {
        return tiempoRecuperacion;
    }

    public void setTiempoRecuperacion(String tiempoRecuperacion) {
        this.tiempoRecuperacion = tiempoRecuperacion;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public String getDuracionTratamiento() {
        return duracionTratamiento;
    }

    public void setDuracionTratamiento(String duracionTratamiento) {
        this.duracionTratamiento = duracionTratamiento;
    }
    
}