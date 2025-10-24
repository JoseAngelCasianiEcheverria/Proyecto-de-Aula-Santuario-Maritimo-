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
import model.Agendas;

/**
 *
 * @author USER
 */
public class AgendasDAO {
    private static final String ARCHIVO = "C:\\Users\\juanp\\OneDrive\\Documentos\\NetBeansProjects\\Proyecto-de-Aula-Santuario-Maritimo-\\src\\Resources\\data\\agenda.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Agendas> cargarRegistros() {
        try (Reader read = new FileReader(ARCHIVO)) {
            Type listaTipo = new TypeToken<ArrayList<Agendas>>(){}.getType();
            List<Agendas> agendas = gson.fromJson(read, listaTipo);
            return agendas != null ? agendas : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("ERROR. No se puede cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarAgenda(Agendas agenda) {
        List<Agendas> agendas = cargarRegistros();
        agendas.add(agenda);
        guardarTodos(agendas);
    }

    public void guardarTodos(List<Agendas> agendas) {
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(agendas, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar agendas: " + e.getMessage());
        }
    }

    public boolean eliminarConID(int id) {
        List<Agendas> agendas = cargarRegistros();
        boolean eliminacion = agendas.removeIf(agenda -> agenda.getId() == id);

        if (eliminacion) {
            guardarTodos(agendas);
        }
        return eliminacion;
    }

    public Agendas buscarConID(int id) {
        List<Agendas> agendas = cargarRegistros();

        for (Agendas agenda : agendas) {
            if (agenda.getId() == id) {
                return agenda;
            }
        }
        return null;
    }
    
  public List<Agendas> obtenerTodas() {
    return cargarRegistros(); // Ya retorna la lista de agendas desde el archivo JSON
}

}