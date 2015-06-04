/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.utils;

import helpdesk.beans.Chamado;
import helpdesk.beans.Equipamento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Admin
 */
public class EquipamentoTableModel extends AbstractTableModel {

    private List<Equipamento>equipamentos;
    private String[]colunas={"Código","Descricao","Marca","Endereço IP","Nº de Série","Setor"};
    private static final int CODIGO=0;
    private static final int DESCRICAO=1;
    private static final int MARCA=2;
    private static final int ENDERECOIP=3;
    private static final int NUMEROSERIE=4;
    private static final int SETOR=5;    
    
    
    public EquipamentoTableModel() {
        equipamentos=new ArrayList<>();
    }
    public EquipamentoTableModel(List<Equipamento>listaDeEquipamentos) {
        equipamentos=new ArrayList<>(listaDeEquipamentos);
    }
    
    public void addChamado(Equipamento e){
        equipamentos.add(e);
    }
    
    @Override
    public int getRowCount() {
        return this.equipamentos.size();
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
            case DESCRICAO:
                return String.class;
            case MARCA:
                return String.class;
            case ENDERECOIP:
                return String.class;
            case NUMEROSERIE:
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
        Equipamento e= equipamentos.get(rowIndex);
        switch(columnIndex){
            case CODIGO:
                return e.getId();
            case DESCRICAO:
                return e.getDescricao();
            case MARCA:
               return e.getNomeMarca();
            case ENDERECOIP:
               return e.getIp();
            case NUMEROSERIE:
               return e.getNumeroSerie();
            case SETOR:
               return e.getNomeSetor();            
            default:
                 //throw new IndexOutOfBoundsException("columnIndex out of bounds");
                return new Integer(0);
        }
    
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
    
        Equipamento e=equipamentos.get(rowIndex);
        switch(columnIndex){
            case CODIGO:
               e.setId((Long)aValue);
            case DESCRICAO:
                e.setDescricao((String)aValue);
            case MARCA:
                e.setNomeMarca((String)aValue);
            case ENDERECOIP:
                e.setIp((String)aValue);
            case NUMEROSERIE:
                e.setNumeroSerie((String)aValue);
            case SETOR:
                e.setNomeSetor((String)aValue);            
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        
        }
        //fireTableCellUpdated(rowIndex, columnIndex);
    
    }
    
    public List<Equipamento> remove(Equipamento c){
        equipamentos.remove(c);
        return equipamentos;
    }
    public Equipamento getProduto(int rowIndex,int columnIndex){
        return equipamentos.get(rowIndex);
    }
    
}
