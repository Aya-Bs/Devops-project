package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.dto.ContratDTO;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.services.IContratService;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contrat")
public class ContratRestController {

	IContratService contratService;
	IEtudiantService etudiantService;
	// http://localhost:8089/Kaddem/contrat/retrieve-all-contrats
	@GetMapping("/retrieve-all-contrats")
	public List<Contrat> getContrats() {
		return contratService.retrieveAllContrats();

	}
	// http://localhost:8089/Kaddem/contrat/retrieve-contrat/8
	@GetMapping("/retrieve-contrat/{contrat-id}")
	public Contrat retrieveContrat(@PathVariable("contrat-id") Integer contratId) {
		return contratService.retrieveContrat(contratId);
	}

	// http://localhost:8089/Kaddem/econtrat/add-contrat
	@PostMapping("/add-contrat")
	public Contrat addContrat(@Valid @RequestBody ContratDTO contratDTO) {
		// Convert DTO to Entity
		Contrat contrat = new Contrat();
		contrat.setDateDebutContrat(contratDTO.getDateDebutContrat());
		contrat.setDateFinContrat(contratDTO.getDateFinContrat());
		contrat.setSpecialite(contratDTO.getSpecialite());
		contrat.setArchive(contratDTO.getArchive());
		contrat.setMontantContrat(contratDTO.getMontantContrat());

		// Fetch Etudiant by ID (handle in service layer)
		Etudiant etudiant = etudiantService.retrieveEtudiant(contratDTO.getEtudiantId());
		contrat.setEtudiant(etudiant);

		return contratService.addContrat(contrat);
	}


	// http://localhost:8089/Kaddem/contrat/remove-contrat/1
	@DeleteMapping("/remove-contrat/{contrat-id}")
	public void removeContrat(@PathVariable("contrat-id") Integer contratId) {
		contratService.removeContrat(contratId);
	}


	// http://localhost:8089/Kaddem/contrat/update-contrat
	@PutMapping("/update-contrat")
	public Contrat updateContrat(@Valid @RequestBody ContratDTO contratDTO) {
		// 1. Fetch existing contract
		Contrat existingContrat = contratService.retrieveContrat(contratDTO.getIdContrat());

		// 2. Update only allowed fields from DTO
		existingContrat.setDateDebutContrat(contratDTO.getDateDebutContrat());
		existingContrat.setDateFinContrat(contratDTO.getDateFinContrat());
		existingContrat.setSpecialite(contratDTO.getSpecialite());
		existingContrat.setArchive(contratDTO.getArchive());
		existingContrat.setMontantContrat(contratDTO.getMontantContrat());

		// 3. Handle student update
		if (!existingContrat.getEtudiant().getIdEtudiant().equals(contratDTO.getEtudiantId())) {
			Etudiant newEtudiant = etudiantService.retrieveEtudiant(contratDTO.getEtudiantId());
			existingContrat.setEtudiant(newEtudiant);
		}

		return contratService.updateContrat(existingContrat);
	}


	@PutMapping(value = "/assignContratToEtudiant/{idContrat}/{nomE}/{prenomE}")
	public Contrat assignContratToEtudiant (@PathVariable Integer idContrat, @PathVariable String nomE, @PathVariable String prenomE){
		return 	(contratService.affectContratToEtudiant(idContrat, nomE, prenomE));
	}

	//The most common ISO Date Format yyyy-MM-dd — for example, "2000-10-31".
		@GetMapping(value = "/getnbContratsValides/{startDate}/{endDate}")
		public Integer getnbContratsValides(@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
										  @PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

			return contratService.nbContratsValides(startDate, endDate);
		}

    //Only no-arg methods may be annotated with @Scheduled
    @Scheduled(cron="0 0 13 * * *")//(cron="0 0 13 * * ?")(fixedRate =21600)
	@PutMapping(value = "/majStatusContrat")
	public void majStatusContrat (){
		contratService.retrieveAndUpdateStatusContrat();

	}


	@GetMapping("/calculChiffreAffaireEntreDeuxDate/{startDate}/{endDate}")
	public float calculChiffreAffaireEntreDeuxDates(@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
	@PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

		return contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);
	}
}


