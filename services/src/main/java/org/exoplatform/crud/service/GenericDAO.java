package org.exoplatform.crud.service;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.crud.service.impl.EntityManagerServiceImpl;

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
    EntityManagerService entityManagerService;
    private Class<T> genericType;
    public GenericDAO(Class<T> type){
        genericType=type;
        entityManagerService = (EntityManagerService) ExoContainerContext.
                    getCurrentContainer().getComponentInstanceOfType(EntityManagerService.class);

        if (entityManagerService == null)
            entityManagerService = new EntityManagerServiceImpl();
    }





    public void persist(T t){
        try {
        EntityManager em = entityManagerService.getEntityManager();
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
            EntityManager em = entityManagerService.getEntityManager();;
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
            EntityManager em = entityManagerService.getEntityManager();;
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
            EntityManager em = entityManagerService.getEntityManager();;
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
            EntityManager em = entityManagerService.getEntityManager();;
            t=(T) em.find(genericType, id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }




}
