package helpdesk.DAO;

import helpdesk.beans.Empresa;
import helpdesk.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

public class EmpresaDAO extends ATodosDAO<Empresa>{

    @Override
    public Empresa buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Empresa) session.load(Empresa.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Empresa> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
