package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Tblunit;
import org.hibernate.Session;
import util.HibernateUtil;

public class UnitDAO {
    private static final Logger logger = Logger.getLogger(UnitDAO.class.getName());

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public List<Tblunit> getAllUnits() {
        Session session = null;
        try {
            session = getSession();
            return session.createQuery("FROM Tblunit").list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving all units", e);
            throw e; // Melempar pengecualian
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Tblunit> getUnitsByUnitId(String unitId) {
        Session session = null;
        try {
            session = getSession();
            return session.createQuery("FROM Tblunit WHERE unitId = :unitId")
                          .setParameter("unitId", unitId)
                          .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving units by unitId", e);
            throw e; // Melempar pengecualian
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
