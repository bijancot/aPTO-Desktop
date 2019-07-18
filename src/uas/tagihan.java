/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author budosen
 */
public class tagihan extends transaksi {
    private String kodetagihan;
    private int num;
    private String hasil,status;

    
     public void setKodetagihan(){
         try {
            String sql = "SELECT count(idtagihan) from apto_tagihan";
            java.sql.Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                num = res.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error");
        }
    }
    public String getKodetagihan() {
        num = num+1;
        if(num > 0 &&num<=9){
            kodetagihan = "TGH000"+num;
        }else if(num>9&&num<=99){
            kodetagihan = "TGH00"+num;
        }else if(num>99&&num<=999){
            kodetagihan = "TGH0"+num;
        }else if(num>999&&num<=9999){
            kodetagihan = "TGH"+num;
        }
        return kodetagihan;
    }    
    
    public void tambahTagihan(String iduser,String tgl,String bulan,String tahun, String kodetag, String subsub, String desdes, String nomnom){
         iduser = iduser.split(" - ")[1];
         String tanggal = tahun+"-"+bulan+"-"+tgl;
        try {
            String sql = "INSERT INTO apto_tagihan(idtagihan,iduser,subject,deskripsi,jatuhtempo,nominal) VALUES('"+kodetag+"','"+iduser+"','"+subsub+"','"+desdes+"','"+tanggal+"','"+nomnom+"')";
            java.sql.Connection conn=(Connection)koneksi.configDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public String lihatTagihan(String idtagihan){
        
        try {
            String sql = "select b.status from apto_tagihan a left join apto_pembayaran b on a.idtagihan = b.idtagihan where a.idtagihan='"+idtagihan+"'";
            java.sql.Connection conn=(Connection)koneksi.configDB();
           java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()){
                hasil = res.getString(1);
            }
   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return hasil;
    }
    public TableModel defTab(String param){
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("No");
        tabel.addColumn("Id Tagihan");
        tabel.addColumn("Id User");
        tabel.addColumn("User Tertagih");
        tabel.addColumn("Subject");
        tabel.addColumn("Deskripsi");
        tabel.addColumn("Jatuh Tempo");
        tabel.addColumn("Nominal");
        
        try {
            int no = 1;
            String sql = param;
            java.sql.Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String[] kolo = res.getString(6).split("-");
                String tanggal = kolo[2]+"-"+kolo[1]+"-"+kolo[0];
                tabel.addRow(new Object[]{no++, res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5),tanggal, res.getString(7)});
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tabel;
    }
    
      public TableModel defTab(String param, String jenis){
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("No");
        tabel.addColumn("Id Tagihan");
        tabel.addColumn("Id User");
        tabel.addColumn("User Tertagih");
        tabel.addColumn("Subject");
        tabel.addColumn("Deskripsi");
        tabel.addColumn("Jatuh Tempo");
        tabel.addColumn("Nominal");
        tabel.addColumn("Id Pembayaran");
        tabel.addColumn("Status");
        
        try {
            int no = 1;
            String sql = param;
            java.sql.Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String[] kolo = res.getString(6).split("-");
                if(res.getString(8) == null){
                   status = "NOT YET";
                }else if(res.getString(8).equalsIgnoreCase("1")){
                    status = "WAITING";
                }else if(res.getString(8).equalsIgnoreCase("2")){
                     status = "ACCEPTED";
                }else if(res.getString(8).equalsIgnoreCase("3")){
                    status = "REJECT";
                }
                String tanggal = kolo[2]+"-"+kolo[1]+"-"+kolo[0];
                tabel.addRow(new Object[]{no++, res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5),tanggal, res.getString(7), res.getString(9),status});
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tabel;
    }
      
}
