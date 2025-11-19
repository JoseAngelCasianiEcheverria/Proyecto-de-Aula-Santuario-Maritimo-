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
import java.util.ArrayList;
import java.util.List;
import Model.HistorialAnimal;


/**
 *
 * @author juanp
 */
public class historialDAO {
    private static final String ARCHIVO = "src/Resources/data/historialAnimal.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public List<HistorialAnimal> cargarHistorial(){
        try(Reader reader = new FileReader(ARCHIVO)){
            Type lista = new TypeToken<ArrayList<HistorialAnimal>>(){}.getType();
            List<HistorialAnimal> historial = gson.fromJson(reader, lista);
            return historial!= null ? historial : new ArrayList<>();
            
        } catch(IOException e){
            System.err.println("No se pudo cargar historial: " + e.getMessage());
            return new ArrayList<>();
            
        }
    }
    
    public void guardarAntecedente(HistorialAnimal antecedente){
        List<HistorialAnimal> historial = cargarHistorial();
        historial.add(antecedente);
        guardarTodos(historial);
        
        
    }
    
    public void guardarTodos(List<HistorialAnimal> historial){
        try(FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(historial, writer);
            
        } catch (IOException e) {
            System.out.println("Error al guardar el historial");
        }
        
    }
    
    public List<HistorialAnimal> buscarPorIdAnimal(int idAnimal) {
        List<HistorialAnimal> historial = cargarHistorial();
        List<HistorialAnimal> resultados = new ArrayList<>();

        for (HistorialAnimal evento : historial) {
            if (evento.getIdAnimal()== idAnimal) {
                resultados.add(evento);
            }
        }
        return resultados;
    }
    
    
    
}
