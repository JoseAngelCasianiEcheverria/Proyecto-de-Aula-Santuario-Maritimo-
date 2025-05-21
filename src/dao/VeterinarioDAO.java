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
import model.Veterinarios;





/**
 *
 * @author juanp
 */
public class VeterinarioDAO {
    private static final String ARCHIVO = "C:\\Users\\juanp\\OneDrive\\Documentos\\NetBeansProjects\\Proyecto-de-Aula-Santuario-Maritimo-\\src\\resources\\data\\veterinario.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public List<Veterinarios> cargarRegistros(){
         try (Reader read = new FileReader(ARCHIVO)) {
            Type Lista = new TypeToken<ArrayList<Veterinarios>>(){}.getType();
            List<Veterinarios> veterinarios = gson.fromJson(read, Lista);
            return veterinarios != null ? veterinarios : new ArrayList<>();
            
  
        } catch (IOException e) {
            System.err.println("ERROR. No se puede cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void guardarVeterinario(Veterinarios veterinario){
        List<Veterinarios> veterinarios = cargarRegistros();
        veterinarios.add(veterinario);
        guardarTodos(veterinarios);
    }
    
    public void guardarTodos(List<Veterinarios> veterinarios){
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(veterinarios, writer);
            
        } catch (IOException e) {
            System.err.println("Error al guardar veterinarios: " + e.getMessage());
        }
    }
    
    public boolean eliminarConId(String id){
        List<Veterinarios> veterinarios = cargarRegistros();
        int iDBuscado;
        
        try {
            iDBuscado = Integer.parseInt(id);
            
        } catch (NumberFormatException e) {
           JOptionPane.showMessageDialog(null, "ID no encontrado en el sistema","Error", JOptionPane.ERROR_MESSAGE);
           return false;
        }
        
        boolean eliminacion = veterinarios.removeIf(veterinario -> veterinario.getiD()== iDBuscado);
        if (eliminacion) {
            guardarTodos(veterinarios);    
        }
        return eliminacion;
    }
    
    public Veterinarios buscarConID(int id){
        List<Veterinarios> veterinarios = cargarRegistros();
        
        for(Veterinarios veterinario : veterinarios){
            if (veterinario.getiD()== id) {
                return veterinario;
            }
        }
        return null;
    }
    
}
