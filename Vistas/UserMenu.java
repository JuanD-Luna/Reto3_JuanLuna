package Vistas;

import Controlador.*;
import Modelo.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UserMenu extends javax.swing.JFrame {
    
    Conexion con = new Conexion();
    Connection cn;
    //Esta libreria Statement nos permite ejecutar codigo Query SQL
    Statement st;
    ResultSet rs;
    //Vamos a crear una instancia de la tabla que creamos para usarla en nustra interfaz
    DefaultTableModel contenidoTablaEmpleados;
    DefaultTableModel contenidoTablaDepartamentos;
    ComboBoxModel enumDepartamentos, enumZona, enumTipoCalle;
   
    public UserMenu() {
        enumDepartamentos = new DefaultComboBoxModel(EnumDepartamento.values());
        enumZona = new DefaultComboBoxModel(EnumZona.values());
        enumTipoCalle = new DefaultComboBoxModel(EnumTipoCalle.values());
                
        initComponents();
        setLocationRelativeTo(null);
        ListarEmpleados();
        ListarDepartamentos();
    }
    
    private void ListarDepartamentos() {
        
        String filtroDepartamento= txtSearchDepartamento.getText();
        
        if (filtroDepartamento.isEmpty()) {
        
            String quertyConsultaDepa = "SELECT nombreSucursal, nombreDepartamento FROM sucursal INNER JOIN direccion ON FK_idDireccion = idDireccion GROUP BY nombreDepartamento, nombreSucursal ORDER BY nombreDepartamento;";
        
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(quertyConsultaDepa);

                Object [] departamentos = new Object[2];

                contenidoTablaDepartamentos = (DefaultTableModel) tblDepartamentos.getModel();

                while (rs.next()) {

                    departamentos [0] = rs.getString("sucursal.nombreSucursal");
                    departamentos [1] = rs.getString("direccion.nombreDepartamento");
                    System.out.println("Sucursal: " + departamentos [0] + " Departamento: " + departamentos [1]);

                    contenidoTablaDepartamentos.addRow(departamentos);
                    tblDepartamentos.setModel(contenidoTablaDepartamentos);

                }

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(this, "No se pudo cargar los departamentos en la tabla", "" ,JOptionPane.ERROR_MESSAGE);
                System.out.println(e);

            }
        } else {
            
            String querty = "SELECT nombreSucursal, nombreDepartamento FROM `sucursal` INNER JOIN `direccion` ON FK_idDireccion = idDireccion AND nombreDepartamento LIKE '%" + filtroDepartamento + "%' GROUP BY nombreSucursal, nombreDepartamento ORDER BY nombreDepartamento;";
            
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(querty);

                Object [] departamentos = new Object[2];

                contenidoTablaDepartamentos = (DefaultTableModel) tblDepartamentos.getModel();

                while (rs.next()) {

                    departamentos [0] = rs.getString("sucursal.nombreSucursal");
                    departamentos [1] = rs.getString("direccion.nombreDepartamento");
                    System.out.println("Sucursal: " + departamentos [0] + " Departamento: " + departamentos [1]);

                    contenidoTablaDepartamentos.addRow(departamentos);
                    tblDepartamentos.setModel(contenidoTablaDepartamentos);

                }

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(this, "No se pudo cargar los departamentos en la tabla", "" ,JOptionPane.ERROR_MESSAGE);
                System.out.println(e);

            }
            
        }
        
    }
    
    // Vamos a crear una metodo que nos va a traer todos los empleados existentes en nustra base de datos
    private void ListarEmpleados () {
        
        String filtrobusqueda = txtsearch.getText();

        if (filtrobusqueda.isEmpty()) {
            String queryConsultaEmpleados = "SELECT * FROM empleado";

            //Vamos a ejecutar nuestro codigo query y obtener una respuesta de la base de datos
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(queryConsultaEmpleados);

                //Crearemos un objeto donde reciviremos el resultado de la consulta query
                Object[] empleados = new Object[6];

                // Actulizamos la definicionde DefaultModel de nustra tabla empleados.
                contenidoTablaEmpleados = (DefaultTableModel)tblEmpleados.getModel();

                //Vamoso a recorrer el resultado de nuestra consulta query
                while (rs.next()) {
                    empleados[0] = rs.getInt("idEmp");
                    empleados[1] = rs.getString("nombreEmp");
                    empleados[2] = rs.getString("apellidos");
                    empleados[3] = rs.getString("tipoDocumento");
                    empleados[4] = rs.getString("documento");
                    empleados[5] = rs.getString("correo");

                    //Creamos una fila en la tabla por cada empleado que nos devuelve el codigo query
                    contenidoTablaEmpleados.addRow(empleados);
                    System.out.println("Empleado con id: " + empleados[0] + ", nombre: " + empleados[1] + ", documento: " + empleados[3] + " y correo: " + empleados[4]);
                }

                tblEmpleados.setModel(contenidoTablaEmpleados);

            } catch (SQLException e) {
                System.out.println("Error");
                }

        } else {

            String queryConsulta = "SELECT * FROM empleado WHERE nombreEmp LIKE '%" + filtrobusqueda + "%' OR apellido LIKE '%" + filtrobusqueda + "%';";           

            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(queryConsulta);

                //Crearemos un objeto donde reciviremos el resultado de la consulta query
                Object[] empleados = new Object[6];

                // Actulizamos la definicionde DefaultModel de nustra tabla empleados.
                contenidoTablaEmpleados = (DefaultTableModel)tblEmpleados.getModel();

                //Vamoso a recorrer el resultado de nuestra consulta query
                while (rs.next()) {
                    empleados[0] = rs.getInt("idEmp");
                    empleados[1] = rs.getString("nombreEmp");
                    empleados[2] = rs.getString("apellido");
                    empleados[3] = rs.getString("tipoDocumento");
                    empleados[4] = rs.getString("documento");
                    empleados[5] = rs.getString("correo");

                    //Creamos una fila en la tabla por cada empleado que nos devuelve el codigo query
                    contenidoTablaEmpleados.addRow(empleados);
                    System.out.println("Empleado con id: " + empleados[0] + ", nombre: " + empleados[1] + ", documento: " + empleados[3] + " y correo: " + empleados[4]);   
                }

                tblEmpleados.setModel(contenidoTablaEmpleados);

                } catch (SQLException e) {
                    System.out.println("Error");
                }    
            
        }
        
    }
    
    //Cada vez que añada a un emplado la tabla se debe actualizar borrandose.
    private void borrarDatosTablaEmp () {
        
        for (int i = 0; i < tblEmpleados.getRowCount(); i++) {
            contenidoTablaEmpleados.removeRow(i);
            i -= 1;
        }
        
    }
    
    private void borrarDatosTablaDepa () {
        
        for (int i = 0; i < tblDepartamentos.getRowCount(); i++) {
            contenidoTablaDepartamentos.removeRow(i);
            i -= 1;
        } 
        
        cbDepartamento.setSelectedIndex(0);
        cbTipoCalle.setSelectedIndex(0);
        txtNumero1.setText("");
        txtNumero2.setText("");
        txtNumero3.setText("");
        
    }
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBase = new javax.swing.JPanel();
        SlidePanel = new javax.swing.JPanel();
        btnSucursal = new javax.swing.JPanel();
        txtLableSucursal = new javax.swing.JLabel();
        btnEmpleados = new javax.swing.JPanel();
        txtLableEmpleados = new javax.swing.JLabel();
        pngLogoMinTic = new javax.swing.JLabel();
        CentraPanel = new javax.swing.JPanel();
        pnlSucursal = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbZona = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbTipoCalle = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbDepartamento = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtNumero1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNumero2 = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtNumero3 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDepartamentos = new javax.swing.JTable();
        txtSearchDepartamento = new javax.swing.JTextField();
        btnsearchSucursal = new javax.swing.JButton();
        btnAddEmpleado = new javax.swing.JButton();
        EmpleadosPage = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtsearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBase.setBackground(new java.awt.Color(255, 255, 255));
        pnlBase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SlidePanel.setBackground(new java.awt.Color(191, 2, 131));
        SlidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSucursal.setBackground(new java.awt.Color(191, 2, 131));
        btnSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSucursalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSucursalMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSucursalMousePressed(evt);
            }
        });

        txtLableSucursal.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        txtLableSucursal.setForeground(new java.awt.Color(255, 255, 255));
        txtLableSucursal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLableSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/sucursalLogo.png"))); // NOI18N
        txtLableSucursal.setText("  Sucursal");

        javax.swing.GroupLayout btnSucursalLayout = new javax.swing.GroupLayout(btnSucursal);
        btnSucursal.setLayout(btnSucursalLayout);
        btnSucursalLayout.setHorizontalGroup(
            btnSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtLableSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        btnSucursalLayout.setVerticalGroup(
            btnSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtLableSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        SlidePanel.add(btnSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 200, 50));

        btnEmpleados.setBackground(new java.awt.Color(191, 2, 131));
        btnEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEmpleadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEmpleadosMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEmpleadosMousePressed(evt);
            }
        });

        txtLableEmpleados.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        txtLableEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        txtLableEmpleados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLableEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/empleadosLogo.png"))); // NOI18N
        txtLableEmpleados.setText("  Empleados");

        javax.swing.GroupLayout btnEmpleadosLayout = new javax.swing.GroupLayout(btnEmpleados);
        btnEmpleados.setLayout(btnEmpleadosLayout);
        btnEmpleadosLayout.setHorizontalGroup(
            btnEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtLableEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        btnEmpleadosLayout.setVerticalGroup(
            btnEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtLableEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        SlidePanel.add(btnEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 200, 50));

        pngLogoMinTic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/MisTICLogo.png"))); // NOI18N
        SlidePanel.add(pngLogoMinTic, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        pnlBase.add(SlidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 500));

        CentraPanel.setBackground(new java.awt.Color(255, 255, 255));
        CentraPanel.setLayout(new java.awt.CardLayout());

        pnlSucursal.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Zona:");

        cbZona.setModel(enumZona);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tipo Calle:");

        cbTipoCalle.setModel(enumTipoCalle);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Departamento:");

        cbDepartamento.setModel(enumDepartamentos);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Numero:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("#");

        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGuardar.setText("Añadir");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("-");

        txtNumero3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumero3ActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/MinTicLogo.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Roboto Medium", 1, 24)); // NOI18N
        jLabel12.setText("AÑADIR SUCURSAL");

        tblDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sucursal", "Departamento"
            }
        ));
        jScrollPane2.setViewportView(tblDepartamentos);

        txtSearchDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchDepartamentoActionPerformed(evt);
            }
        });

        btnsearchSucursal.setText("Buscar");
        btnsearchSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchSucursalActionPerformed(evt);
            }
        });

        btnAddEmpleado.setText("Añadir Empleado");
        btnAddEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSucursalLayout = new javax.swing.GroupLayout(pnlSucursal);
        pnlSucursal.setLayout(pnlSucursalLayout);
        pnlSucursalLayout.setHorizontalGroup(
            pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSucursalLayout.createSequentialGroup()
                .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSucursalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11))
                    .addGroup(pnlSucursalLayout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(btnGuardar)))
                .addGap(0, 509, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSucursalLayout.createSequentialGroup()
                .addGap(0, 303, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(279, 279, 279))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSucursalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSucursalLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(183, 183, 183))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSucursalLayout.createSequentialGroup()
                        .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlSucursalLayout.createSequentialGroup()
                                .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlSucursalLayout.createSequentialGroup()
                                        .addComponent(txtSearchDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(btnsearchSucursal))
                                    .addGroup(pnlSucursalLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbTipoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlSucursalLayout.createSequentialGroup()
                                        .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnAddEmpleado, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(125, 125, 125))))
        );
        pnlSucursalLayout.setVerticalGroup(
            pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSucursalLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel11)
                .addGap(9, 9, 9)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTipoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnGuardar)
                .addGap(60, 60, 60)
                .addGroup(pnlSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsearchSucursal)
                    .addComponent(btnAddEmpleado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        CentraPanel.add(pnlSucursal, "card2");

        EmpleadosPage.setBackground(new java.awt.Color(255, 255, 255));

        tblEmpleados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellidos", "Tipo de documento", "Documento", "Correo"
            }
        ));
        tblEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmpleados);

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 1, 24)); // NOI18N
        jLabel1.setText("INFORMACION EMPLEADOS");

        btnAddUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatarUserMini.png"))); // NOI18N
        btnAddUser.setText("Crea empleado ");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        txtsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/showUser .png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/MinTicLogo.png"))); // NOI18N

        javax.swing.GroupLayout EmpleadosPageLayout = new javax.swing.GroupLayout(EmpleadosPage);
        EmpleadosPage.setLayout(EmpleadosPageLayout);
        EmpleadosPageLayout.setHorizontalGroup(
            EmpleadosPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EmpleadosPageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(234, 234, 234))
            .addGroup(EmpleadosPageLayout.createSequentialGroup()
                .addGroup(EmpleadosPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EmpleadosPageLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(EmpleadosPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(EmpleadosPageLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(29, 29, 29)
                                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddUser))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(EmpleadosPageLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        EmpleadosPageLayout.setVerticalGroup(
            EmpleadosPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmpleadosPageLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addGroup(EmpleadosPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EmpleadosPageLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(EmpleadosPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSearch)
                            .addComponent(jLabel2)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAddUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        CentraPanel.add(EmpleadosPage, "card3");

        pnlBase.add(CentraPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, -9, 810, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBase, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBase, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        
        AddUserForm addUserF = new AddUserForm(this, rootPaneCheckingEnabled);
        addUserF.setVisible(rootPaneCheckingEnabled);
        borrarDatosTablaEmp();
        ListarEmpleados();
        
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void tblEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosMouseClicked
        
        int row = tblEmpleados.getSelectedRow();
        System.out.println("La fila seleccionada es: " + row);
        
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un empleado.","",JOptionPane.INFORMATION_MESSAGE);
        } else {
            int idEmp = Integer.parseInt(tblEmpleados.getValueAt(row, 0).toString());
            String nombreEmp = tblEmpleados.getValueAt(row, 1).toString();
            String apellidosEmp = tblEmpleados.getValueAt(row, 2).toString();
            String tipoDocumentoEmp = tblEmpleados.getValueAt(row, 3).toString();
            String documentoEmp = tblEmpleados.getValueAt(row, 4).toString();
            String correoEmp = tblEmpleados.getValueAt(row, 5).toString();
            
            System.out.println("IdEmp: " + idEmp + ", nombre: " + nombreEmp + ", apellido: " + apellidosEmp + ", Tipo de docuemnto: " + tipoDocumentoEmp + ", documeto: " +documentoEmp + ", correo:" +correoEmp);
            
            ShowUserForm showUserForm = new ShowUserForm(this, true);
            
            showUserForm.recibeDatos(idEmp, nombreEmp, apellidosEmp, tipoDocumentoEmp, documentoEmp, correoEmp);
            showUserForm.setVisible(true);
            borrarDatosTablaEmp();
            ListarEmpleados();
            
        }
        
    }//GEN-LAST:event_tblEmpleadosMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        
        borrarDatosTablaEmp();
        ListarEmpleados();
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       
       String departamentoOption = cbDepartamento.getSelectedItem().toString();
       String zonaOption = cbZona.getSelectedItem().toString();
       String tipoCalleOption = cbTipoCalle.getSelectedItem().toString();
       String numero1 = txtNumero1.getText();
       String numero2 = txtNumero2.getText();
       String numero3 = txtNumero3.getText();
              
       String quertyDireccion = "INSERT INTO `direccion`(`zona`, `tipoCalle`, `numero1`, `numero2`, `numero3`, `nombreDepartamento`) VALUES ('" + zonaOption + "','" + tipoCalleOption + "','" + numero1 + "','" + numero2 + "','" + numero3 + "','" + departamentoOption + "')";
       
        try {
            
            cn = con.getConnection();
            st = cn.createStatement();
            st.executeUpdate(quertyDireccion);
            System.out.println(quertyDireccion);
            
            SucursalForm sucursalForm = new SucursalForm(this, true);
            sucursalForm.setVisible(true);
            String queryIdDireccion = "SELECT idDireccion FROM `Direccion` WHERE nombreDepartamento = '"  + departamentoOption + "' AND zona = '" + zonaOption + "' AND tipoCalle = '" + tipoCalleOption + "' AND numero1 = '" + numero1 + "' AND numero2 = '" + numero2 + "' AND numero3 = '" + numero3 + "';";
            System.out.println(queryIdDireccion);
            
            try {
                
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(queryIdDireccion);
                
                int idDireccion;
                
                while (rs.next()) {
                    
                    int direccion = rs.getInt("idDireccion");
                    sucursalForm.recibeDatosDireccion(direccion);
                    
                }
                
                borrarDatosTablaDepa();
                ListarDepartamentos();
            
            } catch (SQLException e) {
                
                System.out.println(e);
                
            }
            
            JOptionPane.showMessageDialog(this, "Direccion creada correctamente");

        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(this, "No se pudo crear el direccion", "", JOptionPane.ERROR_MESSAGE);
            
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEmpleadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmpleadosMousePressed
        pnlSucursal.setVisible(false);
        EmpleadosPage.setVisible(true);
    }//GEN-LAST:event_btnEmpleadosMousePressed

    private void btnSucursalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalMousePressed
        pnlSucursal.setVisible(true);
        EmpleadosPage.setVisible(false);
    }//GEN-LAST:event_btnSucursalMousePressed

    private void txtNumero3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumero3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumero3ActionPerformed

    private void btnSucursalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalMouseEntered
        btnSucursal.setBackground(new Color(253, 2, 171));
    }//GEN-LAST:event_btnSucursalMouseEntered

    private void btnSucursalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSucursalMouseExited
        btnSucursal.setBackground(new Color(191, 2, 131));
    }//GEN-LAST:event_btnSucursalMouseExited

    private void btnEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmpleadosMouseEntered
        btnEmpleados.setBackground(new Color(253, 2, 171));
    }//GEN-LAST:event_btnEmpleadosMouseEntered

    private void btnEmpleadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmpleadosMouseExited
        btnEmpleados.setBackground(new Color(191, 2, 131));
    }//GEN-LAST:event_btnEmpleadosMouseExited

    private void btnsearchSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsearchSucursalActionPerformed

    private void btnAddEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmpleadoActionPerformed
        
        int nroFila = tblDepartamentos.getSelectedRow();
        
        if (nroFila != -1) {
            
            String sucursal  = tblDepartamentos.getValueAt(nroFila, 0).toString();
            String query = "SELECT idSucursal FROM `sucursal` WHERE nombreSucursal = '" + sucursal + "';";
            System.out.println(query);
            
            try {
                
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(query);
                

                while (rs.next()) {
                    
                    int idSucursal  = rs.getInt("idSucursal");
                    EmpleadosLista  empLista = new EmpleadosLista(this,true);
                    empLista.setVisible(true);
                    empLista.reicibirIdSucursal(idSucursal);
                    
                }
                
            } catch (SQLException e) {
                
                System.out.println(e);
                
            }
            
        } else {
            
            JOptionPane.showMessageDialog(this, "Para añadir el empleado debes seleccionar la sucursal a la que pertenece");
            
        }
        
    }//GEN-LAST:event_btnAddEmpleadoActionPerformed

    private void txtSearchDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchDepartamentoActionPerformed
        
    }//GEN-LAST:event_txtSearchDepartamentoActionPerformed

    private void txtsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchActionPerformed
 
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
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CentraPanel;
    private javax.swing.JPanel EmpleadosPage;
    private javax.swing.JPanel SlidePanel;
    private javax.swing.JButton btnAddEmpleado;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JPanel btnEmpleados;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel btnSucursal;
    private javax.swing.JButton btnsearchSucursal;
    private javax.swing.JComboBox<String> cbDepartamento;
    private javax.swing.JComboBox<String> cbTipoCalle;
    private javax.swing.JComboBox<String> cbZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel pngLogoMinTic;
    private javax.swing.JPanel pnlBase;
    private javax.swing.JPanel pnlSucursal;
    private javax.swing.JTable tblDepartamentos;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JLabel txtLableEmpleados;
    private javax.swing.JLabel txtLableSucursal;
    private javax.swing.JTextField txtNumero1;
    private javax.swing.JTextField txtNumero2;
    private javax.swing.JTextField txtNumero3;
    private javax.swing.JTextField txtSearchDepartamento;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
