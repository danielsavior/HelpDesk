/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.utils;


import java.io.File;
import helpdesk.beans.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author daniel
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() {
        
    }

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                // Create the SessionFactory from standard (hibernate.cfg.xml)
                // config file
                File f=new File(System.getProperty("user.dir")+File.separator+"confHB.xml");
                AnnotationConfiguration ac = new AnnotationConfiguration();
                ac.addAnnotatedClass(Fornecedor.class);
                ac.addAnnotatedClass(Marca.class);
                ac.addAnnotatedClass(SistemaOP.class);
                ac.addAnnotatedClass(Usuario.class);
                ac.addAnnotatedClass(TipoEquipamento.class);
                ac.addAnnotatedClass(Equipamento.class);
                ac.addAnnotatedClass(Chamado.class);
                ac.addAnnotatedClass(Problema.class);
                ac.addAnnotatedClass(Setor.class);
                ac.addAnnotatedClass(Office.class);
                ac.addAnnotatedClass(Impressora.class);
                ac.addAnnotatedClass(Periferico.class);
                
                sessionFactory = ac.configure(f).buildSessionFactory();
                
                
                //SchemaExport se = new SchemaExport(ac);
                //se.create(true, true);
                
                

            } catch (Throwable ex) {
                // Log the exception.
                
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
                
            }

            return sessionFactory;

        } else {
            return sessionFactory;
        }
        
    }

    //public static void main(String[] args) {
    //    HibernateUtil.getSessionFactory();
    //}

}
