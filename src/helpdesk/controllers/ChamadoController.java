/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import helpdesk.DAO.ChamadoDAO;
import helpdesk.beans.Chamado;
import java.util.List;

/**
 *
 * @author daniel
 */
public class ChamadoController implements ITodosController<Chamado> {
    private ChamadoDAO dao;
    @Override
    public void insert(Chamado objeto) {
        dao=new ChamadoDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Chamado objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Chamado objeto) {
        dao=new ChamadoDAO();
        dao.update(objeto);
    }

    @Override
    public Chamado buscarPorID(long id) {
        dao=new ChamadoDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Chamado> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Chamado> listar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Chamado> buscarChamados(long setor) {
        dao=new ChamadoDAO();
        return dao.buscarChamados(setor);
    }
    public List<Chamado> buscarChamados(String filtro) {
        dao=new ChamadoDAO();
        return dao.buscarChamados(filtro);
    }
    
}
