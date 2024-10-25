package dao;

import model.Tblalbum;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public class AlbumDAO {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public void addAlbum(Tblalbum album) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            album.setUnitId(album.getTblunit().getUnitId()); // Set unitId sebelum menyimpan
            session.save(album);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateAlbum(Tblalbum album) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            album.setUnitId(album.getTblunit().getUnitId()); // Set unitId sebelum memperbarui
            session.update(album);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void deleteAlbum(Tblalbum album) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(album);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Tblalbum getAlbumById(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return (Tblalbum) session.createQuery("FROM Tblalbum WHERE albumId = :id")
                                      .setParameter("id", id)
                                      .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Tblalbum> getAllAlbums() {
        Session session = null;
        try {
            session = getSession();
            return session.createQuery("FROM Tblalbum").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Tblalbum> getAlbumsByUnitId(String unitId) {
        Session session = null;
        try {
            session = getSession();
            return session.createQuery("FROM Tblalbum WHERE tblunit.unitId = :unitId ORDER BY albumId")
                          .setParameter("unitId", unitId)
                          .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
