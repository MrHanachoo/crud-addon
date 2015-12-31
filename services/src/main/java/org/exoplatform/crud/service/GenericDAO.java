package org.exoplatform.crud.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.persistence.Persistence;
import java.util.List;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 03/09/15
 */
public class GenericDAO<T> {
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private Class<T> genericType;
    public GenericDAO(Class<T> type){
        genericType=type;
    }


    public static SessionFactory createSessionFactory() {
            Configuration configuration = new Configuration();
            configuration.configure();
            if(sessionFactory==null) {
                serviceRegistry = new ServiceRegistryBuilder().applySettings(
                        configuration.getProperties()).buildServiceRegistry();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        return sessionFactory;
    }

    public Session getSession(){
        if(sessionFactory==null)
            sessionFactory=createSessionFactory();
        return sessionFactory.openSession();


    }

    public void persist(T t){
        Session session=getSession();
        try {
        Transaction tx=session.beginTransaction();
        session.persist(t);
        tx.commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(session.isOpen())
            session.close();
        }

    }

    public void merge(T t){
        Session session=getSession();
        try {
            Transaction tx=session.beginTransaction();
            session.merge(t);
            tx.commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(session.isOpen())
                session.close();
        }

    }

    public void delete(T t){
        Session session=getSession();
        try{
            Transaction tx=session.beginTransaction();
            session.delete(t);
            tx.commit();
        }
        finally {
            if(session.isOpen())
            session.close();
        }
    }

    public List<T> findAll(){
        Session session=getSession();
        List<T> result = null;
        try {
            Query query=session.createQuery("from " + genericType.getSimpleName());
            result=query.list();
        }
        finally {
            if(session.isOpen())
            session.close();
        }
        return result;

    }

    public T findById(int id){
        Session session=getSession();
        T t=null;
        try {
            t=(T) session.get(genericType, id);
        }
        finally {
            if(session.isOpen())
            session.close();
        }
        return t;
    }




}
