/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.Fornecedor;
import helpdesk.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author daniel
 */
public class FornecedorDAO extends ATodosDAO<Fornecedor> {

    @Override
    public Fornecedor buscaPorID(long id) {
       Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Fornecedor) session.load(Fornecedor.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Fornecedor> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
    
}
