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
import helpdesk.beans.Marca;
import helpdesk.beans.Usuario;
import helpdesk.controllers.MarcaController;
import helpdesk.utils.AutoCompleteTextField;
import helpdesk.utils.Utilidades;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class FrmMarca extends javax.swing.JDialog {

    /**
     * Creates new form FrmConfig
     */
    private List<String>listaDeMarcas;
    public FrmMarca(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        listaDeMarcas= new ArrayList<>();
        for(Marca m:new MarcaController().listar()){
            listaDeMarcas.add(m.getDescricao()+" codigo"+m.getId());
        }
        initComponents();  
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("Marca")));        
        tabCadMarca.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabCadMarca.getSelectedIndex()==0){
                    txtCodigoAlt.setText("");
                    txtCodigoAlt.setEnabled(true);
                    txtDescricaoAlt.setText("");                    
                }else{
                    txtDescricao.setText("");                    
                }
            }
        });
        
        btnGravarMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    Marca marca =new Marca();
                    marca.setDescricao(txtDescricao.getText());
                    //fornecedor.setPreco(Double.valueOf(txtPreco.getText().toString()));
                    new MarcaController().insert(marca);
                    txtCodigo.setText(String.valueOf(Integer.valueOf(txtCodigo.getText())+1));
                    txtDescricao.setText("");                    
                }catch(Exception e){                    
                    e.printStackTrace();
                }
            }
        });
        
        btnGravarMarcaAlt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(txtCodigoAlt.getText().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Selecione uma marca para alterar.");
                }else{
                    try{
                        Marca marca= new Marca();
                        marca.setId((long)Integer.valueOf(txtCodigoAlt.getText().toString()));
                        marca.setDescricao(txtDescricaoAlt.getText().toString());
                        //fornecedor.setPreco(Double.valueOf(txtPrecoAlt.getText().toString()));
                        new MarcaController().update(marca);
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
                    //JOptionPane.showMessageDialog(null, "Você pressionou Enter");
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
            }
        });
        
        txtDescricaoAlt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==10){
                    if(txtDescricaoAlt.getText().contains("codigo")){
                        String texto=txtDescricaoAlt.getText();
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
        });
    }
    private void buscaPorID(long id){
        try{
            Marca u=new MarcaController().buscarPorID(id);
            txtDescricaoAlt.setText(u.getDescricao());
            txtCodigoAlt.setEnabled(false);                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Marca não encontrado.");
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

        tabCadMarca = new javax.swing.JTabbedPane();
        painelCadMarca = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        btnGravarMarca = new javax.swing.JButton();
        painelAlteraMarca = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        txtDescricaoAlt = new AutoCompleteTextField(5,false,listaDeMarcas);
        lblDescricaoAlt = new javax.swing.JLabel();
        btnGravarMarcaAlt = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Marcas");
        setResizable(false);

        painelCadMarca.setBackground(java.awt.Color.lightGray);

        lblCodigo.setText("Código");

        txtCodigo.setText("1");
        txtCodigo.setEnabled(false);

        lblDescricao.setText("Descrição");

        btnGravarMarca.setText("Gravar");

        javax.swing.GroupLayout painelCadMarcaLayout = new javax.swing.GroupLayout(painelCadMarca);
        painelCadMarca.setLayout(painelCadMarcaLayout);
        painelCadMarcaLayout.setHorizontalGroup(
            painelCadMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadMarcaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCadMarcaLayout.createSequentialGroup()
                        .addGroup(painelCadMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescricao)
                            .addComponent(lblCodigo))
                        .addGap(3, 3, 3)
                        .addGroup(painelCadMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescricao)
                            .addGroup(painelCadMarcaLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 307, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCadMarcaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravarMarca)))
                .addContainerGap())
        );
        painelCadMarcaLayout.setVerticalGroup(
            painelCadMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadMarcaLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(painelCadMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnGravarMarca)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tabCadMarca.addTab("Cadastro", painelCadMarca);

        painelAlteraMarca.setBackground(java.awt.Color.gray);

        lblCodigoAlt.setText("Código");

        lblDescricaoAlt.setText("Descrição");

        btnGravarMarcaAlt.setText("Gravar");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout painelAlteraMarcaLayout = new javax.swing.GroupLayout(painelAlteraMarca);
        painelAlteraMarca.setLayout(painelAlteraMarcaLayout);
        painelAlteraMarcaLayout.setHorizontalGroup(
            painelAlteraMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraMarcaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelAlteraMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelAlteraMarcaLayout.createSequentialGroup()
                        .addGroup(painelAlteraMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescricaoAlt)
                            .addComponent(lblCodigoAlt))
                        .addGap(3, 3, 3)
                        .addGroup(painelAlteraMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescricaoAlt)
                            .addGroup(painelAlteraMarcaLayout.createSequentialGroup()
                                .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 307, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelAlteraMarcaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGravarMarcaAlt)))
                .addContainerGap())
        );
        painelAlteraMarcaLayout.setVerticalGroup(
            painelAlteraMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraMarcaLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(painelAlteraMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelAlteraMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricaoAlt)
                    .addComponent(txtDescricaoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(painelAlteraMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravarMarcaAlt)
                    .addComponent(btnCancelar))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tabCadMarca.addTab("Alteração", painelAlteraMarca);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCadMarca)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCadMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(FrmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmMarca dialog = new FrmMarca(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravarMarca;
    private javax.swing.JButton btnGravarMarcaAlt;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblDescricaoAlt;
    private javax.swing.JPanel painelAlteraMarca;
    private javax.swing.JPanel painelCadMarca;
    private javax.swing.JTabbedPane tabCadMarca;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDescricaoAlt;
    // End of variables declaration//GEN-END:variables
}
