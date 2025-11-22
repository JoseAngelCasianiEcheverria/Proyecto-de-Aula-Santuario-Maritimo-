/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.InformeZona;
import dao.InformeZonaDAO;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Gercray
 */
public class MonitoreoZonas extends javax.swing.JFrame {

    public MonitoreoZonas() {
        initComponents();
        setLocationRelativeTo(null);
        ((JTextField) txtFielTiempoRecuperacion.getDateEditor().getUiComponent()).setEditable(false);
        setResizable(false);
        DMAH();

        txtFTipoIncidente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                soloLetras(evt);
            }
        });

        txtFTipoIAfectacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                soloLetras(evt);
            }
        });

        txtFEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                soloLetras(evt);
            }
        });

        txtReportado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                soloLetras(evt);
            }
        });

        txtFNumeroZ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                soloNumeros(evt);

            }
        });

        txtFTiempoDet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                soloNumeros(evt);

            }
        });

        FGravedad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                soloNumeros(evt);

            }
        });

    }

    private void soloLetras(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && c != ' ' && c != '\b') {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo se permiten LETRAS", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void soloNumeros(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '\b') {
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo se permiten NUMEROS", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtFNumeroZ.setText("");
        txtFTipoIncidente.setText("");
        FGravedad.setText("");
        txtFielTiempoRecuperacion.setDate(null);
        txtFTipoIAfectacion.setText("");
        txtFEstado.setText("");
        txtReportado.setText("");
    }

    public void aplicarPlaceholder(JTextField campo, String placeholder) {

        campo.setForeground(Color.GRAY);
        campo.setText(placeholder);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setForeground(Color.GRAY);
                    campo.setText(placeholder);
                }
            }
        });
    }

    private void DMAH() {
        LocalDateTime horaActual = LocalDateTime.now();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        txtFTiempoDet.setText(horaActual.format(formato));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtFNumeroZ = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFTiempoDet = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFTipoIncidente = new javax.swing.JTextField();
        FGravedad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFTipoIAfectacion = new javax.swing.JTextField();
        txtEstado = new javax.swing.JLabel();
        txtFEstado = new javax.swing.JTextField();
        AccionesF = new javax.swing.JComboBox<>();
        txtEstado1 = new javax.swing.JLabel();
        txtReportado = new javax.swing.JTextField();
        botonRegresar = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btLimpiar = new javax.swing.JButton();
        txtFielTiempoRecuperacion = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(31, 42, 68));
        jPanel1.setForeground(new java.awt.Color(31, 42, 68));

        jPanel2.setBackground(new java.awt.Color(203, 213, 225));
        jPanel2.setForeground(new java.awt.Color(31, 42, 68));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("MONITOREO DE ZONAS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Numero de Zona:");

        txtFNumeroZ.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Tiempo de Detección:");

        txtFTiempoDet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Tipo de Incidente:");

        txtFTipoIncidente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        FGravedad.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Gravedad del Incidente del 1 al 10:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Tiempo Estimado de Restauracion:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Tipo de Afectación:");

        txtFTipoIAfectacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtEstado.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtEstado.setForeground(new java.awt.Color(204, 204, 204));
        txtEstado.setText("Estado Actual del Incidente:");

        txtFEstado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        AccionesF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AccionesF.setForeground(new java.awt.Color(31, 42, 68));
        AccionesF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Accidentes Comunes", "Accidentes por sustancias o materiales", "Accidentes con fauna marina", "Accidentes del personal", "Accidentes ambientales", "Accidentes con visitantes" }));
        AccionesF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccionesFActionPerformed(evt);
            }
        });

        txtEstado1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtEstado1.setForeground(new java.awt.Color(204, 204, 204));
        txtEstado1.setText("Reportado por:");

        txtReportado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        botonRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BOTON_REGRESAR (1) (1).png"))); // NOI18N
        botonRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegresarMouseClicked(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(45, 212, 191));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(31, 42, 68));
        jButton1.setText("Imprimir");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btLimpiar.setBackground(new java.awt.Color(45, 212, 191));
        btLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btLimpiar.setForeground(new java.awt.Color(31, 42, 68));
        btLimpiar.setText("Limpiar");
        btLimpiar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimpiarActionPerformed(evt);
            }
        });

        txtFielTiempoRecuperacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFielTiempoRecuperacion.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botonRegresar)
                        .addGap(244, 244, 244)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFNumeroZ, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFTipoIncidente, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFTiempoDet, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(156, 156, 156)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtReportado, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtFTipoIAfectacion))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtFEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFielTiempoRecuperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AccionesF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(FGravedad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(88, 88, 88)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(btLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonRegresar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFNumeroZ, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtFielTiempoRecuperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFTiempoDet, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFTipoIAfectacion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFTipoIncidente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FGravedad, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReportado, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AccionesF, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(114, 114, 114))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AccionesFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccionesFActionPerformed
        //PONEMOS QUE NO PUEDAN SELECCIONAR LA PRIMERA OPCION Y QUEDE COMO UN TITULO POR DECIRLO ASI
        int index = AccionesF.getSelectedIndex();
        if (index == 0) {
            return;
        }

        String seleccionado = (String) AccionesF.getSelectedItem();

        //LIMPIAMOS
        switch (seleccionado) {
            case "Accidentes por sustancias o materiales":
                txtFTipoIncidente.setText("Accidentes por sustancias o materiales");
                FGravedad.setText("5");
                txtFTipoIAfectacion.setText("Física / Humana");
                break;

            case "Accidentes con fauna marina":
                txtFTipoIncidente.setText("Accidentes con fauna marina");
                FGravedad.setText("6");
                txtFTipoIAfectacion.setText("Física / Fauna");
                break;

            case "Accidentes del personal":
                txtFTipoIncidente.setText("Accidentes del personal");
                FGravedad.setText("5");
                txtFTipoIAfectacion.setText("Física / Humana / Operativa");
                break;

            case "Accidentes ambientales":
                txtFTipoIncidente.setText("Accidentes ambientales");
                FGravedad.setText("6");
                txtFTipoIAfectacion.setText("Ambiental / Infraestructura / Operativa");
                break;

            case "Accidentes con visitantes":
                txtFTipoIncidente.setText("Accidentes con visitantes");
                FGravedad.setText("6/10");
                txtFTipoIAfectacion.setText("Física / Humana / Ambiental");
                break;
        }
    }//GEN-LAST:event_AccionesFActionPerformed

    private void botonRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegresarMouseClicked
        new Monitoreo().setVisible(true);
        dispose();
    }//GEN-LAST:event_botonRegresarMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        InformeZonaDAO dao = new InformeZonaDAO();
        int idNum;

        String numeroZ = txtFNumeroZ.getText().trim();
        String tiempoDet = txtFTiempoDet.getText().trim();
        String tipoIncidente = txtFTipoIncidente.getText().trim();
        String gravedad = FGravedad.getText().trim();
        String TiempoRecuperacion = (txtFielTiempoRecuperacion.getDate() != null) ? new SimpleDateFormat("yyyy-MM-dd").format(txtFielTiempoRecuperacion.getDate()) : "";
        String tipoAfectacion = txtFTipoIAfectacion.getText().trim();
        String estado = txtFEstado.getText().trim();
        String reportado = txtReportado.getText().trim();

        if (numeroZ.isEmpty() || tiempoDet.isEmpty() || tipoIncidente.isEmpty()
                || gravedad.isEmpty() || TiempoRecuperacion.isEmpty() || tipoAfectacion.isEmpty()
                || estado.isEmpty() || reportado.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            idNum = Integer.parseInt(numeroZ);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "debe ser numérico", "Warning", JOptionPane.WARNING_MESSAGE);
            txtFNumeroZ.requestFocus();
            return;
        }

        if (idNum < 1 || idNum > 10) {
            JOptionPane.showMessageDialog(this, "Solo existen 10 zonas (1 a 10)", "Warning", JOptionPane.WARNING_MESSAGE);
            txtFNumeroZ.requestFocus();
            return;
        }

        if (!gravedad.matches("\\d+") || Integer.parseInt(gravedad) > 10) {
            JOptionPane.showMessageDialog(this, "La gravedad debe ser un número entre 0 y 10",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            FGravedad.requestFocus();
            return;
        }

        InformeZona informe = new InformeZona(
                numeroZ,
                tiempoDet,
                tipoIncidente,
                gravedad,
                TiempoRecuperacion,
                tipoAfectacion,
                estado,
                reportado
        );

        dao.guardarInforme(informe);

        JOptionPane.showMessageDialog(this, "Informe guardado correctamente");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(MonitoreoZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonitoreoZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonitoreoZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonitoreoZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MonitoreoZonas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AccionesF;
    private javax.swing.JTextField FGravedad;
    private javax.swing.JLabel botonRegresar;
    private javax.swing.JButton btLimpiar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel txtEstado;
    private javax.swing.JLabel txtEstado1;
    private javax.swing.JTextField txtFEstado;
    private javax.swing.JTextField txtFNumeroZ;
    private javax.swing.JTextField txtFTiempoDet;
    private javax.swing.JTextField txtFTipoIAfectacion;
    private javax.swing.JTextField txtFTipoIncidente;
    private com.toedter.calendar.JDateChooser txtFielTiempoRecuperacion;
    private javax.swing.JTextField txtReportado;
    // End of variables declaration//GEN-END:variables
}
