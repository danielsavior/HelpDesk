package helpdesk.controllers;

import helpdesk.DAO.EmpresaDAO;
import helpdesk.beans.Empresa;
import java.util.List;

public class EmpresaController implements ITodosController<Empresa>{
    private EmpresaDAO dao;
    @Override
    public void insert(Empresa objeto) {
        dao=new EmpresaDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
        }
    }

    @Override
    public void delete(Empresa objeto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Empresa objeto) {
        dao=new EmpresaDAO();
        try{
            dao.update(objeto);
        }catch(Exception e){
        }
    }

    @Override
    public Empresa buscarPorID(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Empresa> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Empresa> listar() {
        dao=new EmpresaDAO();
        return dao.listar("Empresa");
    }
    
}
