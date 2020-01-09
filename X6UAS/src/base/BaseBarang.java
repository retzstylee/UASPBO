
package base;
import db.DbConnection;

public abstract class BaseBarang {
    public abstract void insert(DbConnection db, String namaBarang, String jenisBarang, String hargaBarang, String stockBarang);
    public abstract void update(DbConnection db, String idBarang, String namaBarang, String jenisBarang, String hargaBarang, String stockBarang);
    public abstract void delete(DbConnection db, String idBarang);
}
