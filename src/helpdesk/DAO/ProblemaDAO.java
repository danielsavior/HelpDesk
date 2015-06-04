/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.Problema;
import helpdesk.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author daniel
 */
public class ProblemaDAO extends ATodosDAO<Problema> {
    
    @Override
    public Problema buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Problema) session.load(Problema.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Problema> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
