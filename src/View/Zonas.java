/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import View.RegistroVisitantes1;

/**
 *
 * @author Gercray
 */
public class Zonas extends javax.swing.JFrame {

    private String textozona1I = "Los asistentes podrán observar el comportamiento natural de peces, "
        + "mantarrayas y otras especies en un gran ventanal panorámico submarino. "
        + "No se interactúa directamente con los animales, pero sí se aprende mediante paneles explicativos y guías ocasionales.";

private String textozona2I = "Aprende cómo se restauran los arrecifes ayudando a colocar "
        + "fragmentos de coral en estructuras simuladas, guiado por expertos marinos.";

private String textozona3I = "Los visitantes podrán interactuar directamente con especies marinas seleccionadas, "
        + "como estrellas de mar, erizos y pepinos de mar, bajo la supervisión del personal. "
        + "Se fomenta el contacto respetuoso con los animales, usando únicamente dos dedos y sin sacarlos del agua.";
    

    
    public Zonas() {
        initComponents();
        this.setResizable(false);
        setLocationRelativeTo(null);
    }

    public String StrtoHtml(String texto){
        return "<html><p>" + texto + "</p></html>";
    }  
    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Zona3 = new javax.swing.JPanel();
        PanelTituloZona3 = new javax.swing.JPanel();
        labelTituloZon3 = new javax.swing.JLabel();
        imgZona3 = new javax.swing.JLabel();
        comboZona3 = new javax.swing.JComboBox<>();
        FielHorarioZona3 = new javax.swing.JTextField();
        PanelRZona3 = new javax.swing.JPanel();
        LabelReglaZona3 = new javax.swing.JLabel();
        labelR4 = new javax.swing.JLabel();
        labelR6 = new javax.swing.JLabel();
        labelR7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        INFORMACION = new javax.swing.JLabel();
        labelAZona3 = new javax.swing.JLabel();
        Zona2 = new javax.swing.JPanel();
        imgZona2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboZona4 = new javax.swing.JComboBox<>();
        FielHorarioZona4 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        labelAZona2 = new javax.swing.JLabel();
        Zona1 = new javax.swing.JPanel();
        imgZona1 = new javax.swing.JLabel();
        FielHorarioZona1 = new javax.swing.JTextField();
        PanelActividadZona1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        labelAZona1 = new javax.swing.JLabel();
        PanelReglasZona1 = new javax.swing.JPanel();
        LabelReglaZona1 = new javax.swing.JLabel();
        labelR1 = new javax.swing.JLabel();
        labelR2 = new javax.swing.JLabel();
        labelR3 = new javax.swing.JLabel();
        PanelTituloZona1 = new javax.swing.JPanel();
        labelTituloZona1 = new javax.swing.JLabel();
        ComboZona1 = new javax.swing.JComboBox<>();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(4);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(224, 247, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("Coralluna");

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jButton1.setText("Guardianes del Arrecife");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 204, 255));
        jButton2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jButton2.setText("Manos al Mar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(102, 204, 255));
        jButton3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jButton3.setText("Ventana del Océano");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(102, 204, 255));
        jButton4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jButton4.setText("REGRESAR");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Login1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 840));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Zonas del santuario ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 330, 70));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Fondo.jpg"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 840, 100));

        Zona3.setBackground(new java.awt.Color(204, 238, 255));

        PanelTituloZona3.setBackground(new java.awt.Color(204, 238, 255));

        labelTituloZon3.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        labelTituloZon3.setText("Manos al Mar");

        javax.swing.GroupLayout PanelTituloZona3Layout = new javax.swing.GroupLayout(PanelTituloZona3);
        PanelTituloZona3.setLayout(PanelTituloZona3Layout);
        PanelTituloZona3Layout.setHorizontalGroup(
            PanelTituloZona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTituloZona3Layout.createSequentialGroup()
                .addGap(278, 278, 278)
                .addComponent(labelTituloZon3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(317, 317, 317))
        );
        PanelTituloZona3Layout.setVerticalGroup(
            PanelTituloZona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTituloZona3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTituloZon3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        imgZona3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Manos al mar (2).png"))); // NOI18N
        imgZona3.setBorder(new javax.swing.border.MatteBorder(null));

        comboZona3.setBackground(new java.awt.Color(0, 119, 170));
        comboZona3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        comboZona3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Animales", "Estrellas de mar", "Pepinos de mar", "Caracoles y conchas vivas", "Erizos de mar", "Babosas marinas" }));

        FielHorarioZona3.setEditable(false);
        FielHorarioZona3.setBackground(new java.awt.Color(0, 119, 170));
        FielHorarioZona3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FielHorarioZona3.setText("9:30AM - 4:00PM");
        FielHorarioZona3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PanelRZona3.setBackground(new java.awt.Color(136, 204, 238));
        PanelRZona3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        LabelReglaZona3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        LabelReglaZona3.setText(" Reglas:");

        labelR4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        labelR4.setText("-Lavado de manos obligatorio antes de la actividad.");

        labelR6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        labelR6.setText("-Tocar solo con dos dedos.");

        labelR7.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        labelR7.setText("-No gritar ni hacer ruidos fuertes.");

        javax.swing.GroupLayout PanelRZona3Layout = new javax.swing.GroupLayout(PanelRZona3);
        PanelRZona3.setLayout(PanelRZona3Layout);
        PanelRZona3Layout.setHorizontalGroup(
            PanelRZona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRZona3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRZona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelR4, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                    .addGroup(PanelRZona3Layout.createSequentialGroup()
                        .addGroup(PanelRZona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelReglaZona3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelR6, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelR7, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        PanelRZona3Layout.setVerticalGroup(
            PanelRZona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRZona3Layout.createSequentialGroup()
                .addComponent(LabelReglaZona3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelR4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelR6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelR7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(136, 204, 238));

        INFORMACION.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        INFORMACION.setText("Información");

        labelAZona3.setBackground(new java.awt.Color(136, 204, 238));
        labelAZona3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(INFORMACION, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAZona3, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(INFORMACION, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAZona3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Zona3Layout = new javax.swing.GroupLayout(Zona3);
        Zona3.setLayout(Zona3Layout);
        Zona3Layout.setHorizontalGroup(
            Zona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Zona3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelTituloZona3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(Zona3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboZona3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(FielHorarioZona3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(290, 290, 290))
            .addGroup(Zona3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(PanelRZona3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(Zona3Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(imgZona3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Zona3Layout.setVerticalGroup(
            Zona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Zona3Layout.createSequentialGroup()
                .addComponent(PanelTituloZona3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgZona3, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Zona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FielHorarioZona3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboZona3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(Zona3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelRZona3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("tab3", Zona3);

        Zona2.setBackground(new java.awt.Color(204, 238, 255));

        imgZona2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Guardianes del arrecife (2).jpg"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel6.setText("Guardianes del Arrecife");

        comboZona4.setBackground(new java.awt.Color(0, 119, 170));
        comboZona4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        comboZona4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Animales", "peces", "erizos", " " }));

        FielHorarioZona4.setEditable(false);
        FielHorarioZona4.setBackground(new java.awt.Color(0, 119, 170));
        FielHorarioZona4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FielHorarioZona4.setText("9:30AM - 3:00PM");
        FielHorarioZona4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel6.setBackground(new java.awt.Color(136, 204, 238));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel11.setText("Reglas:");

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel12.setText("-No se permite extraer fragmentos del entorno.");

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel13.setText("-Manipular materiales solo bajo supervisión.");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel14.setText("-Usar guantes y equipo.");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(136, 204, 238));

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel9.setText("Actividad:");

        labelAZona2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAZona2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAZona2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Zona2Layout = new javax.swing.GroupLayout(Zona2);
        Zona2.setLayout(Zona2Layout);
        Zona2Layout.setHorizontalGroup(
            Zona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Zona2Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 257, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Zona2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(Zona2Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(comboZona4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addComponent(FielHorarioZona4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Zona2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgZona2, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        Zona2Layout.setVerticalGroup(
            Zona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Zona2Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgZona2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(Zona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FielHorarioZona4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboZona4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(Zona2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        jTabbedPane1.addTab("tab2", Zona2);

        Zona1.setBackground(new java.awt.Color(204, 238, 255));
        Zona1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imgZona1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Ventanadeloceano.jpg"))); // NOI18N
        imgZona1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Zona1.add(imgZona1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 750, 320));

        FielHorarioZona1.setEditable(false);
        FielHorarioZona1.setBackground(new java.awt.Color(0, 119, 170));
        FielHorarioZona1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FielHorarioZona1.setText("10:00AM - 4:00PM");
        FielHorarioZona1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Zona1.add(FielHorarioZona1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 190, 40));

        PanelActividadZona1.setBackground(new java.awt.Color(136, 204, 238));
        PanelActividadZona1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Actividad:");

        labelAZona1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        javax.swing.GroupLayout PanelActividadZona1Layout = new javax.swing.GroupLayout(PanelActividadZona1);
        PanelActividadZona1.setLayout(PanelActividadZona1Layout);
        PanelActividadZona1Layout.setHorizontalGroup(
            PanelActividadZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelActividadZona1Layout.createSequentialGroup()
                .addGroup(PanelActividadZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelActividadZona1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 290, Short.MAX_VALUE))
                    .addComponent(labelAZona1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelActividadZona1Layout.setVerticalGroup(
            PanelActividadZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelActividadZona1Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Zona1.add(PanelActividadZona1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 380, 220));

        PanelReglasZona1.setBackground(new java.awt.Color(136, 204, 238));
        PanelReglasZona1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        LabelReglaZona1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        LabelReglaZona1.setText(" Reglas:");

        labelR1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        labelR1.setText("-No golpear el vidrio.");

        labelR2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        labelR2.setText("-Evitar usar flash al tomar fotos.");

        labelR3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        labelR3.setText("-Mantener silencio para no alterar a la fauna marina.");

        javax.swing.GroupLayout PanelReglasZona1Layout = new javax.swing.GroupLayout(PanelReglasZona1);
        PanelReglasZona1.setLayout(PanelReglasZona1Layout);
        PanelReglasZona1Layout.setHorizontalGroup(
            PanelReglasZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelReglasZona1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelReglasZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelR3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelReglasZona1Layout.createSequentialGroup()
                        .addGroup(PanelReglasZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelR1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelReglaZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelR2, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelReglasZona1Layout.setVerticalGroup(
            PanelReglasZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelReglasZona1Layout.createSequentialGroup()
                .addComponent(LabelReglaZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelR1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelR2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelR3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Zona1.add(PanelReglasZona1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 480, 430, 220));

        PanelTituloZona1.setBackground(new java.awt.Color(204, 238, 255));

        labelTituloZona1.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        labelTituloZona1.setText("Ventana del Océano");

        javax.swing.GroupLayout PanelTituloZona1Layout = new javax.swing.GroupLayout(PanelTituloZona1);
        PanelTituloZona1.setLayout(PanelTituloZona1Layout);
        PanelTituloZona1Layout.setHorizontalGroup(
            PanelTituloZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTituloZona1Layout.createSequentialGroup()
                .addGap(272, 272, 272)
                .addComponent(labelTituloZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelTituloZona1Layout.setVerticalGroup(
            PanelTituloZona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelTituloZona1Layout.createSequentialGroup()
                .addComponent(labelTituloZona1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Zona1.add(PanelTituloZona1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 50));

        ComboZona1.setBackground(new java.awt.Color(0, 119, 170));
        ComboZona1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        ComboZona1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Animales", "Peces payaso", "Pez ángel", "Tortugas verdes", "Rayas pequeñas", " " }));
        ComboZona1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboZona1ActionPerformed(evt);
            }
        });
        Zona1.add(ComboZona1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 230, 40));

        jTabbedPane1.addTab("tab1", Zona1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 840, 780));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       labelAZona1.setText(StrtoHtml(textozona1I));
       jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        labelAZona3.setText(StrtoHtml(textozona3I));
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        labelAZona2.setText(StrtoHtml(textozona2I));
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ComboZona1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboZona1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboZona1ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        new RegistroVisitantes1().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4MouseClicked

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
            java.util.logging.Logger.getLogger(Zonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Zonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Zonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Zonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Zonas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboZona1;
    private javax.swing.JTextField FielHorarioZona1;
    private javax.swing.JTextField FielHorarioZona3;
    private javax.swing.JTextField FielHorarioZona4;
    private javax.swing.JLabel INFORMACION;
    private javax.swing.JLabel LabelReglaZona1;
    private javax.swing.JLabel LabelReglaZona3;
    private javax.swing.JPanel PanelActividadZona1;
    private javax.swing.JPanel PanelRZona3;
    private javax.swing.JPanel PanelReglasZona1;
    private javax.swing.JPanel PanelTituloZona1;
    private javax.swing.JPanel PanelTituloZona3;
    private javax.swing.JPanel Zona1;
    private javax.swing.JPanel Zona2;
    private javax.swing.JPanel Zona3;
    private javax.swing.JComboBox<String> comboZona3;
    private javax.swing.JComboBox<String> comboZona4;
    private javax.swing.JLabel imgZona1;
    private javax.swing.JLabel imgZona2;
    private javax.swing.JLabel imgZona3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelAZona1;
    private javax.swing.JLabel labelAZona2;
    private javax.swing.JLabel labelAZona3;
    private javax.swing.JLabel labelR1;
    private javax.swing.JLabel labelR2;
    private javax.swing.JLabel labelR3;
    private javax.swing.JLabel labelR4;
    private javax.swing.JLabel labelR6;
    private javax.swing.JLabel labelR7;
    private javax.swing.JLabel labelTituloZon3;
    private javax.swing.JLabel labelTituloZona1;
    // End of variables declaration//GEN-END:variables
}
