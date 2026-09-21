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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIdOcena", query = "SELECT o FROM Ocena o WHERE o.idOcena = :idOcena"),
    @NamedQuery(name = "Ocena.findByDatum", query = "SELECT o FROM Ocena o WHERE o.datum = :datum"),
    @NamedQuery(name = "Ocena.findByVreme", query = "SELECT o FROM Ocena o WHERE o.vreme = :vreme"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOcena")
    private Integer idOcena;
    @Basic(optional = false)
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Basic(optional = false)
    @Column(name = "vreme")
    @Temporal(TemporalType.TIME)
    private Date vreme;
    @Basic(optional = false)
    @Column(name = "ocena")
    private int ocena;
    @JoinColumn(name = "ocenjenSnimak", referencedColumnName = "idAudiosnimak")
    @ManyToOne(optional = false)
    private Audiosnimak ocenjenSnimak;
    @JoinColumn(name = "ocenjivac", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik ocenjivac;

    public Ocena() {
    }

    public Ocena(Integer idOcena) {
        this.idOcena = idOcena;
    }

    public Ocena(Integer idOcena, Date datum, Date vreme, int ocena) {
        this.idOcena = idOcena;
        this.datum = datum;
        this.vreme = vreme;
        this.ocena = ocena;
    }

    public Integer getIdOcena() {
        return idOcena;
    }

    public void setIdOcena(Integer idOcena) {
        this.idOcena = idOcena;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Audiosnimak getOcenjenSnimak() {
        return ocenjenSnimak;
    }

    public void setOcenjenSnimak(Audiosnimak ocenjenSnimak) {
        this.ocenjenSnimak = ocenjenSnimak;
    }

    public Korisnik getOcenjivac() {
        return ocenjivac;
    }

    public void setOcenjivac(Korisnik ocenjivac) {
        this.ocenjivac = ocenjivac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOcena != null ? idOcena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.idOcena == null && other.idOcena != null) || (this.idOcena != null && !this.idOcena.equals(other.idOcena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Ocena[ idOcena=" + idOcena + " ]";
    }
    
}
