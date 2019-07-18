/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author budosen
 */
public class pembayaran extends transaksi{
    private String kodepembayaran,bankasal,banktujuan,iduser,status;
    private int num;

    public String getBankasal() {
        return bankasal;
    }
    public String getId() {
        return iduser;
    }
    
    public void setId(String idu) {
        this.iduser=idu;
    }

    public String getBanktujuan() {
        return banktujuan;
    }

    public String getKodepembayaran() {
        num = num+1;
        if(num > 0 &&num<=9){
            kodepembayaran = "PAY000"+num;
        }else if(num>9&&num<=99){
            kodepembayaran = "PAY00"+num;
        }else if(num>99&&num<=999){
            kodepembayaran = "PAY0"+num;
        }else if(num>999&&num<=9999){
            kodepembayaran = "PAY"+num;
        }
        return kodepembayaran;
    }

    public void setBankasal(String bankasal) {
        this.bankasal = bankasal;
    }

    public void setBanktujuan(String banktujuan) {
        this.banktujuan = banktujuan;
    }

    public void setKodepembayaran() {
        try {
            String sql = "SELECT count(idpembayaran) from apto_pembayaran";
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
    
     public TableModel defTab(String idd){
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("No");
        tabel.addColumn("Id Tagihan");
        tabel.addColumn("Subject");
        tabel.addColumn("Deskripsi");
        tabel.addColumn("Jatuh Tempo");
        tabel.addColumn("Nominal");
        tabel.addColumn("Status");
        try {
            int no = 1;
            String sql = "select a.idtagihan,a.subject,a.deskripsi,a.jatuhtempo,a.nominal,b.idpembayaran,b.status from apto_tagihan a left join apto_pembayaran b on a.idtagihan=b.idtagihan where a.iduser='"+idd+"' order by b.status desc";
            java.sql.Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
     
               if(res.getString(7) == null){
                    status = "Belum Ada Pembayaran";
                }
                else if(res.getString(7).equalsIgnoreCase("2")){
                    status = "Pembayaran Diterima";
                }else if(res.getString(7).equalsIgnoreCase("3")){
                    status = "Pembayaran Ditolak";
                }else if(res.getString(7).equalsIgnoreCase("1")){
                    status = "Menunggu Verifikasi";
                }
                tabel.addRow(new Object[]{no++, res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5),status});
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tabel;
    }
     public void tambahPembayaran(String param){
         
        try {
            String sql = param;
            java.sql.Connection conn=(Connection)koneksi.configDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
