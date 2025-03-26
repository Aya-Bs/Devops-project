package tn.esprit.spring.kaddem.dto;  // Recommended to put DTOs in a "dto" package

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DepartementDTO {
    private Integer idDepart;

    @NotBlank(message = "Department name is mandatory")
    @Size(min = 2, max = 50, message = "Department name must be between 2 and 50 characters")
    private String nomDepart;

    // Constructors
    public DepartementDTO() {
    }

    public DepartementDTO(Integer idDepart, String nomDepart) {
        this.idDepart = idDepart;
        this.nomDepart = nomDepart;
    }

    // Getters and Setters
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