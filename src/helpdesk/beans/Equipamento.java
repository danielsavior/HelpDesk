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
public class Equipamento implements IHaveID{
    @Id
    @GeneratedValue
    private long id;
    private String descricao;
    private String dataCompra;    
    private long fornecedor;
    private long sistemaOP;
    private long office;
    private String ip;
    private long marca;
    private String numeroSerie;
    private long setor;
    private String processador;
    private String hdd;
    private String memoria;
    private String nomeUsuario;
    private String nomeComputador;
    @Transient         
    private String nomeMarca;
    @Transient         
    private String nomeSetor;
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
     * @return the dataCompra
     */
    public String getDataCompra() {
        return dataCompra;
    }

    /**
     * @param dataCompra the dataCompra to set
     */
    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }
    
    public long getFornecedor() {
        return fornecedor;
    }

    /**
     * @param fornecedor the fornecedor to set
     */
    public void setFornecedor(long fornecedor) {
        this.fornecedor = fornecedor;
    }

    /**
     * @return the sistemaOP
     */
    public long getSistemaOP() {
        return sistemaOP;
    }

    /**
     * @param sistemaOP the sistemaOP to set
     */
    public void setSistemaOP(long sistemaOP) {
        this.sistemaOP = sistemaOP;
    }

    /**
     * @return the office
     */
    public long getOffice() {
        return office;
    }

    /**
     * @param office the office to set
     */
    public void setOffice(long office) {
        this.office = office;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the marca
     */
    public long getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(long marca) {
        this.marca = marca;
    }

    /**
     * @return the numeroSerie
     */
    public String getNumeroSerie() {
        return numeroSerie;
    }

    /**
     * @param numeroSerie the numeroSerie to set
     */
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
    /**
     * @return the setor
     */
    public long getSetor() {
        return setor;
    }

    /**
     * @param setor the setor to set
     */
    public void setSetor(long setor) {
        this.setor = setor;
    }
    
    /**
     * @return the nomeMarca
     */
    public String getNomeMarca() {
        return nomeMarca;
    }

    /**
     * @param nomeMarca the nomeMarca to set
     */
    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    /**
     * @return the nomeSetor
     */
    public String getNomeSetor() {
        return nomeSetor;
    }

    /**
     * @param nomeSetor the nomeSetor to set
     */
    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    /**
     * @return the processador
     */
    public String getProcessador() {
        return processador;
    }

    /**
     * @param processador the processador to set
     */
    public void setProcessador(String processador) {
        this.processador = processador;
    }

    /**
     * @return the hdd
     */
    public String getHdd() {
        return hdd;
    }

    /**
     * @param hdd the hdd to set
     */
    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    /**
     * @return the memoria
     */
    public String getMemoria() {
        return memoria;
    }

    /**
     * @param memoria the memoria to set
     */
    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

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
     * @return the nomeComputador
     */
    public String getNomeComputador() {
        return nomeComputador;
    }

    /**
     * @param nomeComputador the nomeComputador to set
     */
    public void setNomeComputador(String nomeComputador) {
        this.nomeComputador = nomeComputador;
    }
    //</editor-fold>

    

}
