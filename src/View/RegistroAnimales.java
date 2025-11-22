/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.Informes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.InformesDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Gercray
 */
public class RegistroAnimales extends javax.swing.JFrame {

    private Stack<Informes> pilaEliminacion = new Stack<>();

    /**
     * Creates new form RegistoAnimales
     */
    public RegistroAnimales() {
        initComponents();
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.setDefaultEditor(Object.class, null);
        cargarTabla();
        AplicarColor();
    }

    public void cargarTabla() {

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{
            "Fecha", "Especie", "ID", "Veterinario", "Edad", "Lesión",
            "Estado", "Tiempo Recuperación", "Medicamento",
            "Dosis", "Administración", "Duración Tratamiento"
        });

        jTable.setModel(modelo);

        InformesDAO dao = new InformesDAO();
        List<Informes> lista = dao.cargarRegistros();

        for (Informes i : lista) {
            modelo.addRow(new Object[]{
                i.getFecha(),
                i.getEspecie(),
                i.getIdAnimal(),
                i.getVeterinario(),
                i.getEdad(),
                i.getLesion(),
                i.getEstado(),
                i.getTiempoRecuperacion(),
                i.getMedicamento(),
                i.getDosis(),
                i.getAdministracion(),
                i.getDuracionTratamiento()
            });
        }
        jTable.setModel(modelo);
    }

    public void AplicarColor() {
        jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Color colorPar = new Color(243, 255, 245);
                Color colorImpar = new Color(230, 245, 235);
                Color colorVencido = new Color(255, 61, 100);
                Color colorSeleccion = new Color(51, 153, 255);

                try {
                    String fechaRecuperacionStr = table.getValueAt(row, 7).toString();
                    Date fechaRecuperacion = new SimpleDateFormat("yyyy-MM-dd").parse(fechaRecuperacionStr);
                    Date hoy = new Date();

                    if (fechaRecuperacion.before(hoy)) {
                        c.setBackground(colorVencido);
                    } else {
                        if (row % 2 == 0) {
                            c.setBackground(colorPar);
                        } else {
                            c.setBackground(colorImpar);
                        }
                    }

                } catch (Exception e) {
                    if (row % 2 == 0) {
                        c.setBackground(colorPar);
                    } else {
                        c.setBackground(colorImpar);
                    }
                }

                return c;
            }
        });
        Color colorBase = new Color(243, 255, 245);

        jTable.setBackground(colorBase);

        jTable.setOpaque(false);

        jScrollPane1.getViewport().setBackground(colorBase);

        jScrollPane1.setBackground(colorBase);

        JTableHeader header = jTable.getTableHeader();
        header.setBackground(new Color(30, 144, 255));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));

    }

    private void buscarRegistro() {
        String id = txtBusqueda.getText().trim();

        if (id.isEmpty()) {
            cargarTabla();
            return;
        }

        InformesDAO dao = new InformesDAO();
        List<Informes> lista = dao.buscarPorIdAnimal(id);

        DefaultTableModel modelo = (DefaultTableModel) jTable.getModel();
        modelo.setRowCount(0);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontró un informe con ese ID.");
            return;
        }

        for (Informes i : lista) {
            modelo.addRow(new Object[]{
                i.getFecha(),
                i.getEspecie(),
                i.getIdAnimal(),
                i.getVeterinario(),
                i.getEdad(),
                i.getLesion(),
                i.getEstado(),
                i.getTiempoRecuperacion(),
                i.getMedicamento(),
                i.getDosis(),
                i.getAdministracion(),
                i.getDuracionTratamiento()
            });
        }
    }

    private void eliminarInforme() {
        int filaSeleccionada = jTable.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro para eliminar");
            return;
        }

        String idAnimal = jTable.getValueAt(filaSeleccionada, 2).toString();

        InformesDAO dao = new InformesDAO();
        Informes eliminado = dao.eliminarPorIdAnimal(idAnimal);

        if (eliminado != null) {
            pilaEliminacion.push(eliminado);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Registro eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        botonRegresar = new javax.swing.JLabel();
        btEliminar = new javax.swing.JButton();
        btOrganizar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        btnRestaurar = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(243, 255, 245));
        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

        jTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jTable.setModel(new DefaultTableModel());
        jTable.setGridColor(new java.awt.Color(255, 255, 255));
        jTable.setRowHeight(30);
        jTable.setRowMargin(2);
        jTable.setSelectionBackground(new java.awt.Color(102, 102, 255));
        jTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("REGISTRO DE DIAGNOSTICO DE ANIMALES");

        botonRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Devolver(Jose) (2).png"))); // NOI18N
        botonRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegresarMouseClicked(evt);
            }
        });

        btEliminar.setBackground(new java.awt.Color(76, 175, 80));
        btEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btEliminar.setText("ELIMINAR");
        btEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEliminarActionPerformed(evt);
            }
        });

        btOrganizar.setBackground(new java.awt.Color(76, 175, 80));
        btOrganizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btOrganizar.setForeground(new java.awt.Color(255, 255, 255));
        btOrganizar.setText("Organizar");
        btOrganizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btOrganizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOrganizarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Busqueda por ID ");

        txtBusqueda.setBackground(new java.awt.Color(60, 140, 64));
        txtBusqueda.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        txtBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        btnRestaurar.setBackground(new java.awt.Color(76, 175, 80));
        btnRestaurar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRestaurarMouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("RESTAURAR");

        javax.swing.GroupLayout btnRestaurarLayout = new javax.swing.GroupLayout(btnRestaurar);
        btnRestaurar.setLayout(btnRestaurarLayout);
        btnRestaurarLayout.setHorizontalGroup(
            btnRestaurarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
            .addGroup(btnRestaurarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnRestaurarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel26)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnRestaurarLayout.setVerticalGroup(
            btnRestaurarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(btnRestaurarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnRestaurarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel26)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Buscar(Jose) (2).jpg"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1520, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(354, 354, 354)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(btEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165)
                .addComponent(btOrganizar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(160, 160, 160)
                .addComponent(btnRestaurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btOrganizar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnRestaurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegresarMouseClicked
        new Monitoreo().setVisible(true);
        dispose();
    }//GEN-LAST:event_botonRegresarMouseClicked

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased

        if (txtBusqueda.getText().trim().isEmpty()) {
            cargarTabla();
        }
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            buscarRegistro();
        }
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void btnRestaurarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRestaurarMouseClicked

        if (pilaEliminacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La pila está vacía", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Informes informeRestaurado = pilaEliminacion.pop();

        InformesDAO dao = new InformesDAO();
        dao.guardarInforme(informeRestaurado);

        cargarTabla();

        JOptionPane.showMessageDialog(this, "El informe del animal con ID " + informeRestaurado.getIdAnimal() + " fue restaurado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_btnRestaurarMouseClicked

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        buscarRegistro();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void btEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEliminarActionPerformed
        eliminarInforme();
    }//GEN-LAST:event_btEliminarActionPerformed

    private void btOrganizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOrganizarActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) jTable.getModel();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Lista vacía. No hay registros para ordenar", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Object[]> filas = new ArrayList<>();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object[] fila = new Object[modelo.getColumnCount()];
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fila[j] = modelo.getValueAt(i, j);
            }
            filas.add(fila);
        }

        filas.sort((a, b) -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date f1 = sdf.parse(a[0].toString());
                Date f2 = sdf.parse(b[0].toString());
                return f1.compareTo(f2);
            } catch (Exception e) {
                return 0;
            }
        });

        modelo.setRowCount(0);
        for (Object[] fila : filas) {
            modelo.addRow(fila);
        }

        JOptionPane.showMessageDialog(this, "Registros ordenados por fecha", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btOrganizarActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroAnimales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroAnimales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroAnimales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroAnimales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroAnimales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botonRegresar;
    private javax.swing.JButton btEliminar;
    private javax.swing.JButton btOrganizar;
    private javax.swing.JPanel btnRestaurar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
