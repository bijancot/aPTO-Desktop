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
public abstract class transaksi {
    private String status;
    private String nominalTransaksi,subjectTransaksi,deskripsiTransaksi,tanggalTransaksi;
    
    public String getNominal(){
        return nominalTransaksi;
    }
    
    public String getSubject(){
        return subjectTransaksi;
    }
    
    public String getDeskripsi(){
        return deskripsiTransaksi;
    }
    public void setNominal(String nom){
        this.nominalTransaksi = nom;
    }
    public void setSubejct(String subject){
        this.subjectTransaksi = subject;
    }
    public void setDeskripsi(String desk){
        this.deskripsiTransaksi = desk;
    }

    public String getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }
    
 public TableModel defTab(){
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
            String sql = "select a.idtagihan,a.iduser,b.nama,a.subject,a.deskripsi,a.jatuhtempo,a.nominal from apto_tagihan a join apto_user b on a.iduser=b.iduser order by a.idtagihan";
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
    public void hapusTagihan(String idtagihan){
        try {
            String sql ="delete from apto_tagihan where idtagihan='"+idtagihan+"'";
            java.sql.Connection conn=(Connection)koneksi.configDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "berhasil di hapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data tidak bisa dihapus, ada pembayaran");
        }
    }
    public void verifikasiPembayaran(String idtagihan,String hii){
        if(hii.equalsIgnoreCase("a")){
            status ="2";
        }else if(hii.equalsIgnoreCase("r")){
            status ="3";
        }
        try {
            String sql ="update apto_pembayaran set status='"+status+"' where idpembayaran='"+idtagihan+"'";
            java.sql.Connection conn=(Connection)koneksi.configDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "berhasil di update");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data tidak bisa diupdate, ada pembayaran");
        }
    }
    public TableModel defTab(String idu){
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("No");
        tabel.addColumn("Id Tagihan");
        tabel.addColumn("Subject");
        tabel.addColumn("Deskripsi");
        tabel.addColumn("Jatuh Tempo");
        tabel.addColumn("Nominal");
        
        try {
            int no = 1;
            String sql = "select a.idtagihan,a.subject,a.deskripsi,a.jatuhtempo,a.nominal from apto_tagihan a join apto_pembayaran b on a.idtagihan=b.idtagihan where a.iduser='"+idu+"' order by a.idtagihan and b.idpembayaran is null";
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
}
