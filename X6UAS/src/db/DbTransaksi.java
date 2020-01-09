
package db;
import base.*;
import java.sql.*;
public class DbTransaksi extends BaseTransaksi{
    private String nama_pegawai;
    
    public void setPegawai(String pgw){
        this.nama_pegawai = pgw;
    }
    public String getPegawai(){
        return this.nama_pegawai;
    }
    
    @Override
    public void newTransaksi(DbConnection db, String nama, String banyak) {
        Connection conn = db.dbCon;
        try {
            String sql1 = "select * from tb_barang where nama_barang='"+nama+"'";
            String sql2 = "select * from tb_pegawai where nama_pegawai='"+getPegawai()+"'";
            String sql3 = "insert into tb_transaksi(id_barang, id_pegawai, banyak, total_harga) values(?,?,?,?)";
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);
            rs1.next();
            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            rs2.next();
            PreparedStatement st3 = conn.prepareStatement(sql3);
            st3.setString(1, rs1.getString("id_barang"));
            st3.setString(2, rs2.getString("id_pegawai"));
            st3.setString(3, banyak);
            Integer total = Integer.valueOf(rs1.getString("harga_barang")) * Integer.valueOf(banyak);
            st3.setString(4, total.toString());
            st3.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Input Tidak Valid");
            System.out.println("");
        }
    }

    @Override
    public void del_transaksi(DbConnection db, String id) {
        Connection conn = db.dbCon;
        try {
            String sql = "delete from tb_transaksi where id_transaksi=?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, id);
            int row = st.executeUpdate();
            if (row > 0){
                System.out.println("Transaksi berhasil dihapus");
            }else{
                System.out.println("Penghapusan Transaksi gagal");
            }
        } catch (Exception e) {
        }
    }

}
