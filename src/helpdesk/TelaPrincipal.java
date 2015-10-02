/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;

import helpdesk.beans.Usuario;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author daniel
 */
public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    Usuario u;
    public TelaPrincipal() {
        initComponents();
    }

    TelaPrincipal(Usuario usuario) {
        this();
        menuFecharChamado.setVisible(String.valueOf(usuario.getPerfil()).equals("1")||String.valueOf(usuario.getPerfil()).equals("3"));
        menuConsultas.setVisible(String.valueOf(usuario.getPerfil()).equals("1")||String.valueOf(usuario.getPerfil()).equals("3"));        
        menuCadastro.setVisible(String.valueOf(usuario.getPerfil()).equals("1")||String.valueOf(usuario.getPerfil()).equals("3"));        
        menuTrocarSenha.setVisible(String.valueOf(usuario.getPerfil()).equals("2"));        
        try{
            u=usuario;
            this.setTitle("Sistema HelpDesk Versão 1.0 " +usuario.getPerfil()+" "+usuario.getNomeCompleto());
            menuCadForncedor.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmFornecedor f= new FrmFornecedor(TelaPrincipal.this, false);
                    f.setLocation(300,100);
                    f.show();
                }
            });
            menuCadMarca.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmMarca f= new FrmMarca(TelaPrincipal.this, false);
                    f.setLocation(300,100);
                    f.show();
                }
            });
            menuCadProblema.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmProblema f= new FrmProblema(TelaPrincipal.this, false);
                    f.setLocation(300,100);
                    f.show();
                }
            });
            menuCadEmpresa.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmEmpresa f= new FrmEmpresa(TelaPrincipal.this,false);
                    f.setLocation(300,100);
                    f.show();
                }
            });
            menuCadSetor.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmSetor f= new FrmSetor(TelaPrincipal.this, false);
                    f.setLocation(300,100);
                    f.show();
                }
            });
            menuCadSistemasOP.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmSistemaOP f= new FrmSistemaOP(TelaPrincipal.this, false);
                    f.setLocation(300,100);
                    f.show();
                }
            });
            menuCadUsuario.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmUsuarios f= new FrmUsuarios(TelaPrincipal.this, false);
                    f.setLocation(250,100);
                    f.show();
                }
            });
            menuCadEquip.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmEquipamento f= new FrmEquipamento(TelaPrincipal.this, false);
                    f.setLocation(300,50);
                    f.show();
                }
            });
            menuCadOffice.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmOffice f= new FrmOffice(TelaPrincipal.this, false);
                    f.setLocation(300,100);
                    f.show();
                }
            });
            menuAbrirChamado.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmChamado f= new FrmChamado(TelaPrincipal.this, u,0);
                    f.setLocation(200,100);
                    f.show();                    
                }
            });
            
            menuConsultaChamado.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    FrmConsultaChamados f = new FrmConsultaChamados(TelaPrincipal.this,u);
                    f.setLocation(300, 50);
                    f.show();
                }
            });
            
            menuFecharChamado.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmChamado f= new FrmChamado(TelaPrincipal.this,u,1);
                    f.setLocation(200,100);
                    f.show();                    
                }
            });
            menuConsultaEquip.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmConsultaEquipamentos f= new FrmConsultaEquipamentos(TelaPrincipal.this, false);
                    f.setLocation(300,50);
                    f.show();                    
                }
            });
            menuConsultaImpressoras.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmConsultaImpressora f= new FrmConsultaImpressora(TelaPrincipal.this, false);
                    f.setLocation(300,50);
                    f.show();                    
                }
            });
            menuCadImpressoras.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    FrmImpressora f= new FrmImpressora(TelaPrincipal.this, false);
                    f.setLocation(200,100);
                    f.show();                    
                }
            });
            
            menuTrocarSenha.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    FrmTrocarSenha f=new FrmTrocarSenha(TelaPrincipal.this, false, u);
                    f.setLocation(300, 50);
                    f.show();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            });
                                    
            menuSair.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    int resposta=0;
                    resposta=JOptionPane.showConfirmDialog(null, "Deseja sair do sistema? ");
                    if(resposta==0){
                       TelaPrincipal.this.dispose();                       
                       FrmLogin f=new FrmLogin(TelaPrincipal.this, true);
                       f.setLocation(500,250);
                       f.show();
                       dispose();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
         
        }catch(Exception e){
            e.printStackTrace();
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

        jMenuBar1 = new javax.swing.JMenuBar();
        menuCadastro = new javax.swing.JMenu();
        menuCadEquip = new javax.swing.JMenuItem();
        menuCadForncedor = new javax.swing.JMenuItem();
        menuCadEmpresa = new javax.swing.JMenuItem();
        menuCadSetor = new javax.swing.JMenuItem();
        menuCadMarca = new javax.swing.JMenuItem();
        menuCadOffice = new javax.swing.JMenuItem();
        menuCadProblema = new javax.swing.JMenuItem();
        menuCadSistemasOP = new javax.swing.JMenuItem();
        menuCadUsuario = new javax.swing.JMenuItem();
        menuCadImpressoras = new javax.swing.JMenuItem();
        menuChamado = new javax.swing.JMenu();
        menuAbrirChamado = new javax.swing.JMenuItem();
        menuConsultaChamado = new javax.swing.JMenuItem();
        menuFecharChamado = new javax.swing.JMenuItem();
        menuConsultas = new javax.swing.JMenu();
        menuConsultaEquip = new javax.swing.JMenuItem();
        menuConsultaImpressoras = new javax.swing.JMenuItem();
        menuTrocarSenha = new javax.swing.JMenu();
        menuSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuCadastro.setText("Cadastro");

        menuCadEquip.setText("Equipamentos");
        menuCadastro.add(menuCadEquip);

        menuCadForncedor.setText("Fornecedores");
        menuCadastro.add(menuCadForncedor);

        menuCadEmpresa.setText("Empresas");
        menuCadastro.add(menuCadEmpresa);

        menuCadSetor.setText("Setores");
        menuCadastro.add(menuCadSetor);

        menuCadMarca.setText("Marcas");
        menuCadastro.add(menuCadMarca);

        menuCadOffice.setText("Office");
        menuCadastro.add(menuCadOffice);

        menuCadProblema.setText("Problemas");
        menuCadastro.add(menuCadProblema);

        menuCadSistemasOP.setText("Sistemas Operacionais");
        menuCadastro.add(menuCadSistemasOP);

        menuCadUsuario.setText("Usuários");
        menuCadastro.add(menuCadUsuario);

        menuCadImpressoras.setActionCommand("Impressoras");
        menuCadImpressoras.setLabel("Impressoras");
        menuCadImpressoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadImpressorasActionPerformed(evt);
            }
        });
        menuCadastro.add(menuCadImpressoras);

        jMenuBar1.add(menuCadastro);

        menuChamado.setText("Chamado");

        menuAbrirChamado.setText("Abrir Chamado");
        menuChamado.add(menuAbrirChamado);

        menuConsultaChamado.setText("Consultar Chamado");
        menuChamado.add(menuConsultaChamado);

        menuFecharChamado.setText("Fechar Chamado");
        menuChamado.add(menuFecharChamado);

        jMenuBar1.add(menuChamado);

        menuConsultas.setText("Consultas");

        menuConsultaEquip.setText("Equipamentos");
        menuConsultas.add(menuConsultaEquip);

        menuConsultaImpressoras.setText("Impressoras");
        menuConsultas.add(menuConsultaImpressoras);

        jMenuBar1.add(menuConsultas);

        menuTrocarSenha.setText("Alterar senha");
        jMenuBar1.add(menuTrocarSenha);

        menuSair.setText("Sair");
        jMenuBar1.add(menuSair);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 293, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCadImpressorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadImpressorasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuCadImpressorasActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuAbrirChamado;
    private javax.swing.JMenuItem menuCadEmpresa;
    private javax.swing.JMenuItem menuCadEquip;
    private javax.swing.JMenuItem menuCadForncedor;
    private javax.swing.JMenuItem menuCadImpressoras;
    private javax.swing.JMenuItem menuCadMarca;
    private javax.swing.JMenuItem menuCadOffice;
    private javax.swing.JMenuItem menuCadProblema;
    private javax.swing.JMenuItem menuCadSetor;
    private javax.swing.JMenuItem menuCadSistemasOP;
    private javax.swing.JMenuItem menuCadUsuario;
    private javax.swing.JMenu menuCadastro;
    private javax.swing.JMenu menuChamado;
    private javax.swing.JMenuItem menuConsultaChamado;
    private javax.swing.JMenuItem menuConsultaEquip;
    private javax.swing.JMenuItem menuConsultaImpressoras;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenuItem menuFecharChamado;
    private javax.swing.JMenu menuSair;
    private javax.swing.JMenu menuTrocarSenha;
    // End of variables declaration//GEN-END:variables
}
