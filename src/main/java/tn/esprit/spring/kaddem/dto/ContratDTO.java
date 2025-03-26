package tn.esprit.spring.kaddem.dto;

import tn.esprit.spring.kaddem.entities.Specialite;
import javax.validation.constraints.*;
import java.util.Date;

public class ContratDTO {
    private Integer idContrat;

    @NotNull(message = "Start date is mandatory")
    private Date dateDebutContrat;

    @NotNull(message = "End date is mandatory")
    private Date dateFinContrat;

    @NotNull(message = "Specialty is mandatory")
    private Specialite specialite;

    private Boolean archive;

    @Min(value = 0, message = "Contract amount cannot be negative")
    private Integer montantContrat;

    @NotNull(message = "Student ID is mandatory")
    private Integer etudiantId;  // Only store the ID, not the entire Etudiant object

    // Constructors
    public ContratDTO() {
    }

    public ContratDTO(Integer idContrat, Date dateDebutContrat, Date dateFinContrat,
                      Specialite specialite, Boolean archive, Integer montantContrat,
                      Integer etudiantId) {
        this.idContrat = idContrat;
        this.dateDebutContrat = dateDebutContrat;
        this.dateFinContrat = dateFinContrat;
        this.specialite = specialite;
        this.archive = archive;
        this.montantContrat = montantContrat;
        this.etudiantId = etudiantId;
    }

    // Getters and Setters
    public Integer getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(Integer idContrat) {
        this.idContrat = idContrat;
    }

    public Date getDateDebutContrat() {
        return dateDebutContrat;
    }

    public void setDateDebutContrat(Date dateDebutContrat) {
        this.dateDebutContrat = dateDebutContrat;
    }

    public Date getDateFinContrat() {
        return dateFinContrat;
    }

    public void setDateFinContrat(Date dateFinContrat) {
        this.dateFinContrat = dateFinContrat;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public Integer getMontantContrat() {
        return montantContrat;
    }

    public void setMontantContrat(Integer montantContrat) {
        this.montantContrat = montantContrat;
    }

    public Integer getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Integer etudiantId) {
        this.etudiantId = etudiantId;
    }
}