/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import model.Cuidadores;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.cuidadoresDAO;
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
import com.toedter.calendar.JDateChooser;
import model.oConstantes.GeneroEnum;



/**
 *
 * @author juanp
 */
public class GestionCuidadores extends javax.swing.JFrame {
    DefaultTableModel modelo = new DefaultTableModel();

    /**
     * Creates new form GestionCuidadores
     */
    public GestionCuidadores() {
        initComponents();
        cargarTablaCuidadores();
        setLocationRelativeTo(null);
        
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter(){
            public void  keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter(){
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
    
    private final cuidadoresDAO dao = new cuidadoresDAO();
    
    private void guardarRegistroCuidador(){
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String edad = txtEdad.getText().trim();
            String iDText = txtID.getText().trim();
            var generoSeleccionado = comboGenero.getSelectedItem().toString();
            var genero = GeneroEnum.MASCULINO;
            if(generoSeleccionado.toLowerCase().contains("seleccionar")){
                JOptionPane.showMessageDialog(this, "Seleccione un genero");
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
            
         
            if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || iDText.isEmpty() || genero.equals("Seleccionar") || correo.isEmpty() || salario.isEmpty() || cargo.equals("Seleccionar") || horario.equals("Seleccionar") || numTelefonoText.isEmpty() || fechaContratacion == null || area.equals("Seleccionar")) {
              JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Warning", JOptionPane.WARNING_MESSAGE);
              return;
            }
            
            if (iDText.length() !=10 || !iDText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,"El ID debe tener 10 digitos","Warning",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int edadIngresada = Integer.parseInt(edad);
            if (edadIngresada < 22 || edadIngresada > 70) {
                JOptionPane.showMessageDialog(this,"Edad invalida.Solo se permite una edad apartir de 22 a 70 años","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            if (!validacionLetras(nombre)|| !validacionLetras(apellido)) {
                JOptionPane.showMessageDialog(this,"El nombre/apellido debe contener solo LETRAS","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!salario.matches("\\d+") || !edad.matches("\\d+") || !numTelefonoText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,"La edad/salario/numero telefonico deben ser solamente NUMEROS","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (numTelefonoText.length() !=10) {
                JOptionPane.showMessageDialog(this,"El numero de telefono debe contener 10 digitos","Warning",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            
            if (!validacionCorreo(correo)) {
                JOptionPane.showMessageDialog(this,"Correo invalido, ingrese otro","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int iD = Integer.parseInt(iDText);
            String numTelefono = numTelefonoText;
            
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
            
            
            Cuidadores registroExistente = dao.buscarConID(iD);
            if (registroExistente != null ) {
                JOptionPane.showMessageDialog(this, "Registro existente", "Warning", JOptionPane.WARNING_MESSAGE);
                return;   
            }
            
            Cuidadores nuevoCuidador = new Cuidadores(cargo, fechaContratacion, salario, horario, correo, numTelefono, nombre, apellido, genero, edadIngresada, iD, area);
            dao.guardarCuidador(nuevoCuidador);
            
            JOptionPane.showMessageDialog(this,"Registro exitoso", "Exitos", JOptionPane.INFORMATION_MESSAGE);
            cargarTablaCuidadores();
              
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar al cuidador", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    private boolean validacionCorreo(String gmail){
        return gmail.contains("@")&& gmail.contains(".") && gmail.indexOf('@') < gmail.lastIndexOf('.') && !gmail.startsWith("@") && !gmail.endsWith(".");
    }
    
    
    private void cargarTablaCuidadores(){
        modelo.setColumnIdentifiers(new Object[]{"Nombre","Apellido","Edad","ID","Genero","Correo","Salario","Cargo","Horario","Telefono","Contratacion","Area"});
        modelo.setRowCount(0);
        
        List<Cuidadores> listaCuidadores = dao.cargarRegistros();
        
        for(Cuidadores cuidador : listaCuidadores){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            modelo.addRow(new Object[]{
                cuidador.getNombre(),
                cuidador.getApellido(),
                cuidador.getEdad(),
                cuidador.getiD(),
                cuidador.getGenero(),
                cuidador.getCorreo(),
                cuidador.getSalario(),
                cuidador.getCargo(),
                cuidador.getHorario(),
                cuidador.getNumTelefono(),
                sdf.format(cuidador.getFechaContratacion()),
                cuidador.getArea()
            });
        }
        tableCuidador.setModel(modelo);
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
    
    private void eliminarCuidadores(){
        String iDIngresado = JOptionPane.showInputDialog(this, "Ingrese el ID a eliminar");
        
        if (iDIngresado != null && !iDIngresado.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(iDIngresado.trim());
                boolean eliminacion = dao.eliminarConID(id);
                
                if (eliminacion) {
                    cargarTablaCuidadores();
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
    
    
    private void buscarCuidadores(){
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
        
        Cuidadores cuidador = dao.buscarConID(iDIngresada);
        if (cuidador == null) {
            modelo.setRowCount(0);
            JOptionPane.showMessageDialog(null, "No hay resultados");
            
        } else {
            mostrarBusquedaEnTabla(cuidador);
        }
    }
    
    private void mostrarBusquedaEnTabla(Cuidadores cuidador){
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
            cuidador.getNombre(),
            cuidador.getApellido(),
            cuidador.getEdad(),
            cuidador.getiD(),
            cuidador.getGenero(),
            cuidador.getCorreo(),
            cuidador.getSalario(),
            cuidador.getCargo(),
            cuidador.getHorario(),
            cuidador.getNumTelefono(),
            cuidador.getFechaContratacion(),
            cuidador.getArea()
        });
        tableCuidador.setModel(modelo);
    }
    
    private void actualizarCampos(){
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        txtEdad.setEnabled(true);
        txtID.setEnabled(false);
        txtCorreo.setEnabled(true);
        comboGenero.setEnabled(false);
        txtSalario.setEnabled(true);
        comboCargo.setEnabled(true);
        comboHorario.setEnabled(true);
        txtTelefono.setEnabled(true);
        dateContratacion.setEnabled(false);
        comboArea.setEnabled(true);
        
    }
    
 private void actualizarCuidadores() {
    try {
        String idText = txtID.getText().trim();
        String telefonoText = txtTelefono.getText().trim();
        String salarioText = txtSalario.getText().trim();
        String edadText = txtEdad.getText().trim();
        String correo = txtCorreo.getText().trim();
        
        if (!telefonoText.matches("\\d+") || !salarioText.matches("\\d+") || !edadText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Todos los campos numéricos deben contener solamente  números", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validacionCorreo(correo)) {
           JOptionPane.showMessageDialog(this,"Correo invalido, ingrese otro","Error",JOptionPane.ERROR_MESSAGE);
           return;
        }
        
        int edad = Integer.parseInt(edadText);
        if (edad < 22 || edad > 70 ) {
            JOptionPane.showMessageDialog(this, "Edad invalida.Solo se permite una edad apartir de 22 a 70 años","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int iD = Integer.parseInt(idText);

        List<Cuidadores> listaCuidadores = dao.cargarRegistros();
        for (Cuidadores cuidador : listaCuidadores) {
            if (cuidador.getiD()== iD) {
                
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                
                if (!validacionLetras(nombre)|| !validacionLetras(apellido)) {
                   JOptionPane.showMessageDialog(this,"El nombre/apellido debe contener solo LETRAS","Error",JOptionPane.ERROR_MESSAGE);
                   return;
                }
                
                cuidador.setNombre(nombre);
                cuidador.setApellido(apellido);
                cuidador.setEdad(edad);
                cuidador.setSalario(salarioText);
                cuidador.setCorreo(correo);
                cuidador.setCargo(comboCargo.getSelectedItem().toString());
                cuidador.setHorario(comboHorario.getSelectedItem().toString());
                cuidador.setArea(comboArea.getSelectedItem().toString());
                cuidador.setNumTelefono(telefonoText);

                dao.guardarTodos(listaCuidadores);
                JOptionPane.showMessageDialog(this, "Actualizaciones completas", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaCuidadores();
                limpiarCampos();

                txtID.setEnabled(true);
                dateContratacion.setEnabled(true);
                comboGenero.setEnabled(true);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Cuidador no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error inesperado al actualizar", "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        botonRegresar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
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
        txtSalario = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboCargo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        comboHorario = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        comboArea = new javax.swing.JComboBox<>();
        btnActualizacion = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        dateContratacion = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCuidador = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        jLabel20.setText("jLabel20");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(2147483647, 2147483));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 51, 153));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/CUIDADORES (1).png"))); // NOI18N
        jLabel2.setText("GUARDAR/MODIFICAR/BUSCAR/ELIMINAR Cuidadores ");

        jLabel18.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Busqueda por ID ");

        txtBusqueda.setBackground(new java.awt.Color(0, 0, 153));
        txtBusqueda.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        txtBusqueda.setForeground(new java.awt.Color(0, 255, 255));
        txtBusqueda.setSelectionColor(new java.awt.Color(0, 51, 204));
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        botonRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BOTON_REGRESAR (1) (1).png"))); // NOI18N
        botonRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegresarMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BUSQUEDA (1).png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/MARINUS 2.0 (1).png"))); // NOI18N
        jLabel26.setText("jLabel26");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(521, 521, 521))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addComponent(jLabel1)
                .addContainerGap(747, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonRegresar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(38, 38, 38)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INGRESAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(153, 0, 51))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nombre ");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Apellido ");

        txtApellido.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Edad");

        txtEdad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("ID");

        txtID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Genero   ");

        comboGenero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar...", "MASCULINO", "FEMENINO", "OTROS" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Correo  ");

        txtCorreo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Salario  ");

        txtSalario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Cargo");

        comboCargo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Jefe de area", "Auxiliar", "Veterinario" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Horario");

        comboHorario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboHorario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Diurno", "Nocturno" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Telefono ");

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Fecha De Contratacion  ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Area");

        comboArea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Habitat acuatico", "Habitat terrestre" }));

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellido)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtID)))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboGenero, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSalario))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCorreo))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateContratacion, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboArea, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnActualizacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(btnLimpiar)
                .addGap(48, 48, 48))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(comboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(comboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(comboHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13))
                    .addComponent(dateContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(comboArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizacion)
                    .addComponent(btnLimpiar))
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CUIDADORES REGISTRADOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(153, 0, 0))); // NOI18N

        tableCuidador.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableCuidador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Edad", "ID", "Genero", "Correo", "Salario", "Cargo", "Horario", "Telefono", "Contratacion", "Area"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCuidador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCuidadorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableCuidador);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1109, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/CUIDADOR_OCEANICO (1).png"))); // NOI18N

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/CUIDADORES_MARINOS (2).png"))); // NOI18N

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/RESCATISTA (1) (1).png"))); // NOI18N
        jLabel19.setText("jLabel19");

        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GUARDAR (1) (1) (1).png"))); // NOI18N
        jLabel21.setText("GUARDAR");

        javax.swing.GroupLayout btnGuardarLayout = new javax.swing.GroupLayout(btnGuardar);
        btnGuardar.setLayout(btnGuardarLayout);
        btnGuardarLayout.setHorizontalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
            .addGroup(btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnGuardarLayout.createSequentialGroup()
                    .addGap(0, 17, Short.MAX_VALUE)
                    .addComponent(jLabel21)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );
        btnGuardarLayout.setVerticalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
            .addGroup(btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnGuardarLayout.createSequentialGroup()
                    .addGap(0, 1, Short.MAX_VALUE)
                    .addComponent(jLabel21)
                    .addGap(0, 2, Short.MAX_VALUE)))
        );

        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarMouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ACTUALIZAR (2).png"))); // NOI18N
        jLabel22.setText("ACTUALIZAR");

        javax.swing.GroupLayout btnActualizarLayout = new javax.swing.GroupLayout(btnActualizar);
        btnActualizar.setLayout(btnActualizarLayout);
        btnActualizarLayout.setHorizontalGroup(
            btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
            .addGroup(btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnActualizarLayout.createSequentialGroup()
                    .addGap(0, 13, Short.MAX_VALUE)
                    .addComponent(jLabel22)
                    .addGap(0, 13, Short.MAX_VALUE)))
        );
        btnActualizarLayout.setVerticalGroup(
            btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
            .addGroup(btnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnActualizarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel22)
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
            .addGap(0, 130, Short.MAX_VALUE)
            .addGroup(btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnBuscarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel23)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnBuscarLayout.setVerticalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
            .addGroup(btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnBuscarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel23)
                    .addGap(0, 0, Short.MAX_VALUE)))
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
            .addGap(0, 130, Short.MAX_VALUE)
            .addGroup(btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnEliminarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel24)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnEliminarLayout.setVerticalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
            .addGroup(btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnEliminarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel24)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/CUIDADORES_MARINOS (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel17)
                                .addGap(62, 62, 62)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(735, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel19))
                                .addGap(5, 5, 5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1042, 1042, 1042))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            cargarTablaCuidadores();
        }
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            buscarCuidadores();
        }
        
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void tableCuidadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCuidadorMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tableCuidador.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombre.setText(modelo.getValueAt(filaSeleccionada,0).toString());
            txtApellido.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
            txtEdad.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
            txtID.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
            comboGenero.setSelectedItem(tableCuidador.getValueAt(filaSeleccionada, 4).toString());
            txtCorreo.setText(modelo.getValueAt(filaSeleccionada, 5).toString());
            txtSalario.setText(modelo.getValueAt(filaSeleccionada, 6).toString());
            comboCargo.setSelectedItem(modelo.getValueAt(filaSeleccionada,7));
            comboHorario.setSelectedItem(modelo.getValueAt(filaSeleccionada, 8));
            txtTelefono.setText(modelo.getValueAt(filaSeleccionada, 9).toString());
            try {
                String fechaDate = String.valueOf(modelo.getValueAt(filaSeleccionada, 10));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = sdf.parse(fechaDate);
                dateContratacion.setDate(fecha);
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al establecer la fecha de contratación", "Error", JOptionPane.ERROR_MESSAGE);  
            }
            comboArea.setSelectedItem(modelo.getValueAt(filaSeleccionada, 11));
        }
    }//GEN-LAST:event_tableCuidadorMouseClicked

    private void btnActualizacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizacionMouseClicked
        // TODO add your handling code here:
        actualizarCuidadores();
    }//GEN-LAST:event_btnActualizacionMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        // TODO add your handling code here:
        guardarRegistroCuidador();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        // TODO add your handling code here:
        actualizarCampos();
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        // TODO add your handling code here:
        buscarCuidadores();
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        eliminarCuidadores();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void botonRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegresarMouseClicked
        // TODO add your handling code here:
        new Administrador().setVisible(true);
        dispose();
    }//GEN-LAST:event_botonRegresarMouseClicked

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
            java.util.logging.Logger.getLogger(GestionCuidadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionCuidadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionCuidadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionCuidadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionCuidadores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botonRegresar;
    private javax.swing.JButton btnActualizacion;
    private javax.swing.JPanel btnActualizar;
    private javax.swing.JPanel btnBuscar;
    private javax.swing.JPanel btnEliminar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JButton btnLimpiar;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
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
    private javax.swing.JTable tableCuidador;
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
