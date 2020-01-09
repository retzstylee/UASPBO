
package gui;
import db.*;
import java.sql.*;
import java.util.Scanner;
public class GuiSupermarket {
    DbConnection dbc = new DbConnection("x6project");
    DbBarang dbb = new DbBarang();
    DbTransaksi dbt = new DbTransaksi();
    Scanner in = new Scanner(System.in);
    private String namaPegawai;
    private Integer stok;
    
    private void setThisN(String nama){
        this.namaPegawai = nama;
    }
    private String getThisN(){
        return this.namaPegawai;
    }
    public void verify(){
        System.out.println("");
        System.out.println("VERIFIKASI PEGAWAI");
        System.out.println("==================");
        System.out.println("1. Masuk");
        System.out.println("2. Daftar");
        int pil = in.nextInt();
        if(pil == 1){
            boolean cek = true;
            while(cek){
                System.out.println("Username :");
                String username = in.next();
                System.out.println("Password :");
                String password = in.next();
                dbc.login(username, password);
                if(dbc.cekLogin == true){
                    String sql = "SELECT tb_pegawai.nama_pegawai FROM tb_admin INNER JOIN tb_pegawai ON tb_admin.id_pegawai = tb_pegawai.id_pegawai WHERE user_admin = '"+username+"'";
                    try {
                        Connection cn = dbc.dbCon;
                        Statement st = cn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        rs.next();
                        String namapgw = rs.getString(1);
                        setThisN(namapgw);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    cek = false;
                }
                else if(dbc.cekLogin==false){
                    cek = true;
                }
                
            }
            
        } else if (pil == 2){
            System.out.println("Nama :");
            String nama = in.next();
            System.out.println("Jenis Kelamin (L/P):");
            String gender = in.next();
            System.out.println("Username  (min 5 karakter) :");
            String username = in.next();
            System.out.println("Password  (min 5 karakter) :");
            String password = in.next();
            dbc.register(username, password, nama, gender);
            verify();
        }
    }
    public void mainapp(){
        System.out.println("");
        System.out.println("SUPERMARKET APP V1.2");
        System.out.println("by X6");
        System.out.println("====================");
        System.out.println("1. Manajemen Barang");
        System.out.println("2. Transaksi");
        System.out.println("3. Keluar");
        int pil = in.nextInt();
        if(pil == 1){
            manajemen();
        }else if(pil == 2){
            transaksi();
        }
    }

    public void manajemen() {
        System.out.println("");
        System.out.println("Manajemen Barang");
        System.out.println("================");
        System.out.println("");
        System.out.println("Barang Terkini :");
        showBarang();
        System.out.println("");
        System.out.println("1. Tambah Barang");
        System.out.println("2. Update Barang");
        System.out.println("3. Hapus Barang");
        System.out.println("4. Kembali");
        int pil = in.nextInt();
        if(pil == 1){
            System.out.println("Nama :");
            String nama = in.next();
            System.out.println("Jenis :");
            String jenis = in.next();
            System.out.println("Harga :");
            String harga = in.next();
            System.out.println("Banyak :");
            String banyak = in.next();
            dbb.insert(dbc, nama, jenis, harga, banyak);
            manajemen();
        }else if(pil == 2){
            System.out.println("Id");
            String id = in.next();
            System.out.println("Nama :");
            String nama = in.next();
            System.out.println("Jenis :");
            String jenis = in.next();
            System.out.println("Harga :");
            String harga = in.next();
            System.out.println("Banyak :");
            String banyak = in.next();
            dbb.update(dbc, id, nama, jenis, harga, banyak);
            manajemen();
        }else if(pil == 3){
            System.out.println("Id");
            String id = in.next();
            dbb.delete(dbc, id);
            manajemen();
        }else{
            mainapp();
        }
    }
    
    private void transaksi() {
        System.out.println("");
        System.out.println("Transaksi");
        System.out.println("=========");
        System.out.println("1. Transaksi Baru");
        System.out.println("2. Hapus Transaksi");
        System.out.println("3. Tampilkan Semua Transaksi");
        System.out.println("4. Kembali");
        dbt.setPegawai(getThisN());
        int pil = in.nextInt();
        if(pil == 1){
            boolean cek = true;
            while(cek){
                showTransaksiPgw();
                System.out.println("Nama Barang :");
                String nama = in.next();
                System.out.println("Banyak Barang :");
                String banyak = in.next();
                System.out.println("Lagi? (y/n)");
                String pil1 = in.next();
                if("y".equals(pil1)){
                    cek = true;
                }
                else if("n".equals(pil1)){
                    showTransaksiPgw();
                    cek = false;
                    clear();
                }
            }
            transaksi();
        }else if(pil == 2){
            boolean cek = true;
            while(cek){
                showTransaksi();
                System.out.println("Id Transaksi :");
                String id = in.next();
                dbt.del_transaksi(dbc, id);
                System.out.println("Lagi? (y/n)");
                String pil1 = in.next();
                if("y".equals(pil1)){
                    cek = true;
                }
                else if("n".equals(pil1)){
                    cek = false;
                }
            }
            transaksi();
        }else if(pil == 3){
            showTransaksi();
            transaksi();
        }else{
            mainapp();
        }
        
    }
    
    private void showBarang(){
        String sql = "select * from tb_barang";
        try {
            Connection con = dbc.dbCon;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("----------------------------------------------------------------------------------------");
            String header = "%3s %20s %20s %20s %20s";
            System.out.println(String.format(header, "ID", "Nama", "Jenis", "Harga", "Stok"));
            System.out.println("----------------------------------------------------------------------------------------");
            while(rs.next()){
                String id = rs.getString("id_barang");
                String nama = rs.getString("nama_barang");
                String jenis = rs.getString("jenis_barang");
                String harga = rs.getString("harga_barang");
                String stok = rs.getString("banyak");
                String output = "%3s %20s %20s %20s %20s";
                System.out.println(String.format(output, id, nama, jenis, harga, stok));
            }
        } catch (Exception e) {
        }
    }
    private void clear(){
        String sql = "delete from temporary";
        Connection cn = dbc.dbCon;
        try {
            PreparedStatement st = cn.prepareStatement(sql);
            st.executeUpdate();
        } catch (Exception e) {
        }
    }
    private void showTransaksiPgw(){
        String sql = "SELECT temporary.id_transaksi, tb_barang.nama_barang, temporary.banyak, tb_barang.harga_barang, tb_pegawai.nama_pegawai,temporary.total_harga FROM ((temporary INNER JOIN tb_pegawai ON temporary.id_pegawai = tb_pegawai.id_pegawai) INNER JOIN tb_barang ON temporary.id_barang = tb_barang.id_barang) WHERE nama_pegawai = '"+getThisN()+"'";
        String tot = "select sum(total_harga) as 'bayar' from temporary";
        try {
            Connection cn = dbc.dbCon;
            Statement sth = cn.createStatement();
            ResultSet rsh = sth.executeQuery(tot);
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            String header = "%10s %20s %20s %20s %20s %20s";
            System.out.println(String.format(header, "Id", "Nama Pegawai", "Nama", "Harga Satuan", "Banyak", "Total Harga"));
            System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            while(rs.next()){
                String id = rs.getString("id_transaksi");
                String namapgw = rs.getString("nama_pegawai");
                String nama = rs.getString("nama_barang");
                String satuan = rs.getString("harga_barang");
                String banyak = rs.getString("banyak");
                String harga = rs.getString("total_harga");
                String output = "%10s %20s %20s %20s %20s %20s";
                System.out.println(String.format(output, id, namapgw, nama, satuan, banyak, harga));
            }
            while(rsh.next()){
                String harga_tot = rsh.getString("bayar");
                System.out.println("\n");
                System.out.println("--------------------------------------------------------------------------------------------------------------------------");
                System.out.println("                                                                                        Total Pembayaran : "+harga_tot);
                System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (Exception e) {
        }
    }
    private void showTransaksi(){
        String sql = "SELECT tb_transaksi.id_transaksi, tb_barang.nama_barang, tb_transaksi.banyak, tb_barang.harga_barang, tb_pegawai.nama_pegawai,tb_transaksi.total_harga FROM ((tb_transaksi INNER JOIN tb_pegawai ON tb_transaksi.id_pegawai = tb_pegawai.id_pegawai) INNER JOIN tb_barang ON tb_transaksi.id_barang = tb_barang.id_barang)";
        try {
            Connection cn = dbc.dbCon;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            String header = "%10s %20s %20s %20s %20s %20s";
            System.out.println(String.format(header, "Id", "Nama Pegawai", "Nama", "Harga Satuan", "Banyak", "Total Harga"));
            System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            while(rs.next()){
                String id = rs.getString("id_transaksi");
                String namapgw = rs.getString("nama_pegawai");
                String nama = rs.getString("nama_barang");
                String satuan = rs.getString("harga_barang");
                String banyak = rs.getString("banyak");
                String harga = rs.getString("total_harga");
                String output = "%10s %20s %20s %20s %20s %20s";
                System.out.println(String.format(output, id, namapgw, nama, satuan, banyak, harga));
            }
        } catch (Exception e) {
        }
    }
    public void play(){
        verify();
        mainapp();
    }

    
}
