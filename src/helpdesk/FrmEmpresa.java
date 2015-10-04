package helpdesk;

import helpdesk.beans.Empresa;
import helpdesk.controllers.EmpresaController;
import helpdesk.utils.Utilidades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FrmEmpresa extends javax.swing.JDialog {
    
    public FrmEmpresa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("Empresa")));
        tbCadEmpresa.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tbCadEmpresa.getSelectedIndex()==0){
                    txtCodigoAlt.setText("");
                    txtCodigoAlt.setEnabled(true);
                    txtDescricaoAlt.setText("");                    
                }else{
                    txtDescricao.setText("");                    
                }
            }
        });
        btnGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    Empresa empresa=new Empresa();
                    empresa.setDescricao(txtDescricao.getText().toString());
                    new EmpresaController().insert(empresa);
                    txtCodigo.setText(String.valueOf(Integer.valueOf(txtCodigo.getText())+1));
                    txtDescricao.setText("");                    
                }catch(Exception e){}
            }
        });
        btnGravarAlt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    Empresa empresa=new Empresa();
                    empresa.setId(Long.valueOf(txtCodigoAlt.getText().toString()));
                    empresa.setDescricao(txtDescricaoAlt.getText().toString());
                    new EmpresaController().update(empresa);
                    txtCodigoAlt.setText("");                    
                    txtDescricaoAlt.setText("");                    
                    txtCodigoAlt.setEnabled(true);
                }catch(Exception e){}
            }
        });
        txtCodigoAlt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                    buscarPorID(Long.valueOf(txtCodigoAlt.getText().toString()));
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                
            }
        });
        btnCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                txtCodigoAlt.setText("");
                txtDescricaoAlt.setText("");
                txtCodigoAlt.setEnabled(true);
                
            }
        });
    }
    private void buscarPorID(long id){
        try{
            Empresa e=new EmpresaController().buscarPorID(id);
            txtDescricaoAlt.setText(e.getDescricao().toString());
            txtCodigoAlt.setEnabled(false);
        }catch(Exception e){}
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbCadEmpresa = new javax.swing.JTabbedPane();
        panelEmpresaCad = new javax.swing.JPanel();
        panelCadEmpresa = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        btnGravar = new javax.swing.JButton();
        panelEmpresaAlt = new javax.swing.JPanel();
        panelCadEmpresa1 = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        lblDescricaoAlt = new javax.swing.JLabel();
        txtDescricaoAlt = new javax.swing.JTextField();
        btnGravarAlt = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Empresa");
        setResizable(false);

        panelEmpresaCad.setBackground(java.awt.Color.lightGray);

        panelCadEmpresa.setBackground(java.awt.Color.lightGray);
        panelCadEmpresa.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da empresa"));

        lblCodigo.setText("Codigo:");

        txtCodigo.setEnabled(false);

        lblDescricao.setText("Descricao:");

        btnGravar.setText("Gravar");

        javax.swing.GroupLayout panelCadEmpresaLayout = new javax.swing.GroupLayout(panelCadEmpresa);
        panelCadEmpresa.setLayout(panelCadEmpresaLayout);
        panelCadEmpresaLayout.setHorizontalGroup(
            panelCadEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadEmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadEmpresaLayout.createSequentialGroup()
                        .addGroup(panelCadEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescricao)
                            .addComponent(lblCodigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCadEmpresaLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 174, Short.MAX_VALUE))
                            .addComponent(txtDescricao)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadEmpresaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravar)))
                .addContainerGap())
        );
        panelCadEmpresaLayout.setVerticalGroup(
            panelCadEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadEmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGravar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEmpresaCadLayout = new javax.swing.GroupLayout(panelEmpresaCad);
        panelEmpresaCad.setLayout(panelEmpresaCadLayout);
        panelEmpresaCadLayout.setHorizontalGroup(
            panelEmpresaCadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpresaCadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCadEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEmpresaCadLayout.setVerticalGroup(
            panelEmpresaCadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpresaCadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCadEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbCadEmpresa.addTab("Cadastro", panelEmpresaCad);

        panelEmpresaAlt.setBackground(java.awt.Color.gray);

        panelCadEmpresa1.setBackground(java.awt.Color.gray);
        panelCadEmpresa1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da empresa"));

        lblCodigoAlt.setText("Codigo:");

        lblDescricaoAlt.setText("Descricao:");

        btnGravarAlt.setText("Gravar");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout panelCadEmpresa1Layout = new javax.swing.GroupLayout(panelCadEmpresa1);
        panelCadEmpresa1.setLayout(panelCadEmpresa1Layout);
        panelCadEmpresa1Layout.setHorizontalGroup(
            panelCadEmpresa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadEmpresa1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadEmpresa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadEmpresa1Layout.createSequentialGroup()
                        .addGroup(panelCadEmpresa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescricaoAlt)
                            .addComponent(lblCodigoAlt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadEmpresa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCadEmpresa1Layout.createSequentialGroup()
                                .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 174, Short.MAX_VALUE))
                            .addComponent(txtDescricaoAlt)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadEmpresa1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGravarAlt)))
                .addContainerGap())
        );
        panelCadEmpresa1Layout.setVerticalGroup(
            panelCadEmpresa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadEmpresa1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadEmpresa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadEmpresa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricaoAlt)
                    .addComponent(txtDescricaoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCadEmpresa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravarAlt)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEmpresaAltLayout = new javax.swing.GroupLayout(panelEmpresaAlt);
        panelEmpresaAlt.setLayout(panelEmpresaAltLayout);
        panelEmpresaAltLayout.setHorizontalGroup(
            panelEmpresaAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpresaAltLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCadEmpresa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEmpresaAltLayout.setVerticalGroup(
            panelEmpresaAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpresaAltLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCadEmpresa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbCadEmpresa.addTab("Alteracao", panelEmpresaAlt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbCadEmpresa)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbCadEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(FrmEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmEmpresa dialog = new FrmEmpresa(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnGravarAlt;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblDescricaoAlt;
    private javax.swing.JPanel panelCadEmpresa;
    private javax.swing.JPanel panelCadEmpresa1;
    private javax.swing.JPanel panelEmpresaAlt;
    private javax.swing.JPanel panelEmpresaCad;
    private javax.swing.JTabbedPane tbCadEmpresa;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDescricaoAlt;
    // End of variables declaration//GEN-END:variables
}
