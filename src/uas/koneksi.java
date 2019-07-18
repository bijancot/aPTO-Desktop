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
/**
 *
 * @author budosen
 */
public class koneksi {
   private static Connection sambung;
    public static Connection configDB()throws SQLException{
        try {
            String url="jdbc:mysql://172.18.20.3:3306/apto_db"; //url database
            String user="budosen"; //user database
            String pass="bijan2089"; //password database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            sambung=DriverManager.getConnection(url, user, pass);            
        } catch (Exception e) {
            System.err.println("koneksi gagal "+e.getMessage()); //perintah menampilkan error pada koneksi
        }
        return sambung;
    }    
}
