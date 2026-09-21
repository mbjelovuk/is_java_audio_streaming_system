/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem_3;

import entiteti3.Korisnik;
import entiteti3.Audiosnimak;
import entiteti3.Paket;
import entiteti3.Pretplata;
import entiteti3.Slusa;
import entiteti3.Ocena;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author Milica Bjelovuk
 */

public class InterfejsBaze3 {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem3PU");
    
    //paket zahtevi
    //9 - kreiranje paketa
    public Paket createPaket(Paket p) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
            return p;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //10 - promena mesecne cene za paket
    public Paket changeCenaPaketa(Integer id, int novaCena) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Paket p = em.find(Paket.class, id);
            if (p == null) return null;
            p.setTrenutnaCena(novaCena);
            p = em.merge(p);
            tx.commit();
            return p;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    //23 - dohvatanje svih paketa
    public List<Paket> getAllPaketi() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Paket> q = em.createQuery("SELECT p FROM Paket p", Paket.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    //pretplata zahtevi
    //11 - kreiranje pretplate korisnika na paket
    public Pretplata createPretplata(Pretplata pr, Integer idKorisnik, Integer idPaket) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Korisnik k = em.find(Korisnik.class, idKorisnik);
            Paket p = em.find(Paket.class, idPaket);
            if (k == null || p == null) return null;
            
            Date danas = new Date();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date mesecpre = cal.getTime();

            //ima li aktivne pretplate
            TypedQuery<Pretplata> q = em.createQuery(
                "SELECT pr FROM Pretplata pr WHERE pr.pretplacenKorisnik.idKorisnik = :idKorisnik " +
                "AND pr.datumPocetka BETWEEN :mesecpre AND :danas", Pretplata.class);
            q.setParameter("idKorisnik", idKorisnik);
            q.setParameter("mesecpre", mesecpre);
            q.setParameter("danas", danas);
            if (!q.getResultList().isEmpty()) return null;

            pr.setPretplacenKorisnik(k);
            pr.setPaketPretplate(p);
            pr.setCena(p.getTrenutnaCena());

            em.persist(pr);
            tx.commit();
            return pr;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    //24 - dohvatanje svih pretplata za korisnika
    public List<Pretplata> getPretplateZaKorisnika(Integer idKorisnik) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Pretplata> q = em.createQuery(
                    "SELECT pr FROM Pretplata pr " +
                    "WHERE pr.pretplacenKorisnik.idKorisnik = :id", Pretplata.class);
            q.setParameter("id", idKorisnik);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    //slusanje zahtevi
    //12 - kreiranje slushanja
    public Slusa createSlusanje(Slusa s, Integer idKorisnik, Integer idSnimak) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Korisnik k = em.find(Korisnik.class, idKorisnik);
            Audiosnimak a = em.find(Audiosnimak.class, idSnimak);
            if (k == null || a == null) return null;

            s.setSlusalac(k);
            s.setPustenSnimak(a);

            em.persist(s);
            tx.commit();
            return s;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    //25 - dohvatanje svih slusanja za audiosnimak
    public List<Slusa> getSlusanjaZaSnimak(Integer idSnimak) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Slusa> q = em.createQuery(
                    "SELECT s FROM Slusa s " +
                    "WHERE s.pustenSnimak.idAudiosnimak = :id", Slusa.class);
            q.setParameter("id", idSnimak);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    //omiljeni zahtevi
    //13 - dodavanje audiosnimka u omiljene
    public boolean addOmiljeni(Integer idKorisnik, Integer idSnimak) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Korisnik k = em.find(Korisnik.class, idKorisnik);
            Audiosnimak a = em.find(Audiosnimak.class, idSnimak);
            if (k == null || a == null) return false;

            if (k.getAudiosnimakList().contains(a)) return false;

            k.getAudiosnimakList().add(a);
            a.getKorisnikList().add(k);

            em.merge(k);
            em.merge(a);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    //27 - dohvatanje omiljenih audiosnimaka za korisnika
    public List<Audiosnimak> getOmiljeniZaKorisnika(Integer idKorisnik) {
        EntityManager em = emf.createEntityManager();
        try {
            Korisnik k = em.find(Korisnik.class, idKorisnik);
            if (k == null) return null;
            return k.getAudiosnimakList();
        } finally {
            em.close();
        }
    }

    //ocena zahtevi
    //14 - kreiranje ocene
    public Ocena createOcena(Ocena o, Integer idKorisnik, Integer idSnimak) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Korisnik k = em.find(Korisnik.class, idKorisnik);
            Audiosnimak a = em.find(Audiosnimak.class, idSnimak);
            if (k == null || a == null) return null;

            o.setOcenjivac(k);
            o.setOcenjenSnimak(a);

            em.persist(o);
            tx.commit();
            return o;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //15 - menjanje ocene
    public Ocena changeOcena(Integer idKorisnik, Integer idAudiosnimak, int novaOcena) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Ocena> query = em.createQuery(
                "SELECT o FROM Ocena o " +
                "WHERE o.ocenjivac.idKorisnik = :idKorisnik " +
                "AND o.ocenjenSnimak.idAudiosnimak = :idAudiosnimak", Ocena.class
            );
            query.setParameter("idKorisnik", idKorisnik);
            query.setParameter("idAudiosnimak", idAudiosnimak);

            Ocena o;
            try {
                o = query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }

            o.setOcena(novaOcena);
            em.merge(o);
            tx.commit();

            return o;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //16 - brisanje ocene
    public boolean deleteOcena(Integer idOcena, Integer idKorisnik) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Ocena o = em.find(Ocena.class, idOcena);
            if (o == null) return false;
            if (!o.getOcenjivac().getIdKorisnik().equals(idKorisnik)) {
                return false;
            }
            em.remove(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //26 - dohvatanje svih ocena za audiosnimak
    public List<Ocena> getOceneZaSnimak(Integer idSnimak) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Ocena> q = em.createQuery(
                    "SELECT o FROM Ocena o " +
                    "WHERE o.ocenjenSnimak.idAudiosnimak = :id", Ocena.class);
            q.setParameter("id", idSnimak);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
