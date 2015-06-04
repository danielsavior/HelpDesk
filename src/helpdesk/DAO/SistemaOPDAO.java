/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.SistemaOP;
import helpdesk.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author daniel
 */
public class SistemaOPDAO extends ATodosDAO<SistemaOP> {
    
    @Override
    public SistemaOP buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (SistemaOP) session.load(SistemaOP.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SistemaOP> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
