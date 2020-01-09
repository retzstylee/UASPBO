
package db;
import java.sql.*;
import base.BaseBarang;
public class DbBarang extends BaseBarang{
    @Override
    public void insert(DbConnection db, String namaBarang, String jenisBarang, String hargaBarang, String stockBarang) {
        
        Connection conn = db.dbCon;
        String sqlIns = "insert into tb_barang (nama_barang, jenis_barang, harga_barang, banyak) values (?,?,?,?)";
        try {
            PreparedStatement sti = conn.prepareStatement(sqlIns);
            sti.setString(1, namaBarang);
            sti.setString(2, jenisBarang);
            sti.setString(3, hargaBarang);
            sti.setString(4, stockBarang);
            int row = sti.executeUpdate();
            if(row > 0){
                System.out.println("Input Data Sukses");
            }else{
                System.out.println("Data Tidak Terinput");
            }
            
        } catch (Exception e) {
            System.out.println("Input Data Gagal");
        }
    }

    @Override
    public void update(DbConnection db, String idBarang, String namaBarang, String jenisBarang, String hargaBarang, String stockBarang) {
        Connection conn = db.dbCon;
        String sqlUpd = "UPDATE tb_barang SET nama_barang=?, jenis_barang=?, harga_barang=?, banyak=? WHERE id_barang=?";
        try {
            PreparedStatement stu = conn.prepareStatement(sqlUpd);
            stu.setString(1, namaBarang);
            stu.setString(2, jenisBarang);
            stu.setString(3, hargaBarang);
            stu.setString(4, stockBarang);
            stu.setString(5, idBarang);
            int row = stu.executeUpdate();
            if(row > 0){
                System.out.println("Update Data Sukses");
            }else{
                System.out.println("Update Data Gagal");
            }
            
        } catch (Exception e) {
            System.out.println("Update Data Gagal");
        }
    }

    @Override
    public void delete(DbConnection db, String idBarang) {
        Connection conn = db.dbCon;
        String sqlDel = "delete from tb_barang where id_barang=?";
        try {
            PreparedStatement std = conn.prepareStatement(sqlDel);
            std.setString(1, idBarang);
            int row = std.executeUpdate();
            if(row > 0){
               System.out.println("Hapus Data Sukses"); 
            }else{
               System.out.println("Hapus Data Gagal");
            }
        } catch (Exception e) {
            System.out.println("Hapus Data Gagal");
        }
    }
    
    public void showBrg(DbConnection db){
        Connection conn = db.dbCon;
        String sqlBrg = "select * from tb_barang";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlBrg);
            System.out.println("=================================================");
            String header = "%3s %10s %10s %10s %10s";
            System.out.println(String.format(header, "Id", "Nama", "Jenis", "Harga", "Stok"));
            System.out.println("-------------------------------------------------");
            
            while(rs.next()){
                String idBarang = rs.getString("id_barang");
                String nama = rs.getString("nama_barang");
                String jenis = rs.getString("jenis_barang");
                String harga = rs.getString("harga_barang");
                String stok = rs.getString("banyak");
                String output = "%3s %10s %10s %10s %10s";
                System.out.println(String.format(output, idBarang, nama, jenis, harga, stok));
            }
        } catch (Exception e) {
        }
    }

}
