/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import helpdesk.DAO.SetorDAO;
import helpdesk.beans.Setor;
import java.util.List;

/**
 *
 * @author daniel
 */
public class SetorController implements ITodosController<Setor> {
    private SetorDAO dao;
    @Override
    public void insert(Setor objeto) {
        dao=new SetorDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Setor objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Setor objeto) {
        dao=new SetorDAO();
        dao.update(objeto);
    }

    @Override
    public Setor buscarPorID(long id) {
        dao=new SetorDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Setor> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Setor> listar() {
        try{
            dao=new SetorDAO();
            return dao.listar("Setor");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
