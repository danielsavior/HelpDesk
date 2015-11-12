
package helpdesk.beans;

import helpdesk.utils.Coluna;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="periferico")
public class Periferico implements IHaveID{
    @Id
    @GeneratedValue
    private long id;
    private long equipamento;
    private String tipo;
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
    @Coluna(posicao = 0,nomeColuna = "Tipo",get = true,retorno = String.class)
    public String getTipo() {        
        return tipo;
    }
    @Coluna(posicao = 0,nomeColuna = "Tipo",get = false,retorno = String.class)
    public void setTipo(String tipo) {                        
        this.tipo = tipo;
    }
    public long getMarca() {
        return marca;
    }
    public void setMarca(long marca) {
        this.marca = marca;
    }
    @Coluna(posicao = 1,nomeColuna = "Descrição",get = true,retorno = String.class)
    public String getDescricao() {
        return descricao;
    }
    @Coluna(posicao = 1,nomeColuna = "Descrição",get = false,retorno = String.class)
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public char getAtivo() {
        return ativo;
    }
    public void setAtivo(char ativo) {
        this.ativo = ativo;
    }
    @Coluna(posicao = 2,nomeColuna = "Nº de Série",get = true,retorno = String.class)
    public String getNumeroSerie() {
        return nserie;
    }
    @Coluna(posicao = 2,nomeColuna = "Nº de Série",get = false,retorno = String.class)
    public void setNumeroSerie(String numeroSerie) {
        this.nserie = numeroSerie;
    }    
    
}
