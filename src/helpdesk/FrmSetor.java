/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;


import helpdesk.beans.Empresa;
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
import helpdesk.beans.Setor;
import helpdesk.beans.Usuario;
import helpdesk.controllers.EmpresaController;
import helpdesk.controllers.SetorController;
import helpdesk.utils.AutoCompleteTextField;
import helpdesk.utils.Utilidades;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class FrmSetor extends javax.swing.JDialog {

    /**
     * Creates new form FrmConfig
     */
    List<String>listaDeSetores;
    public FrmSetor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        listaDeSetores=new ArrayList<>();
        for(Setor s: new SetorController().listar()){
            listaDeSetores.add(s.getDescricao()+" codigo"+s.getId());
        }
        initComponents();  
        carregarCombos();
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("Setor")));        
        tabCadSetor.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabCadSetor.getSelectedIndex()==0){
                    txtCodigoAlt.setText("");
                    txtCodigoAlt.setEnabled(true);
                    txtDescricaoAlt.setText("");                    
                }else{
                    txtDescricao.setText("");                    
                }
            }
        });
        
        btnGravarSetor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    Setor setor =new Setor();
                    setor.setDescricao(txtDescricao.getText());                    
                    setor.setEmpresa(Long.valueOf(String.valueOf(cmbEmpresa.getSelectedItem().toString().subSequence(0, cmbEmpresa.getSelectedItem().toString().indexOf("-")))));
                    new SetorController().insert(setor);
                    txtCodigo.setText(String.valueOf(Integer.valueOf(txtCodigo.getText())+1));
                    txtDescricao.setText("");                    
                }catch(Exception e){                    
                    e.printStackTrace();
                }
            }
        });
        
        btnGravarSetorAlt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(txtCodigoAlt.getText().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Selecione um setor para alterar.");
                }else{
                    try{
                        Setor setor= new Setor();
                        setor.setId((long)Integer.valueOf(txtCodigoAlt.getText().toString()));
                        setor.setDescricao(txtDescricaoAlt.getText().toString());
                        setor.setEmpresa(Long.valueOf(String.valueOf(cmbEmpresaAlt.getSelectedItem().toString().subSequence(0, cmbEmpresaAlt.getSelectedItem().toString().indexOf("-")))));                        
                        new SetorController().update(setor);
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
            Setor u=new SetorController().buscarPorID(id);
            txtDescricaoAlt.setText(u.getDescricao());
            String empresa="";            
            for(int i=0;i<cmbEmpresaAlt.getItemCount();i++){
                cmbEmpresaAlt.setSelectedIndex(i);
                if(cmbEmpresaAlt.getSelectedItem().toString().substring(0,cmbEmpresaAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getEmpresa()))){
                    empresa=cmbEmpresaAlt.getSelectedItem().toString();   
                    break;
                }
            }
            cmbEmpresaAlt.setSelectedItem(empresa);
            txtCodigoAlt.setEnabled(false);                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Setor não encontrado.");
        }
    }
    private void carregarCombos(){
        cmbEmpresa.removeAllItems();                
        cmbEmpresaAlt.removeAllItems();                
        for(Empresa e:new EmpresaController().listar()){
            cmbEmpresa.addItem(e.getId()+"-"+e.getDescricao());
            cmbEmpresaAlt.addItem(e.getId()+"-"+e.getDescricao());
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

        tabCadSetor = new javax.swing.JTabbedPane();
        painelCadSetor = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnGravarSetor = new javax.swing.JButton();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        lblEmpresa = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox();
        painelAlteraSetor = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        lblDescricaoAlt = new javax.swing.JLabel();
        txtDescricaoAlt = new AutoCompleteTextField(5,false,listaDeSetores);
        btnCancelar = new javax.swing.JButton();
        btnGravarSetorAlt = new javax.swing.JButton();
        lblEmpresaAlt = new javax.swing.JLabel();
        cmbEmpresaAlt = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Setores");
        setResizable(false);

        painelCadSetor.setBackground(java.awt.Color.lightGray);

        jPanel1.setBackground(java.awt.Color.lightGray);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do setor"));

        btnGravarSetor.setText("Gravar");

        lblDescricao.setText("Descrição:");

        txtCodigo.setText("1");
        txtCodigo.setEnabled(false);

        lblCodigo.setText("Código:");

        lblEmpresa.setText("Empresa:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescricao)
                            .addComponent(lblCodigo)
                            .addComponent(lblEmpresa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 259, Short.MAX_VALUE))
                            .addComponent(txtDescricao)
                            .addComponent(cmbEmpresa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravarSetor)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmpresa)
                    .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(btnGravarSetor)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbEmpresa, txtCodigo, txtDescricao});

        javax.swing.GroupLayout painelCadSetorLayout = new javax.swing.GroupLayout(painelCadSetor);
        painelCadSetor.setLayout(painelCadSetorLayout);
        painelCadSetorLayout.setHorizontalGroup(
            painelCadSetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadSetorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelCadSetorLayout.setVerticalGroup(
            painelCadSetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadSetorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabCadSetor.addTab("Cadastro", painelCadSetor);

        painelAlteraSetor.setBackground(java.awt.Color.gray);

        jPanel2.setBackground(java.awt.Color.gray);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do setor"));

        lblCodigoAlt.setText("Código:");

        lblDescricaoAlt.setText("Descrição:");

        btnCancelar.setText("Cancelar");

        btnGravarSetorAlt.setText("Gravar");

        lblEmpresaAlt.setText("Empresa:");

        cmbEmpresaAlt.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblEmpresaAlt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbEmpresaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDescricaoAlt)
                                    .addComponent(lblCodigoAlt))
                                .addGap(3, 3, 3)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescricaoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGravarSetorAlt)))))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbEmpresaAlt, txtDescricaoAlt});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricaoAlt)
                    .addComponent(txtDescricaoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEmpresaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmpresaAlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGravarSetorAlt))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbEmpresaAlt, txtCodigoAlt, txtDescricaoAlt});

        javax.swing.GroupLayout painelAlteraSetorLayout = new javax.swing.GroupLayout(painelAlteraSetor);
        painelAlteraSetor.setLayout(painelAlteraSetorLayout);
        painelAlteraSetorLayout.setHorizontalGroup(
            painelAlteraSetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelAlteraSetorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelAlteraSetorLayout.setVerticalGroup(
            painelAlteraSetorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelAlteraSetorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabCadSetor.addTab("Alteração", painelAlteraSetor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCadSetor)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCadSetor)
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
            java.util.logging.Logger.getLogger(FrmSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmSetor dialog = new FrmSetor(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravarSetor;
    private javax.swing.JButton btnGravarSetorAlt;
    private javax.swing.JComboBox cmbEmpresa;
    private javax.swing.JComboBox cmbEmpresaAlt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblDescricaoAlt;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblEmpresaAlt;
    private javax.swing.JPanel painelAlteraSetor;
    private javax.swing.JPanel painelCadSetor;
    private javax.swing.JTabbedPane tabCadSetor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDescricaoAlt;
    // End of variables declaration//GEN-END:variables
}
