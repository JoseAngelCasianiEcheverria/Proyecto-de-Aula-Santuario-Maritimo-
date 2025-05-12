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
import model.Cuidadores;

/**
 *
 * @author juanp
 */
public class cuidadoresDAO {
    private static final String ARCHIVO = "C:\\Users\\juanp\\OneDrive\\Documentos\\NetBeansProjects\\Proyecto-de-Aula-Santuario-Maritimo-\\src\\resources\\data\\cuidador.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    public List<Cuidadores> cargarRegistros() {
        try (Reader read = new FileReader(ARCHIVO)) {
            Type Lista = new TypeToken<ArrayList<Cuidadores>>(){}.getType();
            List<Cuidadores> animales = gson.fromJson(read, Lista);
            return animales != null ? animales : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("ERROR. No se puede cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void guardarCuidador(Cuidadores cuidador){
        List<Cuidadores> cuidadores = cargarRegistros();
        cuidadores.add(cuidador);
        guardarTodos(cuidadores);

    }
    
    public void guardarTodos(List<Cuidadores> cuidadores){
        try(FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(cuidadores, writer);
            
        } catch (IOException e) {
          System.err.println("Error al guardar cuidadores: " + e.getMessage());
        }  
    }
    
    public boolean eliminarConID(int id){
        List<Cuidadores> cuidadores = cargarRegistros();
        boolean eliminacion = cuidadores.removeIf(cuidador -> cuidador.getiD()== id);
        
        if (eliminacion) {
            guardarTodos(cuidadores);
        }
        return eliminacion;
    }
    
    public Cuidadores buscarConID(int id){
        List<Cuidadores> cuidadores = cargarRegistros();
        
        for(Cuidadores cuidador : cuidadores){
            if (cuidador.getiD()== id) {
                return cuidador;
            }
        }
        return null;
    }
   
}
