package edu.ce.sharif.twolru.db;

import edu.ce.sharif.twolru.db.datatypes.Page;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class RootMgr {
	private static SessionFactory sessionFactory;
	private static Session session;
	private static SessionFactory getInstancedSessionFactory() {
		try {
			if (sessionFactory != null)
				return sessionFactory;
			sessionFactory = new Configuration().configure("edu/ce/sharif/twolru/db/hibernate.cfg.xml")
					.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		return sessionFactory;
	}

	public static Session getInstance(){
		if(session!=null&&session.isOpen())
			return session;
		session=getInstancedSessionFactory().openSession();
		return session;
	}

	public static Long add(Object object) {
		Session session = getInstance();
		Transaction tx = null;
		Long addedId = null;
		try {
			tx = session.beginTransaction();
			addedId = (Long) session.save(object);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return addedId;
	}

	public static void update(Object toUpdate) {
		Session session = getInstance();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(toUpdate);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public static void delete(Page toDelete) {
		Session session = getInstance();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SQLQuery sqlQuery=session.createSQLQuery("DELETE FROM PAGES WHERE ADDRESS = "+toDelete.getAddress());
			sqlQuery.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
