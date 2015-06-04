/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import helpdesk.DAO.SistemaOPDAO;
import helpdesk.beans.SistemaOP;
import java.util.List;

/**
 *
 * @author daniel
 */
public class SistemaOPController implements ITodosController<SistemaOP> {
    private SistemaOPDAO dao;
    @Override
    public void insert(SistemaOP objeto) {
        dao=new SistemaOPDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(SistemaOP objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(SistemaOP objeto) {
        dao=new SistemaOPDAO();
        dao.update(objeto);
    }

    @Override
    public SistemaOP buscarPorID(long id) {
        dao=new SistemaOPDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<SistemaOP> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<SistemaOP> listar() {
        try{
            dao=new SistemaOPDAO();
            return dao.listar("SistemaOP");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
