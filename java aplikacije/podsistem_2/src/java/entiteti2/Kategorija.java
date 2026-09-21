/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti2;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Milica Bjelovuk
 */

@Entity
@Table(name = "kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k"),
    @NamedQuery(name = "Kategorija.findByIdKategorija", query = "SELECT k FROM Kategorija k WHERE k.idKategorija = :idKategorija"),
    @NamedQuery(name = "Kategorija.findByNaziv", query = "SELECT k FROM Kategorija k WHERE k.naziv = :naziv")})
public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKategorija")
    private Integer idKategorija;
    @Basic(optional = false)
    @Column(name = "naziv")
    private String naziv;
    @JoinTable(name = "kategorijesnimaka", joinColumns = {
        @JoinColumn(name = "kategorija", referencedColumnName = "idKategorija")}, inverseJoinColumns = {
        @JoinColumn(name = "snimak", referencedColumnName = "idAudiosnimak")})
    @ManyToMany
    private List<Audiosnimak> audiosnimakList;

    public Kategorija() {
    }

    public Kategorija(Integer idKategorija) {
        this.idKategorija = idKategorija;
    }

    public Kategorija(Integer idKategorija, String naziv) {
        this.idKategorija = idKategorija;
        this.naziv = naziv;
    }

    public Integer getIdKategorija() {
        return idKategorija;
    }

    public void setIdKategorija(Integer idKategorija) {
        this.idKategorija = idKategorija;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<Audiosnimak> getAudiosnimakList() {
        return audiosnimakList;
    }

    public void setAudiosnimakList(List<Audiosnimak> audiosnimakList) {
        this.audiosnimakList = audiosnimakList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKategorija != null ? idKategorija.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.idKategorija == null && other.idKategorija != null) || (this.idKategorija != null && !this.idKategorija.equals(other.idKategorija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Kategorija[ idKategorija=" + idKategorija + " ]";
    }
    
}
