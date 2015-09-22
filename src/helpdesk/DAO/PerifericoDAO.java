package helpdesk.DAO;

import helpdesk.beans.Periferico;
import helpdesk.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
              p.setAtivo((char) vetorIt[5]);
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

    public boolean checaNSerie(String nSerie) {
       boolean retorno=false;
       Session session=null;
       Query query=null;
       StringBuilder s=new StringBuilder();
       s.append("SELECT count(*) FROM Periferico  WHERE nserie='");
       s.append( nSerie);
       s.append( "' and ativo=1 ");   
       try{
           session=HibernateUtil.getSessionFactory().openSession();
           query=session.createQuery(s.toString());  
           retorno=((Long)query.list().get(0))>0?false:true;
       }catch(Exception e){
           
       }
       return retorno;
    }

    public void inativar(Long id) {
       Session session=null;
       Query query=null;
       StringBuilder s=new StringBuilder();
       s.append("UPDATE Periferico SET ativo='0' WHERE id= ");
       s.append(id);       
       try{
           session=HibernateUtil.getSessionFactory().openSession();
           Transaction t= session.beginTransaction();
           query=session.createQuery(s.toString());  
           query.executeUpdate();
           t.commit();
       }catch(Exception e){
           
       }finally{
           session.close();
       }
    }
    
}
