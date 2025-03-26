package tn.esprit.spring.kaddem.dto;

import tn.esprit.spring.kaddem.entities.Niveau;
import javax.validation.constraints.*;
import java.util.Set;

public class EquipeDTO {
    private Integer idEquipe;

    @NotBlank(message = "Team name is mandatory")
    @Size(min = 2, max = 50, message = "Team name must be between 2 and 50 characters")
    private String nomEquipe;

    @NotNull(message = "Level is mandatory")
    private Niveau niveau;

    private Set<Integer> etudiantIds; // Only store IDs, not full objects
    private Integer detailEquipeId;   // Only store ID, not full object

    // Constructors
    public EquipeDTO() {
    }

    public EquipeDTO(Integer idEquipe, String nomEquipe, Niveau niveau) {
        this.idEquipe = idEquipe;
        this.nomEquipe = nomEquipe;
        this.niveau = niveau;
    }

    // Getters and Setters
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

    public Set<Integer> getEtudiantIds() {
        return etudiantIds;
    }

    public void setEtudiantIds(Set<Integer> etudiantIds) {
        this.etudiantIds = etudiantIds;
    }

    public Integer getDetailEquipeId() {
        return detailEquipeId;
    }

    public void setDetailEquipeId(Integer detailEquipeId) {
        this.detailEquipeId = detailEquipeId;
    }
}