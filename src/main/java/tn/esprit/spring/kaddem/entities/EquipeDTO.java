package tn.esprit.spring.kaddem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
public class EquipeDTO implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idEquipe;
    private String nomEquipe;
    @Enumerated(EnumType.STRING)
    private Niveau niveau;
    //@ManyToMany(mappedBy="equipes")
    @ManyToMany(cascade =CascadeType.ALL)

    @JsonIgnore
    private Set<EtudiantDTO> etudiantDTOS;
    @OneToOne
    private DetailEquipeDTO detailEquipeDTO;

    public EquipeDTO() {

    }

    public EquipeDTO(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public EquipeDTO(String nomEquipe, Niveau niveau) {
        super();
        this.nomEquipe = nomEquipe;
        this.niveau = niveau;
    }

    public EquipeDTO(Integer idEquipe, String nomEquipe, Niveau niveau) {
        super();
        this.idEquipe = idEquipe;
        this.nomEquipe = nomEquipe;
        this.niveau = niveau;
    }

    public EquipeDTO(String nomEquipe, Niveau niveau, Set<EtudiantDTO> etudiantDTOS, DetailEquipeDTO detailEquipeDTO) {
        this.nomEquipe = nomEquipe;
        this.niveau = niveau;
        this.etudiantDTOS = etudiantDTOS;
        this.detailEquipeDTO = detailEquipeDTO;
    }

    public EquipeDTO(Integer idEquipe, String nomEquipe, Niveau niveau, Set<EtudiantDTO> etudiantDTOS, DetailEquipeDTO detailEquipeDTO) {
        this.idEquipe = idEquipe;
        this.nomEquipe = nomEquipe;
        this.niveau = niveau;
        this.etudiantDTOS = etudiantDTOS;
        this.detailEquipeDTO = detailEquipeDTO;
    }

    public Set<EtudiantDTO> getEtudiants() {
        return etudiantDTOS;
    }

    public void setEtudiants(Set<EtudiantDTO> etudiantDTOS) {
        this.etudiantDTOS = etudiantDTOS;
    }

    public DetailEquipeDTO getDetailEquipe() {
        return detailEquipeDTO;
    }

    public void setDetailEquipe(DetailEquipeDTO detailEquipeDTO) {
        this.detailEquipeDTO = detailEquipeDTO;
    }

    public Integer getIdEquipe() {
        return idEquipe;
    }
    public void setIdEquipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }
    public String getNomEquipe() {
        return nomEquipe;
    }
    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }
    public Niveau getNiveau() {
        return niveau;
    }
    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

}
