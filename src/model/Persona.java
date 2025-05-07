
package model;

import model.oConstantes.GeneroEnum;

public class Persona {
    private String nombre;
    private String apellido;
    private GeneroEnum genero;
    private String edad;
    private int id;

    public Persona(String nombre, String apellido, GeneroEnum genero, String edad, int id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.edad = edad;
        this.id = id;
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

   

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

   
    
    
}
