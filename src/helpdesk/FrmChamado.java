/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;


import helpdesk.beans.Chamado;
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
import helpdesk.beans.Equipamento;
import helpdesk.beans.Fornecedor;
import helpdesk.beans.Marca;
import helpdesk.beans.Office;
import helpdesk.beans.Problema;
import helpdesk.beans.SistemaOP;
import helpdesk.beans.Usuario;
import helpdesk.controllers.ChamadoController;
import helpdesk.controllers.EquipamentoController;
import helpdesk.controllers.FornecedorController;
import helpdesk.controllers.MarcaController;
import helpdesk.controllers.OfficeController;
import helpdesk.controllers.ProblemaController;
import helpdesk.controllers.SistemaOPController;
import helpdesk.controllers.UsuarioController;
import helpdesk.utils.Utilidades;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author daniel
 */
public class FrmChamado extends javax.swing.JDialog {

    /**
     * Creates new form FrmConfig
     */
    Usuario u;
    private char status;
    private long idUsuario;        
    private String dataRealizacao;
    public FrmChamado(Usuario usuario, int op){
        this(new Frame(),true);
        u=usuario;
        status="P".charAt(0);
        if(!String.valueOf(usuario.getPerfil()).equals("1")){
            tabChamado.removeTabAt(1);
        }else{
            tabChamado.setSelectedIndex(op);
        }
        
        
    }
    public FrmChamado(long codChamado, Usuario meuUsuario){
        this(new Frame(),true);
        this.u=meuUsuario;
        tabChamado.setSelectedIndex(1);
        txtCodigoAlt.setText(String.valueOf(codChamado));
        carregaChamado(codChamado);
        
    }
    public FrmChamado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();  
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("Chamado")));        
        txtDataAbertura.setDate(Calendar.getInstance().getTime());            
        carregaCombos();
        tabChamado.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabChamado.getSelectedIndex()==0){
                    limparAlteracao();                                        
                }else{
                    limparInclusao();                    
                }
            }
        });
        
        btnGravarChamado.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    Chamado chamado =new Chamado();
                    chamado.setUsuario(u.getId());
                    chamado.setDescricao(txtDescricao.getText());  
                    chamado.setProblema(Long.valueOf(String.valueOf(cmbProblema.getSelectedItem().toString().subSequence(0, cmbProblema.getSelectedItem().toString().indexOf("-")))));
                    chamado.setPrioridade(cmbPrioridade.getSelectedIndex()+1);
                    chamado.setDuracao("0");
                    chamado.setDataAbertura(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime()));
                    chamado.setDataRealizacao(new SimpleDateFormat("yyyy-MM-dd").format(new Date("01/01/0001")));
                    chamado.setOperador("");
                    chamado.setSolucao("");
                    chamado.setStatus("P".charAt(0));
                    chamado.setTipo("1");                   
                    new ChamadoController().insert(chamado);
                    txtCodigo.setText(String.valueOf(Integer.valueOf(txtCodigo.getText())+1));
                    limparInclusao();
                }catch(Exception e){                    
                    e.printStackTrace();
                }
            }
        });
        
        btnFecharChamado.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(txtCodigoAlt.getText().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Selecione um equipamento para Fechar.");
                }else{
                    try{
                        String auxOperador="";
                        auxOperador=cmbOperador.getSelectedItem().toString().substring(cmbOperador.getSelectedItem().toString().indexOf("-")+1, cmbOperador.getSelectedItem().toString().length()).toUpperCase();
                        Chamado chamado= new Chamado();
                        chamado.setId((long)Integer.valueOf(txtCodigoAlt.getText()));      
                        chamado.setUsuario(idUsuario);
                        chamado.setDescricao(txtDescricaoAlt.getText());  
                        chamado.setProblema(Long.valueOf(String.valueOf(cmbProblemaAlt.getSelectedItem().toString().subSequence(0, cmbProblemaAlt.getSelectedItem().toString().indexOf("-")))));
                        chamado.setPrioridade(cmbPrioridadeAlt.getSelectedIndex()+1);
                        chamado.setDuracao(txtTempo.getText());
                        chamado.setDataAbertura(new SimpleDateFormat("yyyy-MM-dd").format(txtDataAbertura.getDate()));
                        chamado.setDataRealizacao(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime()));
                        chamado.setOperador(auxOperador.equals("")?u.getLogin().toUpperCase():auxOperador);
                        chamado.setSolucao(txtSolucao.getText());
                        chamado.setStatus("F".charAt(0));
                        chamado.setTipo(String.valueOf(cmbTipo.getSelectedIndex()+1));                   
                        new ChamadoController().update(chamado);                        
                        JOptionPane.showMessageDialog(null, "Chamado fechado com sucesso!");
                        limparAlteracao();
                   }catch(Exception e){                       
                       JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar\n Tente novamente.");                       
                   }
               }
            }
        });        
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(txtCodigoAlt.getText().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Selecione um equipamento para alterar.");
                }else{
                    try{
                        Chamado chamado= new Chamado();
                        chamado.setId((long)Integer.valueOf(txtCodigoAlt.getText()));      
                        chamado.setUsuario(idUsuario);
                        chamado.setDescricao(txtDescricaoAlt.getText());  
                        chamado.setProblema(Long.valueOf(String.valueOf(cmbProblemaAlt.getSelectedItem().toString().subSequence(0, cmbProblemaAlt.getSelectedItem().toString().indexOf("-")))));
                        chamado.setPrioridade(cmbPrioridadeAlt.getSelectedIndex()+1);
                        chamado.setDuracao(txtTempo.getText());
                        chamado.setDataAbertura(new SimpleDateFormat("yyyy-MM-dd").format(txtDataAbertura.getDate()));
                        if(String.valueOf(status).equals("F")){
                            chamado.setDataRealizacao(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(dataRealizacao)));
                        }else{
                            chamado.setDataRealizacao(new SimpleDateFormat("yyyy-MM-dd").format(new Date("01/01/0001")));
                        }
                        chamado.setOperador(cmbOperador.getSelectedItem().toString().substring(cmbOperador.getSelectedItem().toString().indexOf("-")+1, cmbOperador.getSelectedItem().toString().length()).toUpperCase());
                        chamado.setSolucao(txtSolucao.getText());
                        chamado.setStatus(status);
                        chamado.setTipo(String.valueOf(cmbTipo.getSelectedIndex()+1));                   
                        new ChamadoController().update(chamado);                        
                        limparAlteracao();
                   }catch(Exception e){
                       e.printStackTrace();
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
                    carregaChamado((long)Integer.valueOf(txtCodigoAlt.getText()));
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
                limparAlteracao();
            }
        });
        
        
        
        
        
    }
    //<editor-fold desc="Métodos privates">
    private void carregaCombos(){
        cmbProblema.removeAllItems();        
        cmbProblemaAlt.removeAllItems();        
        cmbPrioridade.removeAllItems();      
        cmbOperador.removeAllItems();
        cmbTipo.removeAllItems();
        cmbPrioridadeAlt.removeAllItems();
        List<Problema>problemas;
        problemas=new ProblemaController().listar();
        for(Problema problema:problemas){
            cmbProblema.addItem(problema.getId()+"-"+problema.getDescricao());            
            cmbProblemaAlt.addItem(problema.getId()+"-"+problema.getDescricao());            
        }                
        List<Usuario>operadores;
        operadores=new UsuarioController().listarOperadores();
        cmbOperador.addItem("");
        for(Usuario operador:operadores){
            cmbOperador.addItem(operador.getId()+"-"+operador.getLogin().toUpperCase());
        }
        cmbPrioridade.addItem("Baixa");                   
        cmbPrioridade.addItem("Média");                   
        cmbPrioridade.addItem("Alta");                   
        
        cmbPrioridadeAlt.addItem("Baixa");                   
        cmbPrioridadeAlt.addItem("Média");                   
        cmbPrioridadeAlt.addItem("Alta");                   
        
        cmbTipo.addItem("Presencial");                   
        cmbTipo.addItem("Remoto");                   
        cmbTipo.addItem("Plantão");                   
    }   
    
    private void limparAlteracao(){
        txtCodigoAlt.setText("");
        txtCodigoAlt.setEnabled(true);
        txtDescricaoAlt.setText("");     
        txtSolucao.setText("");     
        txtDataRealizado.setDate(null);
        txtDataAberturaAlt.setDate(null);
        txtTempo.setText("");
    }
    private void limparInclusao(){        
        txtDescricao.setText("");               
        //txtDataAbertura.setDate(null);  
        Dimension d=new Dimension(510, 450);
        this.setPreferredSize(d);
    }
    private void carregaChamado(long id){
        try{
            String problem="",user="";

            Chamado u=new ChamadoController().buscarPorID(id);  
            idUsuario=u.getUsuario();
            status=String.valueOf(u.getStatus()).charAt(0);                        
            dataRealizacao=u.getDataRealizacao();
            btnFecharChamado.setEnabled(String.valueOf(u.getStatus()).equals("F")?false:true);                                                                            
            txtDescricaoAlt.setText(u.getDescricao());
            for(int i=0;i<cmbProblemaAlt.getItemCount();i++){
                cmbProblemaAlt.setSelectedIndex(i);
                if(cmbProblemaAlt.getSelectedItem().toString().substring(0,cmbProblemaAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getProblema()))){
                    problem=cmbProblemaAlt.getSelectedItem().toString();                                
                }                            
            }
            cmbProblemaAlt.setSelectedItem(problem);
            for(int i=0;i<cmbOperador.getItemCount();i++){
                cmbOperador.setSelectedIndex(i);
                if(!cmbOperador.getSelectedItem().toString().equals("")){                            
                    if(cmbOperador.getSelectedItem().toString().substring(cmbOperador.getSelectedItem().toString().indexOf("-")+1,cmbOperador.getSelectedItem().toString().length()).equals(u.getOperador().toUpperCase())){
                        user=cmbOperador.getSelectedItem().toString();                                
                    }                            
                }
            }
            cmbOperador.setSelectedItem(user);
            //cmbProblemaAlt.setSelectedIndex(Integer.valueOf(String.valueOf(u.getProblema()-1)));
            cmbPrioridadeAlt.setSelectedIndex(Integer.valueOf(String.valueOf(u.getPrioridade()-1)));                        
            txtDataAberturaAlt.setDate(new Date(u.getDataAbertura().substring(0, 10).replace("-", "/")));
            if(String.valueOf(u.getStatus()).equals("F")){
                txtDataRealizado.setDate(new Date(u.getDataRealizacao().substring(0, 10).replace("-", "/")));                        
                txtSolucao.setText(u.getSolucao());                            
            }
            cmbTipo.setSelectedIndex(Integer.valueOf(u.getTipo())-1);
            txtTempo.setText(u.getDuracao());
            txtCodigoAlt.setEnabled(false);                        
        }catch(Exception e){           
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Chamado não encontrado.");
        }
    }
    //</editor-fold>
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabChamado = new javax.swing.JTabbedPane();
        painelAbreChm = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        btnGravarChamado = new javax.swing.JButton();
        lblDataAbertura = new javax.swing.JLabel();
        txtDataAbertura = new com.toedter.calendar.JDateChooser();
        lblProblema = new javax.swing.JLabel();
        cmbProblema = new javax.swing.JComboBox();
        lblPrioridade = new javax.swing.JLabel();
        cmbPrioridade = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        painelAlteraEquip = new javax.swing.JPanel();
        painelFechaChm = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        lblProblemaAlt = new javax.swing.JLabel();
        cmbProblemaAlt = new javax.swing.JComboBox();
        cmbPrioridadeAlt = new javax.swing.JComboBox();
        lblPrioridadeAlt = new javax.swing.JLabel();
        lblDescricaoAlt = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescricaoAlt = new javax.swing.JTextArea();
        lblDataAberturaAlt = new javax.swing.JLabel();
        txtDataAberturaAlt = new com.toedter.calendar.JDateChooser();
        btnCancelar = new javax.swing.JButton();
        btnFecharChamado = new javax.swing.JButton();
        lblSolucao = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSolucao = new javax.swing.JTextArea();
        lblDataRelaizado = new javax.swing.JLabel();
        txtDataRealizado = new com.toedter.calendar.JDateChooser();
        lblDuracao = new javax.swing.JLabel();
        try{
            txtTempo = new javax.swing.JFormattedTextField(new MaskFormatter("##:##"));
            lblTipo = new javax.swing.JLabel();
            cmbTipo = new javax.swing.JComboBox();
            cmbOperador = new javax.swing.JComboBox();
            lblIndicarOperador = new javax.swing.JLabel();
            btnAtualizar = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setTitle("Manutenção de Chamados");

            tabChamado.setPreferredSize(new java.awt.Dimension(510, 200));

            painelAbreChm.setBackground(java.awt.Color.lightGray);

            lblCodigo.setText("Código");

            txtCodigo.setText("1");
            txtCodigo.setEnabled(false);

            lblDescricao.setText("Descrição do Problema:");

            btnGravarChamado.setText("Gravar");

            lblDataAbertura.setText("Data Abertura");

            txtDataAbertura.setEnabled(false);

            lblProblema.setText("Problema:");

            lblPrioridade.setText("Prioridade:");

            txtDescricao.setColumns(20);
            txtDescricao.setRows(5);
            jScrollPane1.setViewportView(txtDescricao);

            javax.swing.GroupLayout painelAbreChmLayout = new javax.swing.GroupLayout(painelAbreChm);
            painelAbreChm.setLayout(painelAbreChmLayout);
            painelAbreChmLayout.setHorizontalGroup(
                painelAbreChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelAbreChmLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(painelAbreChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblCodigo)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(painelAbreChmLayout.createSequentialGroup()
                            .addComponent(lblProblema)
                            .addGap(8, 8, 8)
                            .addComponent(cmbProblema, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painelAbreChmLayout.createSequentialGroup()
                            .addComponent(lblPrioridade)
                            .addGap(4, 4, 4)
                            .addComponent(cmbPrioridade, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblDescricao)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDataAbertura)
                        .addGroup(painelAbreChmLayout.createSequentialGroup()
                            .addComponent(txtDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(290, 290, 290)
                            .addComponent(btnGravarChamado))))
            );
            painelAbreChmLayout.setVerticalGroup(
                painelAbreChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelAbreChmLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(lblCodigo)
                    .addGap(3, 3, 3)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addGroup(painelAbreChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelAbreChmLayout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(lblProblema))
                        .addComponent(cmbProblema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(1, 1, 1)
                    .addGroup(painelAbreChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelAbreChmLayout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(lblPrioridade))
                        .addComponent(cmbPrioridade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(6, 6, 6)
                    .addComponent(lblDescricao)
                    .addGap(6, 6, 6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(lblDataAbertura)
                    .addGap(6, 6, 6)
                    .addGroup(painelAbreChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelAbreChmLayout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(txtDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnGravarChamado)))
            );

            tabChamado.addTab("Abertura", painelAbreChm);

            painelAlteraEquip.setBackground(java.awt.Color.gray);

            painelFechaChm.setBackground(java.awt.Color.gray);

            lblCodigoAlt.setText("Código");

            txtCodigoAlt.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtCodigoAltActionPerformed(evt);
                }
            });

            lblProblemaAlt.setText("Problema:");

            lblPrioridadeAlt.setText("Prioridade:");

            lblDescricaoAlt.setText("Descrição do Problema:");

            txtDescricaoAlt.setColumns(20);
            txtDescricaoAlt.setRows(5);
            jScrollPane2.setViewportView(txtDescricaoAlt);

            lblDataAberturaAlt.setText("Data Abertura");

            txtDataAberturaAlt.setEnabled(false);

            btnCancelar.setText("Cancelar");

            btnFecharChamado.setText("Fechar Chamado");

            lblSolucao.setText("Solução:");

            txtSolucao.setColumns(20);
            txtSolucao.setRows(5);
            jScrollPane3.setViewportView(txtSolucao);

            lblDataRelaizado.setText("Data Realizado");

            txtDataRealizado.setEnabled(false);

            lblDuracao.setText("Duração da Tarefa:");

        }catch(Exception e) {}

        lblTipo.setText("Tipo:");

        lblIndicarOperador.setText("Indicar Operador:");

        btnAtualizar.setText("Gravar Alteração");

        javax.swing.GroupLayout painelFechaChmLayout = new javax.swing.GroupLayout(painelFechaChm);
        painelFechaChm.setLayout(painelFechaChmLayout);
        painelFechaChmLayout.setHorizontalGroup(
            painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFechaChmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFechaChmLayout.createSequentialGroup()
                        .addComponent(lblPrioridadeAlt)
                        .addGap(4, 4, 4)
                        .addComponent(cmbPrioridadeAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblDescricaoAlt)
                    .addComponent(lblSolucao)
                    .addGroup(painelFechaChmLayout.createSequentialGroup()
                        .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProblemaAlt)
                            .addComponent(lblCodigoAlt))
                        .addGap(8, 8, 8)
                        .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(painelFechaChmLayout.createSequentialGroup()
                                .addComponent(cmbProblemaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTipo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(painelFechaChmLayout.createSequentialGroup()
                        .addComponent(cmbOperador, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblIndicarOperador)
                    .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(painelFechaChmLayout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addGap(18, 18, 18)
                                .addComponent(btnFecharChamado))
                            .addGroup(painelFechaChmLayout.createSequentialGroup()
                                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDataAberturaAlt)
                                    .addComponent(txtDataAberturaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDataRelaizado, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(painelFechaChmLayout.createSequentialGroup()
                                        .addComponent(txtDataRealizado, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblDuracao)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 93, Short.MAX_VALUE))
        );
        painelFechaChmLayout.setVerticalGroup(
            painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFechaChmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFechaChmLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblProblemaAlt))
                    .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbProblemaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTipo)
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFechaChmLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblPrioridadeAlt))
                    .addComponent(cmbPrioridadeAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDescricaoAlt)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSolucao)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFechaChmLayout.createSequentialGroup()
                        .addComponent(lblDataRelaizado)
                        .addGap(9, 9, 9)
                        .addComponent(txtDataRealizado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDuracao))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFechaChmLayout.createSequentialGroup()
                        .addComponent(lblDataAberturaAlt)
                        .addGap(9, 9, 9)
                        .addComponent(txtDataAberturaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIndicarOperador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbOperador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelFechaChmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnFecharChamado))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelAlteraEquipLayout = new javax.swing.GroupLayout(painelAlteraEquip);
        painelAlteraEquip.setLayout(painelAlteraEquipLayout);
        painelAlteraEquipLayout.setHorizontalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelFechaChm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelAlteraEquipLayout.setVerticalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelFechaChm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabChamado.addTab("Fechamento", painelAlteraEquip);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabChamado, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabChamado, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoAltActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoAltActionPerformed

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
            java.util.logging.Logger.getLogger(FrmChamado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmChamado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmChamado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmChamado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmChamado dialog = new FrmChamado(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFecharChamado;
    private javax.swing.JButton btnGravarChamado;
    private javax.swing.JComboBox cmbOperador;
    private javax.swing.JComboBox cmbPrioridade;
    private javax.swing.JComboBox cmbPrioridadeAlt;
    private javax.swing.JComboBox cmbProblema;
    private javax.swing.JComboBox cmbProblemaAlt;
    private javax.swing.JComboBox cmbTipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblDataAbertura;
    private javax.swing.JLabel lblDataAberturaAlt;
    private javax.swing.JLabel lblDataRelaizado;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblDescricaoAlt;
    private javax.swing.JLabel lblDuracao;
    private javax.swing.JLabel lblIndicarOperador;
    private javax.swing.JLabel lblPrioridade;
    private javax.swing.JLabel lblPrioridadeAlt;
    private javax.swing.JLabel lblProblema;
    private javax.swing.JLabel lblProblemaAlt;
    private javax.swing.JLabel lblSolucao;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel painelAbreChm;
    private javax.swing.JPanel painelAlteraEquip;
    private javax.swing.JPanel painelFechaChm;
    private javax.swing.JTabbedPane tabChamado;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private com.toedter.calendar.JDateChooser txtDataAbertura;
    private com.toedter.calendar.JDateChooser txtDataAberturaAlt;
    private com.toedter.calendar.JDateChooser txtDataRealizado;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextArea txtDescricaoAlt;
    private javax.swing.JTextArea txtSolucao;
    private javax.swing.JFormattedTextField txtTempo;
    // End of variables declaration//GEN-END:variables
}
