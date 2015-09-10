/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;

import helpdesk.beans.Chamado;
import helpdesk.beans.Equipamento;
import helpdesk.beans.Impressora;
import helpdesk.beans.Setor;
import helpdesk.beans.Usuario;
import helpdesk.controllers.ChamadoController;
import helpdesk.controllers.EquipamentoController;
import helpdesk.controllers.ImpressoraController;
import helpdesk.controllers.SetorController;
import helpdesk.controllers.UsuarioController;
import helpdesk.utils.ChamadoTableModel;
import helpdesk.utils.EquipamentoTableModel;
import helpdesk.utils.ImpressoraTableModel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Admin
 */
public class FrmConsultaImpressora extends javax.swing.JDialog {

    /**
     * Creates new form FrmConsultaChamados
     */
    private ImpressoraTableModel tb;
    private List<Impressora>psNovo=new ArrayList<>();
    private Usuario usuario;
    private static int qtd;
    public FrmConsultaImpressora(Usuario u){        
        this(new Frame(),true);
        usuario=u;
        cmbSetor.setEnabled(String.valueOf(usuario.getPerfil()).equals("2")?false:true);                
    }
    public FrmConsultaImpressora(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        tb=new ImpressoraTableModel();             
        initComponents();     
        carregaCombos();    
        carregarImpressoras("");
        tblImpressoras.getTableHeader().setReorderingAllowed(false);        
        
        tblImpressoras.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if(tblImpressoras.getRowCount()>=1){                    
                    if(me.getClickCount()==2){                        
                        FrmImpressora f= new FrmImpressora((long)Integer.valueOf(String.valueOf(tblImpressoras.getValueAt(tblImpressoras.getSelectedRow(),0))));
                        f.setLocation(200,100);
                        f.show();     
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        btnBuscar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder filtro=new StringBuilder("");                
                if(!cmbSetor.getSelectedItem().toString().equals("")){
                    filtro.append(" and s.id= ");
                    filtro.append(cmbSetor.getSelectedItem().toString().substring(0, cmbSetor.getSelectedItem().toString().indexOf("-")));
                }
                carregarImpressoras(filtro.toString());                                
            }
        });
    }
    private void carregaCombos(){
        cmbSetor.removeAllItems();
        
        cmbSetor.addItem("");
        
        List<Setor>setores=new SetorController().listar();
        for(Setor s:setores){
            cmbSetor.addItem(s.getId()+"-"+s.getDescricao());
        }
        
        
    }
    
     private void carregarImpressoras(String filtro){                   
        psNovo.clear();
        tb=new ImpressoraTableModel();
        tblImpressoras.setModel(tb);
        tblImpressoras.repaint();    
        qtd=0;
        for(Impressora e:new ImpressoraController().buscarImpressoras(filtro)){
            psNovo.add(e);
            tb.addImpressora(e);
            tblImpressoras.setModel(tb);
            tblImpressoras.updateUI();
            tblImpressoras.repaint();
            qtd++;
        }                                
        lblQtd.setText(String.valueOf(qtd));
     }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblImpressoras = new javax.swing.JTable();
        cmbSetor = new javax.swing.JComboBox();
        lblSetor = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        lblQtd = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Chamados");

        tblImpressoras.setModel(tb);
        jScrollPane1.setViewportView(tblImpressoras);

        cmbSetor.setToolTipText("");

        lblSetor.setText("Setor:");

        btnBuscar.setText("Buscar");

        lblQtd.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblQtd)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSetor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(lblQtd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(FrmConsultaImpressora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmConsultaImpressora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmConsultaImpressora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmConsultaImpressora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmConsultaImpressora dialog = new FrmConsultaImpressora(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox cmbSetor;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblQtd;
    private javax.swing.JLabel lblSetor;
    private javax.swing.JTable tblImpressoras;
    // End of variables declaration//GEN-END:variables
}
