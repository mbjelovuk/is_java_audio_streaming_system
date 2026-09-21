/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti3;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Milica Bjelovuk
 */

@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdKorisnik", query = "SELECT k FROM Korisnik k WHERE k.idKorisnik = :idKorisnik"),
    @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime"),
    @NamedQuery(name = "Korisnik.findByEmail", query = "SELECT k FROM Korisnik k WHERE k.email = :email"),
    @NamedQuery(name = "Korisnik.findByGodiste", query = "SELECT k FROM Korisnik k WHERE k.godiste = :godiste"),
    @NamedQuery(name = "Korisnik.findByPol", query = "SELECT k FROM Korisnik k WHERE k.pol = :pol")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKorisnik")
    private Integer idKorisnik;
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "godiste")
    private int godiste;
    @Basic(optional = false)
    @Column(name = "pol")
    private String pol;
    @JoinTable(name = "omiljenisnimci", joinColumns = {
        @JoinColumn(name = "korisnik", referencedColumnName = "idKorisnik")}, inverseJoinColumns = {
        @JoinColumn(name = "snimak", referencedColumnName = "idAudiosnimak")})
    @ManyToMany
    private List<Audiosnimak> audiosnimakList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vlasnik")
    private List<Audiosnimak> audiosnimakList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pretplacenKorisnik")
    private List<Pretplata> pretplataList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slusalac")
    private List<Slusa> slusaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ocenjivac")
    private List<Ocena> ocenaList;
//    @JoinColumn(name = "mestoDolaska", referencedColumnName = "idMesto")
//    @ManyToOne(optional = false)
//    private Mesto mestoDolaska;

    public Korisnik() {
    }

    public Korisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public Korisnik(Integer idKorisnik, String ime, String email, int godiste, String pol) {
        this.idKorisnik = idKorisnik;
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
    }

    public Integer getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    @XmlTransient
    public List<Audiosnimak> getAudiosnimakList() {
        return audiosnimakList;
    }

    public void setAudiosnimakList(List<Audiosnimak> audiosnimakList) {
        this.audiosnimakList = audiosnimakList;
    }

    @XmlTransient
    public List<Audiosnimak> getAudiosnimakList1() {
        return audiosnimakList1;
    }

    public void setAudiosnimakList1(List<Audiosnimak> audiosnimakList1) {
        this.audiosnimakList1 = audiosnimakList1;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @XmlTransient
    public List<Slusa> getSlusaList() {
        return slusaList;
    }

    public void setSlusaList(List<Slusa> slusaList) {
        this.slusaList = slusaList;
    }

    @XmlTransient
    public List<Ocena> getOcenaList() {
        return ocenaList;
    }

    public void setOcenaList(List<Ocena> ocenaList) {
        this.ocenaList = ocenaList;
    }

//    public Mesto getMestoDolaska() {
//        return mestoDolaska;
//    }
//
//    public void setMestoDolaska(Mesto mestoDolaska) {
//        this.mestoDolaska = mestoDolaska;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKorisnik != null ? idKorisnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idKorisnik == null && other.idKorisnik != null) || (this.idKorisnik != null && !this.idKorisnik.equals(other.idKorisnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ idKorisnik=" + idKorisnik + " ]";
    }
    
}
