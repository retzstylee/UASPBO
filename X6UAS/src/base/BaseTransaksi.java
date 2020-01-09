
package base;
import db.DbConnection;

/**
 *
 * @author brilyan
 */
public abstract class BaseTransaksi {
    public abstract void newTransaksi(DbConnection db, String nama, String banyak);
    public abstract void del_transaksi(DbConnection db, String id);
}
