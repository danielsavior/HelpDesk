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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JTable;

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
    private List<Periferico>psEdicao=new ArrayList();
    private Set<Long>setInativar=new HashSet();
    private List<Periferico>listaIncluir=new ArrayList();
    private Map<Long,Periferico>mapAlterar=new HashMap<Long,Periferico>();
    private List<String>listaDeEquipamentos;    
    private Integer idAlterado;
    public FrmEquipamento(long codEquipamento){
        this(new Frame(),true);        
        tabEquipamento.setSelectedIndex(1);
        txtCodigoAlt.setText(String.valueOf(codEquipamento));
        buscaPorID(codEquipamento);        
    }
    public FrmEquipamento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        idAlterado=0;
        tb=new PerifericoTableModel();        
        tbAlt=new PerifericoTableModel();
        listaDeEquipamentos=new ArrayList<>();
        for(Equipamento e: new EquipamentoController().listar()){
            listaDeEquipamentos.add(e.getDescricao()+" codigo"+e.getId());
        }
        initComponents();  
        ajustarTabelas();
        
        
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("Equipamento")));        
        carregaCombos();
        tabEquipamento.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                int resposta=0;
                if(tabEquipamento.getSelectedIndex()==0){                    
                    if(!listaIncluir.isEmpty()||!mapAlterar.isEmpty()){                         
                        resposta=JOptionPane.showConfirmDialog(null, "Deseja abandonar as modificações?");
                        if(resposta==0){                                               
                            limparAlteracao();
                        }
                    }else{
                        limparAlteracao();
                    }
                }else{
                    if(!psNovo.isEmpty()){                    
                        resposta=JOptionPane.showConfirmDialog(null, "Deseja abandonar as modificações?");
                        if(resposta==0){                            
                            limparInclusao();
                        }
                    }else{
                       limparInclusao(); 
                    }
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
                    if(psNovo.size()>0){
                        PerifericoController pc= new PerifericoController();
                        long idEquipamento=equipamento.getId();
                        for (Periferico p:psNovo){
                            p.setEquipamento(idEquipamento);
                            pc.insert(p);
                        }
                    }                        
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
                        if(!mapAlterar.isEmpty()){
                            PerifericoController pc=new PerifericoController();
                            for(Periferico p:mapAlterar.values()){                                
                                pc.update(p);
                            }
                        }
                        if(!setInativar.isEmpty()){
                            PerifericoController pc= new PerifericoController();
                            for (Long l:setInativar){
                                pc.inativar(l);
                            }
                        }
                        if(!listaIncluir.isEmpty()){
                            PerifericoController pc=new PerifericoController();
                            for(Periferico p:listaIncluir){                                
                                pc.insert(p);
                            }
                        }
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
                    ajustarTabelas();
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
        
        txtNSeriePerifericoAlt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode()==10){
                    try{
                        boolean sucesso=false;
                        Periferico p = new Periferico();
                        p.setEquipamento(Long.valueOf(txtCodigoAlt.getText()));
                        p.setTipo(cmbTipoPerifericoAlt.getSelectedItem().toString());
                        p.setDescricao(txtDescricaoPerifericoAlt.getText());
                        p.setMarca(Long.valueOf(String.valueOf(cmbMarcaPerifericoAlt.getSelectedItem().toString().subSequence(0, cmbMarcaPerifericoAlt.getSelectedItem().toString().indexOf("-")))));
                        p.setAtivo("1".charAt(0));
                        p.setNumeroSerie(txtNSeriePerifericoAlt.getText());
                        sucesso=adicionarPeriferico(p, psEdicao, tablePerifericosAlt, tbAlt,true,true);    
                        if (sucesso){
                            txtDescricaoPerifericoAlt.setText("");
                            txtNSeriePerifericoAlt.setText("");     
                        }
                        ajustarTabelas();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Verifique os dados do periferico!");
                    }
                    cmbTipoPerifericoAlt.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                
            }
        });
        
        txtNSeriePeriferico.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                 if (e.getKeyCode()==10){
                    try{
                        boolean sucesso=false;
                        Periferico p = new Periferico();                        
                        p.setTipo(cmbTipoPeriferico.getSelectedItem().toString());
                        p.setDescricao(txtDescricaoPeriferico.getText());
                        p.setMarca(Long.valueOf(String.valueOf(cmbMarcaPeriferico.getSelectedItem().toString().subSequence(0, cmbMarcaPeriferico.getSelectedItem().toString().indexOf("-")))));
                        p.setAtivo("1".charAt(0));
                        p.setNumeroSerie(txtNSeriePeriferico.getText());
                        sucesso=adicionarPeriferico(p, psNovo, tablePerifericos, tb,true,false);    
                        if (sucesso){
                            txtDescricaoPeriferico.setText("");
                            txtNSeriePeriferico.setText("");     
                        }
                        ajustarTabelas();
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Verifique os dados do periferico!");
                    }
                    cmbTipoPeriferico.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        
        tablePerifericosAlt.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(tablePerifericosAlt.getRowCount()>=1){
                    Periferico p=(Periferico)tbAlt.getPeriferico(tablePerifericosAlt.getSelectedRow(), tablePerifericosAlt.getSelectedColumn());
                    idAlterado=Integer.valueOf(String.valueOf(p.getId()));
                    txtDescricaoPerifericoAlt.setText(p.getDescricao());
                    txtNSeriePerifericoAlt.setText(p.getNumeroSerie());
                    String marc="";
                    for(int i=0;i<cmbMarcaPerifericoAlt.getItemCount();i++){
                        cmbMarcaPerifericoAlt.setSelectedIndex(i);
                        if(cmbMarcaPerifericoAlt.getSelectedItem().toString().substring(0,cmbMarcaPerifericoAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(p.getMarca()))){
                            marc=cmbMarcaPerifericoAlt.getSelectedItem().toString();                                
                        }
                    }
                    cmbMarcaPerifericoAlt.setSelectedItem(marc);                                                            
                    cmbTipoPerifericoAlt.setSelectedItem(p.getTipo());
                    cmbTipoPerifericoAlt.setEnabled(false);
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
        
        tablePerifericos.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(tablePerifericos.getRowCount()>=1){
                    Periferico p=(Periferico)tb.getPeriferico(tablePerifericos.getSelectedRow(), tablePerifericos.getSelectedColumn());
                    idAlterado=Integer.valueOf(String.valueOf(p.getId()));
                    txtDescricaoPeriferico.setText(p.getDescricao());
                    txtNSeriePeriferico.setText(p.getNumeroSerie());
                    String marc="";
                    for(int i=0;i<cmbMarcaPeriferico.getItemCount();i++){
                        cmbMarcaPeriferico.setSelectedIndex(i);
                        if(cmbMarcaPeriferico.getSelectedItem().toString().substring(0,cmbMarcaPeriferico.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(p.getMarca()))){
                            marc=cmbMarcaPeriferico.getSelectedItem().toString();                                
                        }
                    }
                    cmbMarcaPeriferico.setSelectedItem(marc);                                                            
                    cmbTipoPeriferico.setSelectedItem(p.getTipo());                    
                }
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
    }
    //<editor-fold desc="Métodos privates">
    private void carregaCombos(){
        cmbFornecedor.removeAllItems();
        cmbFornecedorAlt.removeAllItems();
        cmbSistemaOP.removeAllItems();
        cmbSistemaOPAlt.removeAllItems();
        cmbMarca.removeAllItems();
        cmbMarcaAlt.removeAllItems();
        cmbMarcaPeriferico.removeAllItems();
        cmbMarcaPerifericoAlt.removeAllItems();
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
            cmbMarcaPerifericoAlt.addItem(marca.getId()+"-"+marca.getDescricao());
            cmbMarcaPeriferico.addItem(marca.getId()+"-"+marca.getDescricao());
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
        
        txtDescricaoPerifericoAlt.setText("");
        txtNSeriePerifericoAlt.setText("");
        
        tbAlt=new PerifericoTableModel();
        psEdicao.clear();
        setInativar.clear();
        listaIncluir.clear();
        mapAlterar.clear();
        tablePerifericosAlt.setModel(tbAlt);
        tablePerifericosAlt.updateUI();
        tablePerifericosAlt.repaint(); 
        ajustarTabelas();
        idAlterado=0;
        cmbTipoPerifericoAlt.setEnabled(true);
    }
    private void limparInclusao(){        
        txtDescricao.setText("");
        txtNumeroSerie.setText("");
        txtIP.setText("");               
        txtDataCompra.setDate(null);
        chkOffice.setSelected(false);
        
        txtDescricaoPeriferico.setText("");
        txtNSeriePeriferico.setText("");
        
        tb=new PerifericoTableModel();
        psNovo.clear();                 
        tablePerifericos.setModel(tb);
        tablePerifericos.updateUI();
        tablePerifericos.repaint(); 
        ajustarTabelas();
        idAlterado=0;
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
        psEdicao.clear();
        tbAlt=new PerifericoTableModel();
        tablePerifericosAlt.setModel(tb);
        tablePerifericosAlt.repaint();        
        for(Periferico p:perifericos){
            adicionarPeriferico(p, psEdicao, tablePerifericosAlt, tbAlt,false,false);
        }
    }
    
    private boolean adicionarPeriferico(Periferico p,List<Periferico> ps,JTable tabela, PerifericoTableModel tbMeu, boolean checarN,boolean opAlteracao){
        boolean incluir;
        incluir=true;
        try{
            if (checarN){            
                incluir=new PerifericoController().checaNSerie(p.getNumeroSerie());
            }
            if(idAlterado<=0){                            
                if(incluir){
                    for(Periferico pe:ps){
                        if(pe.getTipo().equals(p.getTipo())){
                            if(pe.getId()>0){
                                setInativar.add(pe.getId());
                            }                    
                            if(listaIncluir.size()>0 && opAlteracao){
                                if(listaIncluir.contains(pe)){
                                    listaIncluir.remove(pe);
                                }
                            }
                            ps.remove(pe);
                            tbMeu.remove(pe);                    
                            break;
                        }
                    }
                    if(p.getId()<=0 & opAlteracao){                    
                        listaIncluir.add(p);                    
                    }
                    ps.add(p);
                    tbMeu.addPeriferico(p);
                    tabela.setModel(tbMeu);
                    tabela.updateUI();
                    tabela.repaint();                                        
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null, "Esse número de série já é usado por outro periférico.");
                    return false;
                }
            }else{
                for(Periferico pe:ps){
                    if(pe.getId()==idAlterado){
                        tbMeu.remove(pe);
                        pe.setDescricao(p.getDescricao());
                        pe.setNumeroSerie(p.getNumeroSerie());
                        tbMeu.addPeriferico(pe);
                        tabela.setModel(tbMeu);
                        tabela.updateUI();
                        tabela.repaint();                                        
                        mapAlterar.put(pe.getId(),pe);
                        idAlterado=0;
                        return true;                        
                    }
                }
            }
                
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, "Confira os dados do periferico!");
           return false;
        }
        idAlterado=0;
        return true;
        
    }
    
    
    
    private void ajustarTabelas(){
        tablePerifericos.getTableHeader().setReorderingAllowed(false);
        tablePerifericos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablePerifericos.getColumnModel().getColumn(1).setPreferredWidth(400);
        tablePerifericos.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        tablePerifericosAlt.getTableHeader().setReorderingAllowed(false);
        tablePerifericosAlt.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablePerifericosAlt.getColumnModel().getColumn(1).setPreferredWidth(400);
        tablePerifericosAlt.getColumnModel().getColumn(2).setPreferredWidth(100);    
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
        cmbMarcaPeriferico = new javax.swing.JComboBox();
        lblMarcaPeriferico = new javax.swing.JLabel();
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
        txtDescricaoPerifericoAlt = new javax.swing.JTextField();
        cmbTipoPerifericoAlt = new javax.swing.JComboBox();
        txtNSeriePerifericoAlt = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePerifericosAlt = new javax.swing.JTable();
        cmbMarcaPerifericoAlt = new javax.swing.JComboBox();
        lblMarcaPerifericoAlt = new javax.swing.JLabel();
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
                            .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
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
                                        .addComponent(lblSetor, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
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
                        .addComponent(lblNumeroSerie, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
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

        cmbTipoPeriferico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monitor", "Teclado", "Mouse" }));

        tablePerifericos.setModel(tb);
        jScrollPane1.setViewportView(tablePerifericos);

        lblMarcaPeriferico.setText("Marca:");

        javax.swing.GroupLayout jPanelCadPerifericosLayout = new javax.swing.GroupLayout(jPanelCadPerifericos);
        jPanelCadPerifericos.setLayout(jPanelCadPerifericosLayout);
        jPanelCadPerifericosLayout.setHorizontalGroup(
            jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadPerifericosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
                    .addGroup(jPanelCadPerifericosLayout.createSequentialGroup()
                        .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipoPeriferico)
                            .addComponent(cmbTipoPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescricaoPeriferico, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescricaoPeriferico))
                        .addGap(2, 2, 2)
                        .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCadPerifericosLayout.createSequentialGroup()
                                .addComponent(lblMarcaPeriferico)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbMarcaPeriferico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(2, 2, 2)
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
                    .addComponent(lblNSeriePeriferico)
                    .addComponent(lblMarcaPeriferico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCadPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbTipoPeriferico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricaoPeriferico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMarcaPeriferico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNSeriePeriferico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelCadPerifericosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbTipoPeriferico, txtDescricaoPeriferico, txtNSeriePeriferico});

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
                .addComponent(jPanelCadEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        cmbTipoPerifericoAlt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monitor", "Teclado", "Mouse" }));

        txtNSeriePerifericoAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNSeriePerifericoAltActionPerformed(evt);
            }
        });

        tablePerifericosAlt.setModel(tbAlt);
        jScrollPane2.setViewportView(tablePerifericosAlt);

        lblMarcaPerifericoAlt.setText("Marca:");

        javax.swing.GroupLayout jPanelAltPerifericosLayout = new javax.swing.GroupLayout(jPanelAltPerifericos);
        jPanelAltPerifericos.setLayout(jPanelAltPerifericosLayout);
        jPanelAltPerifericosLayout.setHorizontalGroup(
            jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                        .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipoAltPeriferico)
                            .addComponent(cmbTipoPerifericoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescricaoAltPeriferico)
                            .addComponent(txtDescricaoPerifericoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(lblMarcaPerifericoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbMarcaPerifericoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNSerieAltPeriferico)
                            .addComponent(txtNSeriePerifericoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanelAltPerifericosLayout.setVerticalGroup(
            jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAltPerifericosLayout.createSequentialGroup()
                .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoAltPeriferico)
                    .addComponent(lblDescricaoAltPeriferico)
                    .addComponent(lblNSerieAltPeriferico)
                    .addComponent(lblMarcaPerifericoAlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAltPerifericosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbTipoPerifericoAlt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricaoPerifericoAlt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMarcaPerifericoAlt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNSeriePerifericoAlt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanelAltPerifericosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbTipoPerifericoAlt, txtDescricaoPerifericoAlt, txtNSeriePerifericoAlt});

        btnCancelar.setText("Cancelar");

        btnGravarEquipAlt.setText("Gravar");

        javax.swing.GroupLayout painelAlteraEquipLayout = new javax.swing.GroupLayout(painelAlteraEquip);
        painelAlteraEquip.setLayout(painelAlteraEquipLayout);
        painelAlteraEquipLayout.setHorizontalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelCadEquip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addComponent(jPanelAltPerifericos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtNSeriePerifericoAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNSeriePerifericoAltActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNSeriePerifericoAltActionPerformed

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
    private javax.swing.JComboBox cmbMarcaPeriferico;
    private javax.swing.JComboBox cmbMarcaPerifericoAlt;
    private javax.swing.JComboBox cmbOffice;
    private javax.swing.JComboBox cmbOfficeAlt;
    private javax.swing.JComboBox cmbSetor;
    private javax.swing.JComboBox cmbSetorAlt;
    private javax.swing.JComboBox cmbSistemaOP;
    private javax.swing.JComboBox cmbSistemaOPAlt;
    private javax.swing.JComboBox cmbTipoPeriferico;
    private javax.swing.JComboBox cmbTipoPerifericoAlt;
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
    private javax.swing.JLabel lblMarcaPeriferico;
    private javax.swing.JLabel lblMarcaPerifericoAlt;
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
    private javax.swing.JTable tablePerifericos;
    private javax.swing.JTable tablePerifericosAlt;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private com.toedter.calendar.JDateChooser txtDataCompra;
    private com.toedter.calendar.JDateChooser txtDataCompraAlt;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDescricaoAlt;
    private javax.swing.JTextField txtDescricaoPeriferico;
    private javax.swing.JTextField txtDescricaoPerifericoAlt;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtIPAlt;
    private javax.swing.JTextField txtNSeriePeriferico;
    private javax.swing.JTextField txtNSeriePerifericoAlt;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtNumeroSerieAlt;
    // End of variables declaration//GEN-END:variables
}
