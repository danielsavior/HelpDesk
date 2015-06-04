/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author daniel
 */
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
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the problema
     */
    public long getProblema() {
        return problema;
    }

    /**
     * @param problema the problema to set
     */
    public void setProblema(long problema) {
        this.problema = problema;
    }

    /**
     * @return the dataAbertura
     */
    public String getDataAbertura() {
        return dataAbertura;
    }

    /**
     * @param dataAbertura the dataAbertura to set
     */
    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * @return the dataRealizacao
     */
    public String getDataRealizacao() {
        return dataRealizacao;
    }

    /**
     * @param dataRealizacao the dataRealizacao to set
     */
    public void setDataRealizacao(String dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    /**
     * @return the prioridade
     */
    public int getPrioridade() {
        return prioridade;
    }

    /**
     * @param prioridade the prioridade to set
     */
    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    /**
     * @return the operador
     */
    public String getOperador() {
        return operador;
    }

    /**
     * @param operador the operador to set
     */
    public void setOperador(String operador) {
        this.operador = operador;
    }

    /**
     * @return the solucao
     */
    public String getSolucao() {
        return solucao;
    }

    /**
     * @param solucao the solucao to set
     */
    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the duracao
     */
    public String getDuracao() {
        return duracao;
    }

    /**
     * @param duracao the duracao to set
     */
    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    /**
     * @return the status
     */
    public char getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(char status) {
        this.status = status;
    }
    
    /**
     * @return the usuario
     */
    public long getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }
    //</editor-fold>

    /**
     * @return the nomeUsuario
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * @param nomeUsuario the nomeUsuario to set
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * @return the DescSetor
     */
    public String getDescSetor() {
        return DescSetor;
    }

    /**
     * @param DescSetor the DescSetor to set
     */
    public void setDescSetor(String DescSetor) {
        this.DescSetor = DescSetor;
    }

    /**
     * @return the DescProblema
     */
    public String getDescProblema() {
        return DescProblema;
    }

    /**
     * @param DescProblema the DescProblema to set
     */
    public void setDescProblema(String DescProblema) {
        this.DescProblema = DescProblema;
    }

    /**
     * @return the DescPrioridade
     */
    public String getDescPrioridade() {
        return DescPrioridade;
    }

    /**
     * @param DescPrioridade the DescPrioridade to set
     */
    public void setDescPrioridade(String DescPrioridade) {
        this.DescPrioridade = DescPrioridade;
    }

    /**
     * @return the DescStatus
     */
    public String getDescStatus() {
        return DescStatus;
    }

    /**
     * @param DescStatus the DescStatus to set
     */
    public void setDescStatus(String DescStatus) {
        this.DescStatus = DescStatus;
    }
}
