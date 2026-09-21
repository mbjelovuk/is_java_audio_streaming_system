/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem_1;

import entiteti1.Korisnik;
import entiteti1.Mesto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Milica Bjelovuk
 */

public class InterfejsBaze1 {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem1PU");

//    //bilo bi vise clean sa ovim ali eto nmvz
//    public static void closef() {
//        if (emf.isOpen()) emf.close();
//    }
    
    //korisnik zahtevi
    //2 - kreiranje korisnika
    public Korisnik createKorisnik(Korisnik k, Integer mestoId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (mestoId != null) {
                Mesto m = em.find(Mesto.class, mestoId);
                k.setMestoDolaska(m);
            }

            em.persist(k);
            tx.commit();
            return k;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //3 - promena emaila korisnika
    public Korisnik changeKorisnikEmail(Integer korisnikId, String noviEmail) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Korisnik k = em.find(Korisnik.class, korisnikId);
            if (k == null) return null;
            k.setEmail(noviEmail);
            em.merge(k);

            tx.commit();
            return k;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //4 - promena mesta korisnika
    public Korisnik changeKorisnikMesto(Integer korisnikId, Integer novoMestoId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Korisnik k = em.find(Korisnik.class, korisnikId);
            if (k == null) return null;
            Mesto m = em.find(Mesto.class, novoMestoId);
            k.setMestoDolaska(m);
            em.merge(k);

            tx.commit();
            return k;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    //19 - dohvatanje svih korisnika
    public List<Korisnik> getAllKorisnici() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Korisnik> q = em.createQuery("SELECT k FROM Korisnik k", Korisnik.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    //mesto zahtevi
    //1 - kreiranje mesta
    public Mesto createMesto(Mesto m) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(m);
            tx.commit();
            return m;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //18 - dohvatanje svih mesta
    public List<Mesto> getAllMesta() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Mesto> q = em.createQuery("SELECT m FROM Mesto m", Mesto.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}

