/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.utils;

import helpdesk.beans.Impressora;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Admin
 */
public class ImpressoraTableModel extends AbstractTableModel {

    private List<Impressora>impressoras;
    private String[]colunas={"Código","Modelo","Modo","Compartilhada","Endereço IP","Marca","Setor"};
    private static final int CODIGO=0;
    private static final int MODELO=1;
    private static final int MODO=2;
    private static final int COMPARTILHADA=3;
    private static final int ENDERECOIP=4;
    private static final int MARCA=5;
    private static final int SETOR=6;    
    
    
    public ImpressoraTableModel() {
        impressoras=new ArrayList<>();
    }
    public ImpressoraTableModel(List<Impressora>listaDeImpressoras) {
        impressoras=new ArrayList<>(listaDeImpressoras);
    }
    
    public void addImpressora(Impressora e){
        impressoras.add(e);
    }
    
    @Override
    public int getRowCount() {
        return this.impressoras.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    
    
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }
    
    @Override
    public Class<?>getColumnClass(int columnIndex){
        switch(columnIndex){
            case CODIGO:
                return String.class;
            case MODELO:
                return String.class;
            case MODO:
                return Integer.class;
            case MARCA:
                return String.class;
            case ENDERECOIP:
                return String.class;
            case COMPARTILHADA:
                return String.class;
            case SETOR:
                return String.class;            
            default:
                return String.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex,int columnIndex){
        return false;
    }
    
    @Override
    public Object getValueAt(int rowIndex,int columnIndex){
        Impressora i= impressoras.get(rowIndex);
        switch(columnIndex){
            case CODIGO:
                return i.getId();
            case MODELO:
                return i.getModelo();
            case MODO:
                return i.getModo();
            case MARCA:
               return i.getNomeMarca();
            case ENDERECOIP:
               return i.getIp();
            case COMPARTILHADA:
               return i.getCompartilhada();
            case SETOR:
               return i.getNomeSetor();            
            default:
                 //throw new IndexOutOfBoundsException("columnIndex out of bounds");
                return new Integer(0);
        }
    
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
    
        Impressora i=impressoras.get(rowIndex);
        switch(columnIndex){
            case CODIGO:
               i.setId((Long)aValue);
            case MODELO:
                i.setModelo((String)aValue);
            case MODO:
                i.setModo((Integer)aValue);
            case MARCA:
                i.setNomeMarca((String)aValue);
            case ENDERECOIP:
                i.setIp((String)aValue);
            case COMPARTILHADA:
                i.setCompartilhada(((String)aValue).equals("Sim")?1:2);
            case SETOR:
                i.setNomeSetor((String)aValue);            
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        
        }
        //fireTableCellUpdated(rowIndex, columnIndex);
    
    }
    
    public List<Impressora> remove(Impressora c){
        impressoras.remove(c);
        return impressoras;
    }
    public Impressora getImpressora(int rowIndex,int columnIndex){
        return impressoras.get(rowIndex);
    }
    
}
