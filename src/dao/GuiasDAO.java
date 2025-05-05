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
import model.Guias;

/**
 *
 * @author juanp
 */
public class GuiasDAO {
    private static final String ARCHIVO = "C:\\Users\\juanp\\OneDrive\\Documentos\\NetBeansProjects\\Proyecto-de-Aula-Santuario-Maritimo-\\src\\resources\\data\\guia.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    public List<Guias> cargarRegistros() {
        try (Reader read = new FileReader(ARCHIVO)) {
            Type Lista = new TypeToken<ArrayList<Guias>>(){}.getType();
            List<Guias> guias = gson.fromJson(read, Lista);
            return guias != null ? guias : new ArrayList<>();
            
        } catch (IOException e) {
            System.err.println("ERROR. No se puede cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void guardarGuias(Guias guia){
        List<Guias> guias = cargarRegistros();
        guias.add(guia);
        guardarTodos(guias);
        
    }
    
    public void guardarTodos(List<Guias> guias){
        try(FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(guias, writer);
            
        } catch (IOException e) {
          System.err.println("Error al guardar guias turisticos: " + e.getMessage());
        }     
    }
    
    public boolean eliminarConID(String id){
        List<Guias> guias = cargarRegistros();
        int iDBuscado;
        
        try {
            iDBuscado = Integer.parseInt(id);    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"ID no encontrado","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        } 
        boolean eliminacion = guias.removeIf(guia -> guia.getId() == iDBuscado);
        if (eliminacion) {
            guardarTodos(guias);
        }
        return eliminacion;
    }
    
    public Guias buscarConID(int id){
        List<Guias> guias = cargarRegistros();
        
        for(Guias guia : guias){
            if (guia.getId() == id) {
                return guia;
            }
        }
        return null;
    }
    
     
}
