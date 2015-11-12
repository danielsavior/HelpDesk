package helpdesk.beans;

import helpdesk.utils.Coluna;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

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
    @Coluna(posicao = 0,nomeColuna = "Código",get = true,retorno = Long.class)
    public long getId() {
        return id;
    }
    @Coluna(posicao = 0,nomeColuna = "Código",get = false,retorno = Long.class)
    public void setId(long id) {
        this.id = id;
    }
    @Coluna(posicao = 1,nomeColuna = "Descrição",get = true,retorno = String.class)
    public String getDescricao() {
        return descricao;
    }
    @Coluna(posicao = 1,nomeColuna = "Descrição",get = false,retorno = String.class)
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }    
    public String getDataCompra() {
        return dataCompra;
    }
    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }    
    public long getFornecedor() {
        return fornecedor;
    }
    public void setFornecedor(long fornecedor) {
        this.fornecedor = fornecedor;
    }
    public long getSistemaOP() {
        return sistemaOP;
    }
    public void setSistemaOP(long sistemaOP) {
        this.sistemaOP = sistemaOP;
    }
    public long getOffice() {
        return office;
    }
    public void setOffice(long office) {
        this.office = office;
    }
    @Coluna(posicao = 3,nomeColuna = "IP",get = true,retorno = String.class)
    public String getIp() {
        return ip;
    }
    @Coluna(posicao = 3,nomeColuna = "IP",get = false,retorno = String.class)
    public void setIp(String ip) {
        this.ip = ip;
    }
    public long getMarca() {
        return marca;
    }
    public void setMarca(long marca) {
        this.marca = marca;
    }
    @Coluna(posicao = 4,nomeColuna = "Nº de Série",get = true,retorno = String.class)
    public String getNumeroSerie() {
        return numeroSerie;
    }
    @Coluna(posicao = 4,nomeColuna = "Nº de Série",get = false,retorno = String.class)
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }   
    public long getSetor() {
        return setor;
    }   
    public void setSetor(long setor) {
        this.setor = setor;
    }
    @Coluna(posicao = 2,nomeColuna = "Marca",get = true,retorno = String.class)
    public String getNomeMarca() {
        return nomeMarca;
    }
    @Coluna(posicao = 2,nomeColuna = "Marca",get = false,retorno = String.class)
    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }
    @Coluna(posicao = 5,nomeColuna = "Setor",get = true,retorno = String.class)
    public String getNomeSetor() {
        return nomeSetor;
    }
    @Coluna(posicao = 5,nomeColuna = "Setor",get = false,retorno = String.class)
    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }
    public String getProcessador() {
        return processador;
    }
    public void setProcessador(String processador) {
        this.processador = processador;
    }
    public String getHdd() {
        return hdd;
    }
    public void setHdd(String hdd) {
        this.hdd = hdd;
    }
    public String getMemoria() {
        return memoria;
    }
    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    public String getNomeComputador() {
        return nomeComputador;
    }
    public void setNomeComputador(String nomeComputador) {
        this.nomeComputador = nomeComputador;
    }
    //</editor-fold>

    

}
