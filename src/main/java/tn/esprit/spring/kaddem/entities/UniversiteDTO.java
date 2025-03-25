package tn.esprit.spring.kaddem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
public class UniversiteDTO implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idUniv;
    private String nomUniv;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<DepartementDTO> departements;
    public UniversiteDTO() {

    }

    public UniversiteDTO(String nomUniv) {
        super();
        this.nomUniv = nomUniv;
    }

    public UniversiteDTO(Integer idUniv, String nomUniv) {
        super();
        this.idUniv = idUniv;
        this.nomUniv = nomUniv;
    }

    public Set<DepartementDTO> getDepartements() {
        return departements;
    }

    public void setDepartements(Set<DepartementDTO> departements) {
        this.departements = departements;
    }

    public Integer getIdUniv() {
        return idUniv;
    }
    public void setIdUniv(Integer idUniv) {
        this.idUniv = idUniv;
    }
    public String getNomUniv() {
        return nomUniv;
    }
    public void setNomUniv(String nomUniv) {
        this.nomUniv = nomUniv;
    }

}
