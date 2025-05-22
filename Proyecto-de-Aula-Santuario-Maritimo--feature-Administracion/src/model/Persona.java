
package model;

import model.oConstantes.GeneroEnum;

public class Persona {
    private String nombre;
    private String apellido;
    private GeneroEnum genero;
    private int edad;
    private int iD;

    public Persona(String nombre, String apellido, GeneroEnum genero, int edad, int iD) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.edad = edad;
        this.iD = iD;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    public GeneroEnum getGenero() {
        return genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }
    
    
}
