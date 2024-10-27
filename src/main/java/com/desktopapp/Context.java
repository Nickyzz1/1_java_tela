package com.desktopapp;
// essa classe interage com o bamco de dados
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Context {

    private EntityManagerFactory emf;
    private EntityManager em;

    public Context() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit"); 
    }

    public void begin() {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            em = null;
        }
    }

    public <T> List<T> find(Class<T> entytyClass, String query, Object... values ) {
        EntityManager em = emf.createEntityManager();
        List<T> users = null;
        try {
            var queryObj = em.createQuery(query, entytyClass);
            if(values != null)
            {
                for (Integer i = 0; i < values.length; i++) {
                    queryObj = queryObj.setParameter("arg" + i.toString(), values[i]);
                }
            }
            users = queryObj.getResultList();
        } finally {
            em.close();
        }
        return users;
    }

    public <T> T find(Class<T> entityClass, Object primaryKey) {
        EntityManager em = emf.createEntityManager();
        T user = null;
        try {
            user = em.find(entityClass, primaryKey); 
        } finally {
            em.close();
        }
        return user;
    }

    public void save(Object object) {
        if (em == null) {
            System.out.println("connection is null.");
            return;
        }
        try {
            em.persist(object); 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            em = null;
        }
    }

    public void commit() {
        if (em == null) {
            System.out.println("connection is null.");
            return;
        }
        try {
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
            em = null;
        }
    }

    public <T> TypedQuery<T> createQuery(Class<T> entityClass, String query) {
        EntityManager em = emf.createEntityManager();
        //Aqui, um novo EntityManager é criado a partir da EntityManagerFactory (emf). O EntityManager é responsável por gerenciar a persistência das entidades e realizar operações de banco de dados.
        try {
            var queryObj = em.createQuery(query, entityClass);
            System.out.println("\n\n\n\n\nCriou a query na função");
            return queryObj;
        } catch (Exception e) {
            e.printStackTrace();
            em.close();
            em = null;
            System.out.println("\n\n\n\n\n\nCaiu no erro da função da query");
            return null;
        }
    }

    public void remove(Object object) {
        if (em == null) {
            System.out.println("connection is null.");
            return;
        }
        try {
            em.remove(em.contains(object) ? object : em.merge(object)); 
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            em = null;
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }

}
