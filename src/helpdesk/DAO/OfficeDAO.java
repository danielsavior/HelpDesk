/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.Office;
import helpdesk.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author daniel
 */
public class OfficeDAO extends ATodosDAO<Office> {
    
    @Override
    public Office buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Office) session.load(Office.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Office> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
