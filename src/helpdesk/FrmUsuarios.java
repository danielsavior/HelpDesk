/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;


import helpdesk.beans.Setor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import helpdesk.beans.Usuario;
import helpdesk.controllers.SetorController;
import helpdesk.controllers.UsuarioController;
import helpdesk.utils.AutoCompleteTextField;
import helpdesk.utils.Utilidades;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class FrmUsuarios extends javax.swing.JDialog {

    /**
     * Creates new form FrmConfig
     */
    private List<String>listaDeUsuarios;
    public FrmUsuarios(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        listaDeUsuarios=new ArrayList<>();
        for(Usuario u:new UsuarioController().listar()){
            listaDeUsuarios.add(u.getLogin()+"-"+u.getNomeCompleto()+"-codigo"+u.getId());
        }
        initComponents();  
        
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("Usuario")));
        carregarCombos();
        tabCadUser.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabCadUser.getSelectedIndex()==0){
                    txtCodigoAlt.setText("");
                    txtCodigoAlt.setEnabled(true);
                    txtNomeUserAlt.setText("");
                    txtSenhaUserAlt.setText("");
                    txtNomeCompAlt.setText("");
                }else{
                    txtNomeUser.setText("");
                    txtSenhaUser.setText("");
                    txtNomeCompleto.setText("");
                }
            }
        });
        
        btnGravarUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               try{
                    Usuario usuario= new Usuario();
                    usuario.setLogin(txtNomeUser.getText());
                    usuario.setSenha(Utilidades.criptografarSenha(txtSenhaUser.getText()));
                    usuario.setNomeCompleto(txtNomeCompleto.getText());
                    usuario.setIdsetor(Long.valueOf(String.valueOf(cmbSetor.getSelectedItem().toString().subSequence(0, cmbSetor.getSelectedItem().toString().indexOf("-")))));
                    usuario.setPerfil(String.valueOf(cmbPerfil.getSelectedIndex()+1).charAt(0));
                    
                    new UsuarioController().insert(usuario);
                    txtCodigo.setText(String.valueOf(Integer.valueOf(txtCodigo.getText())+1));
                    txtNomeUser.setText("");
                    txtSenhaUser.setText("");
                    txtNomeCompleto.setText("");
               }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao gravar\n Tente novamente.");
               }
            }
        });
        
        txtCodigoAlt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
               // JOptionPane.showMessageDialog(null, "Typed");
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                //JOptionPane.showMessageDialog(null, "Pressed "+ke.getKeyCode());
                if (ke.getKeyCode()==10){
                    //JOptionPane.showMessageDialog(null, "Você pressionou Enter");
                    
                    buscaPorID(Long.valueOf(txtCodigoAlt.getText()));
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                //JOptionPane.showMessageDialog(null, "Released");
            }
        });
        
        btnGravarUserAlt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!txtCodigoAlt.getText().toString().equals("")){
                    
                    try{
                        int resposta=0;
                        Usuario u= new Usuario();
                        u.setId((long)Integer.valueOf(txtCodigoAlt.getText()));
                        u.setLogin(txtNomeUserAlt.getText());
                        u.setSenha(txtSenhaUserAlt.getText().equals("")?Utilidades.criptografarSenha(txtNomeUserAlt.getText()):Utilidades.criptografarSenha(txtSenhaUserAlt.getText()));
                        u.setNomeCompleto(txtNomeCompAlt.getText());
                        u.setIdsetor(Long.valueOf(String.valueOf(cmbSetorAlt.getSelectedItem().toString().subSequence(0, cmbSetorAlt.getSelectedItem().toString().indexOf("-")))));
                        u.setPerfil(String.valueOf(cmbPerfilAlt.getSelectedIndex()+1).charAt(0));
                        if(txtSenhaUserAlt.getText().equals("")){
                            resposta=JOptionPane.showConfirmDialog(null, "A senha não foi preenchida\n a senha do usuário será igual ao login do mesmo.\nConfirma a operação? ");
                        }
                        if(resposta==0){
                            new UsuarioController().update(u);
                            txtCodigoAlt.setEnabled(true);
                            txtCodigoAlt.setText("");
                            txtNomeUserAlt.setText("");
                            txtSenhaUserAlt.setText("");
                            txtNomeCompAlt.setText("");
                        }
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar\n Tente novamente.");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Escolha um usuario para alterar.");
                }
            }
        });
        btnCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                txtCodigoAlt.setEnabled(true);
                txtCodigoAlt.setText("");
                txtNomeUserAlt.setText("");
                txtSenhaUserAlt.setText("");
                txtCodigoAlt.setText("");
                txtNomeCompAlt.setText("");
            }
        });
        txtNomeUserAlt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==10){
                    if(txtNomeUserAlt.getText().contains("codigo")){
                        String texto=txtNomeUserAlt.getText();
                        texto=texto.substring(texto.indexOf("codigo"),texto.length());
                        texto=texto.substring(texto.indexOf("go")+2,texto.length());
                        buscaPorID(Long.valueOf(texto));                        
                        txtCodigoAlt.setText(texto);
                        txtCodigoAlt.setEnabled(false);
                        
                    }
                    
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } );
    }
    
    private void carregarCombos(){
        cmbSetor.removeAllItems();
        cmbPerfil.removeAllItems();
        cmbSetorAlt.removeAllItems();
        cmbPerfilAlt.removeAllItems();
        List<Setor> lista=new SetorController().listar();
        for(Setor setor:lista){
            cmbSetor.addItem(setor.getId()+"-"+setor.getDescricao());
            cmbSetorAlt.addItem(setor.getId()+"-"+setor.getDescricao());
            
        }
        cmbPerfil.addItem("Administrador");
        cmbPerfil.addItem("Usuario");
        cmbPerfil.addItem("Operador");
        
        cmbPerfilAlt.addItem("Administrador");
        cmbPerfilAlt.addItem("Usuario");
        cmbPerfilAlt.addItem("Operador");
        
    }
    
    private void buscaPorID(long id){
        try{
            String set="";
            Usuario u=new UsuarioController().buscarPorID(id);
            txtNomeUserAlt.setText(u.getLogin());
            txtNomeCompAlt.setText(u.getNomeCompleto());
            for(int i=0;i<cmbSetorAlt.getItemCount();i++){
                cmbSetorAlt.setSelectedIndex(i);
                if(cmbSetorAlt.getSelectedItem().toString().substring(0,cmbSetorAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getIdsetor()))){
                    set=cmbSetorAlt.getSelectedItem().toString();                                
                }

            }
            cmbSetorAlt.setSelectedItem(set);
            //cmbSetorAlt.setSelectedIndex(Integer.valueOf(String.valueOf(u.getIdsetor()-1)));
            cmbPerfilAlt.setSelectedIndex(Integer.valueOf(String.valueOf(u.getPerfil()))-1);
            txtCodigoAlt.setEnabled(false);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
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

        tabCadUser = new javax.swing.JTabbedPane();
        painelCadUser = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNomeUser = new javax.swing.JLabel();
        txtNomeUser = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenhaUser = new javax.swing.JTextField();
        btnGravarUser = new javax.swing.JButton();
        lblNomeCompleto = new javax.swing.JLabel();
        txtNomeCompleto = new javax.swing.JTextField();
        lblSetor = new javax.swing.JLabel();
        cmbSetor = new javax.swing.JComboBox();
        lblPerfil = new javax.swing.JLabel();
        cmbPerfil = new javax.swing.JComboBox();
        painelAlteraUser = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        txtNomeUserAlt = new AutoCompleteTextField(5,false,listaDeUsuarios);
        lblNomeUserAlt = new javax.swing.JLabel();
        lblSenhaAlt = new javax.swing.JLabel();
        txtSenhaUserAlt = new javax.swing.JTextField();
        btnGravarUserAlt = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtNomeCompAlt = new javax.swing.JTextField();
        lblNomeCompAlt = new javax.swing.JLabel();
        lblSetorAlt = new javax.swing.JLabel();
        cmbSetorAlt = new javax.swing.JComboBox();
        lblPerfilAlt = new javax.swing.JLabel();
        cmbPerfilAlt = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Usuários");
        setResizable(false);

        painelCadUser.setBackground(java.awt.Color.lightGray);

        lblCodigo.setText("Código");

        txtCodigo.setText("1");
        txtCodigo.setEnabled(false);

        lblNomeUser.setText("Nome de Usuário:");

        lblSenha.setText("Senha:");

        btnGravarUser.setText("Gravar");

        lblNomeCompleto.setText("Nome Completo:");

        lblSetor.setText("Setor:");

        cmbSetor.setSelectedItem("");

        lblPerfil.setText("Perfil:");
        lblPerfil.setToolTipText("");

        javax.swing.GroupLayout painelCadUserLayout = new javax.swing.GroupLayout(painelCadUser);
        painelCadUser.setLayout(painelCadUserLayout);
        painelCadUserLayout.setHorizontalGroup(
            painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCadUserLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGravarUser))
            .addGroup(painelCadUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNomeUser)
                    .addComponent(lblCodigo)
                    .addComponent(lblSenha)
                    .addComponent(lblNomeCompleto)
                    .addComponent(lblSetor))
                .addGap(3, 3, 3)
                .addGroup(painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeUser)
                    .addComponent(txtSenhaUser)
                    .addComponent(txtNomeCompleto)
                    .addGroup(painelCadUserLayout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(painelCadUserLayout.createSequentialGroup()
                        .addComponent(cmbSetor, 0, 180, Short.MAX_VALUE)
                        .addGap(4, 4, 4)
                        .addComponent(lblPerfil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        painelCadUserLayout.setVerticalGroup(
            painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadUserLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeUser)
                    .addComponent(txtNomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenha)
                    .addComponent(txtSenhaUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNomeCompleto)
                    .addComponent(txtNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPerfil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSetor)
                        .addComponent(lblPerfil)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(btnGravarUser))
        );

        tabCadUser.addTab("Cadastro", painelCadUser);

        painelAlteraUser.setBackground(java.awt.Color.gray);

        lblCodigoAlt.setText("Código");

        lblNomeUserAlt.setText("Nome de Usuário");

        lblSenhaAlt.setText("Senha:");

        btnGravarUserAlt.setText("Gravar");

        btnCancelar.setText("Cancelar");

        lblNomeCompAlt.setText("Nome Completo:");

        lblSetorAlt.setText("Setor:");

        cmbSetorAlt.setSelectedItem("");

        lblPerfilAlt.setText("Perfil:");
        lblPerfilAlt.setToolTipText("");

        javax.swing.GroupLayout painelAlteraUserLayout = new javax.swing.GroupLayout(painelAlteraUser);
        painelAlteraUser.setLayout(painelAlteraUserLayout);
        painelAlteraUserLayout.setHorizontalGroup(
            painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelAlteraUserLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGravarUserAlt))
            .addGroup(painelAlteraUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelAlteraUserLayout.createSequentialGroup()
                        .addComponent(lblSetorAlt)
                        .addGap(3, 3, 3)
                        .addComponent(cmbSetorAlt, 0, 260, Short.MAX_VALUE)
                        .addGap(4, 4, 4)
                        .addComponent(lblPerfilAlt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPerfilAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelAlteraUserLayout.createSequentialGroup()
                        .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNomeUserAlt)
                            .addComponent(lblCodigoAlt)
                            .addComponent(lblSenhaAlt)
                            .addComponent(lblNomeCompAlt))
                        .addGap(3, 3, 3)
                        .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeCompAlt)
                            .addComponent(txtNomeUserAlt)
                            .addGroup(painelAlteraUserLayout.createSequentialGroup()
                                .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 315, Short.MAX_VALUE))
                            .addComponent(txtSenhaUserAlt))))
                .addContainerGap())
        );
        painelAlteraUserLayout.setVerticalGroup(
            painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraUserLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeUserAlt)
                    .addComponent(txtNomeUserAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenhaAlt)
                    .addComponent(txtSenhaUserAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeCompAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNomeCompAlt))
                .addGap(16, 16, 16)
                .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSetorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPerfilAlt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSetorAlt)
                        .addComponent(lblPerfilAlt)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravarUserAlt)
                    .addComponent(btnCancelar)))
        );

        tabCadUser.addTab("Alteração", painelAlteraUser);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCadUser)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabCadUser, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmUsuarios dialog = new FrmUsuarios(new javax.swing.JFrame(), true);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGravarUser;
    private javax.swing.JButton btnGravarUserAlt;
    private javax.swing.JComboBox cmbPerfil;
    private javax.swing.JComboBox cmbPerfilAlt;
    private javax.swing.JComboBox cmbSetor;
    private javax.swing.JComboBox cmbSetorAlt;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblNomeCompAlt;
    private javax.swing.JLabel lblNomeCompleto;
    private javax.swing.JLabel lblNomeUser;
    private javax.swing.JLabel lblNomeUserAlt;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblPerfilAlt;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblSenhaAlt;
    private javax.swing.JLabel lblSetor;
    private javax.swing.JLabel lblSetorAlt;
    private javax.swing.JPanel painelAlteraUser;
    private javax.swing.JPanel painelCadUser;
    private javax.swing.JTabbedPane tabCadUser;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private javax.swing.JTextField txtNomeCompAlt;
    private javax.swing.JTextField txtNomeCompleto;
    private javax.swing.JTextField txtNomeUser;
    private javax.swing.JTextField txtNomeUserAlt;
    private javax.swing.JTextField txtSenhaUser;
    private javax.swing.JTextField txtSenhaUserAlt;
    // End of variables declaration//GEN-END:variables
}
