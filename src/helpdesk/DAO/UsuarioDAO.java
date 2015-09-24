package helpdesk.DAO;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import helpdesk.beans.Usuario;
import helpdesk.utils.HibernateUtil;


public class UsuarioDAO extends ATodosDAO<Usuario> implements IUsuarioDAO  {

    
    
    public Usuario efetuarLogin(String usuario, String senha){
        Session session =null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Query query=session.createQuery("from Usuario where login=:usuario and senha=:senha");
            query.setParameter("usuario", usuario);
            query.setParameter("senha", senha);
            if(query.list().isEmpty()){
                return null;
            }else{
                return (Usuario)query.list().get(0);
            }
        }catch(ExceptionInInitializerError e){
            throw new ExceptionInInitializerError();
        }finally{
            session.close();
        }
        
    }    

    @Override
    public Usuario buscaPorID(long id) {
        Session session =null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            return (Usuario) session.load(Usuario.class, id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Usuario> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public List<Usuario> buscaPorSetor(long setor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public List<Usuario> listarOperadores(){ 
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("FROM Usuario WHERE perfil='1' or perfil='3'").list();
            t.commit();
            return lista;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<Usuario> listarDoSetor(long setor){ 
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();            
            Query query= session.createQuery("FROM Usuario WHERE setor=:setor");
            query.setParameter("setor", setor);
            List lista=query.list();
            t.commit();
            return lista;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String buscarPorLogin(String login) {
        try{
            StringBuilder sb=new StringBuilder();
            sb.append("Select nomeCompleto FROM Usuario WHERE login='");
            sb.append(login);
            sb.append("'");
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query query=session.createQuery(sb.toString());                                    
            String nome = (String)query.list().get(0);            
            return nome;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
