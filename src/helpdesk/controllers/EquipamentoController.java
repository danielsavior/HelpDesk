/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import helpdesk.DAO.EquipamentoDAO;
import helpdesk.beans.Equipamento;
import java.util.List;

/**
 *
 * @author daniel
 */
public class EquipamentoController implements ITodosController<Equipamento> {
    EquipamentoDAO dao;
    @Override
    public void insert(Equipamento objeto) {
        dao=new EquipamentoDAO();
        try{
            dao.insert(objeto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Equipamento objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Equipamento objeto) {
        dao=new EquipamentoDAO();
        dao.update(objeto);
    }

    @Override
    public Equipamento buscarPorID(long id) {
        dao=new EquipamentoDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Equipamento> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Equipamento> listar() {
        dao=new EquipamentoDAO();
        return dao.listar("Equipamento");
    }
     public List<Equipamento> buscarEquipamentos(String filtro) {
        dao=new EquipamentoDAO();
        return dao.buscarEquipamentos(filtro);
    }
}
