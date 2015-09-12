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
import helpdesk.beans.Equipamento;
import helpdesk.beans.Fornecedor;
import helpdesk.beans.Marca;
import helpdesk.beans.Office;
import helpdesk.beans.Periferico;
import helpdesk.beans.Setor;
import helpdesk.beans.SistemaOP;
import helpdesk.beans.Usuario;
import helpdesk.controllers.EquipamentoController;
import helpdesk.controllers.FornecedorController;
import helpdesk.controllers.MarcaController;
import helpdesk.controllers.OfficeController;
import helpdesk.controllers.PerifericoController;
import helpdesk.controllers.SetorController;
import helpdesk.controllers.SistemaOPController;
import helpdesk.utils.AutoCompleteTextField;
import helpdesk.utils.PerifericoTableModel;
import helpdesk.utils.Utilidades;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author daniel
 */
public class FrmEquipamento extends javax.swing.JDialog {

    /**
     * Creates new form FrmConfig
     */
    private PerifericoTableModel tb;
    private PerifericoTableModel tbAlt;
    private List<Periferico>psNovo=new ArrayList();
    private List<Periferico>psNovoAlt=new ArrayList();
    private List<String>listaDeEquipamentos;
    public FrmEquipamento(long codEquipamento){
        this(new Frame(),true);
        tabEquipamento.setSelectedIndex(1);
        txtCodigoAlt.setText(String.valueOf(codEquipamento));
        buscaPorID(codEquipamento);        
    }
    public FrmEquipamento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        tb=new PerifericoTableModel();        
        tbAlt=new PerifericoTableModel();
        listaDeEquipamentos=new ArrayList<>();
        for(Equipamento e: new EquipamentoController().listar()){
            listaDeEquipamentos.add(e.getDescricao()+" codigo"+e.getId());
        }
        initComponents();  
        tablePerifericos.getTableHeader().setReorderingAllowed(false);
        tablePerifericos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablePerifericos.getColumnModel().getColumn(1).setPreferredWidth(400);
        tablePerifericos.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("Equipamento")));        
        carregaCombos();
        tabEquipamento.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabEquipamento.getSelectedIndex()==0){
                    limparAlteracao();
                }else{
                    limparInclusao();
                }
            }
        });
        
        btnGravarEquip.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    Equipamento equipamento =new Equipamento();
                    equipamento.setDescricao(txtDescricao.getText());  
                    equipamento.setFornecedor(Long.valueOf(String.valueOf(cmbFornecedor.getSelectedItem().toString().subSequence(0, cmbFornecedor.getSelectedItem().toString().indexOf("-")))));
                    equipamento.setMarca(Long.valueOf(String.valueOf(cmbMarca.getSelectedItem().toString().subSequence(0, cmbMarca.getSelectedItem().toString().indexOf("-")))));
                    equipamento.setSistemaOP(Long.valueOf(String.valueOf(cmbSistemaOP.getSelectedItem().toString().subSequence(0, cmbSistemaOP.getSelectedItem().toString().indexOf("-")))));
                    equipamento.setOffice(chkOffice.isSelected()?(Long.valueOf(String.valueOf(cmbOffice.getSelectedItem().toString().subSequence(0, cmbOffice.getSelectedItem().toString().indexOf("-"))))):0);
                    equipamento.setSetor(Long.valueOf(String.valueOf(cmbSetor.getSelectedItem().toString().subSequence(0, cmbSetor.getSelectedItem().toString().indexOf("-")))));
                    equipamento.setNumeroSerie(txtNumeroSerie.getText().toString());
                    equipamento.setIp(txtIP.getText().toString());                    
                    equipamento.setDataCompra(new SimpleDateFormat("yyyy-MM-dd").format(txtDataCompra.getDate()));
                    new EquipamentoController().insert(equipamento);
                    txtCodigo.setText(String.valueOf(Integer.valueOf(txtCodigo.getText())+1));
                    limparInclusao();
                }catch(Exception e){                    
                    e.printStackTrace();
                }
            }
        });
        
        btnGravarEquipAlt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(txtCodigoAlt.getText().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Selecione um equipamento para alterar.");
                }else{
                    try{
                        Equipamento equipamento= new Equipamento();
                        equipamento.setId((long)Integer.valueOf(txtCodigoAlt.getText().toString()));
                        equipamento.setDescricao(txtDescricaoAlt.getText().toString());
                        equipamento.setFornecedor(Long.valueOf(String.valueOf(cmbFornecedorAlt.getSelectedItem().toString().subSequence(0, cmbFornecedorAlt.getSelectedItem().toString().indexOf("-")))));
                        equipamento.setMarca(Long.valueOf(String.valueOf(cmbMarcaAlt.getSelectedItem().toString().subSequence(0, cmbMarcaAlt.getSelectedItem().toString().indexOf("-")))));
                        equipamento.setSistemaOP(Long.valueOf(String.valueOf(cmbSistemaOPAlt.getSelectedItem().toString().subSequence(0, cmbSistemaOPAlt.getSelectedItem().toString().indexOf("-")))));
                        equipamento.setOffice(chkOfficeAlt.isSelected()?(Long.valueOf(String.valueOf(cmbOfficeAlt.getSelectedItem().toString().subSequence(0, cmbOfficeAlt.getSelectedItem().toString().indexOf("-"))))):0);
                        equipamento.setSetor(Long.valueOf(String.valueOf(cmbSetorAlt.getSelectedItem().toString().subSequence(0, cmbSetorAlt.getSelectedItem().toString().indexOf("-")))));
                        equipamento.setNumeroSerie(txtNumeroSerieAlt.getText().toString());
                        equipamento.setIp(txtIPAlt.getText().toString());                    
                        equipamento.setDataCompra(new SimpleDateFormat("yyyy-MM-dd").format(txtDataCompraAlt.getDate()));                        
                        new EquipamentoController().update(equipamento);
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
                    buscaPorID(Long.valueOf(txtCodigoAlt.getText()));
                    carregarPerifericos(new PerifericoController().buscarPerifericos(Long.valueOf(txtCodigoAlt.getText())));
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
        
        chkOffice.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                cmbOffice.setEnabled(chkOffice.isSelected());
                if (chkOffice.isSelected()){
                    carregarOffice();       
                }else{
                    cmbOffice.removeAllItems();
                }
            }
        });
        chkOfficeAlt.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                cmbOfficeAlt.setEnabled(chkOfficeAlt.isSelected());
                if (chkOfficeAlt.isSelected()){
                    carregarOfficeAlt();       
                }else{
                    cmbOfficeAlt.removeAllItems();
                }
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
    //<editor-fold desc="Métodos privates">
    private void carregaCombos(){
        cmbFornecedor.removeAllItems();
        cmbFornecedorAlt.removeAllItems();
        cmbSistemaOP.removeAllItems();
        cmbSistemaOPAlt.removeAllItems();
        cmbMarca.removeAllItems();
        cmbMarcaAlt.removeAllItems();
        cmbSetor.removeAllItems();
        cmbSetorAlt.removeAllItems();
        List<Fornecedor>fornecedores;
        fornecedores=new FornecedorController().listar();
        for(Fornecedor fornecedor:fornecedores){            
            cmbFornecedor.addItem(fornecedor.getId()+"-"+fornecedor.getDescricao());
            cmbFornecedorAlt.addItem(fornecedor.getId()+"-"+fornecedor.getDescricao());
        }
        List<SistemaOP>sistemas;
        sistemas=new SistemaOPController().listar();
        for(SistemaOP sistema:sistemas){
            cmbSistemaOP.addItem(sistema.getId()+"-"+sistema.getDescricao());
            cmbSistemaOPAlt.addItem(sistema.getId()+"-"+sistema.getDescricao());
        }
        List<Marca>marcas;
        marcas=new MarcaController().listar();
        for(Marca marca:marcas){
            cmbMarca.addItem(marca.getId()+"-"+marca.getDescricao());
            cmbMarcaAlt.addItem(marca.getId()+"-"+marca.getDescricao());
        }
        List<Setor>setores;
        setores=new SetorController().listar();
        for(Setor setor:setores){
            cmbSetor.addItem(setor.getId()+"-"+setor.getDescricao());
            cmbSetorAlt.addItem(setor.getId()+"-"+setor.getDescricao());
        }
    }
    private void carregarOffice(){
        cmbOffice.removeAllItems();
        List<Office>lista;
        lista=new OfficeController().listar();
        for (Office office:lista){
            cmbOffice.addItem(office.getId()+"-"+office.getDescricao());
        }
    }
    private void carregarOfficeAlt(){
        cmbOfficeAlt.removeAllItems();
        List<Office>lista;
        lista=new OfficeController().listar();
        for (Office office:lista){
            cmbOfficeAlt.addItem(office.getId()+"-"+office.getDescricao());
        }
    }
    private void limparAlteracao(){
        txtCodigoAlt.setText("");
        txtCodigoAlt.setEnabled(true);
        txtDescricaoAlt.setText("");
        txtNumeroSerieAlt.setText("");
        txtIPAlt.setText("");               
        txtDataCompraAlt.setDate(null);
        chkOfficeAlt.setSelected(false);
    }
    private void limparInclusao(){        
        txtDescricao.setText("");
        txtNumeroSerie.setText("");
        txtIP.setText("");               
        txtDataCompra.setDate(null);
        chkOffice.setSelected(false);
    }
    private void buscaPorID(long id){
        try{
            String fornec="",marc="", sisc="",set="";
            Equipamento u=new EquipamentoController().buscarPorID(id);
            txtDescricaoAlt.setText(u.getDescricao());
            //cmbFornecedorAlt.setSelectedIndex(Integer.valueOf(String.valueOf(u.getFornecedor()-1)));
            for(int i=0;i<cmbFornecedorAlt.getItemCount();i++){
                cmbFornecedorAlt.setSelectedIndex(i);
                if(cmbFornecedorAlt.getSelectedItem().toString().substring(0,cmbFornecedorAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getFornecedor()))){
                    fornec=cmbFornecedorAlt.getSelectedItem().toString();                                
                }

            }
            cmbFornecedorAlt.setSelectedItem(fornec);
            for(int i=0;i<cmbMarcaAlt.getItemCount();i++){
                cmbMarcaAlt.setSelectedIndex(i);
                if(cmbMarcaAlt.getSelectedItem().toString().substring(0,cmbMarcaAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getMarca()))){
                    marc=cmbMarcaAlt.getSelectedItem().toString();                                
                }

            }
            cmbMarcaAlt.setSelectedItem(marc);
            //cmbMarcaAlt.setSelectedIndex(Integer.valueOf(String.valueOf(u.getMarca()-1)));
            for(int i=0;i<cmbSistemaOPAlt.getItemCount();i++){
                cmbSistemaOPAlt.setSelectedIndex(i);
                if(cmbSistemaOPAlt.getSelectedItem().toString().substring(0,cmbSistemaOPAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getSistemaOP()))){
                    sisc=cmbSistemaOPAlt.getSelectedItem().toString();                                
                }

            }
            cmbSistemaOPAlt.setSelectedItem(sisc);
            for(int i=0;i<cmbSetorAlt.getItemCount();i++){
                cmbSetorAlt.setSelectedIndex(i);
                if(cmbSetorAlt.getSelectedItem().toString().substring(0,cmbSetorAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getSetor()))){
                    set=cmbSetorAlt.getSelectedItem().toString();                                
                }

            }
            cmbSetorAlt.setSelectedItem(set);
            //cmbSistemaOPAlt.setSelectedIndex(Integer.valueOf(String.valueOf(u.getSistemaOP()-1)));
            txtIPAlt.setText(u.getIp());
            txtNumeroSerieAlt.setText(u.getNumeroSerie());
            if(u.getOffice()>0){
                String offic="";
                chkOfficeAlt.setSelected(true);
                for(int i=0;i<cmbOfficeAlt.getItemCount();i++){
                    cmbOfficeAlt.setSelectedIndex(i);
                    if(cmbOfficeAlt.getSelectedItem().toString().substring(0,cmbOfficeAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getOffice()))){
                        sisc=cmbOfficeAlt.getSelectedItem().toString();                                
                    }

                }
                cmbOfficeAlt.setSelectedItem(sisc);
                //cmbOfficeAlt.setSelectedIndex(Integer.valueOf(String.valueOf(u.getOffice()-1)));                            
            }
            txtCodigoAlt.setEnabled(false);                        
            txtDataCompraAlt.setDate(new Date(u.getDataCompra().replace("-", "/")));
        }catch(Exception e){            
            JOptionPane.showMessageDialog(null, "Equipamento não encontrado.");
        }
    }
    private void carregarPerifericos(List<Periferico>perifericos){
        psNovoAlt.clear();
        tbAlt=new PerifericoTableModel();
        tableAltPerifericos.setModel(tb);
        tableAltPerifericos.repaint();        
        for(Periferico p:perifericos){
            psNovoAlt.add(p);
            tbAlt.addPeriferico(p);
            tableAltPerifericos.setModel(tb);
            tableAltPerifericos.updateUI();
            tableAltPerifericos.repaint();
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

        tabEquipamento = new javax.swing.JTabbedPane();
        painelCadEquip = new javax.swing.JPanel();
        jPanelCadEquipamento = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        cmbFornecedor = new javax.swing.JComboBox();
        lblFornecedor = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        cmbMarca = new javax.swing.JComboBox();
        lblSitemaOP = new javax.swing.JLabel();
        cmbSistemaOP = new javax.swing.JComboBox();
        txtNumeroSerie = new javax.swing.JTextField();
        lblNumeroSerie = new javax.swing.JLabel();
        chkOffice = new javax.swing.JCheckBox();
        cmbOffice = new javax.swing.JComboBox();
        lblSetor = new javax.swing.JLabel();
        cmbSetor = new javax.swing.JComboBox();
        lblIP = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        lblDataCompra = new javax.swing.JLabel();
        txtDataCompra = new com.toedter.calendar.JDateChooser();
        jPanelCadPerifericos = new javax.swing.JPanel();
        lblTipoPeriferico = new javax.swing.JLabel();
        lblDescricaoPeriferico = new javax.swing.JLabel();
        lblNSeriePeriferico = new javax.swing.JLabel();
        txtDescricaoPeriferico = new javax.swing.JTextField();
        cmbTipoPeriferico = new javax.swing.JComboBox();
        txtNSeriePeriferico = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePerifericos = new javax.swing.JTable();
        btnGravarEquip = new javax.swing.JButton();
        painelAlteraEquip = new javax.swing.JPanel();
        painelCadEquip1 = new javax.swing.JPanel();
        jPanelAltEquipamento = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        lblDescricaoAlt = new javax.swing.JLabel();
        txtDescricaoAlt = new AutoCompleteTextField(5,false,listaDeEquipamentos);
        lblFornecedorAlt = new javax.swing.JLabel();
        cmbFornecedorAlt = new javax.swing.JComboBox();
        lblMarcaAlt = new javax.swing.JLabel();
        cmbMarcaAlt = new javax.swing.JComboBox();
        lblSitemaOPAlt = new javax.swing.JLabel();
        cmbSistemaOPAlt = new javax.swing.JComboBox();
        lblNumeroSerieAlt = new javax.swing.JLabel();
        txtNumeroSerieAlt = new javax.swing.JTextField();
        lblIPAlt = new javax.swing.JLabel();
        txtIPAlt = new javax.swing.JTextField();
        chkOfficeAlt = new javax.swing.JCheckBox();
        cmbOfficeAlt = new javax.swing.JComboBox();
        lblDataCompraAlt = new javax.swing.JLabel();
        txtDataCompraAlt = new com.toedter.calendar.JDateChooser();
        lblSetorAlt = new javax.swing.JLabel();
        cmbSetorAlt = new javax.swing.JComboBox();
        jPanelAltPerifericos = new javax.swing.JPanel();
        lblTipoAltPeriferico = new javax.swing.JLabel();
        lblDescricaoAltPeriferico = new javax.swing.JLabel();
        lblNSerieAltPeriferico = new javax.swing.JLabel();
        txtDescricaoAltPeriferico = new javax.swing.JTextField();
        cmbTipoAltPeriferico = new javax.swing.JComboBox();
        txtNSerieAltPeriferico = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAltPerifericos = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        btnGravarEquipAlt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Equipamentos");
        setResizable(false);

        painelCadEquip.setBackground(java.awt.Color.lightGray);

        jPanelCadEquipamento.setBackground(java.awt.Color.lightGray);
        jPanelCadEquipamento.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do equipamento"));

        txtCodigo.setText("1");
        txtCodigo.setEnabled(false);

        lblCodigo.setText("Código");

        lblDescricao.setText("Descrição");

        lblFornecedor.setText("Fornecedor:");

        lblMarca.setText("Marca:");

        lblSitemaOP.setText("Sistema OP:");

        lblNumeroSerie.setText("Nº de série:");

        chkOffice.setBackground(java.awt.Color.lightGray);
        chkOffice.setText("Office");

        cmbOffice.setEnabled(false);

        lblSetor.setText("Setor:");

        cmbSetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSetorActionPerformed(evt);
            }
        });

        lblIP.setText("IP:");

        txtIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIPActionPerformed(evt);
            }
        });

        lblDataCompra.setText("Data da Compra:");

        javax.swing.GroupLayout jPanelCadEquipamentoLayout = new javax.swing.GroupLayout(jPanelCadEquipamento);
        jPanelCadEquipamento.setLayout(jPanelCadEquipamentoLayout);
        jPanelCadEquipamentoLayout.setHorizontalGroup(
            jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                        .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(txtCodigo))
                        .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(lblDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(466, 466, 466))
                            .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                        .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                                        .addComponent(lblMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(14, 14, 14))
                                    .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbSistemaOP, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSitemaOP)))
                            .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                                        .addComponent(lblSetor, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                                        .addGap(165, 165, 165))
                                    .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                                        .addComponent(chkOffice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(96, 96, 96))
                                    .addComponent(cmbOffice, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIP, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtIP, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblDataCompra)
                                            .addComponent(txtDataCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))))))
                        .addContainerGap())))
        );

        jPanelCadEquipamentoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbFornecedor, cmbOffice, cmbSetor, txtNumeroSerie});

        jPanelCadEquipamentoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtDataCompra, txtIP});

        jPanelCadEquipamentoLayout.setVerticalGroup(
            jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSitemaOP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSistemaOP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                        .addComponent(lblNumeroSerie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)
                        .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkOffice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSetor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 5, Short.MAX_VALUE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanelCadEquipamentoLayout.createSequentialGroup()
                        .addComponent(lblIP, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDataCompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanelCadEquipamentoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbFornecedor, cmbMarca, cmbOffice, cmbSetor, cmbSistemaOP, txtCodigo, txtDataCompra, txtDescricao, txtIP, txtNumeroSerie});

        jPanelCadPerifericos.setBackground(java.awt.Color.lightGray);
        jPanelCadPerifericos.setBorder(javax.swing.BorderFactory.createTitledBorder("Perifericos"));

        lblTipoPeriferico.setText("Tipo:");

        lblDescricaoPeriferico.setText("Descrição:");

        lblNSeriePeriferico.setText("Nº de série:");

        cmbTipoPeriferico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1-Monitor", "2-Teclado", "3-Mouse" }));

        tablePerifericos.setModel(tb);
        jScrollPane1.setViewportView(tablePerifericos);

        javax.swing.GroupLayout jPanelCadPerifericosLayout = new javax.swing.GroupLayout(jPanelCadPerifericos);
        jPanelCadPerifericos.setLayout(jPanelCadPerifericosLayout);
        jPanelCadPerifericosLayout.setHorizontalGroup(
            jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadPerifericosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelCadPerifericosLayout.createSequentialGroup()
                        .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipoPeriferico)
                            .addComponent(cmbTipoPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCadPerifericosLayout.createSequentialGroup()
                                .addComponent(lblDescricaoPeriferico)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtDescricaoPeriferico))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNSeriePeriferico)
                            .addComponent(txtNSeriePeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanelCadPerifericosLayout.setVerticalGroup(
            jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadPerifericosLayout.createSequentialGroup()
                .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoPeriferico)
                    .addComponent(lblDescricaoPeriferico)
                    .addComponent(lblNSeriePeriferico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescricaoPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNSeriePeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnGravarEquip.setText("Gravar");

        javax.swing.GroupLayout painelCadEquipLayout = new javax.swing.GroupLayout(painelCadEquip);
        painelCadEquip.setLayout(painelCadEquipLayout);
        painelCadEquipLayout.setHorizontalGroup(
            painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquipLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCadEquipamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCadPerifericos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCadEquipLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravarEquip)))
                .addContainerGap())
        );
        painelCadEquipLayout.setVerticalGroup(
            painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquipLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCadEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCadPerifericos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGravarEquip)
                .addContainerGap())
        );

        tabEquipamento.addTab("Cadastro", painelCadEquip);

        painelAlteraEquip.setBackground(java.awt.Color.gray);

        painelCadEquip1.setBackground(java.awt.Color.gray);

        jPanelAltEquipamento.setBackground(java.awt.Color.gray);
        jPanelAltEquipamento.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do equipamento"));
        jPanelAltEquipamento.setPreferredSize(new java.awt.Dimension(645, 353));

        lblCodigoAlt.setText("Código");

        lblDescricaoAlt.setText("Descrição");

        lblFornecedorAlt.setText("Fornecedor:");

        lblMarcaAlt.setText("Marca:");

        lblSitemaOPAlt.setText("Sistema OP:");

        lblNumeroSerieAlt.setText("Nº de série:");

        lblIPAlt.setText("IP:");

        chkOfficeAlt.setBackground(java.awt.Color.gray);
        chkOfficeAlt.setText("Office");

        cmbOfficeAlt.setEnabled(false);

        lblDataCompraAlt.setText("Data da Compra:");

        lblSetorAlt.setText("Setor:");

        javax.swing.GroupLayout jPanelAltEquipamentoLayout = new javax.swing.GroupLayout(jPanelAltEquipamento);
        jPanelAltEquipamento.setLayout(jPanelAltEquipamentoLayout);
        jPanelAltEquipamentoLayout.setHorizontalGroup(
            jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                        .addComponent(cmbSetorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                        .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCodigoAlt)
                            .addComponent(lblSetorAlt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                                .addComponent(lblDescricaoAlt)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                                .addComponent(txtDescricaoAlt)
                                .addContainerGap())))
                    .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                        .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNumeroSerieAlt)
                                    .addComponent(txtNumeroSerieAlt)
                                    .addComponent(cmbOfficeAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkOfficeAlt))
                                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtDataCompraAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                                        .addGap(290, 290, 290)
                                        .addComponent(lblDataCompraAlt)
                                        .addGap(0, 71, Short.MAX_VALUE))))
                            .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFornecedorAlt)
                                    .addComponent(cmbFornecedorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbMarcaAlt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                                        .addComponent(lblMarcaAlt)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSitemaOPAlt)
                                    .addComponent(lblIPAlt)
                                    .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtIPAlt, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbSistemaOPAlt, 0, 186, Short.MAX_VALUE)))))
                        .addContainerGap())))
        );

        jPanelAltEquipamentoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbFornecedorAlt, cmbOfficeAlt, cmbSetorAlt, txtNumeroSerieAlt});

        jPanelAltEquipamentoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbSistemaOPAlt, txtDataCompraAlt, txtIPAlt});

        jPanelAltEquipamentoLayout.setVerticalGroup(
            jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAltEquipamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(lblDescricaoAlt))
                .addGap(3, 3, 3)
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricaoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFornecedorAlt)
                    .addComponent(lblSitemaOPAlt)
                    .addComponent(lblMarcaAlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFornecedorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSistemaOPAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMarcaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIPAlt)
                    .addComponent(lblNumeroSerieAlt))
                .addGap(4, 4, 4)
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIPAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroSerieAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkOfficeAlt)
                    .addComponent(lblDataCompraAlt))
                .addGap(1, 1, 1)
                .addGroup(jPanelAltEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbOfficeAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataCompraAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblSetorAlt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSetorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanelAltEquipamentoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbFornecedorAlt, cmbMarcaAlt, cmbOfficeAlt, cmbSetorAlt, cmbSistemaOPAlt, txtCodigoAlt, txtDataCompraAlt, txtDescricaoAlt, txtIPAlt, txtNumeroSerieAlt});

        javax.swing.GroupLayout painelCadEquip1Layout = new javax.swing.GroupLayout(painelCadEquip1);
        painelCadEquip1.setLayout(painelCadEquip1Layout);
        painelCadEquip1Layout.setHorizontalGroup(
            painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelAltEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        painelCadEquip1Layout.setVerticalGroup(
            painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelAltEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelAltPerifericos.setBackground(java.awt.Color.gray);
        jPanelAltPerifericos.setBorder(javax.swing.BorderFactory.createTitledBorder("Perifericos"));

        lblTipoAltPeriferico.setText("Tipo:");

        lblDescricaoAltPeriferico.setText("Descrição:");

        lblNSerieAltPeriferico.setText("Nº de série:");

        cmbTipoAltPeriferico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1-Monitor", "2-Teclado", "3-Mouse" }));

        tableAltPerifericos.setModel(tbAlt);
        jScrollPane2.setViewportView(tableAltPerifericos);

        javax.swing.GroupLayout jPanelAltPerifericosLayout = new javax.swing.GroupLayout(jPanelAltPerifericos);
        jPanelAltPerifericos.setLayout(jPanelAltPerifericosLayout);
        jPanelAltPerifericosLayout.setHorizontalGroup(
            jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                        .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipoAltPeriferico)
                            .addComponent(cmbTipoAltPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                                .addComponent(lblDescricaoAltPeriferico)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtDescricaoAltPeriferico))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNSerieAltPeriferico)
                            .addComponent(txtNSerieAltPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanelAltPerifericosLayout.setVerticalGroup(
            jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoAltPeriferico)
                    .addComponent(lblDescricaoAltPeriferico)
                    .addComponent(lblNSerieAltPeriferico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescricaoAltPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoAltPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNSerieAltPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancelar.setText("Cancelar");

        btnGravarEquipAlt.setText("Gravar");

        javax.swing.GroupLayout painelAlteraEquipLayout = new javax.swing.GroupLayout(painelAlteraEquip);
        painelAlteraEquip.setLayout(painelAlteraEquipLayout);
        painelAlteraEquipLayout.setHorizontalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelCadEquip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelAltPerifericos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelAlteraEquipLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGravarEquipAlt)))
                .addContainerGap())
        );
        painelAlteraEquipLayout.setVerticalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelCadEquip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAltPerifericos, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGravarEquipAlt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabEquipamento.addTab("Alteração", painelAlteraEquip);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabEquipamento)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabEquipamento)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSetorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSetorActionPerformed

    private void txtIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIPActionPerformed

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
            java.util.logging.Logger.getLogger(FrmEquipamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEquipamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEquipamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEquipamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmEquipamento dialog = new FrmEquipamento(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravarEquip;
    private javax.swing.JButton btnGravarEquipAlt;
    private javax.swing.JCheckBox chkOffice;
    private javax.swing.JCheckBox chkOfficeAlt;
    private javax.swing.JComboBox cmbFornecedor;
    private javax.swing.JComboBox cmbFornecedorAlt;
    private javax.swing.JComboBox cmbMarca;
    private javax.swing.JComboBox cmbMarcaAlt;
    private javax.swing.JComboBox cmbOffice;
    private javax.swing.JComboBox cmbOfficeAlt;
    private javax.swing.JComboBox cmbSetor;
    private javax.swing.JComboBox cmbSetorAlt;
    private javax.swing.JComboBox cmbSistemaOP;
    private javax.swing.JComboBox cmbSistemaOPAlt;
    private javax.swing.JComboBox cmbTipoAltPeriferico;
    private javax.swing.JComboBox cmbTipoPeriferico;
    private javax.swing.JPanel jPanelAltEquipamento;
    private javax.swing.JPanel jPanelAltPerifericos;
    private javax.swing.JPanel jPanelCadEquipamento;
    private javax.swing.JPanel jPanelCadPerifericos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblDataCompra;
    private javax.swing.JLabel lblDataCompraAlt;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblDescricaoAlt;
    private javax.swing.JLabel lblDescricaoAltPeriferico;
    private javax.swing.JLabel lblDescricaoPeriferico;
    private javax.swing.JLabel lblFornecedor;
    private javax.swing.JLabel lblFornecedorAlt;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblIPAlt;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMarcaAlt;
    private javax.swing.JLabel lblNSerieAltPeriferico;
    private javax.swing.JLabel lblNSeriePeriferico;
    private javax.swing.JLabel lblNumeroSerie;
    private javax.swing.JLabel lblNumeroSerieAlt;
    private javax.swing.JLabel lblSetor;
    private javax.swing.JLabel lblSetorAlt;
    private javax.swing.JLabel lblSitemaOP;
    private javax.swing.JLabel lblSitemaOPAlt;
    private javax.swing.JLabel lblTipoAltPeriferico;
    private javax.swing.JLabel lblTipoPeriferico;
    private javax.swing.JPanel painelAlteraEquip;
    private javax.swing.JPanel painelCadEquip;
    private javax.swing.JPanel painelCadEquip1;
    private javax.swing.JTabbedPane tabEquipamento;
    private javax.swing.JTable tableAltPerifericos;
    private javax.swing.JTable tablePerifericos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private com.toedter.calendar.JDateChooser txtDataCompra;
    private com.toedter.calendar.JDateChooser txtDataCompraAlt;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDescricaoAlt;
    private javax.swing.JTextField txtDescricaoAltPeriferico;
    private javax.swing.JTextField txtDescricaoPeriferico;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtIPAlt;
    private javax.swing.JTextField txtNSerieAltPeriferico;
    private javax.swing.JTextField txtNSeriePeriferico;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtNumeroSerieAlt;
    // End of variables declaration//GEN-END:variables
}
