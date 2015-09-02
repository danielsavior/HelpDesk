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
import helpdesk.beans.Impressora;
import helpdesk.beans.Marca;
import helpdesk.beans.Office;
import helpdesk.beans.Setor;
import helpdesk.beans.SistemaOP;
import helpdesk.beans.Usuario;
import helpdesk.controllers.ImpressoraController;
import helpdesk.controllers.MarcaController;
import helpdesk.controllers.SetorController;
import helpdesk.utils.AutoCompleteTextField;
import helpdesk.utils.Utilidades;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
public class FrmImpressora extends javax.swing.JDialog {

    class RadioButtonHandler implements ItemListener{        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(rbRede.isSelected()){
                if (e.getItem().equals(rbRede)){
                    rbCompartilhada.setSelected(false);               
                    rbCompartilhada.setVisible(false);                                    
                    rbUSB.setSelected(false);
                    lblIP.setVisible(true);
                    txtIP.setVisible(true);                    
                }                
            }                
            if(rbUSB.isSelected()){
                if (e.getItem().equals(rbUSB)){
                    rbCompartilhada.setSelected(false); 
                    rbRede.setSelected(false);
                    rbCompartilhada.setVisible(true);                                    
                    lblIP.setVisible(false);
                    txtIP.setVisible(false);                    
                }               
            }
        }
    }
    class RadioButtonAltHandler implements ItemListener{        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(rbRedeAlt.isSelected()){
                if (e.getItem().equals(rbRedeAlt)){
                    rbCompartilhadaAlt.setSelected(false);               
                    rbCompartilhadaAlt.setVisible(false);                                    
                    rbUSBAlt.setSelected(false);
                    lblIPAlt.setVisible(true);
                    txtIPAlt.setVisible(true);                    
                }                
            }                
            if(rbUSBAlt.isSelected()){
                if (e.getItem().equals(rbUSBAlt)){
                    rbCompartilhadaAlt.setSelected(false); 
                    rbRedeAlt.setSelected(false);
                    rbCompartilhadaAlt.setVisible(true);                                    
                    lblIPAlt.setVisible(false);
                    txtIPAlt.setVisible(false);                    
                }               
            }
        }
    }
    
    
    
    private List<String>listaDeImpressoras;
    public FrmImpressora(long codImpressora){
        this(new Frame(),true);
        tabImpressora.setSelectedIndex(1);
        txtCodigoAlt.setText(String.valueOf(codImpressora));
        buscaPorID(codImpressora);        
    }
    public FrmImpressora(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        listaDeImpressoras=new ArrayList<>();
//        for(Impressora i: new ImpressoraController().listar()){
//            listaDeImpressoras.add(i.getModelo()+" codigo"+i.getId());
//        }
        initComponents();  
        rbCompartilhada.setVisible(false);
        lblIP.setVisible(false);
        txtIP.setVisible(false);
        
        rbCompartilhadaAlt.setVisible(false);
        lblIPAlt.setVisible(false);
        txtIPAlt.setVisible(false);
        txtCodigo.setText(String.valueOf(Utilidades.retornarProximoID("Impressora")));        
        carregaCombos();
        
        tabImpressora.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabImpressora.getSelectedIndex()==0){
                    limparAlteracao();
                }else{
                    limparInclusao();
                }
            }
        });
        
        btnGravarImpressora.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    Impressora impressora =new Impressora();
                    impressora.setModelo(txtModelo.getText());  
                    impressora.setPatrimonio(txtPatrimonio.getText().toString());
                    impressora.setModo(rbRede.isSelected()?1:2);
                    impressora.setCompartilhada(rbCompartilhada.isSelected()?1:2);                    
                    impressora.setMarca(Long.valueOf(String.valueOf(cmbMarca.getSelectedItem().toString().subSequence(0, cmbMarca.getSelectedItem().toString().indexOf("-")))));                    
                    impressora.setSetor(Long.valueOf(String.valueOf(cmbSetor.getSelectedItem().toString().subSequence(0, cmbSetor.getSelectedItem().toString().indexOf("-")))));                    
                    impressora.setIp(txtIP.getText().toString());                                        
                    new ImpressoraController().insert(impressora);
                    txtCodigo.setText(String.valueOf(Integer.valueOf(txtCodigo.getText())+1));
                    limparInclusao();
                }catch(Exception e){                    
                    e.printStackTrace();
                }
            }
        });
        
        btnGravarImpressoraAlt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(txtCodigoAlt.getText().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Selecione um equipamento para alterar.");
                }else{
                    try{
                        Impressora impressora= new Impressora();
                        impressora.setId((long)Integer.valueOf(txtCodigoAlt.getText().toString()));
                        impressora.setModelo(txtModeloAlt.getText().toString());
                        impressora.setPatrimonio(txtPatrimonioAlt.getText().toString());
                        impressora.setModo(rbRedeAlt.isSelected()?1:2);
                        impressora.setCompartilhada(rbCompartilhadaAlt.isSelected()?1:2);                    
                        impressora.setMarca(Long.valueOf(String.valueOf(cmbMarcaAlt.getSelectedItem().toString().subSequence(0, cmbMarcaAlt.getSelectedItem().toString().indexOf("-")))));                                                
                        impressora.setSetor(Long.valueOf(String.valueOf(cmbSetorAlt.getSelectedItem().toString().subSequence(0, cmbSetorAlt.getSelectedItem().toString().indexOf("-")))));                        
                        impressora.setIp(txtIPAlt.getText().toString());                                            
                        new ImpressoraController().update(impressora);
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
        
        
        
        rbRede.addItemListener(new RadioButtonHandler());
        rbUSB.addItemListener(new RadioButtonHandler());        
        rbCompartilhada.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(rbCompartilhada.isSelected()){                                                        
                    lblIP.setVisible(true);
                    txtIP.setVisible(true);                                                    
                }else{
                    if(rbUSB.isSelected()){
                        lblIP.setVisible(false);
                        txtIP.setVisible(false);                                                    
                    }
                }
            }
        });
        rbRedeAlt.addItemListener(new RadioButtonAltHandler());
        rbUSBAlt.addItemListener(new RadioButtonAltHandler());
        rbCompartilhadaAlt.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(rbCompartilhadaAlt.isSelected()){                                                        
                    lblIPAlt.setVisible(true);
                    txtIPAlt.setVisible(true);                                                    
                }else{
                    if(rbUSBAlt.isSelected()){
                        lblIPAlt.setVisible(false);
                        txtIPAlt.setVisible(false);                                                    
                    }
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                limparAlteracao();
            }
        });
                                
        txtModeloAlt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==10){
                    if(txtModeloAlt.getText().contains("codigo")){
                        String texto=txtModeloAlt.getText();
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
        
        cmbMarca.removeAllItems();
        cmbMarcaAlt.removeAllItems();
        cmbSetor.removeAllItems();
        cmbSetorAlt.removeAllItems();
        
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
    
    private void limparAlteracao(){
        txtCodigoAlt.setText("");
        txtCodigoAlt.setEnabled(true);
        txtModeloAlt.setText("");
        txtPatrimonioAlt.setText("");
        txtIPAlt.setText("");                               
    }
    private void limparInclusao(){        
        txtModelo.setText("");
        txtPatrimonio.setText("");
        txtIP.setText("");                               
    }
    private void buscaPorID(long id){
        try{
            String marc="",set="";
            Impressora u=new ImpressoraController().buscarPorID(id);
            txtModeloAlt.setText(u.getModelo());
            for(int i=0;i<cmbMarcaAlt.getItemCount();i++){
                cmbMarcaAlt.setSelectedIndex(i);
                if(cmbMarcaAlt.getSelectedItem().toString().substring(0,cmbMarcaAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getMarca()))){
                    marc=cmbMarcaAlt.getSelectedItem().toString();                                
                }
            }
            cmbMarcaAlt.setSelectedItem(marc);            
                        
            for(int i=0;i<cmbSetorAlt.getItemCount();i++){
                cmbSetorAlt.setSelectedIndex(i);
                if(cmbSetorAlt.getSelectedItem().toString().substring(0,cmbSetorAlt.getSelectedItem().toString().indexOf("-")).equals(String.valueOf(u.getSetor()))){
                    set=cmbSetorAlt.getSelectedItem().toString();                                
                }
            }
            cmbSetorAlt.setSelectedItem(set);
            
            if(u.getModo().equals("IP")){
                rbRedeAlt.setSelected(true);
            }else{
                rbUSBAlt.setSelected(true);            
            }
            if(u.getCompartilhada().equals("Sim")){
                rbCompartilhadaAlt.setSelected(true);
            }
            txtIPAlt.setText(u.getIp());
            txtPatrimonioAlt.setText(u.getPatrimonio());            
            txtCodigoAlt.setEnabled(false);                                    
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

        tabImpressora = new javax.swing.JTabbedPane();
        painelCadEquip = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblPatrimonio = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        btnGravarImpressora = new javax.swing.JButton();
        lblMarca = new javax.swing.JLabel();
        cmbMarca = new javax.swing.JComboBox();
        lblNumeroSerie = new javax.swing.JLabel();
        txtPatrimonio = new javax.swing.JTextField();
        lblSetor = new javax.swing.JLabel();
        cmbSetor = new javax.swing.JComboBox();
        painelModo = new javax.swing.JPanel();
        txtIP = new javax.swing.JTextField();
        lblIP = new javax.swing.JLabel();
        rbRede = new javax.swing.JRadioButton();
        rbUSB = new javax.swing.JRadioButton();
        rbCompartilhada = new javax.swing.JRadioButton();
        painelAlteraEquip = new javax.swing.JPanel();
        painelCadEquip1 = new javax.swing.JPanel();
        lblCodigoAlt = new javax.swing.JLabel();
        txtCodigoAlt = new javax.swing.JTextField();
        lblModeloAlt = new javax.swing.JLabel();
        txtModeloAlt = new AutoCompleteTextField(5,false,listaDeImpressoras);
        lblMarcaAlt = new javax.swing.JLabel();
        cmbMarcaAlt = new javax.swing.JComboBox();
        lblPatrimonioAlt = new javax.swing.JLabel();
        txtPatrimonioAlt = new javax.swing.JTextField();
        cmbSetorAlt = new javax.swing.JComboBox();
        lblSetorAlt = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnGravarImpressoraAlt = new javax.swing.JButton();
        painelModoAlt = new javax.swing.JPanel();
        txtIPAlt = new javax.swing.JTextField();
        lblIPAlt = new javax.swing.JLabel();
        rbRedeAlt = new javax.swing.JRadioButton();
        rbUSBAlt = new javax.swing.JRadioButton();
        rbCompartilhadaAlt = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Impressoras");
        setResizable(false);

        painelCadEquip.setBackground(java.awt.Color.lightGray);

        lblCodigo.setText("Código");

        txtCodigo.setText("1");
        txtCodigo.setEnabled(false);

        lblPatrimonio.setText("Modelo:");

        btnGravarImpressora.setText("Gravar");

        lblMarca.setText("Marca:");

        lblNumeroSerie.setText("Patrimônio:");

        lblSetor.setText("Setor:");

        painelModo.setBackground(java.awt.Color.lightGray);
        painelModo.setBorder(javax.swing.BorderFactory.createTitledBorder("Modo"));
        painelModo.setMaximumSize(new java.awt.Dimension(186, 123));
        painelModo.setMinimumSize(new java.awt.Dimension(186, 123));

        lblIP.setText("IP:");

        rbRede.setBackground(java.awt.Color.lightGray);
        rbRede.setText("Rede");

        rbUSB.setBackground(java.awt.Color.lightGray);
        rbUSB.setText("USB");

        rbCompartilhada.setBackground(java.awt.Color.lightGray);
        rbCompartilhada.setText("Compartilhada");

        javax.swing.GroupLayout painelModoLayout = new javax.swing.GroupLayout(painelModo);
        painelModo.setLayout(painelModoLayout);
        painelModoLayout.setHorizontalGroup(
            painelModoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelModoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelModoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIP)
                    .addGroup(painelModoLayout.createSequentialGroup()
                        .addGroup(painelModoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rbRede)
                            .addComponent(lblIP, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(painelModoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rbCompartilhada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbUSB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        painelModoLayout.setVerticalGroup(
            painelModoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelModoLayout.createSequentialGroup()
                .addGroup(painelModoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbRede)
                    .addComponent(rbUSB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbCompartilhada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout painelCadEquipLayout = new javax.swing.GroupLayout(painelCadEquip);
        painelCadEquip.setLayout(painelCadEquipLayout);
        painelCadEquipLayout.setHorizontalGroup(
            painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquipLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumeroSerie)
                            .addComponent(txtPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(painelModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGravarImpressora, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCadEquipLayout.createSequentialGroup()
                                .addComponent(lblSetor)
                                .addGap(215, 215, 215))
                            .addComponent(cmbSetor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCadEquipLayout.createSequentialGroup()
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquipLayout.createSequentialGroup()
                                .addComponent(lblPatrimonio)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtModelo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMarca)
                            .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painelCadEquipLayout.setVerticalGroup(
            painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquipLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCodigo)
                .addGap(3, 3, 3)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(lblPatrimonio)
                        .addGap(23, 23, 23))
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(lblMarca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(lblNumeroSerie)
                        .addGap(4, 4, 4)
                        .addComponent(txtPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(lblSetor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelCadEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravarImpressora))
                    .addGroup(painelCadEquipLayout.createSequentialGroup()
                        .addComponent(painelModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabImpressora.addTab("Cadastro", painelCadEquip);

        painelAlteraEquip.setBackground(java.awt.Color.gray);

        painelCadEquip1.setBackground(java.awt.Color.gray);

        lblCodigoAlt.setText("Código:");

        lblModeloAlt.setText("Modelo:");

        lblMarcaAlt.setText("Marca:");

        lblPatrimonioAlt.setText("Patrimônio:");

        lblSetorAlt.setText("Setor:");

        btnCancelar.setText("Cancelar");

        btnGravarImpressoraAlt.setText("Gravar");

        painelModoAlt.setBackground(java.awt.Color.gray);
        painelModoAlt.setBorder(javax.swing.BorderFactory.createTitledBorder("Modo"));
        painelModoAlt.setMaximumSize(new java.awt.Dimension(186, 123));
        painelModoAlt.setMinimumSize(new java.awt.Dimension(186, 123));

        lblIPAlt.setText("IP:");

        rbRedeAlt.setBackground(java.awt.Color.gray);
        rbRedeAlt.setText("Rede");

        rbUSBAlt.setBackground(java.awt.Color.gray);
        rbUSBAlt.setText("USB");

        rbCompartilhadaAlt.setBackground(java.awt.Color.gray);
        rbCompartilhadaAlt.setText("Compartilhada");

        javax.swing.GroupLayout painelModoAltLayout = new javax.swing.GroupLayout(painelModoAlt);
        painelModoAlt.setLayout(painelModoAltLayout);
        painelModoAltLayout.setHorizontalGroup(
            painelModoAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelModoAltLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelModoAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIPAlt)
                    .addGroup(painelModoAltLayout.createSequentialGroup()
                        .addGroup(painelModoAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rbRedeAlt)
                            .addComponent(lblIPAlt, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(painelModoAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rbCompartilhadaAlt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbUSBAlt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        painelModoAltLayout.setVerticalGroup(
            painelModoAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelModoAltLayout.createSequentialGroup()
                .addGroup(painelModoAltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbRedeAlt)
                    .addComponent(rbUSBAlt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbCompartilhadaAlt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIPAlt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIPAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout painelCadEquip1Layout = new javax.swing.GroupLayout(painelCadEquip1);
        painelCadEquip1.setLayout(painelCadEquip1Layout);
        painelCadEquip1Layout.setHorizontalGroup(
            painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                                .addComponent(lblModeloAlt)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtModeloAlt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbMarcaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMarcaAlt)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelCadEquip1Layout.createSequentialGroup()
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigoAlt)
                            .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addComponent(painelModoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGravarImpressoraAlt)
                        .addGap(8, 8, 8))
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPatrimonioAlt)
                            .addComponent(txtPatrimonioAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSetorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSetorAlt))))
                .addContainerGap())
        );
        painelCadEquip1Layout.setVerticalGroup(
            painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCadEquip1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCodigoAlt)
                .addGap(3, 3, 3)
                .addComponent(txtCodigoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMarcaAlt)
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addComponent(lblModeloAlt)
                        .addGap(3, 3, 3)
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtModeloAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMarcaAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPatrimonioAlt)
                    .addComponent(lblSetorAlt))
                .addGap(4, 4, 4)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPatrimonioAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSetorAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(painelCadEquip1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnGravarImpressoraAlt)))
                    .addGroup(painelCadEquip1Layout.createSequentialGroup()
                        .addComponent(painelModoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout painelAlteraEquipLayout = new javax.swing.GroupLayout(painelAlteraEquip);
        painelAlteraEquip.setLayout(painelAlteraEquipLayout);
        painelAlteraEquipLayout.setHorizontalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelCadEquip1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelAlteraEquipLayout.setVerticalGroup(
            painelAlteraEquipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAlteraEquipLayout.createSequentialGroup()
                .addComponent(painelCadEquip1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabImpressora.addTab("Alteração", painelAlteraEquip);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabImpressora)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabImpressora)
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
            java.util.logging.Logger.getLogger(FrmImpressora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmImpressora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmImpressora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmImpressora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmImpressora dialog = new FrmImpressora(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravarImpressora;
    private javax.swing.JButton btnGravarImpressoraAlt;
    private javax.swing.JComboBox cmbMarca;
    private javax.swing.JComboBox cmbMarcaAlt;
    private javax.swing.JComboBox cmbSetor;
    private javax.swing.JComboBox cmbSetorAlt;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoAlt;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblIPAlt;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMarcaAlt;
    private javax.swing.JLabel lblModeloAlt;
    private javax.swing.JLabel lblNumeroSerie;
    private javax.swing.JLabel lblPatrimonio;
    private javax.swing.JLabel lblPatrimonioAlt;
    private javax.swing.JLabel lblSetor;
    private javax.swing.JLabel lblSetorAlt;
    private javax.swing.JPanel painelAlteraEquip;
    private javax.swing.JPanel painelCadEquip;
    private javax.swing.JPanel painelCadEquip1;
    private javax.swing.JPanel painelModo;
    private javax.swing.JPanel painelModoAlt;
    private javax.swing.JRadioButton rbCompartilhada;
    private javax.swing.JRadioButton rbCompartilhadaAlt;
    private javax.swing.JRadioButton rbRede;
    private javax.swing.JRadioButton rbRedeAlt;
    private javax.swing.JRadioButton rbUSB;
    private javax.swing.JRadioButton rbUSBAlt;
    private javax.swing.JTabbedPane tabImpressora;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoAlt;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtIPAlt;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtModeloAlt;
    private javax.swing.JTextField txtPatrimonio;
    private javax.swing.JTextField txtPatrimonioAlt;
    // End of variables declaration//GEN-END:variables
}
