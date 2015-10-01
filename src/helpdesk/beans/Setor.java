/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author daniel
 */
@Entity
public class Setor implements IHaveID{
    @Id
    @GeneratedValue
    private long id;
    private String descricao;
    private long empresa;
    //<editor-fold desc="Encapsulamento de campos">    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;            
    }        
    public long getEmpresa() {
        return empresa;
    }    
    public void setEmpresa(long empresa) {
        this.empresa = empresa;
    }
    //</editor-fold>

    
    
}
