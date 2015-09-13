package helpdesk.DAO;

import helpdesk.beans.Periferico;
import helpdesk.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class PerifericoDAO extends ATodosDAO<Periferico>{

    @Override
    public Periferico buscaPorID(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Periferico>buscarPerifericos(long codEquipamento){
       Session session=null;
       Query query=null;
       StringBuilder s=new StringBuilder();
       s.append("SELECT id,equipamento,tipo,marca,descricao,ativo,nserie FROM Periferico  WHERE equipamento= ");
       s.append( String.valueOf(codEquipamento));
       s.append( " and ativo=1 ");           
       
       try{
          session=HibernateUtil.getSessionFactory().openSession();
          query=session.createQuery(s.toString());          
          List<Periferico>perifericos=new ArrayList<>();
          Iterator it=query.list().iterator();
          
          while(it.hasNext()){
              Object[]vetorIt=(Object[])it.next();
              Periferico p=new Periferico();
              p.setId((long)vetorIt[0]);
              p.setEquipamento((long)vetorIt[1]);
              p.setTipo((String)vetorIt[2]);
              p.setMarca((long)vetorIt[3]);
              p.setDescricao((String)vetorIt[4]);
              //p.setAtivo(((String)vetorIt[5]).charAt(0));
              p.setNumeroSerie((String)vetorIt[6]);
              
              //c.setStatus((char)vetorIt[6]);                            
              perifericos.add(p);
          }
          return perifericos;
       }catch(Exception e ){
           e.printStackTrace();
           return null;
       }                                
        
    }

    @Override
    public List<Periferico> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
