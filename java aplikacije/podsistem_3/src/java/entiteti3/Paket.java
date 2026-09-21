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
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p"),
    @NamedQuery(name = "Paket.findByIdPaket", query = "SELECT p FROM Paket p WHERE p.idPaket = :idPaket"),
    @NamedQuery(name = "Paket.findByTrenutnaCena", query = "SELECT p FROM Paket p WHERE p.trenutnaCena = :trenutnaCena"),
    @NamedQuery(name = "Paket.findByNaziv", query = "SELECT p FROM Paket p WHERE p.naziv = :naziv")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPaket")
    private Integer idPaket;
    @Basic(optional = false)
    @Column(name = "trenutnaCena")
    private int trenutnaCena;
    @Basic(optional = false)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paketPretplate")
    private List<Pretplata> pretplataList;

    public Paket() {
    }

    public Paket(Integer idPaket) {
        this.idPaket = idPaket;
    }

    public Paket(Integer idPaket, int trenutnaCena, String naziv) {
        this.idPaket = idPaket;
        this.trenutnaCena = trenutnaCena;
        this.naziv = naziv;
    }

    public Integer getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(Integer idPaket) {
        this.idPaket = idPaket;
    }

    public int getTrenutnaCena() {
        return trenutnaCena;
    }

    public void setTrenutnaCena(int trenutnaCena) {
        this.trenutnaCena = trenutnaCena;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaket != null ? idPaket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.idPaket == null && other.idPaket != null) || (this.idPaket != null && !this.idPaket.equals(other.idPaket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Paket[ idPaket=" + idPaket + " ]";
    }
    
}
