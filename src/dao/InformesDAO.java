/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Model.Informes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import model.Animales;

/**
 *
 * @author Gercray
 */
public class InformesDAO {

    private static final String ARCHIVO = "src/Resources/data/informes.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Informes> cargarRegistros() {
        try (Reader read = new FileReader(ARCHIVO)) {
            Type lista = new TypeToken<ArrayList<Informes>>() {
            }.getType();
            List<Informes> informes = gson.fromJson(read, lista);
            return informes != null ? informes : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("ERROR cargar los informes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarInforme(Informes informe) {
        List<Informes> informes = cargarRegistros();
        informes.add(informe);
        guardarTodos(informes);
    }

    public void guardarTodos(List<Informes> informes) {
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(informes, writer);
        } catch (IOException e) {
            System.err.println("ERROR al guardar informes: " + e.getMessage());
        }
    }

    public List<Informes> buscarPorIdAnimal(String idAnimalBuscado) {
        List<Informes> informes = cargarRegistros();
        List<Informes> filtrados = new ArrayList<>();

        for (Informes inf : informes) {
            if (inf.getIdAnimal().equalsIgnoreCase(idAnimalBuscado)) {
                filtrados.add(inf);
            }
        }

        return filtrados;
    }

    public Informes eliminarPorIdAnimal(String id) {
        List<Informes> informes = cargarRegistros();
        Informes eliminado = null;

        Iterator<Informes> it = informes.iterator();

        while (it.hasNext()) {
            Informes inf = it.next();
            if (inf.getIdAnimal().equalsIgnoreCase(id)) {
                eliminado = inf;
                it.remove();
                break;
            }
        }

        if (eliminado != null) {
            guardarTodos(informes);
        }

        return eliminado;
    }
}
