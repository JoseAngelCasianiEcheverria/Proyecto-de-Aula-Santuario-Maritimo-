/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;

/**
 *
 * @author USER
 */
public class Agendas {
    private String tipoEntrada;
    private String nVisitantes;
    private Date fecha;
    private int id;
    private String correo;
    private String hora;
    private Guias guia;
    private double costo;

    public Agendas(String tipoEntrada, String nVisitantes, Date fecha, int id, String correo, String hora, Guias guia, double costo) {
        this.tipoEntrada = tipoEntrada;
        this.nVisitantes = nVisitantes;
        this.fecha = fecha;
        this.id = id;
        this.correo = correo;
        this.hora = hora;
        this.guia = guia;
        this.costo = costo;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public String getnVisitantes() {
        return nVisitantes;
    }

    public void setnVisitantes(String nVisitantes) {
        this.nVisitantes = nVisitantes;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Guias getGuia() {
        return guia;
    }

    public void setGuia(Guias guia) {
        this.guia = guia;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
}

    
    