package helpdesk;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import helpdesk.beans.Usuario;
import helpdesk.controllers.UsuarioController;
import helpdesk.utils.AutoCompleteTextField;
import helpdesk.utils.Utilidades;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class FrmLogin extends javax.swing.JDialog {
    private List<String>listaDeUsuarios;
    public FrmLogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        listaDeUsuarios=new ArrayList<>();
        for(Usuario u:new UsuarioController().listar()){
            listaDeUsuarios.add(u.getLogin());
        }
        initComponents();  
        
        
        btnEfetuarLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //throw new UnsupportedOperationException("Not supported yet.");
                //        Negocio n=new Negocio(12, 3, "da china");
                    validarLogin();
            }
        });
        
        txtLogin.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                txtLogin.select(0, txtLogin.getText().length());
            }

            @Override
            public void focusLost(FocusEvent e) {                
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        txtLogin.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==10&&!txtLogin.getText().equals("")){
                    UsuarioController uc =new UsuarioController();
                    lblNome.setText(uc.buscarPorLogin(txtLogin.getText().toString()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        txtSenha.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==10){
                    try{
                        validarLogin();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        btnSair.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
//                File f=new File(System.getProperty("user.dir")+File.separator+"confHB.xml");
//                try{
//                    FileWriter fw=new FileWriter(f);
//                    BufferedWriter bw =new BufferedWriter(fw);
//                    bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                            "<!DOCTYPE hibernate-configuration PUBLIC \"-//Hibernate/Hibernate Configuration DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd\">\n" +
//                            "<hibernate-configuration>\n" +
//                            "  <session-factory>\n" +
//                            "    <property name=\"hibernate.dialect\">org.hibernate.dialect.MySQLDialect</property>\n" +
//                            "    <property name=\"hibernate.connection.driver_class\">com.mysql.jdbc.Driver</property>\n" +
//                            "    <property name=\"hibernate.connection.url\">jdbc:mysql://localhost:3306/restaurante</property>\n" +
//                            "    <property name=\"hibernate.connection.username\">root</property>\n" +
//                            "    <property name=\"hibernate.connection.password\">4321</property>\n" +
//                            "  </session-factory>\n" +
//                            "</hibernate-configuration>");
//                    bw.flush();
//                    bw.close();
//                }catch(Exception e){
//                
//                    JOptionPane.showMessageDialog(null, "Erro ao alterar configuração");
//                }
                System.exit(0);
            }
        });
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogin = new javax.swing.JLabel();
        txtLogin = new AutoCompleteTextField(5,false,listaDeUsuarios);
        btnEfetuarLogin = new javax.swing.JButton();
        lblSenha = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        lblInformacao = new java.awt.Label();
        btnSair = new javax.swing.JButton();
        lblNome = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Formulário de Login");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        lblLogin.setText("Login:");

        txtLogin.setName(""); // NOI18N

        btnEfetuarLogin.setText("Login");

        lblSenha.setText("Senha:");

        lblInformacao.setAlignment(java.awt.Label.CENTER);
        lblInformacao.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblInformacao.setText("Efetuar Login");

        btnSair.setText("Sair");

        lblNome.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSair)
                                .addGap(18, 18, 18)
                                .addComponent(btnEfetuarLogin))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLogin)
                                    .addComponent(lblSenha))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addComponent(lblNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInformacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogin)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenha)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEfetuarLogin)
                    .addComponent(btnSair))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmLogin dialog = new FrmLogin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    private void validarLogin(){
        try{
            Usuario usuario= efetuarLogin(txtLogin.getText(),txtSenha.getText());
            if(usuario!=null){
                //chamo o próximo formulario e faço dispose desse
                JOptionPane.showMessageDialog(null, "Login efetuado com sucesso!");
                TelaPrincipal t=new TelaPrincipal(usuario);
                t.setExtendedState(JFrame.MAXIMIZED_BOTH);
                //t.usuario=usuario;
                t.show();
                dispose();
            }else{
               JOptionPane.showMessageDialog(null, "Login ou senha não encontrado" ); 
            }
        }catch(Exception e){
        
            JOptionPane.showMessageDialog(null, "Arquivo de configuração não encontrado");
        }
    }
    private Usuario efetuarLogin(String usuario, String senha){
        try{
            return  new UsuarioController().efetuarLogin(usuario, Utilidades.criptografarSenha(senha));
        }catch(ExceptionInInitializerError e){
            throw new ExceptionInInitializerError();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEfetuarLogin;
    private javax.swing.JButton btnSair;
    private java.awt.Label lblInformacao;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
