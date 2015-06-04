/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.Chamado;
import helpdesk.utils.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author daniel
 */
public class ChamadoDAO extends ATodosDAO<Chamado> {

    

    @Override
    public Chamado buscaPorID(long id) {
        Session session=null;
        try{
            session=HibernateUtil.getSessionFactory().openSession();
            return (Chamado) session.load(Chamado.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Chamado> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Chamado>buscarChamados(long setor){
       Session session=null;
       Query query=null;
       StringBuilder s=new StringBuilder();
       s.append("SELECT c.id,date_format(c.dataAbertura,'%d-%m-%Y'), u.nomeCompleto as usuario, ");       
       s.append("s.descricao as setor,p.descricao as problema,c.prioridade,c.status ");       
       s.append("FROM Chamado c,Usuario u, Setor s, Problema p ");       
       s.append("WHERE  c.problema = p.id AND c.usuario = u.id AND u.setor = s.id ");              
       if(setor>0){
            s.append("and s.id= ");              
            s.append(String.valueOf(setor));              
       }
       try{
          session=HibernateUtil.getSessionFactory().openSession();
          query=session.createQuery(s.toString());          
          List<Chamado>chamados=new ArrayList<>();
          Iterator it=query.list().iterator();
          
          while(it.hasNext()){
              Object[]vetorIt=(Object[])it.next();
              Chamado c=new Chamado();
              c.setId((long)vetorIt[0]);
              c.setDataAbertura(((String)vetorIt[1]));
              c.setNomeUsuario((String)vetorIt[2]);
              c.setDescSetor((String)vetorIt[3]);
              c.setDescProblema((String)vetorIt[4]);
              if(String.valueOf((int)vetorIt[5]).equals("1")){
                  c.setDescPrioridade("Baixa");
              }else if(String.valueOf((int)vetorIt[5]).equals("2")){
                  c.setDescPrioridade("Média");
              }else if(String.valueOf((int)vetorIt[5]).equals("3")){
                  c.setDescPrioridade("Alta");
              }
              //c.setDescPrioridade(String.valueOf((int)vetorIt[5]));
              if(String.valueOf((char)vetorIt[6]).equals("P")){
                  c.setDescStatus("Pendente");                            
              }else if(String.valueOf((char)vetorIt[6]).equals("F")){
                  c.setDescStatus("Finalizado");                            
              }
              //c.setStatus((char)vetorIt[6]);                            
              chamados.add(c);
          }
          return chamados;
       }catch(Exception e ){
           e.printStackTrace();
           return null;
       }
    }
    public List<Chamado>buscarChamados(String filtro){
       Session session=null;
       Query query=null;
       StringBuilder s=new StringBuilder();
       s.append("SELECT c.id,date_format(c.dataAbertura,'%d-%m-%Y'), u.nomeCompleto as usuario, ");       
       s.append("s.descricao as setor,p.descricao as problema,c.prioridade,c.status ");       
       s.append("FROM Chamado c,Usuario u, Setor s, Problema p ");       
       s.append("WHERE  c.problema = p.id AND c.usuario = u.id AND u.setor = s.id ");              
       s.append(filtro);              
       try{
          session=HibernateUtil.getSessionFactory().openSession();
          query=session.createQuery(s.toString());          
          List<Chamado>chamados=new ArrayList<>();
          Iterator it=query.list().iterator();
          
          while(it.hasNext()){
              Object[]vetorIt=(Object[])it.next();
              Chamado c=new Chamado();
              c.setId((long)vetorIt[0]);
              c.setDataAbertura(((String)vetorIt[1]));
              c.setNomeUsuario((String)vetorIt[2]);
              c.setDescSetor((String)vetorIt[3]);
              c.setDescProblema((String)vetorIt[4]);
              if(String.valueOf((int)vetorIt[5]).equals("1")){
                  c.setDescPrioridade("Baixa");
              }else if(String.valueOf((int)vetorIt[5]).equals("2")){
                  c.setDescPrioridade("Média");
              }else if(String.valueOf((int)vetorIt[5]).equals("3")){
                  c.setDescPrioridade("Alta");
              }
              //c.setDescPrioridade(String.valueOf((int)vetorIt[5]));
              if(String.valueOf((char)vetorIt[6]).equals("P")){
                  c.setDescStatus("Pendente");                            
              }else if(String.valueOf((char)vetorIt[6]).equals("F")){
                  c.setDescStatus("Finalizado");                            
              }
              //c.setStatus((char)vetorIt[6]);                            
              chamados.add(c);
          }
          return chamados;
       }catch(Exception e ){
           e.printStackTrace();
           return null;
       }
    }
    
    
}
