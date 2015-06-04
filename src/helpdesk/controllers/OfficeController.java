/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;



import helpdesk.DAO.OfficeDAO;
import helpdesk.beans.Office;
import java.util.List;

/**
 *
 * @author daniel
 */
public class OfficeController implements ITodosController<Office> {
    private OfficeDAO dao;
    @Override
    public void insert(Office objeto) {
        dao=new OfficeDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Office objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Office objeto) {
        dao=new OfficeDAO();
        dao.update(objeto);
    }

    @Override
    public Office buscarPorID(long id) {
        dao=new OfficeDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Office> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Office> listar() {
        try{
            dao=new OfficeDAO();
            return dao.listar("Office");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
