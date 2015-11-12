/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.beans;

import helpdesk.utils.Coluna;
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
    @Coluna(posicao=0,nomeColuna = "Código",get = true,retorno = Long.class)
    public long getId() {
        return id;
    }    
    @Coluna(posicao=0,nomeColuna = "Código",get = false,retorno = String.class)
    public void setId(long id) {
        this.id = id;
    }
    @Coluna(posicao=1,nomeColuna = "Modelo",get = true,retorno = String.class)
    public String getModelo() {
        return modelo;
    }        
    @Coluna(posicao=1,nomeColuna = "Modelo",get = false,retorno = String.class)
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }    
    @Coluna(posicao=4, nomeColuna = "IP",get = true,retorno = String.class)
    public String getIp() {
        return ip;
    }    
    @Coluna(posicao=4, nomeColuna = "IP",get = false, retorno = String.class)
    public void setIp(String ip) {
        this.ip = ip;
    }    
    public long getMarca() {
        return marca;
    }
    public void setMarca(long marca) {
        this.marca = marca;
    }        
    
    public long getSetor() {
        return setor;
    }
    public void setSetor(long setor) {
        this.setor = setor;        
    }
    @Coluna(posicao=5,nomeColuna = "Marca",get = true, retorno = String.class)
    public String getNomeMarca() {
        return nomeMarca;
    }   
    @Coluna(posicao=5,nomeColuna = "Marca",get = false, retorno = String.class)
    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }
    @Coluna(posicao=6,nomeColuna = "Setor",get = true,retorno = String.class)
    public String getNomeSetor() {
        return nomeSetor;
    }    
    @Coluna(posicao=6,nomeColuna = "Setor",get = false,retorno = String.class)
    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }
    public String getPatrimonio() {
        return patrimonio;
    }
    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }
    @Coluna(posicao=2,nomeColuna = "Modo",get = true,retorno = String.class)
    public String getModo() {
        return modo;
    }       
    @Coluna(posicao=2,nomeColuna = "Modo",get = false,retorno = String.class)
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
    @Coluna(posicao=3,nomeColuna = "Compartilhada",get = true,retorno = String.class)
    public String getCompartilhada() {
        return compartilhada;
    }    
    @Coluna(posicao=3,nomeColuna = "Compartilhada",get = false,retorno = String.class)
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
    //</editor-fold>    
    

}
