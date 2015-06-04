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
import helpdesk.beans.Setor;
import helpdesk.beans.SistemaOP;
import helpdesk.beans.Usuario;
import helpdesk.controllers.EquipamentoController;
import helpdesk.controllers.FornecedorController;
import helpdesk.controllers.MarcaController;
import helpdesk.controllers.OfficeController;
import helpdesk.controllers.SetorController;
import helpdesk.controllers.SistemaOPController;
import helpdesk.utils.AutoCompleteTextField;
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
    private List<String>listaDeEquipamentos;
    public FrmEquipamento(long codEquipamento){
        this(new Frame(),true);
        tabEquipamento.setSelectedIndex(1);
        txtCodigoAlt.setText(String.valueOf(codEquipamento));
        buscaPorID(codEquipamento);        
    }
    public FrmEquipamento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        listaDeEquipamentos=new ArrayList<>();
        for(Equipamento e: new EquipamentoController().listar()){
            listaDeEquipamentos.add(e.getDescricao()+" codigo"+e.getId());
        }
        initComponents();  
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
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        btnGravarEquip = new javax.swing.JButton();
        lblDataCompra = new javax.swing.JLabel();
        txtDataCompra = new com.toedter.calendar.JDateChooser();
        lblFornecedor = new javax.swing.JLabel();
        cmbFornecedor = new javax.swing.JComboBox();
        lblSitemaOP = new javax.swing.JLabel();
        cmbSistemaOP = new javax.swing.JComboBox();
        chkOffice = new javax.swing.JCheckBox();
        lblIP = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        lblMarca = new javax.swing.JLabel();
        cmbMarca = new javax.swing.JComboBox();
        lblNumeroSerie = new javax.swing.JLabel();
        txtNumeroSerie = new javax.swing.JTextField();
        cmbOffice = new javax.swing.JComboBox();
        lblSetor = new javax.swing.JLabel();
        cmbSetor = new javax.swing.JComboBox();
        painelAlteraEquip = new javax.swing.JPanel();
        btnGravarEquipAlt = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        painelCadEquip1 = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        lblDescricaoAlt = new javax.swing.JLabel();
        txtDescricaoAlt = new AutoCompleteTextField(5,false,listaDeEquipamentos);
        lblDataCompraAlt = new javax.swing.JLabel();
        txtDataCompraAlt = new com.toedter.calendar.JDateChooser();
        lblFornecedorAlt = new javax.swing.JLabel();
        cmbFornecedorAlt = new javax.swing.JComboBox();
        lblSitemaOPAlt = new javax.swing.JLabel();
        cmbSistemaOPAlt = new javax.swing.JComboBox();
        chkOfficeAlt = new javax.swing.JCheckBox();
        lblIPAlt = new javax.swing.JLabel();
        txtIPAlt = new javax.swing.JTextField();
        lblMarcaAlt = new javax.swing.JLabel();
        cmbMarcaAlt = new javax.swing.JComboBox();
        lblNumeroSerieAlt = new javax.swing.JLabel();
        txtNumeroSerieAlt = new javax.swing.JTextField();
        cmbOfficeAlt = new javax.swing.JComboBox();
        lblSetorAlt = new javax.swing.JLabel();
        cmbSetorAlt = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Equipamentos");
        setResizable(false);

        painelCadEquip.setBackground(java.awt.Color.lightGray);

        lblCodigo.setText("Código");

        txtCodigo.setText("1");
        txtCodigo.setEnabled(false);

        lblDescricao.setText("Descrição");

        btnGravarEquip.setText("Gravar");

        lblDataCompra.setText("Data da Compra:");

        lblFornecedor.setText("Fornecedor:");

        lblSitemaOP.setText("Sistema OP:");

        chkOffice.setBackground(java.awt.Color.lightGray);
        chkOffice.setText("Office");

        lblIP.setText("IP:");

        lblMarca.setText("Marca:");

        lblNumeroSerie.setText("Nº de série:");

        cmbOffice.setEnabled(false);

        lblSetor.setText("Setor:");

        javax.swing.GroupLayout painelCadEquipLayout = new javax.swing.GroupLayout(painelCadEquip);
        painelCadEquip.setLayout(painelCadEquipLayout);
        painelCadEquipLayout.setHorizontalGroup(
            painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquipLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescricao))
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquipLayout.createSequentialGroup()
                                .addComponent(lblCodigo)
                                .addGap(46, 46, 46)
                                .addComponent(lblDescricao)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCadEquipLayout.createSequentialGroup()
                                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFornecedor)
                                    .addComponent(cmbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                                        .addComponent(lblMarca)
                                        .addGap(0, 98, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquipLayout.createSequentialGroup()
                                .addComponent(lblSitemaOP)
                                .addGap(0, 71, Short.MAX_VALUE))
                            .addComponent(cmbSistemaOP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNumeroSerie)
                            .addComponent(txtNumeroSerie, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(cmbOffice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chkOffice)
                            .addComponent(cmbSetor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquipLayout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(btnGravarEquip))
                            .addComponent(lblIP)
                            .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDataCompra)))
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(lblSetor)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painelCadEquipLayout.setVerticalGroup(
            painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquipLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblDescricao))
                .addGap(3, 3, 3)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFornecedor)
                    .addComponent(lblSitemaOP)
                    .addComponent(lblMarca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSistemaOP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIP)
                    .addComponent(lblNumeroSerie))
                .addGap(4, 4, 4)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(chkOffice)
                        .addGap(1, 1, 1)
                        .addComponent(cmbOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquipLayout.createSequentialGroup()
                                .addGap(0, 5, Short.MAX_VALUE)
                                .addComponent(btnGravarEquip))
                            .addGroup(painelCadEquipLayout.createSequentialGroup()
                                .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(lblDataCompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabEquipamento.addTab("Cadastro", painelCadEquip);

        painelAlteraEquip.setBackground(java.awt.Color.gray);

        btnGravarEquipAlt.setText("Gravar");

        btnCancelar.setText("Cancelar");

        painelCadEquip1.setBackground(java.awt.Color.gray);

        lblCodigoAlt.setText("Código");

        lblDescricaoAlt.setText("Descrição");

        lblDataCompraAlt.setText("Data da Compra:");

        lblFornecedorAlt.setText("Fornecedor:");

        lblSitemaOPAlt.setText("Sistema OP:");

        chkOfficeAlt.setBackground(java.awt.Color.gray);
        chkOfficeAlt.setText("Office");

        lblIPAlt.setText("IP:");

        lblMarcaAlt.setText("Marca:");

        lblNumeroSerieAlt.setText("Nº de série:");

        cmbOfficeAlt.setEnabled(false);

        javax.swing.GroupLayout painelCadEquip1Layout = new javax.swing.GroupLayout(painelCadEquip1);
        painelCadEquip1.setLayout(painelCadEquip1Layout);
        painelCadEquip1Layout.setHorizontalGroup(
            painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescricaoAlt))
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                                .addComponent(lblCodigoAlt)
                                .addGap(46, 46, 46)
                                .addComponent(lblDescricaoAlt)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCadEquip1Layout.createSequentialGroup()
                                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFornecedorAlt)
                                    .addComponent(cmbFornecedorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbMarcaAlt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                                        .addComponent(lblMarcaAlt)
                                        .addGap(0, 87, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                                .addComponent(lblSitemaOPAlt)
                                .addGap(0, 59, Short.MAX_VALUE))
                            .addComponent(cmbSistemaOPAlt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblNumeroSerieAlt)
                                .addComponent(txtNumeroSerieAlt, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                .addComponent(cmbOfficeAlt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(chkOfficeAlt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIPAlt)
                            .addComponent(txtIPAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataCompraAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDataCompraAlt))
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        painelCadEquip1Layout.setVerticalGroup(
            painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoAlt)
                    .addComponent(lblDescricaoAlt))
                .addGap(3, 3, 3)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricaoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFornecedorAlt)
                    .addComponent(lblSitemaOPAlt)
                    .addComponent(lblMarcaAlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFornecedorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSistemaOPAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMarcaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIPAlt)
                    .addComponent(lblNumeroSerieAlt))
                .addGap(4, 4, 4)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIPAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroSerieAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addComponent(chkOfficeAlt)
                        .addGap(1, 1, 1)
                        .addComponent(cmbOfficeAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addComponent(lblDataCompraAlt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDataCompraAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        lblSetorAlt.setText("Setor:");

        javax.swing.GroupLayout painelAlteraEquipLayout = new javax.swing.GroupLayout(painelAlteraEquip);
        painelAlteraEquip.setLayout(painelAlteraEquipLayout);
        painelAlteraEquipLayout.setHorizontalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelCadEquip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                        .addComponent(cmbSetorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGravarEquipAlt)
                        .addGap(6, 6, 6))
                    .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                        .addComponent(lblSetorAlt)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        painelAlteraEquipLayout.setVerticalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelCadEquip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSetorAlt)
                .addGroup(painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnGravarEquipAlt))
                        .addContainerGap())
                    .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSetorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblDataCompra;
    private javax.swing.JLabel lblDataCompraAlt;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblDescricaoAlt;
    private javax.swing.JLabel lblFornecedor;
    private javax.swing.JLabel lblFornecedorAlt;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblIPAlt;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMarcaAlt;
    private javax.swing.JLabel lblNumeroSerie;
    private javax.swing.JLabel lblNumeroSerieAlt;
    private javax.swing.JLabel lblSetor;
    private javax.swing.JLabel lblSetorAlt;
    private javax.swing.JLabel lblSitemaOP;
    private javax.swing.JLabel lblSitemaOPAlt;
    private javax.swing.JPanel painelAlteraEquip;
    private javax.swing.JPanel painelCadEquip;
    private javax.swing.JPanel painelCadEquip1;
    private javax.swing.JTabbedPane tabEquipamento;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private com.toedter.calendar.JDateChooser txtDataCompra;
    private com.toedter.calendar.JDateChooser txtDataCompraAlt;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDescricaoAlt;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtIPAlt;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtNumeroSerieAlt;
    // End of variables declaration//GEN-END:variables
}
