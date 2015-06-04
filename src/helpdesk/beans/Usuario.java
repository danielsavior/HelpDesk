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
public class Usuario implements IHaveID{
    @Id
    @GeneratedValue
    private long id;
    private String login;
    private String senha;
    private String nomeCompleto;
    private char perfil;
    private long setor;
    //<editor-fold desc="Encapsulamento de campos">
    /**
     * @return the idUsuario
     */
    public long getId() {
        return id;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setId(long idUsuario) {
        this.id = idUsuario;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the nomeCompleto
     */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /**
     * @param nomeCompleto the nomeCompleto to set
     */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /**
     * @return the perfil
     */
    public char getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(char perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the idsetor
     */
    public long getIdsetor() {
        return setor;
    }

    /**
     * @param idsetor the idsetor to set
     */
    public void setIdsetor(long idsetor) {
        this.setor = idsetor;
    }
     //</editor-fold>
}
