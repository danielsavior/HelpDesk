package helpdesk.utils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModelGenerico extends AbstractTableModel {

    private List<?super Object>lista;
    private Class<?>classe;
    private String[]colunas;            
    public TableModelGenerico() {
        lista=new ArrayList<>();        
    }
    public TableModelGenerico(List<?>listaDeItens, Class<?> cl) {
        lista=new ArrayList<>(listaDeItens);
        classe=cl;
        colunas= new String[classe.getDeclaredMethods().length];
        
        try{                    
            for(Method m: classe.getDeclaredMethods()){                
                Coluna c = m.getAnnotation(Coluna.class);
                if(c!=null){
                    if(c.get()){
                        colunas[c.posicao()]=c.nomeColuna();
                    }           
                }
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    public void addItem(Object e){                 
        lista.add(e);
    }    
    @Override
    public int getRowCount() {
        return lista.size();
    }
    @Override
    public int getColumnCount() {
        int conta=0;
        for(Method m: classe.getDeclaredMethods()){
            Coluna c = m.getAnnotation(Coluna.class);
                if(c!=null){
                    if(c.get()){
                        conta++;                       
                    }
                }
        }
        return conta;
    }        
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }    
    @Override
    public Class<?>getColumnClass(int columnIndex){
        Class<?>retorno=String.class;
        bloco:
        for(Method m: classe.getDeclaredMethods()){
            Coluna c = m.getAnnotation(Coluna.class);
                if(c!=null){
                    if(c.posicao()==columnIndex && c.get()){                         
                        retorno= c.retorno();
                        break bloco;
                    }
                }
        }
        return retorno;
    }    
    @Override
    public boolean isCellEditable(int rowIndex,int columnIndex){
        return false;
    }    
    @Override
    public Object getValueAt(int rowIndex,int columnIndex){        
        try{
            Object objeto = lista.get(rowIndex);            
            for(Method m: classe.getDeclaredMethods()){
                Coluna c = m.getAnnotation(Coluna.class);
                if(c!=null){
                    if(c.posicao()==columnIndex && c.get())
                        return m.invoke(objeto);
                    }
                }
        }catch(Exception e){
            
        }
        return "";
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){            
       classe=lista.get(rowIndex).getClass();
       try{
            Object objeto = lista.get(rowIndex);
            for(Method m: classe.getDeclaredMethods()){
                Coluna c = m.getAnnotation(Coluna.class);
                if(c.posicao()==columnIndex && !c.get())
                     m.invoke(objeto);
            }
        }catch(Exception e){
            
        }
       
    }    
    public List<?> remove(Object c){
        lista.remove(c);
        return lista;
    }    
    public Object getItem(int rowIndex,int columnIndex){
        return lista.get(rowIndex);
    }
    
}
