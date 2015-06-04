/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk;

import java.awt.Frame;

/**
 *
 * @author daniel
 */
public class HelpDesk {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here aqui teste
        FrmLogin f=new FrmLogin(new Frame(), true);
        f.setLocation(500,250);
        f.show();
    }
}
