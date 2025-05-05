/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import model.Vigilantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.vigilantesDAO;
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

/**
 *
 * @author juanp
 */
public class GestionVigilantes extends javax.swing.JFrame {
    DefaultTableModel modelo = new DefaultTableModel();

    /**
     * Creates new form GestionVigilantes
     */
    public GestionVigilantes() {
        initComponents();
        cargarTablaVigilantes();
        setLocationRelativeTo(null);
    }
    
    private final vigilantesDAO dao = new vigilantesDAO();
    
    private void guardarRegistroVigilante(){
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String edad = txtEdad.getText().trim();
            String iDText = txtID.getText().trim();
            String genero = comboGenero.getSelectedItem().toString();
            String correo = txtCorreo.getText().trim();
            String salario = txtSalario.getText().trim();
            String cargo = comboCargo.getSelectedItem().toString();
            String horario = comboHorario.getSelectedItem().toString();
            String numTelefonoText = txtTelefono.getText().trim();
            String contratacionText = txtFechaContratacion.getText().trim();
            String area = comboArea.getSelectedItem().toString();
            
            if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || genero.equals("Seleccionar") || correo.isEmpty() || salario.isEmpty() || cargo.equals("Seleccionar") || horario.equals("Seleccionar") || numTelefonoText.isEmpty() || contratacionText.isEmpty() || area.equals("Seleccionar")) {
               JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Warning", JOptionPane.WARNING_MESSAGE);
               return;
            }
            
            if (!validacionLetras(nombre)) {
               JOptionPane.showMessageDialog(this, "El nombre solo se permite letras", "Error", JOptionPane.ERROR_MESSAGE);
               return;
      
            }
        
            if (!validacionLetras(apellido)) {
               JOptionPane.showMessageDialog(this, "El apellido solo se permite letras", "Error",JOptionPane.ERROR_MESSAGE);
               return;    
            }
            
            if (!salario.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "El salario debe ser en numeros","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!edad.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "La edad debe ser en numeros","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!numTelefonoText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "El numero de telefono debe ser en numeros","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String numTelefono = numTelefonoText;
            int iD = Integer.parseInt(iDText);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaContratacion = sdf.parse(contratacionText);
            
            Vigilantes registroExistente = dao.buscarConID(iD);
            if (registroExistente != null ) {
                JOptionPane.showMessageDialog(this, "Registro existente", "Warning", JOptionPane.WARNING_MESSAGE);
                return;   
            }
            
            
            Vigilantes nuevoVigilante = new Vigilantes(cargo, fechaContratacion, salario, horario, correo, numTelefono, nombre, apellido, genero, edad, iD);
            nuevoVigilante.setArea(area);
            dao.guardarVigilantes(nuevoVigilante);
            
            JOptionPane.showMessageDialog(this,"Registro exitoso", "Exitos", JOptionPane.INFORMATION_MESSAGE);
            cargarTablaVigilantes();
            
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar al vigilante", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    private void cargarTablaVigilantes(){
        modelo.setColumnIdentifiers(new Object[]{"Nombre","Apellido","Edad","ID","Genero","Correo","Cargo","Salario","Horario","Area","Telefono","Contratacion"});
        modelo.setRowCount(0);
        
        List<Vigilantes> listaVigilantes = dao.cargarRegistros();
        
        for(Vigilantes vigilante : listaVigilantes){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            modelo.addRow(new Object[]{
                vigilante.getNombre(),
                vigilante.getApellido(),
                vigilante.getEdad(),
                vigilante.getId(),
                vigilante.getGenero(),
                vigilante.getCorreo(),
                vigilante.getCargo(),
                vigilante.getSalario(),
                vigilante.getHorario(),
                vigilante.getArea(),
                vigilante.getNumTelefono(),
                sdf.format(vigilante.getFechaContratacion()),
            });
        }
        tableVigilantes.setModel(modelo);
    }
    
    private void limpiarCampos(){
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtID.setText("");
        comboGenero.setSelectedIndex(0);
        txtCorreo.setText("");
        txtSalario.setText("");
        comboCargo.setSelectedIndex(0);
        comboHorario.setSelectedIndex(0);
        txtTelefono.setText("");
        txtFechaContratacion.setText("");
        comboArea.setSelectedIndex(0);
    }
    
    private void eliminarVigilantes(){
        String iDEliminada = JOptionPane.showInputDialog(this, "Ingrese un ID para eliminar: ");
        
        if (iDEliminada != null && !iDEliminada.trim().isEmpty()) {
            dao.eliminarConID(iDEliminada.trim());
            cargarTablaVigilantes();
            
            JOptionPane.showMessageDialog(this, "Eliminacion exitosa", "Exitos", JOptionPane.INFORMATION_MESSAGE);   
        } else {
            JOptionPane.showMessageDialog(this, "Cancelando eliminacion","Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void buscarVigilantes(){
        String iDBuscada = txtBusqueda.getText().trim();
        
        if (iDBuscada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID para buscar en el sistema", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int iDIngresada;
        
        try {
            iDIngresada = Integer.parseInt(iDBuscada);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Solo se permite ingresar numeros", "Warning", JOptionPane.WARNING_MESSAGE);
            return;   
        }
        
        Vigilantes vigilante = dao.buscarConID(iDIngresada);
        if (vigilante == null) {
            JOptionPane.showMessageDialog(null, "No hay resultados");
            
        } else {
            mostrarBusquedaEnTabla(vigilante);
        }
    }
    
    private void mostrarBusquedaEnTabla(Vigilantes vigilante){
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
            vigilante.getNombre(),
            vigilante.getApellido(),
            vigilante.getEdad(),
            vigilante.getId(),
            vigilante.getGenero(),
            vigilante.getCorreo(),
            vigilante.getCargo(),
            vigilante.getSalario(),
            vigilante.getHorario(),
            vigilante.getArea(),
            vigilante.getNumTelefono(),
            vigilante.getFechaContratacion()
        });
        tableVigilantes.setModel(modelo);
        
    }
    private void actualizarCampos(){
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        txtEdad.setEnabled(true);
        txtID.setEnabled(false);
        comboGenero.setEnabled(false);
        txtCorreo.setEnabled(true);
        comboCargo.setEnabled(true);
        txtSalario.setEnabled(true);
        comboHorario.setEnabled(true);
        comboArea.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtFechaContratacion.setEnabled(false);
        
    }
    
    private void actualizarVigilantes(){
        try {
            String idText = txtID.getText().trim();
            String telefonoText = txtTelefono.getText().trim();
            String salarioText = txtSalario.getText().trim();
            String edadText = txtEdad.getText().trim();
            
            if (!idText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,"El ID debe ser en numeros","Error",JOptionPane.ERROR_MESSAGE);
                return;
                
            }
            
            if (!telefonoText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "El numero de telefono debe ser en numeros","Error",JOptionPane.ERROR_MESSAGE);
                return;
                
            }
            if (!salarioText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,"El salario debe ser un numero","Error",JOptionPane.ERROR_MESSAGE);
                return;    
            }
            if (!edadText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un numero","Error",JOptionPane.ERROR_MESSAGE);
                return;

            }
            
            
            int iD = Integer.parseInt(txtID.getText().trim());
            List<Vigilantes> listaVigilantes = dao.cargarRegistros();
            
            for(Vigilantes vigilante : listaVigilantes){
                if (vigilante.getId() == iD) {
                    
                    String nombre = txtNombre.getText().trim();
                    String apellido = txtApellido.getText().trim();
                    
                    if (!validacionLetras(nombre)) {
                        JOptionPane.showMessageDialog(this, "El nombre no puede tener numeros","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!validacionLetras(apellido)) {
                        JOptionPane.showMessageDialog(this, "El apellido no puede tener numeros","Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    vigilante.setNombre(nombre);
                    vigilante.setApellido(apellido);
                    vigilante.setEdad(txtEdad.getText().trim());
                    vigilante.setCorreo(txtCorreo.getText().trim());
                    vigilante.setCargo(comboCargo.getSelectedItem().toString());
                    vigilante.setSalario(txtSalario.getText().trim());
                    vigilante.setHorario(comboHorario.getSelectedItem().toString());
                    vigilante.setArea(comboArea.getSelectedItem().toString());
                    vigilante.setNumTelefono(txtTelefono.getText().trim());
                    
                    dao.guardarTodos(listaVigilantes);
                    JOptionPane.showMessageDialog(this, "Actualizaciones completas", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTablaVigilantes();
                    
                    txtID.setEnabled(true);
                    comboGenero.setEnabled(true);
                    txtFechaContratacion.setEnabled(true);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "vigilante no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            
            
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
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        comboGenero = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comboCargo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        comboHorario = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        comboArea = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtFechaContratacion = new javax.swing.JTextField();
        btnActualizacion = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVigilantes = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 51, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/VIGILANTE (1).png"))); // NOI18N
        jLabel1.setText("GUARDAR/MODIFICAR/BUSCAR/ELIMINAR Vigilantes");

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("BUSQUEDA POR ID");

        txtBusqueda.setBackground(new java.awt.Color(0, 0, 153));
        txtBusqueda.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        txtBusqueda.setForeground(new java.awt.Color(0, 255, 255));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BUSQUEDA (1).png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/MARINUS 2.0 (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel17))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INGRESAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(0, 51, 255))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nombre ");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Apellido ");

        txtApellido.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Edad");

        txtEdad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("ID ");

        txtID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Genero");

        comboGenero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", " ", "Masculino", "Femenino" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Correo");

        txtCorreo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Cargo");

        comboCargo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", " ", "Guardia de entrada", "Patrullero", "Guardia por exterior" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Salario ");

        txtSalario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Horario");

        comboHorario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboHorario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", " ", "Diurno", "Nocturno", " " }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Area");

        comboArea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", " ", "Zona de corales", "Entrada principal", "Torre de control", "Manejo ambiental", " ", " " }));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Telefono ");

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Fecha de contratacion  ");

        txtFechaContratacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnActualizacion.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnActualizacion.setText("ACTUALIZAR");
        btnActualizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizacionMouseClicked(evt);
            }
        });
        btnActualizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizacionActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnActualizacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txtID))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboGenero, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCorreo)
                            .addComponent(comboCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(txtSalario))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboArea, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(txtFechaContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(comboArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtFechaContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizacion)
                    .addComponent(btnLimpiar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VIGILANTES REGISTRADOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(0, 0, 255))); // NOI18N

        tableVigilantes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableVigilantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Edad", "ID", "Genero", "Correo", "Cargo", "Salario", "Horario", "Area", "Telefono", "Contratacion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableVigilantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableVigilantesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableVigilantes);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1107, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/VIGILANTE_MARINOOO (1).png"))); // NOI18N

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/CUIDADORA_OCEANICA (1) (1).png"))); // NOI18N

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GUARDIAN_MARINO (1).png"))); // NOI18N

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GUARDIAN_DEL_OCEANOOO (1) (1)_1.png"))); // NOI18N

        btnGuardar.setBackground(new java.awt.Color(0, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GUARDAR (1) (1) (1).png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(0, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ACTUALIZAR (2).png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarMouseClicked(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(0, 255, 255));
        btnBuscar.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BUSCAR.png"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(0, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ELIMINAR.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102)
                                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(191, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnGuardar)
                                .addComponent(btnActualizar)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        // TODO add your handling code here:
        guardarRegistroVigilante();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        eliminarVigilantes();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        // TODO add your handling code here:
        buscarVigilantes();
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        // TODO add your handling code here:
        actualizarCampos();
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnActualizacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizacionMouseClicked
        // TODO add your handling code here:
        actualizarVigilantes();
    }//GEN-LAST:event_btnActualizacionMouseClicked

    private void tableVigilantesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableVigilantesMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tableVigilantes.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombre.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
            txtApellido.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
            txtEdad.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
            txtID.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
            comboGenero.setSelectedItem(modelo.getValueAt(filaSeleccionada, 4));
            txtCorreo.setText(modelo.getValueAt(filaSeleccionada, 5).toString());
            comboCargo.setSelectedItem(modelo.getValueAt(filaSeleccionada, 6));
            txtSalario.setText(modelo.getValueAt(filaSeleccionada, 7).toString());
            comboHorario.setSelectedItem(modelo.getValueAt(filaSeleccionada, 8));
            comboArea.setSelectedItem(modelo.getValueAt(filaSeleccionada, 9));
            txtTelefono.setText(modelo.getValueAt(filaSeleccionada, 10).toString());
            txtFechaContratacion.setText(modelo.getValueAt(filaSeleccionada, 11).toString());
            
        }
    }//GEN-LAST:event_tableVigilantesMouseClicked

    private void btnActualizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizacionActionPerformed

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

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
            java.util.logging.Logger.getLogger(GestionVigilantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionVigilantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionVigilantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionVigilantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionVigilantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizacion;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> comboArea;
    private javax.swing.JComboBox<String> comboCargo;
    private javax.swing.JComboBox<String> comboGenero;
    private javax.swing.JComboBox<String> comboHorario;
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
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JTable tableVigilantes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtFechaContratacion;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
