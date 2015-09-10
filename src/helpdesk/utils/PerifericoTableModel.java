package helpdesk.utils;

import helpdesk.beans.Periferico;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PerifericoTableModel extends AbstractTableModel{
    private List<Periferico>perifericos;
    private String[]colunas={"Tipo","Descrição","Nº de série"};
    private static final int TIPO=0;
    private static final int DESCRICAO=1;
    private static final int NSERIE=2;    
    
    public PerifericoTableModel(){
        perifericos=new ArrayList<>();
    }
    public PerifericoTableModel(List<Periferico>listaDePerifericos){
        perifericos=new ArrayList<>(listaDePerifericos);
    }
    public void addPeriferico(Periferico p){
        perifericos.add(p);
    }
    @Override
    public int getRowCount() {
        return this.perifericos.size();
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
            case TIPO:
                return Integer.class;
            case DESCRICAO:
                return String.class;
            case NSERIE:
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
        Periferico p= perifericos.get(rowIndex);
        switch(columnIndex){
            case TIPO:
                return p.getTipo();
            case DESCRICAO:
                return p.getDescricao();
            case NSERIE:
                return p.getNumeroSerie();            
            default:                 
                return new Integer(0);
        }
    
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
    
        Periferico p=perifericos.get(rowIndex);
        switch(columnIndex){
            case TIPO:
               p.setTipo((int)aValue);
            case DESCRICAO:
                p.setDescricao((String)aValue);
            case NSERIE:
                p.setNumeroSerie((String)aValue);            
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");        
        }        
    
    }    
    public List<Periferico> remove(Periferico p){
        perifericos.remove(p);
        return perifericos;
    }
    public Periferico getPeriferico(int rowIndex,int columnIndex){
        return perifericos.get(rowIndex);
    }    
}
