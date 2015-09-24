/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import java.util.List;
import helpdesk.DAO.UsuarioDAO;
import helpdesk.beans.Usuario;

/**
 *
 * @author daniel
 */
public class UsuarioController implements IUsuarioController, ITodosController<Usuario> {
    private UsuarioDAO dao;
    @Override
    public void insert(Usuario usuario) {
        dao=new UsuarioDAO();
        try{
            dao.insert(usuario);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Usuario usuario) {
        dao=new UsuarioDAO();
        dao.update(usuario);
    }

    @Override
    public Usuario buscarPorID(long id) {
        dao=new UsuarioDAO();
        return dao.buscaPorID(id);
    }

    @Override
    public List<Usuario> buscarPorNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String buscarPorLogin(String login) {
        dao=new UsuarioDAO();
        return dao.buscarPorLogin(login);
    }

    @Override
    public List<Usuario> listar() {
        dao=new UsuarioDAO();
        return dao.listar("Usuario");
    }
    public List<Usuario> listarDoSetor(long setor) {
        dao=new UsuarioDAO();
        return dao.listarDoSetor(setor);
    }

    @Override
    public Usuario efetuarLogin(String usuario, String senha) {
        dao= new UsuarioDAO();
        try{
            return dao.efetuarLogin(usuario,senha);
        }catch(ExceptionInInitializerError e){
            throw new ExceptionInInitializerError();
        }
    }
    public List<Usuario> buscarPorSetor(long setor){
        dao=new UsuarioDAO();
        return dao.buscaPorSetor(setor);
    }
    
    public List<Usuario>listarOperadores(){
        dao=new UsuarioDAO();
        return dao.listarOperadores();
    }
    
}
