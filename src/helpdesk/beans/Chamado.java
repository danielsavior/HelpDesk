package helpdesk.beans;

import helpdesk.utils.Coluna;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Chamado implements IHaveID{
    @Id
    @GeneratedValue
    private long id;
    private long usuario;
    private String descricao;
    private long problema;
    private String dataAbertura;
    private String dataRealizacao;
    private int prioridade;
    private String  operador;
    private String solucao; 
    private String tipo;
    private String duracao;
    private char status;
    
    @Transient
    private String nomeUsuario;
    @Transient
    private String DescSetor;
    @Transient
    private String DescProblema;
    @Transient
    private String DescPrioridade;
    @Transient
    private String DescStatus;
//<editor-fold desc="Encapsulamento de campos">
    @Coluna(posicao = 0,nomeColuna = "Código",get = true,retorno = Long.class)
    public long getId() {
        return id;
    }
    @Coluna(posicao = 0,nomeColuna = "Código",get = false,retorno = Long.class)
    public void setId(long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public long getProblema() {
        return problema;
    }
    public void setProblema(long problema) {
        this.problema = problema;
    }
    @Coluna(posicao = 1,nomeColuna = "Data de abertura",get = true,retorno = String.class)
    public String getDataAbertura() {
        return dataAbertura;
    }
    @Coluna(posicao = 1,nomeColuna = "Data de abertura",get = false,retorno = String.class)
    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
    public String getDataRealizacao() {
        return dataRealizacao;
    }
    public void setDataRealizacao(String dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
    public int getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
    public String getOperador() {
        return operador;
    }
    public void setOperador(String operador) {
        this.operador = operador;
    }
    public String getSolucao() {
        return solucao;
    }
    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getDuracao() {
        return duracao;
    }
    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
    public char getStatus() {
        return status;
    }
    public void setStatus(char status) {
        this.status = status;
    }   
    public long getUsuario() {
        return usuario;
    }
    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }
    //</editor-fold>

    @Coluna(posicao = 2,nomeColuna = "Usuário",get = true,retorno = String.class)
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    @Coluna(posicao = 2,nomeColuna = "Usuário",get = false,retorno = String.class)
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    @Coluna(posicao = 3,nomeColuna = "Setor",get = true,retorno = String.class)
    public String getDescSetor() {
        return DescSetor;
    }
    @Coluna(posicao = 3,nomeColuna = "Usuário",get = false,retorno = String.class)
    public void setDescSetor(String DescSetor) {
        this.DescSetor = DescSetor;
    }
    @Coluna(posicao = 4,nomeColuna = "Problema",get = true,retorno = String.class)
    public String getDescProblema() {
        return DescProblema;
    }
    @Coluna(posicao = 4,nomeColuna = "Problema",get = false,retorno = String.class)
    public void setDescProblema(String DescProblema) {
        this.DescProblema = DescProblema;
    }
    @Coluna(posicao = 5,nomeColuna = "Prioridade",get = true,retorno = String.class)
    public String getDescPrioridade() {
        return DescPrioridade;
    }
    @Coluna(posicao = 5,nomeColuna = "Prioridade",get = false,retorno = String.class)
    public void setDescPrioridade(String DescPrioridade) {
        this.DescPrioridade = DescPrioridade;
    }
    @Coluna(posicao = 6,nomeColuna = "Status",get = true,retorno = String.class)
    public String getDescStatus() {
        return DescStatus;
    }
    @Coluna(posicao = 6,nomeColuna = "Status",get = false,retorno = String.class)
    public void setDescStatus(String DescStatus) {
        this.DescStatus = DescStatus;
    }
}
