package tn.esprit.spring.kaddem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
public class DepartementDTO implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idDepart;
    private String nomDepart;
    @OneToMany(mappedBy="departement")
    @JsonIgnore
    private Set<Etudiant> etudiants;
    public DepartementDTO() {

    }

    public DepartementDTO(String nomDepart) {
        super();
        this.nomDepart = nomDepart;
    }

    public DepartementDTO(Integer idDepart, String nomDepart) {
        super();
        this.idDepart = idDepart;
        this.nomDepart = nomDepart;
    }

    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Integer getIdDepart() {
        return idDepart;
    }
    public void setIdDepart(Integer idDepart) {
        this.idDepart = idDepart;
    }
    public String getNomDepart() {
        return nomDepart;
    }
    public void setNomDepart(String nomDepart) {
        this.nomDepart = nomDepart;
    }

}
