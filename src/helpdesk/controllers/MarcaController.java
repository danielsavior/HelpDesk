/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;



import helpdesk.DAO.MarcaDAO;
import helpdesk.beans.Marca;
import java.util.List;

/**
 *
 * @author daniel
 */
public class MarcaController implements ITodosController<Marca> {
    private MarcaDAO dao;
    @Override
    public void insert(Marca objeto) {
        dao=new MarcaDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Marca objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Marca objeto) {
        dao=new MarcaDAO();
        dao.update(objeto);
    }

    @Override
    public Marca buscarPorID(long id) {
        dao=new MarcaDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Marca> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Marca> listar() {
        try{
            dao=new MarcaDAO();
            return dao.listar("Marca");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
