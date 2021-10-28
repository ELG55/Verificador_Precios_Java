
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.Statement;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Verificador extends javax.swing.JFrame {

    private Dimension size;
    private String codigo = "";
    private int width, height;
    private boolean loading = false;
    Timer tmr_return;

    public Verificador() {
        initComponents();
        pnl_inicio.setVisible(true);
        pnl_loading.setVisible(false);
        pnl_no_encontrado.setVisible(false);
        ClearProductData();
        
        this.setLocationRelativeTo(null);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (KeyEvent.KEY_PRESSED == e.getID()) {
                    HandleKeyPresses(e);
                }
                return false;
            }
        });
    }
    
    void HandleKeyPresses(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (!loading)) {
            
            pnl_inicio.setVisible(false);
            pnl_no_encontrado.setVisible(false);
            pnl_loading.setVisible(true);
            tbpnl_tabs.setSelectedIndex(0);
            ClearProductData();
            loading = true;
            if (tmr_return != null) {
                tmr_return.cancel();
            }
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    //Do stuff
                    loading = false;
                    ShowSearchResults();
                    t.cancel();
                }
            }, 1000, 1000);
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (codigo.length() > 0)
                {
                    codigo = codigo.substring(0, codigo.length() - 1);  
                    tf_codigo.setText(codigo);
                }
        }
        else if (codigo.length() < 5) {
            codigo += e.getKeyChar();
            tf_codigo.setText(codigo);
        }
    }
    
    private void ShowSearchResults() {
            if (!isNumeric(codigo))
            {
                codigo = "-1";
                tf_codigo.setText("");
            }
            try {
                Connection con = null;
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/verificador_de_precios", "root", "");
                PreparedStatement pstm = con.prepareStatement("SELECT producto_codigo, producto_nombre, producto_cantidad, producto_precio, producto_imagen FROM productos WHERE producto_codigo = " + codigo);
                ResultSet rs = pstm.executeQuery();

                //Aquí los índices empiezan en 1
                if (rs.next()) {
                    lb_datos_producto.setText(
                            "<html><b>Código:</b> " + rs.getString(1) +
                            "<br><b>Nombre:</b> " + rs.getString(2) +
                            "<br><b>Stock:</b> " + rs.getString(3) +
                            "<br><b>Precio regular:</b> $" + rs.getString(4) +
                            "<br><b>Descuento:</b> 0%" +
                            "<br><b>Precio final:</b> $" + rs.getString(4));
                    String ruta = rs.getString(5);
                    ImageIcon icono = new ImageIcon(new URL(ruta));
                    lb_producto_imagen.setIcon(new ImageIcon(icono.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
                    
                    codigo = "";
                    tf_codigo.setText(codigo);
                    tbpnl_tabs.setSelectedIndex(1);
                    pnl_loading.setVisible(false);
                    pnl_inicio.setVisible(true);
                    
                    
                    tmr_return = new Timer();
                    tmr_return.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //Do stuff
                            if (!loading) {
                                tbpnl_tabs.setSelectedIndex(0);
                            }
                            tmr_return.cancel();
                        }
                    }, 12000, 12000);
                } else {
                    pnl_loading.setVisible(false);
                    pnl_inicio.setVisible(false);
                    pnl_no_encontrado.setVisible(true);
                    tbpnl_tabs.setSelectedIndex(0);
                    codigo = "";
                    tf_codigo.setText(codigo);
                    
                    ClearProductData();
                                        
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //Do stuff
                            if (!loading) {
                                pnl_no_encontrado.setVisible(false);
                                pnl_inicio.setVisible(true);
                            }
                            t.cancel();
                        }
                    }, 8000, 8000);
                }
            } catch (Exception ex) {
                pnl_loading.setVisible(false);
                pnl_no_encontrado.setVisible(false);
                pnl_inicio.setVisible(true);
                tbpnl_tabs.setSelectedIndex(0);
                codigo = "";
                tf_codigo.setText(codigo);

                ClearProductData();
                JOptionPane.showMessageDialog(null, "No se ha logrado conectar.");
            }
            codigo = "";
    }
    
    private void ClearProductData() {
        lb_datos_producto.setText(
                "<html><b>Código:</b> -"
                + "<br><b>Nombre:</b> -"
                + "<br><b>Stock:</b> -"
                + "<br><b>Precio regular:</b> -"
                + "<br><b>Descuento:</b> -"
                + "<br><b>Precio final:</b> -");
        ImageIcon icon = new ImageIcon((getClass().getResource("/img/No image.png")));
        lb_producto_imagen.setIcon(new ImageIcon(icon.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbpnl_tabs = new javax.swing.JTabbedPane();
        pnl_lectura = new javax.swing.JPanel();
        pnl_inicio = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 100));
        lb_logo = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 30));
        lb_logo1 = new javax.swing.JLabel();
        lb_codigo_ingresado = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 10));
        tf_codigo = new javax.swing.JTextField();
        pnl_loading = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 100));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 30));
        lb_loading = new javax.swing.JLabel();
        lb_buscando = new javax.swing.JLabel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 10));
        pnl_no_encontrado = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 100));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 30));
        lb_loading1 = new javax.swing.JLabel();
        lb_buscando1 = new javax.swing.JLabel();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 10));
        lb_buscando2 = new javax.swing.JLabel();
        pnl_producto = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lb_producto_imagen = new javax.swing.JLabel();
        lb_datos_producto = new javax.swing.JLabel();
        lb_logo2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(213, 232, 212));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_lectura.setBackground(new java.awt.Color(213, 232, 212));
        pnl_lectura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_inicio.setBackground(new java.awt.Color(213, 232, 212));
        pnl_inicio.setMaximumSize(new java.awt.Dimension(363, 684));
        pnl_inicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnl_inicio.add(filler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 0, -1, 75));

        lb_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        lb_logo.setToolTipText("");
        lb_logo.setAlignmentX(0.5F);
        lb_logo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_inicio.add(lb_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));
        pnl_inicio.add(filler2, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 175, -1, 22));

        lb_logo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_logo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/scan.png"))); // NOI18N
        lb_logo1.setToolTipText("");
        lb_logo1.setAlignmentX(0.5F);
        lb_logo1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_inicio.add(lb_logo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, -1, -1));

        lb_codigo_ingresado.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lb_codigo_ingresado.setForeground(new java.awt.Color(0, 0, 0));
        lb_codigo_ingresado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_codigo_ingresado.setText("Código ingresado");
        lb_codigo_ingresado.setAlignmentX(0.5F);
        pnl_inicio.add(lb_codigo_ingresado, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 440, -1, -1));
        pnl_inicio.add(filler3, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 421, -1, 7));

        tf_codigo.setEditable(false);
        tf_codigo.setBackground(new java.awt.Color(255, 255, 255));
        tf_codigo.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        tf_codigo.setForeground(new java.awt.Color(0, 0, 0));
        tf_codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_codigo.setToolTipText("");
        tf_codigo.setFocusable(false);
        tf_codigo.setMaximumSize(new java.awt.Dimension(200, 50));
        tf_codigo.setMinimumSize(new java.awt.Dimension(200, 50));
        pnl_inicio.add(tf_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, 200, 43));

        pnl_lectura.add(pnl_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, 363, 690));

        pnl_loading.setBackground(new java.awt.Color(213, 232, 212));
        pnl_loading.setMaximumSize(new java.awt.Dimension(363, 684));
        pnl_loading.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setMinimumSize(new java.awt.Dimension(200, 200));
        jLabel3.setName(""); // NOI18N
        pnl_loading.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 0, -1, -1));
        pnl_loading.add(filler4, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 0, -1, 75));
        pnl_loading.add(filler5, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 175, -1, 22));

        lb_loading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Loading.gif"))); // NOI18N
        lb_loading.setToolTipText("");
        lb_loading.setAlignmentX(0.5F);
        lb_loading.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_loading.add(lb_loading, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        lb_buscando.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        lb_buscando.setForeground(new java.awt.Color(0, 0, 0));
        lb_buscando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_buscando.setText("Buscando");
        lb_buscando.setAlignmentX(0.5F);
        pnl_loading.add(lb_buscando, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, -1, -1));
        pnl_loading.add(filler6, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 421, -1, 7));

        pnl_lectura.add(pnl_loading, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 363, 690));

        pnl_no_encontrado.setBackground(new java.awt.Color(213, 232, 212));
        pnl_no_encontrado.setMaximumSize(new java.awt.Dimension(363, 684));
        pnl_no_encontrado.setPreferredSize(new java.awt.Dimension(1000, 513));
        pnl_no_encontrado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setMinimumSize(new java.awt.Dimension(200, 200));
        jLabel4.setName(""); // NOI18N
        pnl_no_encontrado.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 0, -1, -1));
        pnl_no_encontrado.add(filler7, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 0, -1, 75));
        pnl_no_encontrado.add(filler8, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 175, -1, 22));

        lb_loading1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_loading1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Warning.png"))); // NOI18N
        lb_loading1.setToolTipText("");
        lb_loading1.setAlignmentX(0.5F);
        lb_loading1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_no_encontrado.add(lb_loading1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, -1, -1));

        lb_buscando1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lb_buscando1.setForeground(new java.awt.Color(0, 0, 0));
        lb_buscando1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_buscando1.setText("No se encontró el producto");
        lb_buscando1.setAlignmentX(0.5F);
        pnl_no_encontrado.add(lb_buscando1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, -1, -1));
        lb_buscando1.getAccessibleContext().setAccessibleName("");

        pnl_no_encontrado.add(filler9, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 421, -1, 7));

        lb_buscando2.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        lb_buscando2.setForeground(new java.awt.Color(0, 0, 0));
        lb_buscando2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_buscando2.setText("Por favor pase a servicio al cliente para obtener ayuda.");
        lb_buscando2.setAlignmentX(0.5F);
        pnl_no_encontrado.add(lb_buscando2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, -1, -1));

        pnl_lectura.add(pnl_no_encontrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 1000, 690));

        tbpnl_tabs.addTab("Lectura", pnl_lectura);

        pnl_producto.setBackground(new java.awt.Color(213, 232, 212));
        pnl_producto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(249, 247, 237));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_producto_imagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_producto_imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/No image.png"))); // NOI18N
        lb_producto_imagen.setToolTipText("");
        lb_producto_imagen.setAlignmentX(0.5F);
        lb_producto_imagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lb_producto_imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        pnl_producto.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 350, 450));

        lb_datos_producto.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        lb_datos_producto.setForeground(new java.awt.Color(0, 0, 0));
        lb_datos_producto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_datos_producto.setText("Sin producto");
        lb_datos_producto.setAlignmentX(0.5F);
        pnl_producto.add(lb_datos_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 550, 350));
        lb_datos_producto.getAccessibleContext().setAccessibleName("");
        lb_datos_producto.getAccessibleContext().setAccessibleDescription("");

        lb_logo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_logo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        lb_logo2.setToolTipText("");
        lb_logo2.setAlignmentX(0.5F);
        lb_logo2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnl_producto.add(lb_logo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 20, -1, -1));

        tbpnl_tabs.addTab("Producto", pnl_producto);

        getContentPane().add(tbpnl_tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Verificador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Verificador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Verificador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Verificador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Verificador().setVisible(true);
            }
        });
    }

    private boolean isNumeric(String string) {
        int intValue;
        if (string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lb_buscando;
    private javax.swing.JLabel lb_buscando1;
    private javax.swing.JLabel lb_buscando2;
    private javax.swing.JLabel lb_codigo_ingresado;
    private javax.swing.JLabel lb_datos_producto;
    private javax.swing.JLabel lb_loading;
    private javax.swing.JLabel lb_loading1;
    private javax.swing.JLabel lb_logo;
    private javax.swing.JLabel lb_logo1;
    private javax.swing.JLabel lb_logo2;
    private javax.swing.JLabel lb_producto_imagen;
    private javax.swing.JPanel pnl_inicio;
    private javax.swing.JPanel pnl_lectura;
    private javax.swing.JPanel pnl_loading;
    private javax.swing.JPanel pnl_no_encontrado;
    private javax.swing.JPanel pnl_producto;
    private javax.swing.JTabbedPane tbpnl_tabs;
    private javax.swing.JTextField tf_codigo;
    // End of variables declaration//GEN-END:variables
}
