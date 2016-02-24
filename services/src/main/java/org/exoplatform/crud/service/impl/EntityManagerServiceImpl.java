package org.exoplatform.crud.service.impl;

import org.exoplatform.crud.service.EntityManagerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 24/02/16
 */
public class EntityManagerServiceImpl implements EntityManagerService {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public EntityManagerServiceImpl(){
        emf = Persistence.createEntityManagerFactory("crudDb");
    }

    public EntityManager getEntityManager(){
        if(emf == null){
            emf= Persistence.createEntityManagerFactory("crudDb");
        }
        if (em == null )
            em = emf.createEntityManager();
        return em;
    }


}
