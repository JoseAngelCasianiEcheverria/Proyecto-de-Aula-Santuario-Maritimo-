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
import model.oConstantes.GeneroEnum;

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
        
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter(){
            public void  keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });
        txtEdad.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloNumeros(evt);
            }
        });
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt){
                char c = evt.getKeyChar();
                
                if (!Character.isDigit(c) || txtID.getText().length() >= 10) {
                    evt.consume();
                    soloNumeros(evt);
                }
            }
        });
        txtSalario.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloNumeros(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() { 
            public void keyTyped(java.awt.event.KeyEvent evt){
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) || txtTelefono.getText().length() >= 10) {
                    evt.consume();
                    soloNumeros(evt);
                }
            }
        });  
        
    }
    
    private final vigilantesDAO dao = new vigilantesDAO();
    
    private void guardarRegistroVigilante(){
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String edad = txtEdad.getText().trim();
            String iDText = txtID.getText().trim();
            var generoSeleccionado = comboGenero.getSelectedItem().toString();
            var genero = GeneroEnum.MASCULINO;
            if (generoSeleccionado.toLowerCase().contains("seleccionar")) {
               JOptionPane.showMessageDialog(this,"Seleccione un genero");
               return;
            }
            if (generoSeleccionado.equalsIgnoreCase(GeneroEnum.MASCULINO.toString())) {
              genero = GeneroEnum.MASCULINO;
            }
            else if (generoSeleccionado.equalsIgnoreCase(GeneroEnum.FEMENINO.toString())) {
              genero = GeneroEnum.FEMENINO; 
            }
            else{
              genero = GeneroEnum.OTROS;
            }
            String correo = txtCorreo.getText().trim();
            String salario = txtSalario.getText().trim();
            String cargo = comboCargo.getSelectedItem().toString();
            String horario = comboHorario.getSelectedItem().toString();
            String numTelefonoText = txtTelefono.getText().trim();
            Date fechaContratacion = dateContratacion.getDate();
            String area = comboArea.getSelectedItem().toString();
            
            if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || genero.equals("Seleccionar") || correo.isEmpty() || salario.isEmpty() || cargo.equals("Seleccionar") || horario.equals("Seleccionar") || numTelefonoText.isEmpty() || fechaContratacion == null || area.equals("Seleccionar")) {
               JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Warning", JOptionPane.WARNING_MESSAGE);
               return;
            }
            
            int edadIngresada = Integer.parseInt(edad);
            if (edadIngresada < 22) {
                JOptionPane.showMessageDialog(this,"Solo se permite una edad de 22 años o mas","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            
            if (!validacionLetras(nombre) || !validacionLetras(apellido)) {
               JOptionPane.showMessageDialog(this,"El nombre/apellido debe tener LETRAS","Error",JOptionPane.ERROR_MESSAGE);
               return;
            }
            
            if (!salario.matches("\\d+")||!edad.matches("\\d+")||!numTelefonoText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "El salario/edad/numero telefonico deben ser NUMEROS","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String numTelefono = numTelefonoText;
            int iD = Integer.parseInt(iDText);
            
            if (fechaContratacion == null) {
              JOptionPane.showMessageDialog(this, "Seleccione una fecha de contratación", "Error", JOptionPane.ERROR_MESSAGE);
              return;
            }
            
            int añoSeleccionado = fechaContratacion.getYear() + 1900;
            int añoActual = LocalDate.now().getYear();
            
            if (añoSeleccionado > añoActual) {
               JOptionPane.showMessageDialog(this,"No se puede seleccionar un año mayor al actual","Warning",JOptionPane.WARNING_MESSAGE);
               return;
            }
            
            
            
            
            Vigilantes registroExistente = dao.buscarConID(iD);
            if (registroExistente != null ) {
                JOptionPane.showMessageDialog(this, "Registro existente", "Warning", JOptionPane.WARNING_MESSAGE);
                return;   
            }
            
            Vigilantes nuevoVigilante = new Vigilantes(cargo, fechaContratacion, salario, horario, correo, numTelefono, nombre, apellido, genero, edadIngresada, iD);
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
    
    private void soloLetras(java.awt.event.KeyEvent evt){
        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && c != ' ' && c != '\b') {
            evt.consume();
            JOptionPane.showMessageDialog(this,"Solo se permiten LETRAS","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void soloNumeros(java.awt.event.KeyEvent evt){
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '\b') {
            evt.consume();
            JOptionPane.showMessageDialog(this,"Solo se permiten NUMEROS","Error",JOptionPane.ERROR_MESSAGE);
        }
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
                vigilante.getiD(),
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
        dateContratacion.setDate(null);
        comboArea.setSelectedIndex(0);
    }
    
    private void eliminarVigilantes(){
        String iDIngresado = JOptionPane.showInputDialog(this, "Ingrese el ID a eliminar");
        
        if (iDIngresado != null && !iDIngresado.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(iDIngresado.trim());
                boolean eliminacion = dao.eliminarConID(id);
                
                if (eliminacion) {
                    cargarTablaVigilantes();
                    JOptionPane.showMessageDialog(this,"Eliminacion exitosa","Exitos",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,"ID no encontrado");
                    return;
                }
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Id invalido","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this,"Cancelando eliminacion","Warning",JOptionPane.WARNING_MESSAGE);
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
            modelo.setRowCount(0);
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
            vigilante.getiD(),
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
        dateContratacion.setEnabled(false);
        
    }
    
    private void actualizarVigilantes(){
        try {
            String idText = txtID.getText().trim();
            String telefonoText = txtTelefono.getText().trim();
            String salarioText = txtSalario.getText().trim();
            String edadText = txtEdad.getText().trim();
               
            if (!telefonoText.matches("\\d+") || !salarioText.matches("\\d+") || !edadText.matches("\\d+")) {
               JOptionPane.showMessageDialog(this, "Todos los campos numéricos deben contener solamente  números", "Error", JOptionPane.ERROR_MESSAGE);
               return;
            }
            
            int edad = Integer.parseInt(edadText);
            if (edad < 22) {
               JOptionPane.showMessageDialog(this, "Solo se permite una edad de 22 años o mas","Error",JOptionPane.ERROR_MESSAGE);
               return;
            }
            
            int iD = Integer.parseInt(txtID.getText().trim());
            List<Vigilantes> listaVigilantes = dao.cargarRegistros();
            
            for(Vigilantes vigilante : listaVigilantes){
                if (vigilante.getiD()== iD) {
                    
                    String nombre = txtNombre.getText().trim();
                    String apellido = txtApellido.getText().trim();
                    
                    if (!validacionLetras(nombre)|| !validacionLetras(apellido)) {
                       JOptionPane.showMessageDialog(this,"El nombre/apellido debe contener solo LETRAS","Error",JOptionPane.ERROR_MESSAGE);
                       return;
                    }
                    
                    vigilante.setNombre(nombre);
                    vigilante.setApellido(apellido);
                    vigilante.setEdad(edad);
                    vigilante.setCorreo(txtCorreo.getText().trim());
                    vigilante.setCargo(comboCargo.getSelectedItem().toString());
                    vigilante.setSalario(salarioText);
                    vigilante.setHorario(comboHorario.getSelectedItem().toString());
                    vigilante.setArea(comboArea.getSelectedItem().toString());
                    vigilante.setNumTelefono(telefonoText);
                    
                    dao.guardarTodos(listaVigilantes);
                    JOptionPane.showMessageDialog(this, "Actualizaciones completas", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTablaVigilantes();
                    limpiarCampos();
                    
                    txtID.setEnabled(true);
                    comboGenero.setEnabled(true);
                    dateContratacion.setEnabled(true);
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
        btnRgreso = new javax.swing.JLabel();
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
        btnActualizacion = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        dateContratacion = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVigilantes = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();

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
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BUSQUEDA (1).png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/MARINUS 2.0 (1).png"))); // NOI18N

        btnRgreso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BOTON_REGRESAR (1) (1).png"))); // NOI18N
        btnRgreso.setText("jLabel22");
        btnRgreso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRgresoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(btnRgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(851, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(171, 171, 171))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRgreso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        comboGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "MASCULINO", "FEMENINO", "OTROS", " " }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Correo");

        txtCorreo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Cargo");

        comboCargo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Guardia de entrada", "Patrullero", "Guardia por exterior" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Salario ");

        txtSalario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Horario");

        comboHorario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboHorario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Diurno", "Nocturno", " " }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Area");

        comboArea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Zona de corales", "Entrada principal", "Torre de control", "Manejo ambiental", " ", " " }));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Telefono ");

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Fecha de contratacion  ");

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
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
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
                        .addComponent(jLabel14))
                    .addComponent(dateContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GUARDAR (1) (1) (1).png"))); // NOI18N
        jLabel20.setText("GUARDAR");

        javax.swing.GroupLayout btnGuardarLayout = new javax.swing.GroupLayout(btnGuardar);
        btnGuardar.setLayout(btnGuardarLayout);
        btnGuardarLayout.setHorizontalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnGuardarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnGuardarLayout.setVerticalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
            .addGroup(btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnGuardarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarMouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ACTUALIZAR (2).png"))); // NOI18N
        jLabel23.setText("ACTUALIZAR");

        javax.swing.GroupLayout btnActualizarLayout = new javax.swing.GroupLayout(btnActualizar);
        btnActualizar.setLayout(btnActualizarLayout);
        btnActualizarLayout.setHorizontalGroup(
            btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnActualizarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel23)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnActualizarLayout.setVerticalGroup(
            btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnActualizarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel23)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BUSCAR.png"))); // NOI18N
        jLabel24.setText("BUSCAR");

        javax.swing.GroupLayout btnBuscarLayout = new javax.swing.GroupLayout(btnBuscar);
        btnBuscar.setLayout(btnBuscarLayout);
        btnBuscarLayout.setHorizontalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnBuscarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel24)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnBuscarLayout.setVerticalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnBuscarLayout.createSequentialGroup()
                    .addGap(0, 6, Short.MAX_VALUE)
                    .addComponent(jLabel24)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );

        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ELIMINAR.png"))); // NOI18N
        jLabel25.setText("ELIMINAR");

        javax.swing.GroupLayout btnEliminarLayout = new javax.swing.GroupLayout(btnEliminar);
        btnEliminar.setLayout(btnEliminarLayout);
        btnEliminarLayout.setHorizontalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnEliminarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel25)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnEliminarLayout.setVerticalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnEliminarLayout.createSequentialGroup()
                    .addGap(0, 6, Short.MAX_VALUE)
                    .addComponent(jLabel25)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(399, 399, 399))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
            comboGenero.setSelectedItem(tableVigilantes.getValueAt(filaSeleccionada, 4).toString());
            txtCorreo.setText(modelo.getValueAt(filaSeleccionada, 5).toString());
            comboCargo.setSelectedItem(modelo.getValueAt(filaSeleccionada, 6));
            txtSalario.setText(modelo.getValueAt(filaSeleccionada, 7).toString());
            comboHorario.setSelectedItem(modelo.getValueAt(filaSeleccionada, 8));
            comboArea.setSelectedItem(modelo.getValueAt(filaSeleccionada, 9));
            txtTelefono.setText(modelo.getValueAt(filaSeleccionada, 10).toString());
            try {
                String fechaDate = String.valueOf(modelo.getValueAt(filaSeleccionada, 11));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = sdf.parse(fechaDate);
                dateContratacion.setDate(fecha);
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al establecer la fecha de contratación", "Error", JOptionPane.ERROR_MESSAGE);  
            
        }
            
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

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        // TODO add your handling code here:
        guardarRegistroVigilante();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        // TODO add your handling code here:
        actualizarCampos();
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        // TODO add your handling code here:
        buscarVigilantes();
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        eliminarVigilantes();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        // TODO add your handling code here:
        if (txtBusqueda.getText().trim().isEmpty()) {
            cargarTablaVigilantes();
        }
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            buscarVigilantes();
        }
        
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void btnRgresoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRgresoMouseClicked
        // TODO add your handling code here:
        new Administrador().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnRgresoMouseClicked

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
    private javax.swing.JPanel btnActualizar;
    private javax.swing.JPanel btnBuscar;
    private javax.swing.JPanel btnEliminar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel btnRgreso;
    private javax.swing.JComboBox<String> comboArea;
    private javax.swing.JComboBox<String> comboCargo;
    private javax.swing.JComboBox<String> comboGenero;
    private javax.swing.JComboBox<String> comboHorario;
    private com.toedter.calendar.JDateChooser dateContratacion;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
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
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
