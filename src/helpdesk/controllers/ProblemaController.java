/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import helpdesk.DAO.ProblemaDAO;
import helpdesk.beans.Problema;
import java.util.List;

/**
 *
 * @author daniel
 */
public class ProblemaController implements ITodosController<Problema> {
    private ProblemaDAO dao;
    @Override
    public void insert(Problema objeto) {
        dao=new ProblemaDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Problema objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Problema objeto) {
        dao=new ProblemaDAO();
        dao.update(objeto);
    }

    @Override
    public Problema buscarPorID(long id) {
        dao=new ProblemaDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Problema> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Problema> listar() {
        try{
            dao = new ProblemaDAO();
            return dao.listar("Problema");
        }catch(Exception e){
            return null;
        }
    }
    
}
