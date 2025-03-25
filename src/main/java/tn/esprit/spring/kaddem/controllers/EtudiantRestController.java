package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.EtudiantDTO;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")
public class EtudiantRestController {
	IEtudiantService etudiantService;
	// http://localhost:8089/Kaddem/etudiant/retrieve-all-etudiants
	@GetMapping("/retrieve-all-etudiants")
	public List<EtudiantDTO> getEtudiants() {
		return etudiantService.retrieveAllEtudiants();
	}
	// http://localhost:8089/Kaddem/etudiant/retrieve-etudiant/8
	@GetMapping("/retrieve-etudiant/{etudiant-id}")
	public EtudiantDTO retrieveEtudiant(@PathVariable("etudiant-id") Integer etudiantId) {
		return etudiantService.retrieveEtudiant(etudiantId);
	}

	// http://localhost:8089/Kaddem/etudiant/add-etudiant
	@PostMapping("/add-etudiant")
	public EtudiantDTO addEtudiant(@RequestBody EtudiantDTO e) {  // NOSONAR
		return etudiantService.addEtudiant(e);
	}

	// http://localhost:8089/Kaddem/etudiant/remove-etudiant/1
	@DeleteMapping("/remove-etudiant/{etudiant-id}")
	public void removeEtudiant(@PathVariable("etudiant-id") Integer etudiantId) {
		etudiantService.removeEtudiant(etudiantId);
	}

	// http://localhost:8089/Kaddem/etudiant/update-etudiant
	@PutMapping("/update-etudiant")
	public EtudiantDTO updateEtudiant(@RequestBody EtudiantDTO e) {  // NOSONAR
		return etudiantService.updateEtudiant(e);


	}

	//@PutMapping("/affecter-etudiant-departement")
	@PutMapping(value="/affecter-etudiant-departement/{etudiantId}/{departementId}")
	public void affecterEtudiantToDepartement(@PathVariable("etudiantId") Integer etudiantId, @PathVariable("departementId")Integer departementId){
		etudiantService.assignEtudiantToDepartement(etudiantId, departementId);
	}
	//addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe)
	/* Ajouter un étudiant tout en lui affectant un contrat et une équipe */
	@PostMapping("/add-assign-Etudiant/{idContrat}/{idEquipe}")
	public EtudiantDTO addEtudiantWithEquipeAndContract(@RequestBody EtudiantDTO e, @PathVariable("idContrat") Integer idContrat, @PathVariable("idEquipe") Integer idEquipe) {  // NOSONAR
		return etudiantService.addAndAssignEtudiantToEquipeAndContract(e,idContrat,idEquipe);
	}

	@GetMapping(value = "/getEtudiantsByDepartement/{idDepartement}")
	public List<EtudiantDTO> getEtudiantsParDepartement(@PathVariable("idDepartement") Integer idDepartement) {

		return etudiantService.getEtudiantsByDepartementDTO(idDepartement);
	}

}