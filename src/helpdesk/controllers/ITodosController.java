/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.controllers;

import java.util.List;

/**
 *
 * @author daniel
 */
public interface ITodosController<T> {
    void insert(T objeto);
    void delete(T objeto);
    void update(T objeto);
    T buscarPorID(long id);
    List<T> buscarPorNome(String nome);
    List<T> listar();
}
