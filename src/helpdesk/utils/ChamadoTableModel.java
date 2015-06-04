/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.utils;

import helpdesk.beans.Chamado;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Admin
 */
public class ChamadoTableModel extends AbstractTableModel {

    private List<Chamado>chamados;
    private String[]colunas={"Código","Data de abertura","Usuário","Setor","Problema","Prioridade","Status"};
    private static final int CODIGO=0;
    private static final int DATAABERTURA=1;
    private static final int USUARIO=2;
    private static final int SETOR=3;
    private static final int PROBLEMA=4;
    private static final int PRIORIDADE=5;
    private static final int STATUS=6;
    
    
    public ChamadoTableModel() {
        chamados=new ArrayList<>();
    }
    public ChamadoTableModel(List<Chamado>listaDeChamados) {
        chamados=new ArrayList<>(listaDeChamados);
    }
    
    public void addChamado(Chamado c){
        chamados.add(c);
    }
    
    @Override
    public int getRowCount() {
        return this.chamados.size();
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
            case DATAABERTURA:
                return String.class;
            case USUARIO:
                return String.class;
            case SETOR:
                return String.class;
            case PROBLEMA:
                return String.class;
            case PRIORIDADE:
                return String.class;
            case STATUS:
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
        Chamado c= chamados.get(rowIndex);
        switch(columnIndex){
            case CODIGO:
                return c.getId();
            case DATAABERTURA:
                return c.getDataAbertura();
            case USUARIO:
               return c.getNomeUsuario();
            case SETOR:
               return c.getDescSetor();
            case PROBLEMA:
               return c.getDescProblema();
            case PRIORIDADE:
               return c.getDescPrioridade();
            case STATUS:
               return c.getDescStatus();                
            default:
                 //throw new IndexOutOfBoundsException("columnIndex out of bounds");
                return new Integer(0);
        }
    
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
    
        Chamado c=chamados.get(rowIndex);
        switch(columnIndex){
            case CODIGO:
               c.setId((Long)aValue);
            case DATAABERTURA:
                c.setDataAbertura((String)aValue);
            case USUARIO:
                c.setNomeUsuario((String)aValue);
            case SETOR:
                c.setDescSetor((String)aValue);
            case PROBLEMA:
                c.setDescProblema((String)aValue);
            case PRIORIDADE:
                c.setDescPrioridade((String)aValue);
            case STATUS:
                c.setDescStatus(((String)aValue));
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        
        }
        //fireTableCellUpdated(rowIndex, columnIndex);
    
    }
    
    public List<Chamado> remove(Chamado c){
        chamados.remove(c);
        return chamados;
    }
    public Chamado getProduto(int rowIndex,int columnIndex){
        return chamados.get(rowIndex);
    }
    
}
