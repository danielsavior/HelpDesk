/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.utils;

import helpdesk.beans.Chamado;
import helpdesk.beans.Equipamento;
import helpdesk.beans.Fornecedor;
import helpdesk.beans.IHaveID;
import helpdesk.beans.Impressora;
import helpdesk.beans.Marca;
import helpdesk.beans.Office;
import helpdesk.beans.Problema;
import helpdesk.beans.Setor;
import helpdesk.beans.SistemaOP;
import helpdesk.beans.TipoEquipamento;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import helpdesk.beans.Usuario;


/**
 *
 * @author daniel
 */
public final class Utilidades {
   
   public static DecimalFormat formatadorDecimal=new DecimalFormat("##,##00.00");
   public static String criptografarSenha(String senhaPassada){
       try{
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(senhaPassada.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
              hexString.append(String.format("%02X", 0xFF & b));
            }
            return hexString.toString();   
        }catch(Throwable t){
            return null;
         }
   }
   //<editor-fold desc="Retornar próximo ID">
   public static int retornarProximoID(String tabela){
       Session session =null;
       try{
            session = HibernateUtil.getSessionFactory().openSession();
            Query query=null;
            IHaveID h=null;
            switch(tabela){
                case"Usuario":
                    query=session.createQuery("FROM Usuario ORDER BY id DESC LIMIT 1");
                    h=(Usuario)query.list().get(0);
                break;
                case"Chamado":
                    query=session.createQuery("FROM Chamado ORDER BY id DESC LIMIT 1");
                    h=(Chamado)query.list().get(0);
                break;
                case"Equipamento":
                    query=session.createQuery("FROM Equipamento ORDER BY id DESC LIMIT 1");
                    h=(Equipamento)query.list().get(0);
                break;
                case"Fornecedor":
                    query=session.createQuery("FROM Fornecedor ORDER BY id DESC LIMIT 1");
                    h=(Fornecedor)query.list().get(0);
                break;
                case"Marca":
                    query=session.createQuery("FROM Marca ORDER BY id DESC LIMIT 1");
                    h=(Marca)query.list().get(0);
                break;
                case"Problema":
                    query=session.createQuery("FROM Problema ORDER BY id DESC LIMIT 1");
                    h=(Problema)query.list().get(0);
                break;
                case"Setor":
                    query=session.createQuery("FROM Setor ORDER BY id DESC LIMIT 1");
                    h=(Setor)query.list().get(0);
                break;
                case"SistemaOP":
                    query=session.createQuery("FROM SistemaOP ORDER BY id DESC LIMIT 1");
                    h=(SistemaOP)query.list().get(0);
                break;
                case"Office":
                    query=session.createQuery("FROM Office ORDER BY id DESC LIMIT 1");
                    h=(Office)query.list().get(0);
                break;
                case"Impressora":
                    query=session.createQuery("FROM Impressora ORDER BY id DESC LIMIT 1");
                    h=(Impressora)query.list().get(0);
                break;
            }

            if(query.list().isEmpty()){
                 return 1;

            }else{
                 return (int)h.getId()+1;
            }
       }catch(Exception e){           
           return 1;
       }finally{
           session.close();
       }
   }
   //</editor-fold>
   
   public static String retornarIP(){

        try{
            return InetAddress.getByName(InetAddress.getLocalHost().getHostName()+".local").getHostAddress();
        }catch(UnknownHostException e){

            return null;
        }
    }
}
