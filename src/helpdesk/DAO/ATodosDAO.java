/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author daniel
 */
public abstract class ATodosDAO <T>{
    public void insert(T objeto){
        Session session =null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction t= session.beginTransaction();
            session.save(objeto);
            t.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    public void delete(T objeto){}
    public void update(T objeto){
        Session session =null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(objeto);
            t.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    abstract T buscaPorID(long id);
    abstract List<T> buscaPorNome(String nome);
    public List<T> listar(String tabela){ 
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from "+tabela).list();
            t.commit();
            return lista;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
