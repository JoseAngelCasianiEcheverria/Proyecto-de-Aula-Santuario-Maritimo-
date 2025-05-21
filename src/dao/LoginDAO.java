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
import Model.Usuario;

/**
 *
 * @author Gercray
 */
public class LoginDAO {
    private static final String ARCHIVO = ("C:\\Users\\Wender\\Documents\\NetBeansProjects\\ProyectoAulaSantuarioMaritimo\\src\\Resources\\data\\login.json");
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public List<Usuario> cargar(){
        try (Reader read = new FileReader(ARCHIVO)) {
            Type Lista = new TypeToken<ArrayList<Usuario>>(){}.getType();
            List<Usuario> usuarios = gson.fromJson(read, Lista);
            return usuarios != null ? usuarios : new ArrayList<>();
            
        } catch (IOException e) {
            System.err.println("ERROR. No se puede cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void guardarUsuarios(Usuario usuario) {
        List<Usuario> usuarios = cargar();
        usuarios.add(usuario);
        guardarTodos(usuarios);
    }
    
     public void guardarTodos(List<Usuario> usuarios) {
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar el usuario: " + e.getMessage());
        }
    }
    
    
}
