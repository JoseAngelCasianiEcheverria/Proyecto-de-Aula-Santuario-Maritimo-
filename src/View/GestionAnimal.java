/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import model.Animales;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.animalesDAO;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import Model.oConstantes.SexoEnum;
import dao.historialDAO;
import Model.HistorialAnimal;
import java.util.ArrayList;

/**
 *
 * @author juanp
 */
public class GestionAnimal extends javax.swing.JFrame {
    
    DefaultTableModel modelo = new DefaultTableModel();

    /**
     * Creates new form GestionAnimal
     */
    public GestionAnimal() {
        initComponents();
        cargarTablaAnimal();
        setLocationRelativeTo(null);
        
        
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });
        txtID.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                char c = evt.getKeyChar();
                
                if (!Character.isDigit(c) || txtID.getText().length() >= 10) {
                    evt.consume();
                    soloNumeros(evt);
                }  
            }
        });
        txtPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloNumeros(evt);
                
            }
        });
        txtCuidador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });     
    }
    
    private final animalesDAO dao = new animalesDAO();
    
    private void guardarRegistroAnimal(){
        try {
            String nombre = txtNombre.getText().trim();
            String iDText = txtID.getText().trim();
            String peso = txtPeso.getText().trim();
            String especie = comboEspecie.getSelectedItem().toString();
            var sexoSeleccionado = comboSexo.getSelectedItem().toString();
            var sexo = SexoEnum.MACHO;
            if (sexoSeleccionado.toLowerCase().contains("Seleccionar")) {
                JOptionPane.showMessageDialog(this, "Seleccione un sexo");
                return;
            }
            if (sexoSeleccionado.equalsIgnoreCase(SexoEnum.MACHO.toString())) {
                sexo = SexoEnum.MACHO;
                
            } else if (sexoSeleccionado.equalsIgnoreCase(SexoEnum.HEMBRA.toString())) {
                sexo = SexoEnum.HEMBRA;
            }
            String estado = comboEstado.getSelectedItem().toString();
            String ubicacion = comboUbicacion.getSelectedItem().toString();
            String habitat = comboHabitat.getSelectedItem().toString();
            String cuidador = txtCuidador.getText().trim();
            Date fechaIngreso = DateIngreso.getDate();
            
            if (nombre.isEmpty() || iDText.isEmpty()|| peso.isEmpty() || especie.equals("Seleccionar") || sexo.equals("Seleccionar") || estado.equals("Seleccionar") || ubicacion.equals("Seleccionar") || fechaIngreso == null || habitat.equals("Seleccionar") || cuidador.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Warning",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (iDText.length() !=10 || !iDText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,"El ID debe tener 10 digitos","Warning",JOptionPane.WARNING_MESSAGE);
                return;
            }
             
            
            if (!validacionLetras(nombre)) {
               JOptionPane.showMessageDialog(this,"El nombre solo permite letras","Error",JOptionPane.ERROR_MESSAGE);
               return;
                
            }
            if (!peso.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "El peso debe ser en numeros","Error",JOptionPane.ERROR_MESSAGE);
                return;    
            }
            
            if (!validacionLetras(cuidador)) {
                JOptionPane.showMessageDialog(this, "El nombre del cuidador debe ser en LETRAS","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int iD = Integer.parseInt(iDText);
            
            if (fechaIngreso == null) {
              JOptionPane.showMessageDialog(this,"Ingrese una fecha de ingreso","Error",JOptionPane.ERROR_MESSAGE);
              return;
            }
            
            int añoSeleccionado = fechaIngreso.getYear() + 1900;
            int añoActual = LocalDate.now().getYear();
            
            if (añoSeleccionado > añoActual) {
               JOptionPane.showMessageDialog(this,"No se puede seleccionar un año mayor al actual","Warning",JOptionPane.WARNING_MESSAGE);
               return;
            }
            
            Animales registroExistente = dao.buscarConId(iD);
            if (registroExistente != null) {
                JOptionPane.showMessageDialog(this, "Registro existente", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Animales nuevoAnimal = new Animales(nombre, iD, peso, especie, sexo, estado, ubicacion, habitat, cuidador, fechaIngreso);
            dao.guardarAnimal(nuevoAnimal);
            
            JOptionPane.showMessageDialog(this, "Animal registrado exitosamente", "Exitos", JOptionPane.INFORMATION_MESSAGE);   
            cargarTablaAnimal();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar al animal: "+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
          
    }
    private boolean validacionLetras(String text){
        for(char c : text.toCharArray()){
            if (!Character.isLetter(c) && c != ' ' ) {
                return false;
            }
        }
        return true;
    }
    
    private void soloLetras(java.awt.event.KeyEvent evt){
        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && c != ' ' && c != '\b') {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo se permiten LETRAS","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void soloNumeros(java.awt.event.KeyEvent evt){
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '\b') {
            evt.consume();
            JOptionPane.showMessageDialog(this,"Solo se permiten NUMEROS","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    private void cargarTablaAnimal(){
        modelo.setColumnIdentifiers(new Object[]{ "Nombre","ID","Peso","Especie","Sexo","Estado","Ubicacion","Habitat","Cuidador","Ingreso"});
        modelo.setRowCount(0);
        
        List<Animales> listaAnimal = dao.cargarRegistros();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Animales animal : listaAnimal){
            String fechaFormateada = "Sin fecha";
            if (animal.getFechaIngreso() != null) {
              fechaFormateada = sdf.format(animal.getFechaIngreso());
            }
            modelo.addRow(new Object[]{
                animal.getNombre(),
                animal.getIdAnimal(),
                animal.getPeso(),
                animal.getEspecie(),
                animal.getSexo(),
                animal.getEstadoSalud(),
                animal.getUbicacion(),
                animal.getHabitat(),
                animal.getCuidadorAsignado(),
                fechaFormateada
            });
        }
        tableAnimal.setModel(modelo);
    }
    
    private void limpiarCampos(){
        txtNombre.setText("");
        txtID.setText("");
        txtPeso.setText("");
        comboEspecie.setSelectedIndex(0);
        comboSexo.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        comboUbicacion.setSelectedIndex(0);
        txtCuidador.setText("");
        comboHabitat.setSelectedIndex(0);
        DateIngreso.setDate(null);
    }
    
    //METODO DE ELIMINACION
    private void eliminarAnimales(){
        String idEliminada = JOptionPane.showInputDialog(this, "Ingrese un ID para eliminar: ");
        
        if (idEliminada != null && !idEliminada.trim().isEmpty()) {
            dao.eliminarConId(idEliminada.trim());
            cargarTablaAnimal();
            
            JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente", "Exitos", JOptionPane.INFORMATION_MESSAGE);  
        } else {
            JOptionPane.showMessageDialog(this, "Cancelando eliminacion","Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    //METODO DE BUSQUEDA
    private void buscarAnimales(){
        String idBuscado = txtBusqueda.getText().trim();
        
        if (idBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID para buscarlo en los registros", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idIngresado;
        
        try {
            idIngresado = Integer.parseInt(idBuscado);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Solo se permiten numeros", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Animales animal = dao.buscarConId(idIngresado);
        if (animal == null) {
            modelo.setRowCount(0);
            JOptionPane.showMessageDialog(this, "No hay resultados");
        } else {
            mostrarBusquedEnTabla(animal);
        }
    }
    
    //MOSTRAMOS EL ID BUSCADO
    private void mostrarBusquedEnTabla(Animales animal){
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
            animal.getNombre(),
            animal.getIdAnimal(),
            animal.getPeso(),
            animal.getEspecie(),
            animal.getSexo(),
            animal.getEstadoSalud(),
            animal.getUbicacion(),
            animal.getHabitat(),
            animal.getCuidadorAsignado(),
            animal.getFechaIngreso()
        });
        tableAnimal.setModel(modelo);
    }
    
    //ACTUALIZAMOS TODOS LOS CAMPOS EXEPTUANDO EL NOMBRE Y EL ID SIENDO DATOS PERSONALES
    private void actualizarCampos(){
        txtNombre.setEnabled(false);
        txtID.setEnabled(false);
        txtPeso.setEnabled(true);
        comboEspecie.setEnabled(false);
        comboSexo.setEnabled(false);
        comboEstado.setEnabled(true);
        comboUbicacion.setEnabled(true);
        comboHabitat.setEnabled(true);
        txtCuidador.setEnabled(true);
        DateIngreso.setEnabled(false);
        
        
    }
    
    private void actualizarAnimal(){
        try {
            int iD = Integer.parseInt(txtID.getText().trim());
            List<Animales> listaAnimal = dao.cargarRegistros();
            
            for(Animales animal : listaAnimal){
                if (animal.getIdAnimal() == iD) {
                    String peso = txtPeso.getText().trim();
                    String cuidador = txtCuidador.getText().trim();
                    
                    if (!peso.matches("\\d+")) {
                        JOptionPane.showMessageDialog(this,"El peso debe ser en numeros","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (!validacionLetras(cuidador)) {
                        JOptionPane.showMessageDialog(this, "El nombre del cuidador debe ser en LETRAS","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
               
                animal.setPeso(peso);
                animal.setEstadoSalud(comboEstado.getSelectedItem().toString());
                animal.setUbicacion(comboUbicacion.getSelectedItem().toString());
                animal.setHabitat(comboHabitat.getSelectedItem().toString());
                animal.setCuidadorAsignado(cuidador);
                
                dao.guardarTodos(listaAnimal);
                JOptionPane.showMessageDialog(this, "Datos actualizados exitosamente", "Exitos", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaAnimal();
                limpiarCampos();
                
                txtNombre.setEnabled(true);
                txtID.setEnabled(true);
                comboEspecie.setEnabled(true);
                comboSexo.setEnabled(true);
                DateIngreso.setEnabled(true);
                return;
                }
            }
            JOptionPane.showMessageDialog(this, "Animal no encontrado", "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        btnRegreso = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comboEspecie = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        comboSexo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        comboEstado = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        comboUbicacion = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtCuidador = new javax.swing.JTextField();
        btnActualizacion = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        comboHabitat = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        DateIngreso = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAnimal = new javax.swing.JTable();
        btnActualizar = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        btnGuardar1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnVerHistorial = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnAgregarHistorial = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        btnOrdenamiento = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 51, 153));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/AÑADIR.png"))); // NOI18N
        jLabel2.setText("GUARDAR/MODIFICAR/BUSCAR/ELIMINAR Animales ");

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Busqueda por ID ");

        txtBusqueda.setBackground(new java.awt.Color(0, 0, 153));
        txtBusqueda.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        txtBusqueda.setForeground(new java.awt.Color(0, 255, 255));
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        btnRegreso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BOTON_REGRESAR (1) (1).png"))); // NOI18N
        btnRegreso.setText("jLabel17");
        btnRegreso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegresoMouseClicked(evt);
            }
        });

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/MARINUS 2.0 (1).png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BUSQUEDA (1).png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BALLENA_OCEANICA-removebg-preview (1).png"))); // NOI18N

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/DOLPHIN-removebg-preview (1) (1) (1) (2).png"))); // NOI18N

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/LAGARTIJA_ACUATICA-removebg-preview (1) (1).png"))); // NOI18N

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BACALAO_DEL_MAR-removebg-preview (2).png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnRegreso, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnRegreso)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel2))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INGRESAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Nombre ");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("ID");

        txtID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Peso");

        txtPeso.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Especie  ");

        comboEspecie.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboEspecie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Mamifero", "Ave", "Pez", "Reptil", "Crustaceo", "Cefalopodo", "Molusco", "Equinodermo", "Cnidario" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Sexo ");

        comboSexo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "MACHO", "HEMBRA" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Estado ");

        comboEstado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Sano", "En tratamiento", "En recuperacion" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Ubicacion ");

        comboUbicacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboUbicacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Habitat natural", "Zona de crianza", "Zona de reproduccion", "Area de rehabilitacion", "Educacion ambiental", "Nutricion animal", "Zona de investigacion" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Cuidador");

        txtCuidador.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnActualizacion.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnActualizacion.setText("ACTUALIZAR");
        btnActualizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizacionMouseClicked(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Habitat");

        comboHabitat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboHabitat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Tanque", "Laguna", "Arrecife", "Manglar", "Area de rehabilitacion" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Fecha Ingreso");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnActualizacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNombre)
                                            .addComponent(txtID)
                                            .addComponent(txtPeso)
                                            .addComponent(comboEspecie, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comboSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comboEstado, 0, 173, Short.MAX_VALUE))))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(26, 26, 26)
                                    .addComponent(comboHabitat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtCuidador, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(DateIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(comboHabitat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(txtCuidador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(DateIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimpiar)
                    .addComponent(btnActualizacion))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ANIMALES REGISTRADOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        tableAnimal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableAnimal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "ID", "Peso", "Especie", "Sexo", "Estado", "Ubicacion", "Habitat", "Cuidador", "Ingreso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableAnimal.setName(""); // NOI18N
        tableAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAnimalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableAnimal);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarMouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ACTUALIZAR (2).png"))); // NOI18N
        jLabel20.setText("ACTUALIZAR");

        javax.swing.GroupLayout btnActualizarLayout = new javax.swing.GroupLayout(btnActualizar);
        btnActualizar.setLayout(btnActualizarLayout);
        btnActualizarLayout.setHorizontalGroup(
            btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnActualizarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnActualizarLayout.setVerticalGroup(
            btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
            .addGroup(btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnActualizarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnGuardar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardar1MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GUARDAR (1) (1) (1).png"))); // NOI18N
        jLabel19.setText("GUARDAR");

        javax.swing.GroupLayout btnGuardar1Layout = new javax.swing.GroupLayout(btnGuardar1);
        btnGuardar1.setLayout(btnGuardar1Layout);
        btnGuardar1Layout.setHorizontalGroup(
            btnGuardar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(btnGuardar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnGuardar1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel19)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnGuardar1Layout.setVerticalGroup(
            btnGuardar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(btnGuardar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnGuardar1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel19)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BUSCAR.png"))); // NOI18N
        jLabel23.setText("BUSCAR");

        javax.swing.GroupLayout btnBuscarLayout = new javax.swing.GroupLayout(btnBuscar);
        btnBuscar.setLayout(btnBuscarLayout);
        btnBuscarLayout.setHorizontalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnBuscarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel23)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnBuscarLayout.setVerticalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnBuscarLayout.createSequentialGroup()
                    .addGap(0, 7, Short.MAX_VALUE)
                    .addComponent(jLabel23)
                    .addGap(0, 7, Short.MAX_VALUE)))
        );

        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ELIMINAR.png"))); // NOI18N
        jLabel24.setText("ELIMINAR");

        javax.swing.GroupLayout btnEliminarLayout = new javax.swing.GroupLayout(btnEliminar);
        btnEliminar.setLayout(btnEliminarLayout);
        btnEliminarLayout.setHorizontalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnEliminarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel24)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnEliminarLayout.setVerticalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
            .addGroup(btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnEliminarLayout.createSequentialGroup()
                    .addGap(0, 7, Short.MAX_VALUE)
                    .addComponent(jLabel24)
                    .addGap(0, 7, Short.MAX_VALUE)))
        );

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/LEONES_MARINOS (1) (1) (1).png"))); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ESPADA_PEZ (1) (1) (1)_1.png"))); // NOI18N

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/FOCA_BLUE (1) (1).png"))); // NOI18N

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ORCA_MARINA (1).png"))); // NOI18N

        btnVerHistorial.setBackground(new java.awt.Color(0, 51, 255));
        btnVerHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerHistorialMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("VER HISTORIAL");

        javax.swing.GroupLayout btnVerHistorialLayout = new javax.swing.GroupLayout(btnVerHistorial);
        btnVerHistorial.setLayout(btnVerHistorialLayout);
        btnVerHistorialLayout.setHorizontalGroup(
            btnVerHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(btnVerHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnVerHistorialLayout.createSequentialGroup()
                    .addGap(0, 28, Short.MAX_VALUE)
                    .addComponent(jLabel12)
                    .addGap(0, 29, Short.MAX_VALUE)))
        );
        btnVerHistorialLayout.setVerticalGroup(
            btnVerHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(btnVerHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnVerHistorialLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel12)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnAgregarHistorial.setBackground(new java.awt.Color(0, 51, 255));
        btnAgregarHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarHistorialMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("AGREGAR HISTORIAL");

        javax.swing.GroupLayout btnAgregarHistorialLayout = new javax.swing.GroupLayout(btnAgregarHistorial);
        btnAgregarHistorial.setLayout(btnAgregarHistorialLayout);
        btnAgregarHistorialLayout.setHorizontalGroup(
            btnAgregarHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
            .addGroup(btnAgregarHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnAgregarHistorialLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel21)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnAgregarHistorialLayout.setVerticalGroup(
            btnAgregarHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
            .addGroup(btnAgregarHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnAgregarHistorialLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel21)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnOrdenamiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOrdenamientoMouseClicked(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ORDENAR (1).png"))); // NOI18N
        jLabel25.setText("ORDENAR LISTA");

        javax.swing.GroupLayout btnOrdenamientoLayout = new javax.swing.GroupLayout(btnOrdenamiento);
        btnOrdenamiento.setLayout(btnOrdenamientoLayout);
        btnOrdenamientoLayout.setHorizontalGroup(
            btnOrdenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
            .addGroup(btnOrdenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnOrdenamientoLayout.createSequentialGroup()
                    .addGap(0, 10, Short.MAX_VALUE)
                    .addComponent(jLabel25)
                    .addGap(0, 10, Short.MAX_VALUE)))
        );
        btnOrdenamientoLayout.setVerticalGroup(
            btnOrdenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(btnOrdenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnOrdenamientoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel25)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAgregarHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVerHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addComponent(btnOrdenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(9474, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVerHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOrdenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18317, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        // TODO add your handling code here:
        if (txtBusqueda.getText().trim().isEmpty()) {
            cargarTablaAnimal();
        }
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
           buscarAnimales();
        }
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void tableAnimalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAnimalMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tableAnimal.getSelectedRow();
        
        if (filaSeleccionada != -1) {
            txtNombre.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
            txtID.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
            txtPeso.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
            comboEspecie.setSelectedItem(modelo.getValueAt(filaSeleccionada, 3));
            comboSexo.setSelectedItem(tableAnimal.getValueAt(filaSeleccionada, 4).toString());
            comboEstado.setSelectedItem(modelo.getValueAt(filaSeleccionada, 5));
            comboUbicacion.setSelectedItem(modelo.getValueAt(filaSeleccionada, 6));
            comboHabitat.setSelectedItem(modelo.getValueAt(filaSeleccionada, 7));
            txtCuidador.setText(modelo.getValueAt(filaSeleccionada, 8).toString());
            try {
                String fechaDate = String.valueOf(modelo.getValueAt(filaSeleccionada, 9));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = sdf.parse(fechaDate);
                DateIngreso.setDate(fecha);
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al establecer la fecha de ingreso", "Error", JOptionPane.ERROR_MESSAGE);  
            }
            
            
        }
    }//GEN-LAST:event_tableAnimalMouseClicked

    private void btnActualizacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizacionMouseClicked
        // TODO add your handling code here:
        actualizarAnimal();
    }//GEN-LAST:event_btnActualizacionMouseClicked

    private void btnGuardar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardar1MouseClicked
        // TODO add your handling code here:
        guardarRegistroAnimal();
    }//GEN-LAST:event_btnGuardar1MouseClicked

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        // TODO add your handling code here:
        actualizarCampos();
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        // TODO add your handling code here:
        buscarAnimales();
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        eliminarAnimales();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnRegresoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresoMouseClicked
        // TODO add your handling code here:
        new Administrador().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnRegresoMouseClicked

    private void btnVerHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerHistorialMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tableAnimal.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un animal primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;   
        }
        
        int idAnimal = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 1).toString());
        VerHistorialAnimal ventana = new VerHistorialAnimal(idAnimal);
        ventana.setVisible(true);
        
        
        
        
    }//GEN-LAST:event_btnVerHistorialMouseClicked

    private void btnAgregarHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarHistorialMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tableAnimal.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,"Porfavor.Seleccionar un animal","Warning",JOptionPane.WARNING_MESSAGE);
            return;
            
        }
        
        int idAnimal = Integer.parseInt(modelo.getValueAt(filaSeleccionada,1).toString());
        historialDAO dao = new historialDAO();
        
        List<HistorialAnimal> historialExistente = dao.buscarPorIdAnimal(idAnimal);
        
        if (!historialExistente.isEmpty()) {
            StringBuilder builder = new StringBuilder(idAnimal);
            builder.append("Historial anterior del animal:\n\n");
            for(HistorialAnimal historial : historialExistente){
                builder.append("FECHA: ").append(new java.text.SimpleDateFormat("dd/MM/yyyy").format(historial.getFechaAntecedente())).append("\n");
                builder.append("TIPO: ").append(historial.getTipoAntecedente()).append("\n");
                builder.append("DESCRIPCION: ").append(historial.getDescripcion()).append("\n");
                builder.append("ENCARGADO RESPONSABLE: ").append(historial.getResponsable()).append("\n");
                 
            }
            JOptionPane.showMessageDialog(this,builder.toString(),"Historial existente",JOptionPane.INFORMATION_MESSAGE);
           
        } else {
            JOptionPane.showMessageDialog(this, "Este animal no tiene historiales previos. Se creará uno nuevo.", "Warning", JOptionPane.WARNING_MESSAGE);
            
        }
        
         
        String tipoAntecedente = JOptionPane.showInputDialog(this, "Ingrese el tipo de antecedente (ejemplo: Enfermedad, Tratamiento, Traspaso):");
        if (tipoAntecedente == null || tipoAntecedente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un antecedente.", "Error", JOptionPane.ERROR_MESSAGE);
           return;
        }
        
        String descripcion = JOptionPane.showInputDialog(this, "Ingrese la descripción del antecedente:");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
           return;
            
        }
        
        String responsable = JOptionPane.showInputDialog(this, "Ingrese el nombre del encargado responsable:");
        if (responsable == null || responsable.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el responsable del antecedente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  
        }
        
        HistorialAnimal nuevo = new HistorialAnimal();
        nuevo.setIdAnimal(idAnimal);
        nuevo.setTipoAntecedente(tipoAntecedente);
        nuevo.setDescripcion(descripcion);
        nuevo.setResponsable(responsable);
        nuevo.setFechaAntecedente(new java.util.Date());
        
        
        dao.guardarAntecedente(nuevo);
        
        JOptionPane.showMessageDialog(this,"Historial guardado exitosamente","Exitos",JOptionPane.INFORMATION_MESSAGE);
        
        
    }//GEN-LAST:event_btnAgregarHistorialMouseClicked

    private void btnOrdenamientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrdenamientoMouseClicked
        // TODO add your handling code here:
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,"Lista vacia. No hay registros para ordenar","Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Object[]> filas = new ArrayList<>();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object[] fila = new Object[modelo.getColumnCount()];
            
            for(int j = 0; j < modelo.getColumnCount(); j++){
                fila[j] = modelo.getValueAt(i,j);
                
            }
            filas.add(fila);
        }
        
        filas.sort((a, b) -> {
            try {
               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
               Date primeraFecha = sdf.parse(a[9].toString());
               Date segundaFecha = sdf.parse(b[9].toString());
               return primeraFecha.compareTo(segundaFecha);
                
            } catch (Exception e) {
                return 0;
            }
        });
        modelo.setRowCount(0);
        for (Object[] fila : filas) {
            modelo.addRow(fila);
        }
        
        JOptionPane.showMessageDialog(this,"Registros ordenados por fecha","Exitos",JOptionPane.INFORMATION_MESSAGE);
                
        
        
    }//GEN-LAST:event_btnOrdenamientoMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestionAnimal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionAnimal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionAnimal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionAnimal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionAnimal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateIngreso;
    private javax.swing.JButton btnActualizacion;
    private javax.swing.JPanel btnActualizar;
    private javax.swing.JPanel btnAgregarHistorial;
    private javax.swing.JPanel btnBuscar;
    private javax.swing.JPanel btnEliminar;
    private javax.swing.JPanel btnGuardar1;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JPanel btnOrdenamiento;
    private javax.swing.JLabel btnRegreso;
    private javax.swing.JPanel btnVerHistorial;
    private javax.swing.JComboBox<String> comboEspecie;
    private javax.swing.JComboBox<String> comboEstado;
    private javax.swing.JComboBox<String> comboHabitat;
    private javax.swing.JComboBox<String> comboSexo;
    private javax.swing.JComboBox<String> comboUbicacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableAnimal;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCuidador;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPeso;
    // End of variables declaration//GEN-END:variables
}
