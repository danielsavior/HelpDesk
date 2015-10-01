package helpdesk.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empresa")
public class Empresa implements IHaveID{
    @Id
    @GeneratedValue
    private long id;
    private String descricao;
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
    //</editor-fold>
}
