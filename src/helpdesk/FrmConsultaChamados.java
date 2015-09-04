/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;

import helpdesk.beans.Chamado;
import helpdesk.beans.Setor;
import helpdesk.beans.Usuario;
import helpdesk.controllers.ChamadoController;
import helpdesk.controllers.SetorController;
import helpdesk.controllers.UsuarioController;
import helpdesk.utils.ChamadoTableModel;
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
public class FrmConsultaChamados extends javax.swing.JDialog {

    /**
     * Creates new form FrmConsultaChamados
     */
    private ChamadoTableModel tb;
    private List<Chamado>psNovo=new ArrayList<>();
    private Usuario usuario;
    
    public FrmConsultaChamados(Frame f,Usuario u){
        
        this(f,false);
        usuario=u;
        cmbSetor.setEnabled(String.valueOf(usuario.getPerfil()).equals("2")?false:true);        
        carregaCombos();    
        carregaChamados();
    }
    public FrmConsultaChamados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        tb=new ChamadoTableModel();             
        initComponents();        
        tblChamados.getTableHeader().setReorderingAllowed(false);        
        
        tblChamados.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if(tblChamados.getRowCount()>=1){                    
                    if(me.getClickCount()==2 && (String.valueOf(usuario.getPerfil()).equals("1")||String.valueOf(usuario.getPerfil()).equals("3"))){                        
                         FrmChamado f= new FrmChamado((long)Integer.valueOf(String.valueOf(tblChamados.getValueAt(tblChamados.getSelectedRow(),0))),usuario);
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
                boolean precisaAgrupar=false;
                if(String.valueOf(usuario.getPerfil()).equals("2")){
                    filtro.append(" and s.id= ");
                    filtro.append(String.valueOf(usuario.getIdsetor()));                    
                }
                if(chkFinalizado.isSelected()){
                    if(chkPendente.isSelected()){
                        filtro.append(" and (c.status='F'");
                        filtro.append(" or c.status='P')");
                        precisaAgrupar=true;
                    }else{
                        filtro.append(" and c.status='F'");
                    }
                }else{                
                    if(chkPendente.isSelected()){
                        filtro.append(" and c.status='P'");
                    }
                }
                if(!cmbSetor.getSelectedItem().toString().equals("")){
                    filtro.append(" and s.id= ");
                    filtro.append(cmbSetor.getSelectedItem().toString().substring(0, cmbSetor.getSelectedItem().toString().indexOf("-")));
                }
                if(!cmbUsuario.getSelectedItem().toString().equals("")){
                    filtro.append(" and u.id= ");
                    filtro.append(cmbUsuario.getSelectedItem().toString().substring(0, cmbUsuario.getSelectedItem().toString().indexOf("-")));
                }
                if(!cmbPrioridade.getSelectedItem().toString().equals("")){
                    filtro.append(" and c.prioridade= ");
                    filtro.append(cmbPrioridade.getSelectedIndex());
                }
                if(!cmbOperador.getSelectedItem().toString().equals("")){
                    filtro.append(" and c.operador= '");
                    filtro.append(cmbOperador.getSelectedItem().toString().substring(cmbOperador.getSelectedItem().toString().indexOf("-")+1,cmbOperador.getSelectedItem().toString().length()).toUpperCase());
                    filtro.append("' ");
                }
                if(txtDataInicio.getDate()!=null){
                    if(txtDataFinal.getDate()!=null){
                        filtro.append("and (c.dataAbertura between '");
                        filtro.append(new SimpleDateFormat("yyyy-MM-dd").format(txtDataInicio.getDate()));
                        filtro.append("' and '");
                        filtro.append(new SimpleDateFormat("yyyy-MM-dd").format(txtDataFinal.getDate()));
                        filtro.append("') ");
                        filtro.append(precisaAgrupar?" group by c.id":" ");
                        filtro.append(" order by c.id");
                        carregaChamadosComFiltro(new ChamadoController().buscarChamados(filtro.toString()));                
                    }else{
                        JOptionPane.showMessageDialog(null, "Informe um intervalo de datas válido para a pesquisa");
                    }
                }else{                
                    filtro.append(precisaAgrupar?" group by c.id":" ");
                    filtro.append(" order by c.id");
                    carregaChamadosComFiltro(new ChamadoController().buscarChamados(filtro.toString()));                
                }
            }
        });
    }
    private void carregaCombos(){
        cmbSetor.removeAllItems();
        cmbUsuario.removeAllItems();
        cmbSetor.addItem("");
        cmbUsuario.addItem("");
        cmbPrioridade.addItem("");
        cmbOperador.addItem("");
        List<Setor>setores=new SetorController().listar();
        for(Setor s:setores){
            cmbSetor.addItem(s.getId()+"-"+s.getDescricao());
        }
        if(String.valueOf(usuario.getPerfil()).equals("2")){
            List<Usuario>usuarios=new UsuarioController().listarDoSetor(usuario.getIdsetor());
            for(Usuario u:usuarios){
                cmbUsuario.addItem(u.getId()+"-"+u.getNomeCompleto());
            }
        }else{
            List<Usuario>usuarios=new UsuarioController().listar();
            for(Usuario u:usuarios){
                cmbUsuario.addItem(u.getId()+"-"+u.getNomeCompleto());
            }
        }
        List<Usuario>operadores;
        operadores=new UsuarioController().listarOperadores();        
        for(Usuario operador:operadores){
            cmbOperador.addItem(operador.getId()+"-"+operador.getLogin().toUpperCase());
        }
        cmbPrioridade.addItem("Baixa");
        cmbPrioridade.addItem("Média");
        cmbPrioridade.addItem("Alta");
        
    }
    
     private void carregaChamados(){                   
        psNovo.clear();
        tb=new ChamadoTableModel();
        tblChamados.setModel(tb);
        tblChamados.repaint();
        //itensAdicionadosEdicao.clear();
        //lblTotalAlt.setText("0");
        if(String.valueOf(usuario.getPerfil()).equals("2")){
            for(Chamado c:new ChamadoController().buscarChamados(usuario.getIdsetor())){
                psNovo.add(c);
                tb.addChamado(c);
                tblChamados.setModel(tb);
                tblChamados.updateUI();
                tblChamados.repaint();
            }                   
        }else{
            for(Chamado c:new ChamadoController().buscarChamados(0)){
                psNovo.add(c);
                tb.addChamado(c);
                tblChamados.setModel(tb);
                tblChamados.updateUI();
                tblChamados.repaint();
            }                   
        }
        
     }
     private void carregaChamadosComFiltro(List<Chamado> chamados){
        psNovo.clear();
        tb=new ChamadoTableModel();
        tblChamados.setModel(tb);
        tblChamados.repaint();
        //itensAdicionadosEdicao.clear();
        //lblTotalAlt.setText("0");
        for(Chamado c:chamados){
            psNovo.add(c);
            tb.addChamado(c);
            tblChamados.setModel(tb);
            tblChamados.updateUI();
            tblChamados.repaint();
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

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChamados = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        chkPendente = new javax.swing.JCheckBox();
        chkFinalizado = new javax.swing.JCheckBox();
        cmbSetor = new javax.swing.JComboBox();
        cmbUsuario = new javax.swing.JComboBox();
        lblSetor = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblPrioridade = new javax.swing.JLabel();
        lblOperador = new javax.swing.JLabel();
        cmbPrioridade = new javax.swing.JComboBox();
        cmbOperador = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        txtDataInicio = new com.toedter.calendar.JDateChooser();
        lblInicio = new javax.swing.JLabel();
        lblFinal = new javax.swing.JLabel();
        txtDataFinal = new com.toedter.calendar.JDateChooser();
        btnBuscar = new javax.swing.JButton();

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

        tblChamados.setModel(tb);
        jScrollPane1.setViewportView(tblChamados);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Situação"));

        chkPendente.setText("Pendente");

        chkFinalizado.setText("Finalizado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkPendente)
                    .addComponent(chkFinalizado))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(chkPendente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkFinalizado))
        );

        cmbSetor.setToolTipText("");

        lblSetor.setText("Setor:");

        lblUsuario.setText("Usuário:");

        lblPrioridade.setText("Prioridade:");

        lblOperador.setText("Operador:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Abertura"));

        lblInicio.setText("Inicio:");

        lblFinal.setText("Final:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInicio)
                    .addComponent(lblFinal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDataInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblInicio)
                    .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFinal)))
        );

        btnBuscar.setText("Buscar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSetor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblUsuario)
                                .addGap(18, 18, 18)
                                .addComponent(cmbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblOperador)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbOperador, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lblPrioridade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbPrioridade, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBuscar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrioridade)
                            .addComponent(cmbPrioridade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSetor))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuario)
                            .addComponent(lblOperador)
                            .addComponent(cmbOperador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(FrmConsultaChamados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmConsultaChamados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmConsultaChamados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmConsultaChamados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmConsultaChamados dialog = new FrmConsultaChamados(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox chkFinalizado;
    private javax.swing.JCheckBox chkPendente;
    private javax.swing.JComboBox cmbOperador;
    private javax.swing.JComboBox cmbPrioridade;
    private javax.swing.JComboBox cmbSetor;
    private javax.swing.JComboBox cmbUsuario;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFinal;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblOperador;
    private javax.swing.JLabel lblPrioridade;
    private javax.swing.JLabel lblSetor;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblChamados;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDataInicio;
    // End of variables declaration//GEN-END:variables
}
