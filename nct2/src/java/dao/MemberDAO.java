package dao;

import model.Tblmember;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public class MemberDAO {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public void addMember(Tblmember member) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(member); // Simpan anggota baru ke database
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

    public void updateMember(Tblmember member) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            session.update(member);
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

    public void deleteMember(Tblmember member) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            session.delete(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e; // Lempar kembali exception agar dapat ditangkap di Tblmember
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Tblmember getMemberById(String id) {
        Session session = null;
        try {
            session = getSession();
            return (Tblmember) session.createQuery("FROM Tblmember WHERE memberId = :id")
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

    public List<Tblmember> getAllMembers() {
        Session session = null;
        try {
            session = getSession();
            return session.createQuery("FROM Tblmember ORDER BY tblunit.unitId, memberId").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Tblmember> getMembersByUnitId(String unitId) {
        Session session = null;
        try {
            session = getSession();
            return session.createQuery("FROM Tblmember WHERE tblunit.unitId = :unitId ORDER BY memberId")
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
