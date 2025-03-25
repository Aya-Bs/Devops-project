package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.DepartementDTO;
import tn.esprit.spring.kaddem.entities.UniversiteDTO;
import tn.esprit.spring.kaddem.services.IUniversiteService;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/universite")
public class UniversiteRestController {
	IUniversiteService universiteService;
	// http://localhost:8089/Kaddem/universite/retrieve-all-universites
	@GetMapping("/retrieve-all-universites")
	public List<UniversiteDTO> getUniversites() {
		return universiteService.retrieveAllUniversites();
	}
	// http://localhost:8089/Kaddem/universite/retrieve-universite/8
	@GetMapping("/retrieve-universite/{universite-id}")
	public UniversiteDTO retrieveUniversite(@PathVariable("universite-id") Integer universiteId) {
		return universiteService.retrieveUniversite(universiteId);
	}

	// http://localhost:8089/Kaddem/universite/add-universite
	@PostMapping("/add-universite")
	public UniversiteDTO addUniversite(@RequestBody UniversiteDTO u) {
		return universiteService.addUniversite(u);
	}

	// http://localhost:8089/Kaddem/universite/remove-universite/1
	@DeleteMapping("/remove-universite/{universite-id}")
	public void removeUniversite(@PathVariable("universite-id") Integer universiteId) {
		universiteService.deleteUniversite(universiteId);
	}

	// http://localhost:8089/Kaddem/universite/update-universite
	@PutMapping("/update-universite")
	public UniversiteDTO updateUniversite(@RequestBody UniversiteDTO u) {
		return universiteService.updateUniversite(u);
	}

	//@PutMapping("/affecter-etudiant-departement")
	@PutMapping(value="/affecter-universite-departement/{universiteId}/{departementId}")
	public void affectertUniversiteToDepartement(@PathVariable("universiteId") Integer universiteId, @PathVariable("departementId")Integer departementId){
		universiteService.assignUniversiteToDepartement(universiteId, departementId);
	}

	@GetMapping(value = "/listerDepartementsUniversite/{idUniversite}")
	public Set<DepartementDTO> listerDepartementsUniversite(@PathVariable("idUniversite") Integer idUniversite) {

		return universiteService.retrieveDepartementsByUniversite(idUniversite);
	}

}