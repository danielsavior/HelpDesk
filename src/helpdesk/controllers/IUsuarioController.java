package helpdesk.controllers;

import java.util.List;
import helpdesk.beans.Usuario;


public interface IUsuarioController {
    
    Usuario efetuarLogin(String usuario,String senha);
}
