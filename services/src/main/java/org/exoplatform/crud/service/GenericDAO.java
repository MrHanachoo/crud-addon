package org.exoplatform.crud.service;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 03/09/15
 */
public class GenericDAO<T> {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private Class<T> genericType;
    public GenericDAO(Class<T> type){
        genericType=type;
    }

    public static EntityManager getEntityManager(){
        if(emf == null){
            emf=Persistence.createEntityManagerFactory("crudDb");
        }
        if (em == null )
            em = emf.createEntityManager();
        return em;
    }



    public void persist(T t){
        try {
        EntityManager em = getEntityManager();
        if (! em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        em.persist(t);
        em.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    public void merge(T t){

        try {
            EntityManager em = getEntityManager();
            if (! em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.merge(t);
            em.getTransaction().commit();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void delete(T t){
        try{
            EntityManager em = getEntityManager();
            if (! em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.remove(em.contains(t) ? t : em.merge(t));
            em.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }



    }

    public List<T> findAll(){
        List<T> result = null;
        try {
            EntityManager em = getEntityManager();
            if (! em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            Query query=em.createQuery("Select P from " + genericType.getSimpleName() +" P");
            result=query.getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return result;

    }

    public T findById(int id){
        T t=null;
        try {
            EntityManager em = getEntityManager();
            t=(T) em.find(genericType, id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }




}
