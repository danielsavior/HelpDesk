/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.DAO;

import helpdesk.beans.TipoEquipamento;
import java.util.List;

/**
 *
 * @author daniel
 */
public class TipoEquipamentoDAO extends ATodosDAO<TipoEquipamento> {


    @Override
    public TipoEquipamento buscaPorID(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TipoEquipamento> buscaPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
