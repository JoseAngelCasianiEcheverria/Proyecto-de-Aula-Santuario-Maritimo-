/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.Visitantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.VisitantesDAO;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Guias;
import model.oConstantes.GeneroEnum;
import dao.GuiasDAO;
import dao.AgendasDAO;
import model.Agendas;

/**
 *
 * @author USER
 */
public class RegistroVisitantes1 extends javax.swing.JFrame {
    DefaultTableModel modelo = new DefaultTableModel();
    
    private boolean botonesHabilitados = false;

    /**
     * Creates new form RegistroVisitantes
     */
    public RegistroVisitantes1() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);


        Color colorOriginal = new Color(0, 153, 204);
        Color colorPresionado = new Color(0, 102, 153);

        btnUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUsuario.setBackground(colorPresionado);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnUsuario.setBackground(colorOriginal);
            }
        });

        btnAgendas.setBackground(colorOriginal);

        btnAgendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAgendas.setBackground(colorPresionado);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnAgendas.setBackground(colorOriginal);
            }
        });
        
        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(colorPresionado);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(colorOriginal);
            }
        });
        
        btnComprar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnComprar.setBackground(colorPresionado);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnComprar.setBackground(colorOriginal);
            }
        });
        
        btnPrecios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPrecios.setBackground(colorPresionado);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnPrecios.setBackground(colorOriginal);
            }
        });
        
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSalir.setBackground(colorPresionado);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnSalir.setBackground(colorOriginal);
            }
        });
        
       

    }
    
    
    private final VisitantesDAO dao = new VisitantesDAO();

private void guardarRegistroVisitantes() {
    try {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String iDText = txtID.getText().trim();
        String edadText = txtEdad.getText().trim();
        String nTelefono = txtNTelefonico.getText().trim();
        String contraseña = txtContraseña.getText().trim();
        String correo = txtCorreo.getText().trim();
        GeneroEnum genero = null;

        // Validaciones obligatorias
        if (nombre.isEmpty() || apellido.isEmpty() || iDText.isEmpty() || edadText.isEmpty() || nTelefono.isEmpty()
                || contraseña.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar que ID tenga 10 dígitos numéricos
        if (iDText.length() != 10 || !iDText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El ID debe tener exactamente 10 dígitos numéricos", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar letras en nombre y apellido
        if (!validacionLetras(nombre)) {
            JOptionPane.showMessageDialog(this, "El nombre solo permite letras", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validacionLetras(apellido)) {
            JOptionPane.showMessageDialog(this, "El apellido solo permite letras", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar número telefónico
        if (!nTelefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El número telefónico solo puede contener números", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar edad numérica y mayor o igual a 18
        int edad;
        try {
            edad = Integer.parseInt(edadText);
            if (edad < 18 || edad > 120) {
                JOptionPane.showMessageDialog(this, "La edad debe ser mayor o igual a 18 y no superar 120", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Edad inválida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir ID a int
        int id = Integer.parseInt(iDText);

        // Verificar si el visitante ya está registrado
        Visitantes visitanteExistente = dao.buscarPorId(id);
        if (visitanteExistente != null) {
            JOptionPane.showMessageDialog(this, "Este ID ya está registrado", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear nuevo visitante
        Visitantes nuevoVisitante = new Visitantes(correo, nTelefono, contraseña, nombre, apellido, genero, edad, id);
        dao.guardarVisitante(nuevoVisitante);

        JOptionPane.showMessageDialog(this, "Visitante registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // ✅ Habilitar botones después del registro
        habilitarPaneles(true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al registrar al visitante: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



private void actualizarVisitantes() {
    try {
        String nombreT = txtNombreUser.getText().trim();
        String apellidoT = txtApellidoUser.getText().trim();
        String iDtextT = txtIDUser.getText().trim();
        String edadTextT = txtEdadUser.getText().trim();
        String nTelefonoT = txtNTelefonicoUser.getText().trim();
        String contraseñaT = txtContraseñaUser.getText().trim();
        String correoT = txtCorreoUser.getText().trim();

        // Validar campos numéricos
        if (!iDtextT.matches("\\d+") || !edadTextT.matches("\\d+") || !nTelefonoT.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID, Edad y Teléfono deben contener solo números", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }



        // Validar edad
        int edad = Integer.parseInt(edadTextT);
        if (edad < 18 || edad > 120) {
            JOptionPane.showMessageDialog(this, "Edad inválida. Solo se permite una edad a partir de 18 a 120 años", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(iDtextT);
        List<Visitantes> listaVisitantes = dao.cargarRegistros();

        for (Visitantes visitante : listaVisitantes) {
            if (visitante.getiD() == id) {

                // Validar nombre y apellido
                if (!validacionLetras(nombreT) || !validacionLetras(apellidoT)) {
                    JOptionPane.showMessageDialog(this, "El nombre y apellido deben contener solo letras", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                visitante.setNombre(nombreT);
                visitante.setApellido(apellidoT);
                visitante.setEdad(edad);
                visitante.setCorreo(correoT);
                visitante.setTelefono(nTelefonoT);
                visitante.setContraseña(contraseñaT);

                dao.guardarTodos(listaVisitantes);

                JOptionPane.showMessageDialog(this, "Actualización completada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Si tienes uno para limpiar los campos
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Visitante no encontrado", "Error", JOptionPane.ERROR_MESSAGE);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

  
   private void pasarDatosAActualizar() {
    // Verificar que los campos de actualización estén vacíos
    if (txtNombreUser.getText().isEmpty() ||
        txtApellidoUser.getText().isEmpty() ||
        txtIDUser.getText().isEmpty() ||
        txtEdadUser.getText().isEmpty() ||
        txtNTelefonicoUser.getText().isEmpty() ||
        txtCorreoUser.getText().isEmpty()) {
        
        // Si están vacíos, copiar los datos desde los campos originales
        txtNombreUser.setText(txtNombre.getText().trim());
        txtApellidoUser.setText(txtApellido.getText().trim());
        txtIDUser.setText(txtID.getText().trim());
        txtEdadUser.setText(txtEdad.getText().trim());
        txtNTelefonicoUser.setText(txtNTelefonico.getText().trim());
        txtContraseñaUser.setText(txtContraseña.getText().trim());
        txtCorreoUser.setText(txtCorreo.getText().trim());
    }
}

   
   //Metodo para Calcular Precio
   private double calcularPrecio() throws NumberFormatException {
    int numAcompañantes = Integer.parseInt(txtNumAcompanantes.getText().trim());
    Date fecha = fechaIngreso.getDate();
    String tipoEntrada = comboTipoEntrada.getSelectedItem().toString();
    String horaSeleccionada = comboHoraIngreso.getSelectedItem().toString();

    if (fecha == null || tipoEntrada.equals("Seleccionar") || horaSeleccionada.equals("Seleccionar")) {
        throw new IllegalArgumentException("Por favor completa todos los campos");
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fecha);
    int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

    int precioBase;
    switch (diaSemana) {
        case Calendar.MONDAY: precioBase = 30000; break;
        case Calendar.TUESDAY: precioBase = 44000; break;
        case Calendar.WEDNESDAY: precioBase = 35000; break;
        case Calendar.THURSDAY: precioBase = 25000; break;
        case Calendar.FRIDAY: precioBase = 49000; break;
        default:
            throw new IllegalArgumentException("Solo se permiten visitas de Lunes a Viernes");
    }

    double descuento = 0;
    if (horaSeleccionada.contains("08:00")) {
        descuento = 0.50;
    } else if (horaSeleccionada.contains("10:00")) {
        descuento = 0.25;
    } else if (horaSeleccionada.contains("02:00") || horaSeleccionada.contains("14:00")) {
        descuento = 0.30;
    }

    double precioConDescuento = precioBase * (1 - descuento);

    if (tipoEntrada.equalsIgnoreCase("VIP")) {
        precioConDescuento *= 1.70;
    }

    int totalPersonas = numAcompañantes + 1;
    double total = precioConDescuento * totalPersonas;

    // Mostrar en txtPrecio también, si quieres mantener eso
    txtPrecio.setText(String.format("%.0f COP", total));

    return total;
}
   
   private void iniciarSesionVisitante() {
    String idText = txtIDsesion.getText().trim();
    String contrasena = txtContrasesion.getText().trim();

    if (idText.isEmpty() || contrasena.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingresa tu ID y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(idText);
        Visitantes visitante = dao.buscarPorId(id);

        if (visitante != null && visitante.getContraseña().equals(contrasena)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // 🔓 Habilita botones o paneles
            habilitarPaneles(true);

            // 📝 Cargar datos en los campos de texto
            txtNombreUser.setText(visitante.getNombre());
            txtApellidoUser.setText(visitante.getApellido());
            txtIDUser.setText(String.valueOf(visitante.getiD()));
            txtEdadUser.setText(String.valueOf(visitante.getEdad()));
            txtNTelefonicoUser.setText(visitante.getTelefono());
            txtContraseñaUser.setText(visitante.getContraseña());
            txtCorreoUser.setText(visitante.getCorreo());

        } else {
            JOptionPane.showMessageDialog(this, "ID o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

   
   private void habilitarPaneles(boolean habilitado) {
    botonesHabilitados = habilitado;

    Color colorActivo = new Color(0, 153, 204);    // colorOriginal
    Color colorDesactivado = new Color(0, 102, 153); // Gris claro o puedes elegir otro

    btnUsuario.setBackground(habilitado ? colorActivo : colorDesactivado);
    btnPrecios.setBackground(habilitado ? colorActivo : colorDesactivado);
    btnComprar.setBackground(habilitado ? colorActivo : colorDesactivado);
    btnAgendas.setBackground(habilitado ? colorActivo : colorDesactivado);
    btnSalir.setBackground(habilitado ? colorActivo : colorDesactivado);
}




   
   private final GuiasDAO daoG = new GuiasDAO();
   private void cargarTablaGuias(){
        modelo.setColumnIdentifiers(new Object[]{"Nombre","Apellido","Edad","ID","Genero","Correo","Telefono"});
        modelo.setRowCount(0);
        
        List<Guias> listaGuias = daoG.cargarRegistros();
        
        for(Guias guia : listaGuias){
            modelo.addRow(new Object[]{
                guia.getNombre(),
                guia.getApellido(),
                guia.getEdad(),
                guia.getiD(),
                guia.getGenero(),
                guia.getCorreo(),
                guia.getNumTelefono(),
            });
        }
        tableGuias.setModel(modelo);
    }

 
   private final AgendasDAO daoA = new AgendasDAO();

private void guardarAgendaVisitantes() {
    try {
        // Obtener datos del formulario
        String numAcompanantes = txtNumAcompanantes.getText().trim();
        Date fecha = fechaIngreso.getDate();
        String tipoEntrada = comboTipoEntrada.getSelectedItem().toString();
        String horaSeleccionada = comboHoraIngreso.getSelectedItem().toString();
        String idText = txtIDUser.getText().trim(); // ID del visitante
        String correoA = null;

        // Validaciones básicas
        if (fecha == null || tipoEntrada.equals("Seleccionar") || horaSeleccionada.equals("Seleccionar") || idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar que el ID sea numérico
        if (!idText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idVisitante = Integer.parseInt(idText);

        // Buscar visitante existente
        Visitantes visitante = dao.buscarPorId(idVisitante);
        if (visitante == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un visitante con este ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        correoA = visitante.getCorreo(); // Se guarda el correo del visitante

        // Validar número de acompañantes (aunque sea String, se puede validar que sea un número)
        if (!numAcompanantes.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Número de acompañantes inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener guía seleccionado (corregido: el ID está en la columna 3)
        int filaSeleccionada = tableGuias.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un guía", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idGuia = Integer.parseInt(tableGuias.getValueAt(filaSeleccionada, 3).toString()); // columna 3 = ID
        GuiasDAO daoGuias = new GuiasDAO();
        Guias guiaSeleccionado = daoGuias.buscarConID(idGuia);
        if (guiaSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Guía no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular precio
        double costo = calcularPrecio(); // Este método ya debe considerar cantidad, tipo, hora, etc.

        // Crear nueva agenda
        Agendas nuevaAgenda = new Agendas(tipoEntrada,numAcompanantes, fecha, idVisitante, correoA, horaSeleccionada, guiaSeleccionado, costo);
        daoA.guardarAgenda(nuevaAgenda);

        JOptionPane.showMessageDialog(this, "Agenda registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Número inválido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar la agenda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


private void mostrarAgendasEnTabla(List<Agendas> listaAgendas) {
    DefaultTableModel modelos = new DefaultTableModel();
    modelos.setColumnIdentifiers(new Object[] {
        "Fecha", "ID", "N° Acompañantes", "Tipo de Entrada", "Hora", "Guía", "Costo"
    });

    for (Agendas agenda : listaAgendas) {
        // Formatear fecha y costo si se desea
        String fechaStr = new SimpleDateFormat("yyyy-MM-dd").format(agenda.getFecha());
        int id = agenda.getId();
        String nAcompanantes = agenda.getnVisitantes();
        String tipoEntrada = agenda.getTipoEntrada(); // esto si quieres mostrar tipo real
        String hora = agenda.getHora();
        Guias guia = agenda.getGuia();
        String nombreGuia = guia.getNombre() + " " + guia.getApellido();
        String costoFormateado = String.format("%.0f", agenda.getCosto()); // sin decimales

        modelos.addRow(new Object[] {
            fechaStr,
            id,
            nAcompanantes,
            tipoEntrada,
            hora,
            nombreGuia,
            costoFormateado
        });
    }

    tableAgendas.setModel(modelos);
}


   
    
    
    
    //Validaciones
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
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGuias = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableGuias = new javax.swing.JTable();
        jLabel47 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        panelInicio = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txtIDsesion = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtContrasesion = new javax.swing.JPasswordField();
        jButton11 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        tablaEleccion = new javax.swing.JTabbedPane();
        panelRegistro = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtEdad = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtNTelefonico = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        jButton10 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panelUsuario = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtNombreUser = new javax.swing.JTextField();
        txtApellidoUser = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        txtIDUser = new javax.swing.JTextField();
        txtEdadUser = new javax.swing.JTextField();
        txtCorreoUser = new javax.swing.JTextField();
        txtNTelefonicoUser = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtContraseñaUser = new javax.swing.JPasswordField();
        panelPrecios = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel7 = new PanelConImagen("/img/precio-Jose.png");
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel5 =  new PanelConImagen("/img/fondo-Jose.png")
        ;
        jLabel17 = new javax.swing.JLabel();
        jPanel6 = new PanelConImagen("/img/Ballena-Jose.png");
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        etiLunes = new javax.swing.JTextField();
        etiMartes = new javax.swing.JTextField();
        etiMiercoles = new javax.swing.JTextField();
        etiJueves = new javax.swing.JTextField();
        etiJueves1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtDescuento1 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        panelComprar = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtNumAcompanantes = new javax.swing.JTextField();
        comboTipoEntrada = new javax.swing.JComboBox<>();
        fechaIngreso = new com.toedter.calendar.JDateChooser();
        comboHoraIngreso = new javax.swing.JComboBox<>();
        btnCalcularPrecio = new javax.swing.JButton();
        txtPrecio = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        btnSeleccionar = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        txtNombreGuia = new javax.swing.JTextField();
        panelAgendas = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAgendas = new javax.swing.JTable();
        panelBotones = new javax.swing.JPanel();
        btnUsuario = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnPrecios = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnComprar = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnAgendas = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel8 = new PanelConImagen("/img/ballena-logo-Jose.png");

        panelGuias.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));

        tableGuias.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableGuias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Edad", "ID", "Genero", "Correo", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableGuias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableGuiasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableGuias);

        jLabel47.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel47.setText("Tabla de Guias disponibles");

        jButton9.setBackground(new java.awt.Color(51, 153, 255));
        jButton9.setText("Seleccionar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGuiasLayout = new javax.swing.GroupLayout(panelGuias.getContentPane());
        panelGuias.getContentPane().setLayout(panelGuiasLayout);
        panelGuiasLayout.setHorizontalGroup(
            panelGuiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGuiasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGuiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGuiasLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelGuiasLayout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addGap(36, 36, 36))))
        );
        panelGuiasLayout.setVerticalGroup(
            panelGuiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGuiasLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelGuiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInicio.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(102, 153, 255));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("INICIAR SESION");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(225, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addGap(217, 217, 217))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addGap(17, 17, 17))
        );

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, -30, 620, 100));

        jLabel49.setText("Escriba su ID");
        jPanel11.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));
        jPanel11.add(txtIDsesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 170, -1));

        jLabel50.setText("Escriba su contraseña");
        jPanel11.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        txtContrasesion.setText("jPasswordField1");
        jPanel11.add(txtContrasesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 170, -1));

        jButton11.setBackground(new java.awt.Color(51, 153, 255));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton11.setText("Aceptar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, -1, -1));

        javax.swing.GroupLayout panelInicioLayout = new javax.swing.GroupLayout(panelInicio.getContentPane());
        panelInicio.getContentPane().setLayout(panelInicioLayout);
        panelInicioLayout.setHorizontalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
            .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
            .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelInicioLayout.createSequentialGroup()
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRegistro.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Nombres");

        jLabel3.setText("Numero de cedula");

        jLabel4.setText("Correo Electronico");
        jLabel4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jLabel4KeyTyped(evt);
            }
        });

        jLabel5.setText("Numero telefonico");

        jLabel6.setText("Edad");

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar-usuario-Jose.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("REGISTRAR USUARIO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel35.setText("Apellidos");

        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDKeyTyped(evt);
            }
        });

        txtEdad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEdadKeyTyped(evt);
            }
        });

        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoKeyTyped(evt);
            }
        });

        txtNTelefonico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNTelefonicoKeyTyped(evt);
            }
        });

        jLabel23.setText("Contraseña");

        txtContraseña.setText("jPasswordField1");

        jButton10.setBackground(new java.awt.Color(51, 51, 255));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Registrar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 51, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Iniciar Sesion");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRegistroLayout = new javax.swing.GroupLayout(panelRegistro);
        panelRegistro.setLayout(panelRegistroLayout);
        panelRegistroLayout.setHorizontalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelRegistroLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel35)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addGap(58, 58, 58)
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre)
                            .addComponent(txtApellido)
                            .addComponent(txtEdad)
                            .addComponent(txtCorreo)
                            .addComponent(txtNTelefonico)
                            .addComponent(txtContraseña)
                            .addComponent(txtID))))
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jButton2))
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton10)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(189, 189, 189))
        );
        panelRegistroLayout.setVerticalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(25, 25, 25)
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRegistroLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(111, 111, 111))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRegistroLayout.createSequentialGroup()
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel5))
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtNTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jButton2)))
                .addGap(21, 21, 21)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        tablaEleccion.addTab("Tab1", panelRegistro);

        panelUsuario.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setText("Nombres");

        jLabel25.setText("Numero de cedula");

        jLabel26.setText("Correo Electronico");
        jLabel26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jLabel26KeyTyped(evt);
            }
        });

        jLabel27.setText("Numero telefonico");

        jLabel28.setText("Edad");

        jPanel4.setBackground(new java.awt.Color(0, 153, 204));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar-usuario-Jose.png"))); // NOI18N

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("DATOS DE USUARIO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel44)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtNombreUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreUserKeyTyped(evt);
            }
        });

        txtApellidoUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoUserKeyTyped(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(51, 51, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Actualizar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel45.setText("Apellidos");

        txtIDUser.setEditable(false);
        txtIDUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDUserKeyTyped(evt);
            }
        });

        txtEdadUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEdadUserKeyTyped(evt);
            }
        });

        txtCorreoUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoUserKeyTyped(evt);
            }
        });

        txtNTelefonicoUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNTelefonicoUserKeyTyped(evt);
            }
        });

        jLabel46.setText("Contraseña");

        txtContraseñaUser.setText("jPasswordField1");

        javax.swing.GroupLayout panelUsuarioLayout = new javax.swing.GroupLayout(panelUsuario);
        panelUsuario.setLayout(panelUsuarioLayout);
        panelUsuarioLayout.setHorizontalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelUsuarioLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel45)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26))
                        .addGap(58, 58, 58)
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreUser)
                            .addComponent(txtApellidoUser)
                            .addComponent(txtEdadUser)
                            .addComponent(txtCorreoUser)
                            .addComponent(txtNTelefonicoUser)
                            .addComponent(txtContraseñaUser)
                            .addComponent(txtIDUser))))
                .addGap(50, 50, 50)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(171, 171, 171))
        );
        panelUsuarioLayout.setVerticalGroup(
            panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuarioLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(86, 86, 86)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addComponent(txtNombreUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellidoUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIDUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEdadUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(txtCorreoUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel27))
                    .addGroup(panelUsuarioLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtNTelefonicoUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(panelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtContraseñaUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 37, Short.MAX_VALUE))
        );

        tablaEleccion.addTab("Tab1", panelUsuario);

        panelPrecios.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 153, 204));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("DETALLES DE PRECIOS");

        jPanel7.setOpaque(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 86, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel21)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel21)
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BALLENA (1) (1).png"))); // NOI18N

        jPanel6.setOpaque(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 148, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 144, Short.MAX_VALUE)
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Precios de la semana");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("MARTES");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("LUNES");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("MIERCOLES");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("JUEVES");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("VIERNES");

        etiLunes.setEditable(false);
        etiLunes.setBackground(new java.awt.Color(255, 255, 255));
        etiLunes.setText("30 mil COP");
        etiLunes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etiLunesActionPerformed(evt);
            }
        });

        etiMartes.setEditable(false);
        etiMartes.setBackground(new java.awt.Color(255, 255, 255));
        etiMartes.setText("44 mil COP");

        etiMiercoles.setEditable(false);
        etiMiercoles.setBackground(new java.awt.Color(255, 255, 255));
        etiMiercoles.setText("35 mil COP");

        etiJueves.setEditable(false);
        etiJueves.setBackground(new java.awt.Color(255, 255, 255));
        etiJueves.setText("25 mil COP");

        etiJueves1.setEditable(false);
        etiJueves1.setBackground(new java.awt.Color(255, 255, 255));
        etiJueves1.setText("49 mil COP");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ofertas por hora de entrada");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("8:00 AM – 09:30 AM --");

        txtDescuento1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDescuento1.setForeground(new java.awt.Color(255, 255, 255));
        txtDescuento1.setText("50%");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("10:00 AM – 11:30 AM --");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("25%");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("02:00 PM – 03:30 PM --");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("30%");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel18))
                                        .addGap(31, 31, 31))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addGap(48, 48, 48)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(etiMiercoles)
                                    .addComponent(etiMartes)
                                    .addComponent(etiJueves)
                                    .addComponent(etiLunes)
                                    .addComponent(etiJueves1))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(110, 110, 110))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(88, 88, 88))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel34))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(18, 18, 18)
                                .addComponent(txtDescuento1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(etiLunes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(etiMartes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(etiMiercoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(etiJueves, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(etiJueves1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))))
                .addGap(49, 49, 49)
                .addComponent(jLabel7)
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtDescuento1))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel20))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel5);

        javax.swing.GroupLayout panelPreciosLayout = new javax.swing.GroupLayout(panelPrecios);
        panelPrecios.setLayout(panelPreciosLayout);
        panelPreciosLayout.setHorizontalGroup(
            panelPreciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPreciosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(322, 322, 322))
            .addGroup(panelPreciosLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelPreciosLayout.setVerticalGroup(
            panelPreciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPreciosLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaEleccion.addTab("Tab1", panelPrecios);

        panelComprar.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(0, 153, 204));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cita-Jose.png"))); // NOI18N

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("AGENDA TU VISITA");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton5.setBackground(new java.awt.Color(204, 204, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setText("Cancelar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(51, 51, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Comprar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel36.setText("Numero de acompañantes");

        jLabel37.setText("Tipo de entrada");

        jLabel38.setText("Fecha de Ingreso");

        jLabel39.setText("Hora de Ingreso");

        comboTipoEntrada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Estandar", "VIP" }));

        comboHoraIngreso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "08:00 AM – 09:30 AM", "10:00 AM – 11:30 AM", "12:00 PM – 01:30 PM", "02:00 PM – 03:30 PM", "04:00 PM – 05:30 PM" }));

        btnCalcularPrecio.setBackground(new java.awt.Color(204, 204, 255));
        btnCalcularPrecio.setText("Calcular Precio");
        btnCalcularPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularPrecioActionPerformed(evt);
            }
        });

        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });

        jLabel40.setText("Seleccionar Guia");

        btnSeleccionar.setBackground(new java.awt.Color(0, 153, 204));
        btnSeleccionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSeleccionarMouseClicked(evt);
            }
        });
        btnSeleccionar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Seleccionar");
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });
        btnSeleccionar.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 5, 70, 20));

        txtNombreGuia.setEditable(false);
        txtNombreGuia.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreGuia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreGuiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelComprarLayout = new javax.swing.GroupLayout(panelComprar);
        panelComprar.setLayout(panelComprarLayout);
        panelComprarLayout.setHorizontalGroup(
            panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComprarLayout.createSequentialGroup()
                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelComprarLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(panelComprarLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38)
                            .addComponent(jLabel39)
                            .addComponent(btnCalcularPrecio)
                            .addComponent(jLabel40))
                        .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelComprarLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNumAcompanantes, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboTipoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboHoraIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelComprarLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(165, 165, 165))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelComprarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtNombreGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(193, 193, 193))
        );
        panelComprarLayout.setVerticalGroup(
            panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComprarLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelComprarLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(txtNumAcompanantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelComprarLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelComprarLayout.createSequentialGroup()
                                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboTipoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel37))
                                .addGap(18, 18, 18)
                                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38))
                                .addGap(18, 18, 18)
                                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39)
                                    .addComponent(comboHoraIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelComprarLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(panelComprarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCalcularPrecio)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        tablaEleccion.addTab("Tab1", panelComprar);

        panelAgendas.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(0, 153, 204));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar-usuario-Jose.png"))); // NOI18N

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("TUS AGENDAS");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton7.setBackground(new java.awt.Color(204, 204, 255));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setText("Cancelar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(51, 51, 255));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Aceptar");

        tableAgendas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableAgendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Fecha", "ID", "N° Acompañantes", "Tipo", "Hora", "Guia", "Costo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableAgendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAgendasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableAgendas);

        javax.swing.GroupLayout panelAgendasLayout = new javax.swing.GroupLayout(panelAgendas);
        panelAgendas.setLayout(panelAgendasLayout);
        panelAgendasLayout.setHorizontalGroup(
            panelAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAgendasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(310, 310, 310))
            .addGroup(panelAgendasLayout.createSequentialGroup()
                .addGroup(panelAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAgendasLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAgendasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAgendasLayout.setVerticalGroup(
            panelAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgendasLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaEleccion.addTab("Tab1", panelAgendas);

        jPanel2.add(tablaEleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, -38, 700, 470));

        panelBotones.setBackground(new java.awt.Color(51, 255, 255));
        panelBotones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUsuario.setBackground(new java.awt.Color(0, 153, 204));
        btnUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUsuarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUsuarioMousePressed(evt);
            }
        });
        btnUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tu Usuario");
        btnUsuario.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        panelBotones.add(btnUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 230, 140, 45));

        btnPrecios.setBackground(new java.awt.Color(0, 153, 204));
        btnPrecios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPreciosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPreciosMousePressed(evt);
            }
        });
        btnPrecios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Precios");
        btnPrecios.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 10, 60, -1));

        panelBotones.add(btnPrecios, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 270, 140, 45));

        btnComprar.setBackground(new java.awt.Color(0, 153, 204));
        btnComprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComprarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnComprarMousePressed(evt);
            }
        });
        btnComprar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Comprar Tickets");
        btnComprar.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 10, -1, -1));

        panelBotones.add(btnComprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 310, 140, 45));

        btnAgendas.setBackground(new java.awt.Color(0, 153, 204));
        btnAgendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgendasMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAgendasMousePressed(evt);
            }
        });
        btnAgendas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tus Agendas");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        btnAgendas.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        panelBotones.add(btnAgendas, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 350, 140, 45));

        btnSalir.setBackground(new java.awt.Color(0, 153, 204));
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });
        btnSalir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Salir");
        btnSalir.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 10, -1, -1));

        panelBotones.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 390, 140, 45));

        btnRegistrar.setBackground(new java.awt.Color(0, 153, 204));
        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistrarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRegistrarMousePressed(evt);
            }
        });
        btnRegistrar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Registro");
        btnRegistrar.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        panelBotones.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 190, 140, 45));

        jPanel8.setOpaque(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        panelBotones.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 90));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 562, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarMouseClicked
tablaEleccion.setSelectedIndex(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarMouseClicked

    private void btnUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUsuarioMouseClicked
if (!botonesHabilitados) return;

        pasarDatosAActualizar();
        tablaEleccion.setSelectedIndex(1);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuarioMouseClicked

    private void btnPreciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreciosMouseClicked
if (!botonesHabilitados) return;
        tablaEleccion.setSelectedIndex(2);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPreciosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
txtNombre.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
txtApellido.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyTyped
txtID.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                char c = evt.getKeyChar();
                
                if (!Character.isDigit(c) || txtID.getText().length() >= 10) {
                    evt.consume();
                    soloNumeros(evt);
                }  
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDKeyTyped

    private void jLabel4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel4KeyTyped

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4KeyTyped

    private void txtNTelefonicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNTelefonicoKeyTyped
txtNTelefonico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloNumeros(evt);
                
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNTelefonicoKeyTyped

    private void txtCorreoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyTyped

        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoKeyTyped

    private void jLabel26KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel26KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel26KeyTyped

    private void txtNombreUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreUserKeyTyped
     txtNombreUser.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreUserKeyTyped

    private void txtApellidoUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoUserKeyTyped
    txtNombreUser.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloLetras(evt);
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoUserKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtIDUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDUserKeyTyped
txtIDUser.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                char c = evt.getKeyChar();
                
                if (!Character.isDigit(c) || txtID.getText().length() >= 10) {
                    evt.consume();
                    soloNumeros(evt);
                }  
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDUserKeyTyped

    private void txtCorreoUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoUserKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoUserKeyTyped

    private void txtNTelefonicoUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNTelefonicoUserKeyTyped
txtNTelefonicoUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt){
                soloNumeros(evt);
                
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNTelefonicoUserKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
actualizarVisitantes();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtEdadUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEdadUserKeyTyped
txtEdadUser.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                char c = evt.getKeyChar();
                
                if (!Character.isDigit(c) || txtID.getText().length() > 18) {
                    evt.consume();
                    JOptionPane.showMessageDialog(null, "Debes tener 18 años o mas");
                }  
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEdadUserKeyTyped

    private void txtEdadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEdadKeyTyped
txtEdad.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt){
                char c = evt.getKeyChar();
                
                if (!Character.isDigit(c) || txtID.getText().length() > 18) {
                    evt.consume();
                    JOptionPane.showMessageDialog(null, "Debes tener 18 años o mas");
                }  
            }
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEdadKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tableAgendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAgendasMouseClicked
       
    }//GEN-LAST:event_tableAgendasMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
panelInicio.setSize(543, 350);
panelInicio.setLocationRelativeTo(null);
panelInicio.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSalirMouseClicked

    private void etiLunesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etiLunesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_etiLunesActionPerformed

    private void btnSeleccionarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeleccionarMouseClicked
cargarTablaGuias();
panelGuias.setSize(543, 300);
panelGuias.setLocationRelativeTo(null);
panelGuias.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSeleccionarMouseClicked

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void btnCalcularPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularPrecioActionPerformed
calcularPrecio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalcularPrecioActionPerformed

    private void btnComprarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprarMouseClicked
if (!botonesHabilitados) return;
        tablaEleccion.setSelectedIndex(3);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnComprarMouseClicked

    private void tableGuiasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGuiasMouseClicked
int filaSeleccionada = tableGuias.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = tableGuias.getValueAt(filaSeleccionada, 0).toString();  // Columna 0 = Nombre
            String apellido = tableGuias.getValueAt(filaSeleccionada, 1).toString(); // Columna 1 = Apellido

            txtNombreGuia.setText(nombre+" "+apellido);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tableGuiasMouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel43MouseClicked

    private void txtNombreGuiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreGuiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreGuiaActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
 
 panelGuias.setVisible(false);

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
tablaEleccion.setSelectedIndex(4);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
guardarAgendaVisitantes();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnRegistrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarMousePressed

    private void btnUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUsuarioMousePressed
if (!botonesHabilitados) return;

        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuarioMousePressed

    private void btnPreciosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreciosMousePressed
if (!botonesHabilitados) return;
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPreciosMousePressed

    private void btnComprarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprarMousePressed
if (!botonesHabilitados) return;
        // TODO add your handling code here:
    }//GEN-LAST:event_btnComprarMousePressed

    private void btnAgendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgendasMouseClicked
List<Agendas> todas = daoA.obtenerTodas(); // daoA = instancia de AgendasDAO
        mostrarAgendasEnTabla(todas);
tablaEleccion.setSelectedIndex(4);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgendasMouseClicked

    private void btnAgendasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgendasMousePressed
List<Agendas> todas = daoA.obtenerTodas(); // daoA = instancia de AgendasDAO
        mostrarAgendasEnTabla(todas);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgendasMousePressed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
guardarRegistroVisitantes();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
iniciarSesionVisitante();
panelInicio.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroVisitantes1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroVisitantes1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroVisitantes1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVisitantes1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroVisitantes1().setVisible(true);
            }
        });
    }

    
    

public class PanelConImagen extends JPanel {
    private Image imagen;

    public PanelConImagen(String ruta) {
        this.imagen = new ImageIcon(getClass().getResource(ruta)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Escalar imagen al tamaño del panel
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAgendas;
    private javax.swing.JButton btnCalcularPrecio;
    private javax.swing.JPanel btnComprar;
    private javax.swing.JPanel btnPrecios;
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JPanel btnSeleccionar;
    private javax.swing.JPanel btnUsuario;
    private javax.swing.JComboBox<String> comboHoraIngreso;
    private javax.swing.JComboBox<String> comboTipoEntrada;
    private javax.swing.JTextField etiJueves;
    private javax.swing.JTextField etiJueves1;
    private javax.swing.JTextField etiLunes;
    private javax.swing.JTextField etiMartes;
    private javax.swing.JTextField etiMiercoles;
    private com.toedter.calendar.JDateChooser fechaIngreso;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel panelAgendas;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelComprar;
    private javax.swing.JDialog panelGuias;
    private javax.swing.JDialog panelInicio;
    private javax.swing.JPanel panelPrecios;
    private javax.swing.JPanel panelRegistro;
    private javax.swing.JPanel panelUsuario;
    private javax.swing.JTabbedPane tablaEleccion;
    private javax.swing.JTable tableAgendas;
    private javax.swing.JTable tableGuias;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtApellidoUser;
    private javax.swing.JPasswordField txtContrasesion;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JPasswordField txtContraseñaUser;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtCorreoUser;
    private javax.swing.JLabel txtDescuento1;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtEdadUser;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDUser;
    private javax.swing.JTextField txtIDsesion;
    private javax.swing.JTextField txtNTelefonico;
    private javax.swing.JTextField txtNTelefonicoUser;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreGuia;
    private javax.swing.JTextField txtNombreUser;
    private javax.swing.JTextField txtNumAcompanantes;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
