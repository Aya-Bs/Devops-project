package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.EtudiantDTO;

import java.util.List;

public interface IEtudiantService {
    public List<EtudiantDTO> retrieveAllEtudiants();

    public EtudiantDTO addEtudiant (EtudiantDTO e);

    public EtudiantDTO updateEtudiant (EtudiantDTO e);

    public EtudiantDTO retrieveEtudiant(Integer  idEtudiant);

    public void removeEtudiant(Integer idEtudiant);

    public void assignEtudiantToDepartement (Integer etudiantId, Integer departementId);

    public EtudiantDTO addAndAssignEtudiantToEquipeAndContract(EtudiantDTO e, Integer idContrat, Integer idEquipe);

    public 	List<EtudiantDTO> getEtudiantsByDepartementDTO (Integer idDepartement);
}
