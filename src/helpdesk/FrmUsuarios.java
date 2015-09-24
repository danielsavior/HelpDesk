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
                    break;
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
        jPanelCadUser = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNomeUser = new javax.swing.JLabel();
        txtNomeUser = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenhaUser = new javax.swing.JTextField();
        lblNomeCompleto = new javax.swing.JLabel();
        txtNomeCompleto = new javax.swing.JTextField();
        lblSetor = new javax.swing.JLabel();
        cmbSetor = new javax.swing.JComboBox();
        lblPerfil = new javax.swing.JLabel();
        cmbPerfil = new javax.swing.JComboBox();
        btnGravarUser = new javax.swing.JButton();
        painelAlteraUser = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtCodigoAlt = new javax.swing.JTextField();
        lblCodigoAlt = new javax.swing.JLabel();
        txtNomeUserAlt = new AutoCompleteTextField(5,false,listaDeUsuarios);
        lblNomeUserAlt = new javax.swing.JLabel();
        lblSenhaAlt = new javax.swing.JLabel();
        txtSenhaUserAlt = new javax.swing.JTextField();
        lblNomeCompAlt = new javax.swing.JLabel();
        txtNomeCompAlt = new javax.swing.JTextField();
        cmbSetorAlt = new javax.swing.JComboBox();
        lblSetorAlt = new javax.swing.JLabel();
        lblPerfilAlt = new javax.swing.JLabel();
        cmbPerfilAlt = new javax.swing.JComboBox();
        btnCancelar = new javax.swing.JButton();
        btnGravarUserAlt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Usuários");
        setResizable(false);

        painelCadUser.setBackground(java.awt.Color.lightGray);

        jPanelCadUser.setBackground(java.awt.Color.lightGray);
        jPanelCadUser.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do usuário"));
        jPanelCadUser.setPreferredSize(new java.awt.Dimension(330, 218));

        lblCodigo.setText("Código:");

        txtCodigo.setText("1");
        txtCodigo.setEnabled(false);

        lblNomeUser.setText("Nome de Usuário:");

        lblSenha.setText("Senha:");

        lblNomeCompleto.setText("Nome Completo:");

        lblSetor.setText("Setor:");

        cmbSetor.setSelectedItem("");

        lblPerfil.setText("Perfil:");
        lblPerfil.setToolTipText("");

        btnGravarUser.setText("Gravar");

        javax.swing.GroupLayout jPanelCadUserLayout = new javax.swing.GroupLayout(jPanelCadUser);
        jPanelCadUser.setLayout(jPanelCadUserLayout);
        jPanelCadUserLayout.setHorizontalGroup(
            jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCadUserLayout.createSequentialGroup()
                        .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCadUserLayout.createSequentialGroup()
                                .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                .addGap(48, 48, 48))
                            .addComponent(lblNomeUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelCadUserLayout.createSequentialGroup()
                                .addComponent(lblSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(51, 51, 51))
                            .addGroup(jPanelCadUserLayout.createSequentialGroup()
                                .addComponent(lblNomeCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(6, 6, 6))
                            .addGroup(jPanelCadUserLayout.createSequentialGroup()
                                .addComponent(lblSetor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(55, 55, 55))
                            .addGroup(jPanelCadUserLayout.createSequentialGroup()
                                .addComponent(lblPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(57, 57, 57)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbPerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadUserLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                .addGap(138, 138, 138))
                            .addComponent(cmbSetor, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNomeCompleto, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSenhaUser, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNomeUser, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadUserLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravarUser, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelCadUserLayout.setVerticalGroup(
            jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadUserLayout.createSequentialGroup()
                .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeUser)
                    .addComponent(txtNomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenha)
                    .addComponent(txtSenhaUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNomeCompleto)
                    .addComponent(txtNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSetor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPerfil)
                    .addComponent(cmbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGravarUser)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelCadUserLayout = new javax.swing.GroupLayout(painelCadUser);
        painelCadUser.setLayout(painelCadUserLayout);
        painelCadUserLayout.setHorizontalGroup(
            painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCadUser, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelCadUserLayout.setVerticalGroup(
            painelCadUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCadUser, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tabCadUser.addTab("Cadastro", painelCadUser);

        painelAlteraUser.setBackground(java.awt.Color.gray);

        jPanel1.setBackground(java.awt.Color.gray);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do usuário"));

        lblCodigoAlt.setText("Código:");

        lblNomeUserAlt.setText("Nome de usuário:");

        lblSenhaAlt.setText("Senha:");

        lblNomeCompAlt.setText("Nome Completo:");

        cmbSetorAlt.setSelectedItem("");

        lblSetorAlt.setText("Setor:");

        lblPerfilAlt.setText("Perfil:");
        lblPerfilAlt.setToolTipText("");

        btnCancelar.setText("Cancelar");

        btnGravarUserAlt.setText("Gravar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodigoAlt)
                    .addComponent(lblNomeUserAlt)
                    .addComponent(lblSenhaAlt)
                    .addComponent(lblNomeCompAlt)
                    .addComponent(lblSetorAlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSetorAlt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNomeCompAlt)
                    .addComponent(txtSenhaUserAlt)
                    .addComponent(txtNomeUserAlt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 183, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGravarUserAlt))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmbPerfilAlt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblPerfilAlt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeUserAlt)
                    .addComponent(txtNomeUserAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenhaAlt)
                    .addComponent(txtSenhaUserAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeCompAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNomeCompAlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSetorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSetorAlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPerfilAlt)
                    .addComponent(cmbPerfilAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravarUserAlt)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelAlteraUserLayout = new javax.swing.GroupLayout(painelAlteraUser);
        painelAlteraUser.setLayout(painelAlteraUserLayout);
        painelAlteraUserLayout.setHorizontalGroup(
            painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelAlteraUserLayout.setVerticalGroup(
            painelAlteraUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            .addComponent(tabCadUser, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCadUser;
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
