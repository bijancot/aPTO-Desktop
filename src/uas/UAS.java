/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author budosen
 */
public class UAS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        koneksi kon = new koneksi();
        main a = new main();
        admin w = new admin();
       try{
           a.setId("sdad");
           System.out.println(a.getId());
           String[] o = w.getUser();
                   tagihan tag = new tagihan();
                   System.out.println(tag.getKodetagihan());
                   System.out.println(o[1]);
                   w.sgetStatus();
                   System.out.println(w.getsSt());

}catch(Exception e){
    JOptionPane.showMessageDialog(null, e.getMessage());
}
    }
    
}
