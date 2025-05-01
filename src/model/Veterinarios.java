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
public class Veterinarios extends Trabajadores{
    
    private String area;

    public Veterinarios(String cargo, Date fechaContratacion, String salario, String horario, String correo, String numTelefono, String nombre, String apellido, String genero, String edad, int id) {
        super(cargo, fechaContratacion, salario, horario, correo, numTelefono, nombre, apellido, genero, edad, id);
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
    
    
}
  
   
