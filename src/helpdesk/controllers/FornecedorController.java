/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import helpdesk.DAO.FornecedorDAO;
import helpdesk.beans.Fornecedor;
import java.util.List;

/**
 *
 * @author daniel
 */
public class FornecedorController implements ITodosController<Fornecedor> {
    private FornecedorDAO dao;
    @Override
    public void insert(Fornecedor objeto) {
        dao=new FornecedorDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Fornecedor objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Fornecedor objeto) {
        dao=new FornecedorDAO();
        dao.update(objeto);
    }

    @Override
    public Fornecedor buscarPorID(long id) {
        dao=new FornecedorDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Fornecedor> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Fornecedor> listar() {
        try{
            dao=new FornecedorDAO();
            return dao.listar("Fornecedor");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
