
package helpdesk.DAO;


import java.util.List;
import helpdesk.beans.Usuario;


public interface IUsuarioDAO {
    
    Usuario efetuarLogin(String usuario, String senha);
    
}
