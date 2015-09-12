
package helpdesk.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="equipamento")
public class Periferico {
    @Id
    @GeneratedValue
    private long id;
    private long equipamento;
    private int tipo;
    private long marca;
    private String descricao;
    private char ativo;               
    private String nserie;

    
    public long getId() {
        return id;
    }   
    public void setId(long id) {
        this.id = id;
    }    
    public long getEquipamento() {
        return equipamento;
    }
    public void setEquipamento(long equipamento) {
        this.equipamento = equipamento;
    }
    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public long getMarca() {
        return marca;
    }
    public void setMarca(long marca) {
        this.marca = marca;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public char getAtivo() {
        return ativo;
    }
    public void setAtivo(char ativo) {
        this.ativo = ativo;
    }
    public String getNumeroSerie() {
        return nserie;
    }
    public void setNumeroSerie(String numeroSerie) {
        this.nserie = numeroSerie;
    }    
    
}
