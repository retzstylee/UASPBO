
package db;
import base.*;
import java.sql.*;
public class DbConnection implements BaseDb, BaseAdmin {
    public String dbName, dbUser, dbPass, userAdmin, passAdmin, sqlAdmin, namaPegawai, genderPegawai, sqlPegawai;
    public boolean cekLogin;
    public Connection dbCon;
    
    public DbConnection(String dbName, String dbUser, String dbPass){
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        
        String dbUrl = "jdbc:mysql://localhost:3306/"+this.dbName;
        try {
            dbCon = DriverManager.getConnection(dbUrl, this.dbUser, this.dbPass);
            System.out.println("Database Terkoneksi");
        } catch (SQLException e) {
            System.out.println("Database Gagal Terkoneksi");
        }
    }
    public DbConnection(String dbName){
        this.dbName = dbName;
        String dbUrl = "jdbc:mysql://localhost:3306/"+this.dbName;
        try {
            dbCon = DriverManager.getConnection(dbUrl, "root", "");
            System.out.println("Database Terkoneksi");
        } catch (SQLException e) {
            System.out.println("Database Gagal Terkoneksi");
        }
    }

    @Override
    public void disconnect() {
        try {
            dbCon.close();
            System.out.println("Database Terputus");
        } catch (Exception e) {
            System.out.println("Database Gagal Terputus");
        }
    }

    @Override
    public void login(String userAdmin, String passAdmin) {
        
        this.userAdmin = userAdmin;
        this.passAdmin = passAdmin;
        try {
            sqlAdmin = "select * from tb_admin where user_admin='"+this.userAdmin+"' and pass_admin='"+this.passAdmin+"'";
            Statement st = dbCon.createStatement();
            ResultSet rs = st.executeQuery(sqlAdmin);
            
            int baris = 0;
            while(rs.next()){
                baris = rs.getRow();
            }
            if(baris > 0){
                System.out.println("Selamat Anda Berhasil Login");
                cekLogin = true;
            }else{
                System.out.println("Anda Tidak Terdaftar, Silakan Register");
                cekLogin = false;
            }
        } catch (Exception e) {
        }
        
    }

    @Override
    public void register(String userAdmin, String passAdmin, String namaPegawai, String genderPegawai) {
        
        try{
            if(namaPegawai.length() > 0 && userAdmin.length() >= 5 && passAdmin.length() >= 5 && genderPegawai.length() > 0){
                sqlPegawai = "insert into tb_pegawai(nama_pegawai, gender_pegawai) values(?,?)";
                PreparedStatement stp = dbCon.prepareStatement(sqlPegawai);
                stp.setString(1, namaPegawai);
                stp.setString(2, genderPegawai);
                stp.executeUpdate();
                sqlAdmin = "insert into tb_admin(user_admin, pass_admin) values(?,?)";
                PreparedStatement sta = dbCon.prepareStatement(sqlAdmin);
                sta.setString(1, userAdmin);
                sta.setString(2, passAdmin);
                sta.executeUpdate();
                String sql = "SELECT * FROM tb_pegawai WHERE nama_pegawai='"+namaPegawai+"'";
                Statement statement = dbCon.createStatement();
                ResultSet r1 = statement.executeQuery(sql);
                r1.next();
                String sqlRegisterFinal = "update tb_admin set id_pegawai=? where tb_admin.user_admin=?";
                PreparedStatement sts = dbCon.prepareStatement(sqlRegisterFinal);
                sts.setString(1, r1.getString("id_pegawai"));
                sts.setString(2, userAdmin);
                sts.executeUpdate();
                
            }else{
                System.out.println("Username dan Password Minimal 5 Karakter, Nama dan Gender Wajib Diisi!");
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
