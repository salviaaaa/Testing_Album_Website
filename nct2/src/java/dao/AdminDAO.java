package dao;

import java.util.ArrayList;
import java.util.List;
import util.HibernateUtil;
import model.Tbladmin;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class AdminDAO {
    public List<Tbladmin> getBy(String uName, String uPass) {
        Tbladmin u = new Tbladmin();
        List<Tbladmin> user = new ArrayList();
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Query query = session.createQuery("from Tbladmin where username="
                    + ":uName AND password= :uPass");
            query.setString("uName", uName);
            query.setString("uPass", uPass);
            u = (Tbladmin) query.uniqueResult();
            user = query.list();
            trans.commit();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        
        // Logika tambahan untuk mengembalikan hasil yang sesuai
        if (user != null && !user.isEmpty()) {
            return user; // Kembalikan pengguna yang valid
        } else {
            // Jika tidak ada pengguna yang valid, kembalikan daftar kosong
            return new ArrayList<>(); // Kembalikan daftar kosong untuk menunjukkan error
        }
    }
}
