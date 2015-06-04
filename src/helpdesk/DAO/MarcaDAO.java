/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.Marca;
import helpdesk.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author daniel
 */
public class MarcaDAO extends ATodosDAO<Marca> {
    
    @Override
    public Marca buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Marca) session.load(Marca.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Marca> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
