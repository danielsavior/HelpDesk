/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import helpdesk.DAO.ImpressoraDAO;
import helpdesk.beans.Impressora;
import java.util.List;

/**
 *
 * @author daniel
 */
public class ImpressoraController implements ITodosController<Impressora> {
    ImpressoraDAO dao;
    @Override
    public void insert(Impressora objeto) {
        dao=new ImpressoraDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Impressora objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Impressora objeto) {
        dao=new ImpressoraDAO();
        dao.update(objeto);
    }

    @Override
    public Impressora buscarPorID(long id) {
        dao=new ImpressoraDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Impressora> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Impressora> listar() {
        dao=new ImpressoraDAO();
        return dao.listar("Impressora");
    }
     public List<Impressora> buscarImpressoras(String filtro) {
        dao=new ImpressoraDAO();
        return dao.buscarImpressoras(filtro);
    }
}
