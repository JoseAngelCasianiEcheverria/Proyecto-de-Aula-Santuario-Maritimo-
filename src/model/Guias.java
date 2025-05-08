/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import model.oConstantes.GeneroEnum;

/**
 *
 * @author juanp
 */
public class Guias extends Trabajadores{
    
    private String especialidad;

    public Guias(String cargo, Date fechaContratacion, String salario, String horario, String correo, String numTelefono, String nombre, String apellido, GeneroEnum genero, String edad, int id, String especialidad) {
        super(cargo, fechaContratacion, salario, horario, correo, numTelefono, nombre, apellido, genero, edad, id);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    
    
    
}
