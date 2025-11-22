/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Gercray
 */
public class InformeZona {

    private String numeroZ;
    private String tiempoDet;
    private String tipoIncidente;
    private String gravedad;
    private String tiempoRecuperacion;
    private String tipoAfectacion;
    private String estado;
    private String reportado;

    public InformeZona() {
    }

    public InformeZona(String numeroZ, String tiempoDet, String tipoIncidente, String gravedad, String tiempoRecuperacion, String tipoAfectacion, String estado, String reportado) {
        this.numeroZ = numeroZ;
        this.tiempoDet = tiempoDet;
        this.tipoIncidente = tipoIncidente;
        this.gravedad = gravedad;
        this.tiempoRecuperacion = tiempoRecuperacion;
        this.tipoAfectacion = tipoAfectacion;
        this.estado = estado;
        this.reportado = reportado;
    }

    public String getNumeroZ() {
        return numeroZ;
    }

    public void setNumeroZ(String numeroZ) {
        this.numeroZ = numeroZ;
    }

    public String getTiempoDet() {
        return tiempoDet;
    }

    public void setTiempoDet(String tiempoDet) {
        this.tiempoDet = tiempoDet;
    }

    public String getTipoIncidente() {
        return tipoIncidente;
    }

    public void setTipoIncidente(String tipoIncidente) {
        this.tipoIncidente = tipoIncidente;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public String getTiempoRecuperacion() {
        return tiempoRecuperacion;
    }

    public void setTiempoRecuperacion(String tiempoRecuperacion) {
        this.tiempoRecuperacion = tiempoRecuperacion;
    }

    public String getTipoAfectacion() {
        return tipoAfectacion;
    }

    public void setTipoAfectacion(String tipoAfectacion) {
        this.tipoAfectacion = tipoAfectacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReportado() {
        return reportado;
    }

    public void setReportado(String reportado) {
        this.reportado = reportado;
    }    
    
}
