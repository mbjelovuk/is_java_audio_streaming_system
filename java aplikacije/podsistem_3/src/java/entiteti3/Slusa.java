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
@Table(name = "slusa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slusa.findAll", query = "SELECT s FROM Slusa s"),
    @NamedQuery(name = "Slusa.findByIdSlusa", query = "SELECT s FROM Slusa s WHERE s.idSlusa = :idSlusa"),
    @NamedQuery(name = "Slusa.findByDatumPoc", query = "SELECT s FROM Slusa s WHERE s.datumPoc = :datumPoc"),
    @NamedQuery(name = "Slusa.findByVremePoc", query = "SELECT s FROM Slusa s WHERE s.vremePoc = :vremePoc"),
    @NamedQuery(name = "Slusa.findByPocetniSekund", query = "SELECT s FROM Slusa s WHERE s.pocetniSekund = :pocetniSekund"),
    @NamedQuery(name = "Slusa.findByOdslusanoSekundi", query = "SELECT s FROM Slusa s WHERE s.odslusanoSekundi = :odslusanoSekundi")})
public class Slusa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSlusa")
    private Integer idSlusa;
    @Basic(optional = false)
    @Column(name = "datumPoc")
    @Temporal(TemporalType.DATE)
    private Date datumPoc;
    @Basic(optional = false)
    @Column(name = "vremePoc")
    @Temporal(TemporalType.TIME)
    private Date vremePoc;
    @Basic(optional = false)
    @Column(name = "pocetniSekund")
    private int pocetniSekund;
    @Basic(optional = false)
    @Column(name = "odslusanoSekundi")
    private int odslusanoSekundi;
    @JoinColumn(name = "pustenSnimak", referencedColumnName = "idAudiosnimak")
    @ManyToOne(optional = false)
    private Audiosnimak pustenSnimak;
    @JoinColumn(name = "slusalac", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik slusalac;

    public Slusa() {
    }

    public Slusa(Integer idSlusa) {
        this.idSlusa = idSlusa;
    }

    public Slusa(Integer idSlusa, Date datumPoc, Date vremePoc, int pocetniSekund, int odslusanoSekundi) {
        this.idSlusa = idSlusa;
        this.datumPoc = datumPoc;
        this.vremePoc = vremePoc;
        this.pocetniSekund = pocetniSekund;
        this.odslusanoSekundi = odslusanoSekundi;
    }

    public Integer getIdSlusa() {
        return idSlusa;
    }

    public void setIdSlusa(Integer idSlusa) {
        this.idSlusa = idSlusa;
    }

    public Date getDatumPoc() {
        return datumPoc;
    }

    public void setDatumPoc(Date datumPoc) {
        this.datumPoc = datumPoc;
    }

    public Date getVremePoc() {
        return vremePoc;
    }

    public void setVremePoc(Date vremePoc) {
        this.vremePoc = vremePoc;
    }

    public int getPocetniSekund() {
        return pocetniSekund;
    }

    public void setPocetniSekund(int pocetniSekund) {
        this.pocetniSekund = pocetniSekund;
    }

    public int getOdslusanoSekundi() {
        return odslusanoSekundi;
    }

    public void setOdslusanoSekundi(int odslusanoSekundi) {
        this.odslusanoSekundi = odslusanoSekundi;
    }

    public Audiosnimak getPustenSnimak() {
        return pustenSnimak;
    }

    public void setPustenSnimak(Audiosnimak pustenSnimak) {
        this.pustenSnimak = pustenSnimak;
    }

    public Korisnik getSlusalac() {
        return slusalac;
    }

    public void setSlusalac(Korisnik slusalac) {
        this.slusalac = slusalac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSlusa != null ? idSlusa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slusa)) {
            return false;
        }
        Slusa other = (Slusa) object;
        if ((this.idSlusa == null && other.idSlusa != null) || (this.idSlusa != null && !this.idSlusa.equals(other.idSlusa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Slusa[ idSlusa=" + idSlusa + " ]";
    }
    
}
