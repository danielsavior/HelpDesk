/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;

import helpdesk.beans.Usuario;
import helpdesk.controllers.UsuarioController;
import helpdesk.utils.Utilidades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class FrmTrocarSenha extends javax.swing.JDialog {

    /**
     * Creates new form FrmTrocarSenha
     */
    private Usuario usuario;
    public FrmTrocarSenha(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    public FrmTrocarSenha(java.awt.Frame parent, boolean modal,Usuario u) {
        this(parent,modal);
        usuario=u;
        txtNome.setText(usuario.getNomeCompleto());        
        
        btnGravar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                alterarSenha();
            }
        });
        
        txtSenhaNova.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    if(!txtSenhaAtual.getText().toString().equals("")&&!txtSenhaNova.getText().toString().equals("")){
                        alterarSenha();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
    }
    private void alterarSenha(){
        if(!usuario.getSenha().equals(Utilidades.criptografarSenha(txtSenhaAtual.getText().toString()))){
            JOptionPane.showMessageDialog(null, "Senha atual não confere!");
        }else{
            UsuarioController uc =new UsuarioController();
            usuario.setSenha(Utilidades.criptografarSenha(txtSenhaNova.getText().toString()));
            uc.update(usuario);                    
            JOptionPane.showMessageDialog(null, "Sua senha foi alterada com sucesso!");
            txtSenhaAtual.setText("");
            txtSenhaNova.setText("");
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

        panelTrocaSenha = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblSenhaAtual = new javax.swing.JLabel();
        lblSenhaNova = new javax.swing.JLabel();
        btnGravar = new javax.swing.JButton();
        txtSenhaAtual = new javax.swing.JPasswordField();
        txtSenhaNova = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alteração de senha");
        setResizable(false);

        panelTrocaSenha.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do usuário"));

        lblNome.setText("Nome completo:");

        txtNome.setEnabled(false);

        lblSenhaAtual.setText("Senha atual:");

        lblSenhaNova.setText("Senha nova:");

        btnGravar.setText("Gravar");

        javax.swing.GroupLayout panelTrocaSenhaLayout = new javax.swing.GroupLayout(panelTrocaSenha);
        panelTrocaSenha.setLayout(panelTrocaSenhaLayout);
        panelTrocaSenhaLayout.setHorizontalGroup(
            panelTrocaSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTrocaSenhaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTrocaSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTrocaSenhaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravar))
                    .addGroup(panelTrocaSenhaLayout.createSequentialGroup()
                        .addGroup(panelTrocaSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addGroup(panelTrocaSenhaLayout.createSequentialGroup()
                                .addComponent(lblSenhaAtual)
                                .addGap(112, 112, 112)
                                .addComponent(lblSenhaNova)))
                        .addGap(0, 114, Short.MAX_VALUE))
                    .addGroup(panelTrocaSenhaLayout.createSequentialGroup()
                        .addComponent(txtSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSenhaNova)))
                .addContainerGap())
        );
        panelTrocaSenhaLayout.setVerticalGroup(
            panelTrocaSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTrocaSenhaLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTrocaSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSenhaAtual)
                    .addComponent(lblSenhaNova))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTrocaSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenhaNova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(btnGravar))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTrocaSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTrocaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(FrmTrocarSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmTrocarSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmTrocarSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmTrocarSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmTrocarSenha dialog = new FrmTrocarSenha(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravar;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblSenhaAtual;
    private javax.swing.JLabel lblSenhaNova;
    private javax.swing.JPanel panelTrocaSenha;
    private javax.swing.JTextField txtNome;
    private javax.swing.JPasswordField txtSenhaAtual;
    private javax.swing.JPasswordField txtSenhaNova;
    // End of variables declaration//GEN-END:variables
}
