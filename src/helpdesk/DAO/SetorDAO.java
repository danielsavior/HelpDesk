/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.Setor;
import helpdesk.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author daniel
 */
public class SetorDAO extends ATodosDAO<Setor> {
    
    @Override
    public Setor buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Setor) session.load(Setor.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Setor> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
