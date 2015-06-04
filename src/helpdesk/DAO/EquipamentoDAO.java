/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.Equipamento;
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
public class EquipamentoDAO extends ATodosDAO<Equipamento> {
    
    @Override
    public Equipamento buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Equipamento) session.load(Equipamento.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Equipamento> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Equipamento>buscarEquipamentos(String filtro){
       Session session=null;
       Query query=null;
       StringBuilder s=new StringBuilder();
       s.append("select e.id,e.descricao,m.descricao,e.ip,e.numeroSerie,s.descricao ");       
       s.append("from Equipamento e , Marca m, Setor s ");       
       s.append("where e.marca=m.id and e.setor=s.id ");       
       s.append(filtro);       
       s.append(" order by e.ip ");       
               
       
       try{
          session=HibernateUtil.getSessionFactory().openSession();
          query=session.createQuery(s.toString());          
          List<Equipamento>equipamentos=new ArrayList<>();
          Iterator it=query.list().iterator();
          
          while(it.hasNext()){
              Object[]vetorIt=(Object[])it.next();
              Equipamento e=new Equipamento();
              e.setId((long)vetorIt[0]);
              e.setDescricao(((String)vetorIt[1]));
              e.setNomeMarca((String)vetorIt[2]);
              e.setIp((String)vetorIt[3]);
              e.setNumeroSerie((String)vetorIt[4]);
              e.setNomeSetor((String)vetorIt[5]);                                        
              equipamentos.add(e);
          }
          return equipamentos;
       }catch(Exception e ){
           e.printStackTrace();
           return null;
       }
    }
    
}
