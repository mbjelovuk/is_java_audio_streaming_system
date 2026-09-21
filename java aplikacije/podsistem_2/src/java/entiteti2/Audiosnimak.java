/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti2;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Milica Bjelovuk
 */

@Entity
@Table(name = "audiosnimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audiosnimak.findAll", query = "SELECT a FROM Audiosnimak a"),
    @NamedQuery(name = "Audiosnimak.findByIdAudiosnimak", query = "SELECT a FROM Audiosnimak a WHERE a.idAudiosnimak = :idAudiosnimak"),
    @NamedQuery(name = "Audiosnimak.findByNaziv", query = "SELECT a FROM Audiosnimak a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Audiosnimak.findByTrajanje", query = "SELECT a FROM Audiosnimak a WHERE a.trajanje = :trajanje"),
    @NamedQuery(name = "Audiosnimak.findByDatumPostavljanja", query = "SELECT a FROM Audiosnimak a WHERE a.datumPostavljanja = :datumPostavljanja"),
    @NamedQuery(name = "Audiosnimak.findByVremePostavljanja", query = "SELECT a FROM Audiosnimak a WHERE a.vremePostavljanja = :vremePostavljanja")})
public class Audiosnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAudiosnimak")
    private Integer idAudiosnimak;
    @Basic(optional = false)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false) 
    @Column(name = "datumPostavljanja")
    @Temporal(TemporalType.DATE)
    private Date datumPostavljanja;
    @Basic(optional = false)
    @Column(name = "vremePostavljanja")
    @Temporal(TemporalType.TIME)
    private Date vremePostavljanja;
    @ManyToMany(mappedBy = "audiosnimakList")
    private List<Kategorija> kategorijaList;
//    @ManyToMany(mappedBy = "audiosnimakList")
//    private List<Korisnik> korisnikList;
    @JoinColumn(name = "vlasnik", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik vlasnik;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pustenSnimak")
//    private List<Slusa> slusaList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ocenjenSnimak")
//    private List<Ocena> ocenaList;

    public Audiosnimak() {
    }

    public Audiosnimak(Integer idAudiosnimak) {
        this.idAudiosnimak = idAudiosnimak;
    }

    public Audiosnimak(Integer idAudiosnimak, String naziv, int trajanje, Date datumPostavljanja, Date vremePostavljanja) {
        this.idAudiosnimak = idAudiosnimak;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumPostavljanja = datumPostavljanja;
        this.vremePostavljanja = vremePostavljanja;
    }

    public Integer getIdAudiosnimak() {
        return idAudiosnimak;
    }

    public void setIdAudiosnimak(Integer idAudiosnimak) {
        this.idAudiosnimak = idAudiosnimak;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumPostavljanja() {
        return datumPostavljanja;
    }

    public void setDatumPostavljanja(Date datumPostavljanja) {
        this.datumPostavljanja = datumPostavljanja;
    }

    public Date getVremePostavljanja() {
        return vremePostavljanja;
    }

    public void setVremePostavljanja(Date vremePostavljanja) {
        this.vremePostavljanja = vremePostavljanja;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }
//
//    @XmlTransient
//    public List<Korisnik> getKorisnikList() {
//        return korisnikList;
//    }
//
//    public void setKorisnikList(List<Korisnik> korisnikList) {
//        this.korisnikList = korisnikList;
//    }

    public Korisnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Korisnik vlasnik) {
        this.vlasnik = vlasnik;
    }

//    @XmlTransient
//    public List<Slusa> getSlusaList() {
//        return slusaList;
//    }
//
//    public void setSlusaList(List<Slusa> slusaList) {
//        this.slusaList = slusaList;
//    }
//
//    @XmlTransient
//    public List<Ocena> getOcenaList() {
//        return ocenaList;
//    }
//
//    public void setOcenaList(List<Ocena> ocenaList) {
//        this.ocenaList = ocenaList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAudiosnimak != null ? idAudiosnimak.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Audiosnimak)) {
            return false;
        }
        Audiosnimak other = (Audiosnimak) object;
        if ((this.idAudiosnimak == null && other.idAudiosnimak != null) || (this.idAudiosnimak != null && !this.idAudiosnimak.equals(other.idAudiosnimak))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Audiosnimak[ idAudiosnimak=" + idAudiosnimak + " ]";
    }
    
}
