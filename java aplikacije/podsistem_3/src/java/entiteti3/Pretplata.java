/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti3;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Milica Bjelovuk
 */

@Entity
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIdPretplata", query = "SELECT p FROM Pretplata p WHERE p.idPretplata = :idPretplata"),
    @NamedQuery(name = "Pretplata.findByDatumPocetka", query = "SELECT p FROM Pretplata p WHERE p.datumPocetka = :datumPocetka"),
    @NamedQuery(name = "Pretplata.findByVremePocetka", query = "SELECT p FROM Pretplata p WHERE p.vremePocetka = :vremePocetka"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPretplata")
    private Integer idPretplata;
    @Basic(optional = false)
    @Column(name = "datumPocetka")
    @Temporal(TemporalType.DATE)
    private Date datumPocetka;
    @Basic(optional = false)
    @Column(name = "vremePocetka")
    @Temporal(TemporalType.TIME)
    private Date vremePocetka;
    @Basic(optional = false)
    @Column(name = "cena")
    private int cena;
    @JoinColumn(name = "pretplacenKorisnik", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik pretplacenKorisnik;
    @JoinColumn(name = "paketPretplate", referencedColumnName = "idPaket")
    @ManyToOne(optional = false)
    private Paket paketPretplate;

    public Pretplata() {
    }

    public Pretplata(Integer idPretplata) {
        this.idPretplata = idPretplata;
    }

    public Pretplata(Integer idPretplata, Date datumPocetka, Date vremePocetka, int cena) {
        this.idPretplata = idPretplata;
        this.datumPocetka = datumPocetka;
        this.vremePocetka = vremePocetka;
        this.cena = cena;
    }

    public Integer getIdPretplata() {
        return idPretplata;
    }

    public void setIdPretplata(Integer idPretplata) {
        this.idPretplata = idPretplata;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public Date getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(Date vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Korisnik getPretplacenKorisnik() {
        return pretplacenKorisnik;
    }

    public void setPretplacenKorisnik(Korisnik pretplacenKorisnik) {
        this.pretplacenKorisnik = pretplacenKorisnik;
    }

    public Paket getPaketPretplate() {
        return paketPretplate;
    }

    public void setPaketPretplate(Paket paketPretplate) {
        this.paketPretplate = paketPretplate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPretplata != null ? idPretplata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idPretplata == null && other.idPretplata != null) || (this.idPretplata != null && !this.idPretplata.equals(other.idPretplata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Pretplata[ idPretplata=" + idPretplata + " ]";
    }
    
}
