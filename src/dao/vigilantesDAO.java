/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Vigilantes;

/**
 *
 * @author juanp
 */
public class vigilantesDAO {
    
    private static final String ARCHIVO = "C:\\Users\\juanp\\OneDrive\\Documentos\\NetBeansProjects\\Proyecto-de-Aula-Santuario-Maritimo-\\src\\resources\\data\\vigilante.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public List<Vigilantes> cargarRegistros(){
        try(Reader read = new FileReader(ARCHIVO)) {
            Type lista = new TypeToken<ArrayList<Vigilantes>>(){}.getType();
            List<Vigilantes> vigilantes = gson.fromJson(read, lista);
            return vigilantes != null ? vigilantes : new ArrayList<>();
            
        } catch (IOException e) {
            System.out.println("Error. No se puede cargar" + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void guardarVigilantes(Vigilantes vigilante){
        List<Vigilantes> vigilantes = cargarRegistros();
        vigilantes.add(vigilante);
        guardarTodos(vigilantes);
        
    }
    public void guardarTodos(List<Vigilantes> vigilantes){
         try(FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(vigilantes, writer);
            
        } catch (IOException e) {
          System.err.println("Error al guardar vigilantes: " + e.getMessage());
        }  
    }
    
    public boolean eliminarConID(String id){
        List<Vigilantes> vigilantes = cargarRegistros();
        int iDBuscado;
        
        try {
            iDBuscado = Integer.parseInt(id);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID no encontrado en el sistema","Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        boolean eliminacion = vigilantes.removeIf(vigilante -> vigilante.getId() == iDBuscado);
        if (eliminacion) {
            guardarTodos(vigilantes);
        }
        return eliminacion;
    }
    
      public Vigilantes buscarConID(int id){
        List<Vigilantes> vigilantes = cargarRegistros();
        
        for(Vigilantes vigilante : vigilantes){
            if (vigilante.getId() == id) {
                return vigilante;
            }
        }
        return null;
    }
    
    
    
}
