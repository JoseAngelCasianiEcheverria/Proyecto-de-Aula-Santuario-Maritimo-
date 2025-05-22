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
import javax.swing.JOptionPane;
import model.Visitantes;

public class VisitantesDAO {
    private static final String ARCHIVO_JSON = System.getProperty("user.home") + "\\Documents\\NetBeansProjects\\Proyecto-de-Aula-Santuario-Maritimo--feature-Administracion\\Proyecto-de-Aula-Santuario-Maritimo--feature-Administracion\\src\\resources\\data\\visitante.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Visitantes> cargarRegistros() {
        try (Reader read = new FileReader(ARCHIVO_JSON)) {
            Type lista = new TypeToken<ArrayList<Visitantes>>() {}.getType();
            List<Visitantes> visitantes = gson.fromJson(read, lista);
            return visitantes != null ? visitantes : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("ERROR. No se puede cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarVisitante(Visitantes visitante) {
        List<Visitantes> visitantes = cargarRegistros();
        visitantes.add(visitante);
        guardarTodos(visitantes);
    }

    public void guardarTodos(List<Visitantes> visitantes) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(visitantes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar visitantes: " + e.getMessage());
        }
    }

    public boolean eliminarConId(String idTexto) {
        List<Visitantes> visitantes = cargarRegistros();
        int idBuscado;
        try {
            idBuscado = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean eliminado = visitantes.removeIf(v -> v.getiD() == idBuscado);

        if (eliminado) {
            guardarTodos(visitantes);
        }

        return eliminado;
    }

    public Visitantes buscarPorId(int id) {
        List<Visitantes> visitantes = cargarRegistros();
        for (Visitantes v : visitantes) {
            if (v.getiD() == id) {
                return v;
            }
        }
        return null;
    }
}


