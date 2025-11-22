/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.InformeZona;
import dao.InformeZonaDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Gercray
 */
public class RegistroZonas extends javax.swing.JFrame {

    private Stack<InformeZona> pilaEliminacion = new Stack<>();

    public RegistroZonas() {
        initComponents();
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.setDefaultEditor(Object.class, null);
        cargarTabla();
        AplicarColor();
    }

    public void cargarTabla() {

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{
            "Número Zona",
            "Tiempo Detectado",
            "Tipo Incidente",
            "Gravedad",
            "Tiempo Recuperación",
            "Tipo Afectación",
            "Estado",
            "Reportado Por"
        });

        jTable.setModel(modelo);

        InformeZonaDAO dao = new InformeZonaDAO();
        List<InformeZona> lista = dao.cargarRegistros();

        for (InformeZona i : lista) {
            modelo.addRow(new Object[]{
                i.getNumeroZ(),
                i.getTiempoDet(),
                i.getTipoIncidente(),
                i.getGravedad(),
                i.getTiempoRecuperacion(),
                i.getTipoAfectacion(),
                i.getEstado(),
                i.getReportado()
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

                Color colorPar = new Color(233, 242, 255);
                Color colorImpar = new Color(255, 255, 255);
                Color colorVencido = new Color(255, 102, 102);
                Color seleccion = new Color(190, 214, 255);

                try {
                    String fechaRecuperacionStr = table.getValueAt(row, 4).toString();
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

        Color fondoGeneral = new Color(246, 247, 251);

        jTable.setBackground(fondoGeneral);
        jTable.setOpaque(false);

        jScrollPane1.getViewport().setBackground(fondoGeneral);
        jScrollPane1.setBackground(fondoGeneral);

        JTableHeader header = jTable.getTableHeader();
        header.setBackground(new Color(255, 255, 255));
        header.setForeground(new Color(44, 62, 80));
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBorder(BorderFactory.createLineBorder(new Color(208, 208, 208)));
    }

    private void buscarRegistro() {
        String numeroZ = txtBusqueda.getText().trim();

        if (numeroZ.isEmpty()) {
            cargarTabla();
            return;
        }

        InformeZonaDAO dao = new InformeZonaDAO();
        List<InformeZona> lista = dao.buscarPorNumeroZ(numeroZ);

        DefaultTableModel modelo = (DefaultTableModel) jTable.getModel();
        modelo.setRowCount(0);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontró ninguna zona con ese número.");
            return;
        }

        for (InformeZona i : lista) {
            modelo.addRow(new Object[]{
                i.getNumeroZ(),
                i.getTiempoDet(),
                i.getTipoIncidente(),
                i.getGravedad(),
                i.getTiempoRecuperacion(),
                i.getTipoAfectacion(),
                i.getEstado(),
                i.getReportado()
            });
        }
    }

    private void eliminarInforme() {
        int filaSeleccionada = jTable.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro para eliminar");
            return;
        }

        String numeroZ = jTable.getValueAt(filaSeleccionada, 0).toString();

        InformeZonaDAO dao = new InformeZonaDAO();
        InformeZona eliminado = dao.eliminarPorNumeroZ(numeroZ);

        if (eliminado != null) {
            pilaEliminacion.push(eliminado);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Registro eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar");
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

        jPanel1.setBackground(new java.awt.Color(246, 247, 251));
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
        jLabel1.setText("REGISTRO DE DIAGNOSTICO DE ZONAS");

        botonRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Devolversenegro(Jose) (2).png"))); // NOI18N
        botonRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegresarMouseClicked(evt);
            }
        });

        btEliminar.setBackground(new java.awt.Color(74, 144, 226));
        btEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btEliminar.setText("ELIMINAR");
        btEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEliminarActionPerformed(evt);
            }
        });

        btOrganizar.setBackground(new java.awt.Color(74, 144, 226));
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
        jLabel11.setText("Busqueda por numero de zona ");

        txtBusqueda.setBackground(new java.awt.Color(58, 120, 194));
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

        btnRestaurar.setBackground(new java.awt.Color(74, 144, 226));
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
            .addGroup(btnRestaurarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel26)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        btnRestaurarLayout.setVerticalGroup(
            btnRestaurarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnRestaurarLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel26)
                .addContainerGap(17, Short.MAX_VALUE))
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
                        .addComponent(botonRegresar)
                        .addGap(380, 380, 380)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(btEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)
                        .addComponent(btOrganizar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 138, 138)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(121, 121, 121)
                        .addComponent(btnRestaurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(167, Short.MAX_VALUE))
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

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBusquedaActionPerformed

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

        InformeZona informeRestaurado = pilaEliminacion.pop();

        InformeZonaDAO dao = new InformeZonaDAO();
        dao.guardarInforme(informeRestaurado);

        cargarTabla();

        JOptionPane.showMessageDialog(this, "El informe de la zona " + informeRestaurado.getNumeroZ() + " fue restaurado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnRestaurarMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        buscarRegistro();
    }//GEN-LAST:event_jLabel2MouseClicked

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
            java.util.logging.Logger.getLogger(RegistroZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroZonas().setVisible(true);
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
