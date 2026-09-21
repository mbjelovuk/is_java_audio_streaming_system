/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem_2;

import entiteti2.Korisnik;
import entiteti2.Audiosnimak;
import entiteti2.Kategorija;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Milica Bjelovuk
 */

public class InterfejsBaze2 {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem2PU");
    
    //kategorija zahtevi
    //5 - kreiranje kategorije
    public Kategorija createKategorija(Kategorija k) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
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

    //20 - dohvatanje svih kategorija
    public List<Kategorija> getAllKategorije() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Kategorija> q = em.createQuery("SELECT k FROM Kategorija k", Kategorija.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    //audiosnimak zahtevi
    //6 - kreiranje audiosnimka
    public Audiosnimak createAudiosnimak(Audiosnimak a, Integer idVlasnik) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (idVlasnik != null) {
                Korisnik k = em.find(Korisnik.class, idVlasnik);
                a.setVlasnik(k);
            }
            em.persist(a);
            tx.commit();
            return a;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //7 - promena naziva audiosnimka
    public Audiosnimak changeNazivAudiosnimak(Integer id, String noviNaziv) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Audiosnimak a = em.find(Audiosnimak.class, id);
            if (a == null) return null;
            a.setNaziv(noviNaziv);
            em.merge(a);
            tx.commit();
            return a;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    //8 - dodavanje kategorije audiosnimku
    public Audiosnimak addKategorijaAudiosnimku(Integer audiosnimakId, Integer kategorijaId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Audiosnimak a = em.find(Audiosnimak.class, audiosnimakId);
            Kategorija k = em.find(Kategorija.class, kategorijaId);
            if (a == null || k == null) return null;

            a.getKategorijaList().add(k);
            k.getAudiosnimakList().add(a);

            em.merge(a);
            em.merge(k);

            tx.commit();
            return a;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    //17 - brisanje audio snimka
    public boolean deleteAudiosnimak(Integer idAudiosnimak, Integer idKorisnik) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Audiosnimak a = em.find(Audiosnimak.class, idAudiosnimak);
            if (a == null || !a.getVlasnik().getIdKorisnik().equals(idKorisnik)) {
                return false;
            }
            em.remove(a);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //21 - dohvatanje svih audiosnimaka
    public List<Audiosnimak> getAllAudiosnimci() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Audiosnimak> q = em.createQuery("SELECT a FROM Audiosnimak a", Audiosnimak.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    //22 - dohvatanje kategorija za odredjeni audio snimak
    public List<Kategorija> getKategorijeZaAudiosnimak(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Audiosnimak a = em.find(Audiosnimak.class, id);
            if (a == null) return Collections.emptyList();
            a.getKategorijaList().size(); 
            return a.getKategorijaList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

}
