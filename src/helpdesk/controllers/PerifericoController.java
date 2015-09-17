
package helpdesk.controllers;
import helpdesk.DAO.PerifericoDAO;
import helpdesk.beans.Periferico;
import java.util.List;

public class PerifericoController implements ITodosController<Periferico> {
    PerifericoDAO dao;
    @Override
    public void insert(Periferico objeto) {
        dao=new PerifericoDAO();
         try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Periferico objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Periferico objeto) {        
        dao=new PerifericoDAO();
        dao.update(objeto);
    }

    @Override
    public Periferico buscarPorID(long id) {
       dao=new PerifericoDAO();
       return dao.buscaPorID(id);
    }
    
    public boolean checaNSerie(String nSerie){
        dao=new PerifericoDAO();
        return dao.checaNSerie(nSerie);
    }
    
    public List<Periferico>buscarPerifericos(long codEquipamento){
        dao=new PerifericoDAO();
        return dao.buscarPerifericos(codEquipamento);
    }
    @Override
    public List<Periferico> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Periferico> listar() {
       dao=new PerifericoDAO();
       return dao.listar("Periferico"); 
    }
    
    
}
