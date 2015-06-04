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
public class Impressora implements IHaveID{
    @Id
    @GeneratedValue
    private long id;
    private String modelo;
    private String patrimonio;    
    private String modo;
    private String compartilhada;    
    private String ip;
    private long marca;    
    private long setor;
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
    public String getModelo() {
        return modelo;
    }
    /**
     * @param descricao the descricao to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }    
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
    //</editor-fold>

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
     * @return the patrimonio
     */
    public String getPatrimonio() {
        return patrimonio;
    }

    /**
     * @param patrimonio the patrimonio to set
     */
    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    /**
     * @return the modo
     */
    public String getModo() {
        return modo;
    }

    /**
     * @param modo the modo to set
     */
    public void setModo(int modo) {    
        switch(modo){
            case 1:
                this.modo="IP";                                   
                break;
            case 2:
                this.modo="USB";                                   
                break;
            default:
                this.modo="USB";                                   
        }
    }

    /**
     * @return the compartilhada
     */
    public String getCompartilhada() {
        return compartilhada;
    }

    /**
     * @param compartilhada the compartilhada to set
     */
    public void setCompartilhada(int compartilhada) {        
        switch(compartilhada){
            case 1:
                this.compartilhada = "Sim";                                
                break;
            case 2:
                this.compartilhada = "Não";                                
                break;
            default:
                this.compartilhada = "Não";                                
        }
    }

}
