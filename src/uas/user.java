/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author budosen
 */
public abstract class user {

    private String nama, jk, email, notelp, alamat, iduser, username, password, level, kelasuser,status;
    private String[] result;
    private ArrayList<String> list= new ArrayList<String>();

    public void setUser(String nama1, String jk1, String email1, String notelp1, String alamat1, String iduser1, String username1, String password1, String level, String kelasuser1,String status1) {
        this.nama = nama1;
        this.jk = jk1;
        this.email = email1;
        this.notelp = notelp1;
        this.alamat = alamat1;
        this.iduser = iduser1;
        this.username = username1;
        this.password = password1;
        this.level = level;
        this.kelasuser = kelasuser1;
        this.status = status1;
    }

    public void cekUser(String username, String password) throws SQLException {
        try {
            String password2 = MD5.getMd5(password);
            String sql = "SELECT iduser,nama,email,alamat,notelp,jk,username,password,level,tingkat,status "
                    + "from apto_user where username='" + username + "' and password='" + password2 + "' and status='1'";
            java.sql.Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                admin f = new admin();
                iduser = res.getString(1);
                nama = res.getString(2);
                email = res.getString(3);
                alamat = res.getString(4);
                notelp = res.getString(5);
                jk = res.getString(6);
                username = res.getString(7);
                password = res.getString(8);
                level = res.getString(9);
                kelasuser = res.getString(10);

                f.setUser(nama, jk, email, notelp, alamat, iduser, username, password, level, kelasuser,status);
           
                JOptionPane.showMessageDialog(null, "Berhasil Login" + f.getId());
                main m = new main();
                m.setVisible(false);
                if (level.equals("0")) {
                    auth aut = new auth(f.getId(),f.getNama());
                    aut.setVisible(true);
                } else if (level.equals("1")) {
                    auth aut = new auth(f.getId(),f.getNama());
                    aut.setVisible(true);
                } else if (level.equals("2")) {
                    nonauth non = new nonauth(f.getId(),f.getNama());
                    non.setVisible(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void setId(String iduser) {
        this.iduser = iduser;
    }

    public String getId() {
        return iduser;
    }
    public String getNama(){
        return nama;
    }
    
    public String[] getUser(){
         try {
            String sql = "SELECT iduser,nama from apto_user where level='2'";
            java.sql.Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
             while (res.next()) {
                 list.add(res.getString("nama")+" - "+res.getString("iduser"));   
             }
     
             result = new String[list.size()];
             result = list.toArray(result);
     
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         return result;
    }
    
    public void sgetStatus(){
         try {
            String sql = "SELECT iduser,nama,email,alamat,notelp,jk,username,password,level,tingkat,status from apto_user where username='budosen' and password='604de12585cffbd7ba06e6bafe601188'";
            java.sql.Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                admin f = new admin();
                iduser = res.getString(1);
                nama = res.getString(2);
                email = res.getString(3);
                alamat = res.getString(4);
                notelp = res.getString(5);
                jk = res.getString(6);
                username = res.getString(7);
                password = res.getString(8);
                level = res.getString(9);
                kelasuser = res.getString(10);
                status = res.getString(11);

                f.setUser(nama, jk, email, notelp, alamat, iduser, username, password, level, kelasuser,status);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public String getsSt(){
        return status;
    }
   
}
