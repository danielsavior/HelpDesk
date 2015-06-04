/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.Impressora;
import helpdesk.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author daniel
 */
public class ImpressoraDAO extends ATodosDAO<Impressora> {
    
    @Override
    public Impressora buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Impressora) session.load(Impressora.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Impressora> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Impressora>buscarImpressoras(String filtro){
       Session session=null;
       Query query=null;
       StringBuilder s=new StringBuilder();
       s.append("select i.id,i.modelo,i.modo,i.compartilhada,m.descricao,i.ip,s.descricao ");       
       s.append("from Impressora i , Marca m, Setor s ");       
       s.append("where i.marca=m.id and i.setor=s.id ");       
       s.append(filtro);       
       s.append(" order by i.ip ");       
               
       
       try{
          session=HibernateUtil.getSessionFactory().openSession();
          query=session.createQuery(s.toString());          
          List<Impressora>impressoras=new ArrayList<>();
          Iterator it=query.list().iterator();
          
          while(it.hasNext()){
              Object[]vetorIt=(Object[])it.next();
              Impressora e=new Impressora();
              e.setId((long)vetorIt[0]);
              e.setModelo(((String)vetorIt[1]));
              e.setModo(((String)vetorIt[2]).equals("IP")?1:2);
              e.setCompartilhada(((String)vetorIt[3]).equals("Sim")?1:2);
              e.setNomeMarca((String)vetorIt[4]);
              e.setIp((String)vetorIt[5]);              
              e.setNomeSetor((String)vetorIt[6]);                                        
              impressoras.add(e);
          }
          return impressoras;
       }catch(Exception e ){
           e.printStackTrace();
           return null;
       }
    }
    
}
