package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Etudiant;

import java.util.List;

 public interface IEtudiantService {
     List<Etudiant> retrieveAllEtudiants();

     Etudiant addEtudiant (Etudiant e);

     Etudiant updateEtudiant (Etudiant e);

     Etudiant retrieveEtudiant(Integer  idEtudiant);

     void removeEtudiant(Integer idEtudiant);

     void assignEtudiantToDepartement (Integer etudiantId, Integer departementId);

     Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe);

     	List<Etudiant> getEtudiantsByDepartement (Integer idDepartement);

}
