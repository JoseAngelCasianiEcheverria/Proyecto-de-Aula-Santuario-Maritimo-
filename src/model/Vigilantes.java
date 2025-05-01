/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author juanp
 */
public class Vigilantes extends Trabajadores{
    
    private String Area;
    
    public Vigilantes(String cargo, Date fechaContratacion, String salario, String horario, String correo, String numTelefono, String nombre, String apellido, String genero, String edad, int id) {
    super(cargo, fechaContratacion, salario, horario, correo, numTelefono, nombre, apellido, genero, edad, id);
}

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }
    
    
    
    
}
