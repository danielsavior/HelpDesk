/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;

import java.awt.Frame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author daniel
 */
public class HelpDesk {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        // TODO code application logic here aqui teste
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        FrmLogin f=new FrmLogin(new Frame(), true);
        f.setLocation(500,250);
        f.show();
    }
}
