/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import helpdesk.beans.SistemaOP;
import helpdesk.beans.Usuario;
import helpdesk.controllers.SistemaOPController;
import helpdesk.utils.AutoCompleteTextField;
import helpdesk.utils.Utilidades;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class FrmSistemaOP extends javax.swing.JDialog {

    /**
     * Creates new form FrmConfig
     */
    List<String>listaDeSistemas;
    public FrmSistemaOP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        listaDeSistemas= new ArrayList<>();
        for(SistemaOP op: new SistemaOPController().listar()){
            listaDeSistemas.add(op.getDescricao()+" codigo"+op.getId());
        }
        initComponents();  
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("SistemaOP")));        
        tabCadSisOP.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabCadSisOP.getSelectedIndex()==0){
                    txtCodigoAlt.setText("");
                    txtCodigoAlt.setEnabled(true);
                    txtDescricaoAlt.setText("");                    
                }else{
                    txtDescricao.setText("");                    
                }
            }
        });
        
        btnGravarSisOP.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    SistemaOP sistemaOP =new SistemaOP();
                    sistemaOP.setDescricao(txtDescricao.getText());                    
                    new SistemaOPController().insert(sistemaOP);
                    txtCodigo.setText(String.valueOf(Integer.valueOf(txtCodigo.getText())+1));
                    txtDescricao.setText("");                    
                }catch(Exception e){                    
                    e.printStackTrace();
                }
            }
        });
        
        btnGravarSisOPAlt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(txtCodigoAlt.getText().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Selecione um sistema operacional para alterar.");
                }else{
                    try{
                        SistemaOP sistemaOP= new SistemaOP();
                        sistemaOP.setId((long)Integer.valueOf(txtCodigoAlt.getText().toString()));
                        sistemaOP.setDescricao(txtDescricaoAlt.getText().toString());
                        //fornecedor.setPreco(Double.valueOf(txtPrecoAlt.getText().toString()));
                        new SistemaOPController().update(sistemaOP);
                        txtCodigoAlt.setEnabled(true);
                        txtCodigoAlt.setText("");
                        txtDescricaoAlt.setText("");                        
                   }catch(Exception e){
                       JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar\n Tente novamente.");
                       
                   }
               }
            }
        });
        
        txtCodigoAlt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
                if (ke.getKeyCode()==10){                                        
                    buscaPorID(Long.valueOf(txtCodigoAlt.getText()));
                }  
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                txtCodigoAlt.setEnabled(true);
                txtCodigoAlt.setText("");
                txtDescricaoAlt.setText("");
                //txtPrecoAlt.setText("");
                        
            }
        });
        txtDescricaoAlt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(txtDescricaoAlt.getText().contains("codigo")){
                    String texto=txtDescricaoAlt.getText();
                    texto=texto.substring(texto.indexOf("codigo"),texto.length());
                    texto=texto.substring(texto.indexOf("go")+2,texto.length());
                    buscaPorID(Long.valueOf(texto));                        
                    txtCodigoAlt.setText(texto);
                    txtCodigoAlt.setEnabled(false);                        
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    private void buscaPorID(long id){
        try{
            SistemaOP u=new SistemaOPController().buscarPorID(id);
            txtDescricaoAlt.setText(u.getDescricao());
            txtCodigoAlt.setEnabled(false);                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Sistema Operacional não encontrado.");
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

        tabCadSisOP = new javax.swing.JTabbedPane();
        painelCadSisOP = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        btnGravarSisOP = new javax.swing.JButton();
        painelAlteraSisOP = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        txtDescricaoAlt = new AutoCompleteTextField(5,false,listaDeSistemas);
        lblDescricaoAlt = new javax.swing.JLabel();
        btnGravarSisOPAlt = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Sistemas Operacionais");
        setResizable(false);

        painelCadSisOP.setBackground(java.awt.Color.lightGray);

        lblCodigo.setText("Código");

        txtCodigo.setText("1");
        txtCodigo.setEnabled(false);

        lblDescricao.setText("Descrição");

        btnGravarSisOP.setText("Gravar");

        javax.swing.GroupLayout painelCadSisOPLayout = new javax.swing.GroupLayout(painelCadSisOP);
        painelCadSisOP.setLayout(painelCadSisOPLayout);
        painelCadSisOPLayout.setHorizontalGroup(
            painelCadSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadSisOPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCadSisOPLayout.createSequentialGroup()
                        .addGroup(painelCadSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescricao)
                            .addComponent(lblCodigo))
                        .addGap(3, 3, 3)
                        .addGroup(painelCadSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescricao)
                            .addGroup(painelCadSisOPLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 307, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCadSisOPLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravarSisOP)))
                .addContainerGap())
        );
        painelCadSisOPLayout.setVerticalGroup(
            painelCadSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadSisOPLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(painelCadSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnGravarSisOP)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tabCadSisOP.addTab("Cadastro", painelCadSisOP);

        painelAlteraSisOP.setBackground(java.awt.Color.gray);

        lblCodigoAlt.setText("Código");

        lblDescricaoAlt.setText("Descrição");

        btnGravarSisOPAlt.setText("Gravar");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout painelAlteraSisOPLayout = new javax.swing.GroupLayout(painelAlteraSisOP);
        painelAlteraSisOP.setLayout(painelAlteraSisOPLayout);
        painelAlteraSisOPLayout.setHorizontalGroup(
            painelAlteraSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraSisOPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelAlteraSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelAlteraSisOPLayout.createSequentialGroup()
                        .addGroup(painelAlteraSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescricaoAlt)
                            .addComponent(lblCodigoAlt))
                        .addGap(3, 3, 3)
                        .addGroup(painelAlteraSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescricaoAlt)
                            .addGroup(painelAlteraSisOPLayout.createSequentialGroup()
                                .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 307, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelAlteraSisOPLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGravarSisOPAlt)))
                .addContainerGap())
        );
        painelAlteraSisOPLayout.setVerticalGroup(
            painelAlteraSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraSisOPLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(painelAlteraSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelAlteraSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricaoAlt)
                    .addComponent(txtDescricaoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(painelAlteraSisOPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravarSisOPAlt)
                    .addComponent(btnCancelar))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tabCadSisOP.addTab("Alteração", painelAlteraSisOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCadSisOP)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCadSisOP, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(FrmSistemaOP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSistemaOP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSistemaOP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSistemaOP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmSistemaOP dialog = new FrmSistemaOP(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravarSisOP;
    private javax.swing.JButton btnGravarSisOPAlt;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblDescricaoAlt;
    private javax.swing.JPanel painelAlteraSisOP;
    private javax.swing.JPanel painelCadSisOP;
    private javax.swing.JTabbedPane tabCadSisOP;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDescricaoAlt;
    // End of variables declaration//GEN-END:variables
}
