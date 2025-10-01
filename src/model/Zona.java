
package model;

import java.util.ArrayList;

public class Zona {
   private int idZona;
   private String ubicacion;
   private String fauna;
   private String flora;
   private ArrayList <String> listarAnimales;
   private String tipoHabitad;
   private String nombre;
   private String nivelProteccion;
   private double superficie;

    public Zona(int idZona, String ubicacion, String fauna, String flora, ArrayList listarAnimales, String tipoHabitad, String nombre, String nivelProteccion, double superficie) {
        this.idZona = idZona;
        this.ubicacion = ubicacion;
        this.fauna = fauna;
        this.flora = flora;
        this.listarAnimales = listarAnimales;
        this.tipoHabitad = tipoHabitad;
        this.nombre = nombre;
        this.nivelProteccion = nivelProteccion;
        this.superficie = superficie;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFauna() {
        return fauna;
    }

    public void setFauna(String fauna) {
        this.fauna = fauna;
    }

    public String getFlora() {
        return flora;
    }

    public void setFlora(String flora) {
        this.flora = flora;
    }

    public ArrayList getListarAnimales() {
        return listarAnimales;
    }

    public void setListarAnimales(ArrayList listarAnimales) {
        this.listarAnimales = listarAnimales;
    }

    public String getTipoHabitad() {
        return tipoHabitad;
    }

    public void setTipoHabitad(String tipoHabitad) {
        this.tipoHabitad = tipoHabitad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivelProteccion() {
        return nivelProteccion;
    }

    public void setNivelProteccion(String nivelProteccion) {
        this.nivelProteccion = nivelProteccion;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }
}
