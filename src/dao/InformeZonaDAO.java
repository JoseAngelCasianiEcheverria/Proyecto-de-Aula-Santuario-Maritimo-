/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Model.InformeZona;
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

/**
 *
 * @author Gercray
 */
public class InformeZonaDAO {

    private static final String ARCHIVO = "src/Resources/data/informesZonas.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<InformeZona> cargarRegistros() {
        try (Reader read = new FileReader(ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<InformeZona>>() {
            }.getType();
            List<InformeZona> informes = gson.fromJson(read, tipoLista);
            return informes != null ? informes : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("ERROR al cargar informes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void guardarInforme(InformeZona informe) {
        List<InformeZona> informes = cargarRegistros();
        informes.add(informe);
        guardarTodos(informes);
    }

    public void guardarTodos(List<InformeZona> informes) {
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(informes, writer);
        } catch (IOException e) {
            System.err.println("ERROR al guardar informes: " + e.getMessage());
        }
    }

    public List<InformeZona> buscarPorNumeroZ(String numeroZBuscado) {
        List<InformeZona> Informes = cargarRegistros();
        List<InformeZona> filtrados = new ArrayList<>();

        for (InformeZona inf : Informes) {
            if (inf.getNumeroZ().equalsIgnoreCase(numeroZBuscado)) {
                filtrados.add(inf);
            }
        }

        return filtrados;
    }

    public InformeZona eliminarPorNumeroZ(String numeroZ) {
        List<InformeZona> Informes = cargarRegistros();
        InformeZona eliminado = null;

        Iterator<InformeZona> it = Informes.iterator();

        while (it.hasNext()) {
            InformeZona inf = it.next();
            if (inf.getNumeroZ().equalsIgnoreCase(numeroZ)) {
                eliminado = inf;
                it.remove();
                break;
            }
        }

        if (eliminado != null) {
            guardarTodos(Informes);
        }

        return eliminado;
    }

}
