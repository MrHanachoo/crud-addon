package org.exoplatform.crud.service;

import javax.persistence.EntityManager;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 24/02/16
 */
public interface EntityManagerService {
    EntityManager getEntityManager();
}
