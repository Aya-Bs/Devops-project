package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class EtudiantServiceImpl implements IEtudiantService {

	@Autowired
	private EtudiantRepository etudiantRepository;

	@Autowired
	private ContratRepository contratRepository;

	@Autowired
	private EquipeRepository equipeRepository;

	@Autowired
	private DepartementRepository departementRepository;

	@Override
	public List<Etudiant> retrieveAllEtudiants() {
		log.info("Récupération de tous les étudiants");
		List<Etudiant> etudiants = new ArrayList<>();
		etudiantRepository.findAll().forEach(etudiants::add);
		log.info("Nombre d'étudiants récupérés : {}", etudiants.size());
		return etudiants;
	}

	@Override
	public Etudiant addEtudiant(Etudiant e) {
		if (e == null) {
			log.error("Tentative d'ajout d'un étudiant null");
			throw new IllegalArgumentException("L'étudiant ne peut pas être null");
		}
		log.info("Ajout d'un nouvel étudiant : {} {}", e.getNomE(), e.getPrenomE());
		Etudiant savedEtudiant = etudiantRepository.save(e);
		log.info("Étudiant ajouté avec succès, ID : {}", savedEtudiant.getIdEtudiant());
		return savedEtudiant;
	}

	@Override
	public Etudiant updateEtudiant(Etudiant e) {
		if (e == null) {
			log.error("Tentative de mise à jour d'un étudiant null");
			throw new IllegalArgumentException("L'étudiant ne peut pas être null");
		}
		if (e.getIdEtudiant() == null) {
			log.error("Tentative de mise à jour d'un étudiant sans ID");
			throw new IllegalArgumentException("L'ID de l'étudiant ne peut pas être null§§");
		}
		log.info("Mise à jour de l'étudiant ID : {}", e.getIdEtudiant());
		return etudiantRepository.save(e);
	}

	@Override
	public Etudiant retrieveEtudiant(Integer idEtudiant) {
		if (idEtudiant == null) {
			log.error("Tentative de récupération d'un étudiant avec un ID null");
			throw new IllegalArgumentException("Lid de l'étudiant ne peut pas être null");
		}
		log.info("Récupération de l'étudiant ID : {}", idEtudiant);
		return etudiantRepository.findById(idEtudiant)
				.orElseThrow(() -> {
					log.error("Étudiant non trouvé avec l'ID : {}", idEtudiant);
					return new NoSuchElementException("Étudiant non trouvé avec l'ID : " + idEtudiant);
				});
	}

	@Override
	public void removeEtudiant(Integer idEtudiant) {
		if (idEtudiant == null) {
			log.error("Tentative de suppression d'un étudiant avec un ID null");
			throw new IllegalArgumentException("lid de l'étudiant ne peut pas être null");
		}
		log.info("Suppression de l'étudiant ID : {}", idEtudiant);
		Etudiant e = retrieveEtudiant(idEtudiant);
		etudiantRepository.delete(e);
		log.info("Étudiant supprimé avec succès");
	}

	@Override
	public void assignEtudiantToDepartement(Integer etudiantId, Integer departementId) {
		if (etudiantId == null || departementId == null) {
			log.error("Tentative d'affectation avec des IDs null");
			throw new IllegalArgumentException("Les IDs ne peuvent pas être null");
		}
		log.info("Affectation de l'étudiant {} au département {}", etudiantId, departementId);
		
		Etudiant etudiant = etudiantRepository.findById(etudiantId)
				.orElseThrow(() -> {
					log.error("Étudiant non trouvé avec l'ID : {}", etudiantId);
					return new NoSuchElementException("Étudiant non trouvé");
				});

		Departement departement = departementRepository.findById(departementId)
				.orElseThrow(() -> {
					log.error("Département non trouvé avec l'ID : {}", departementId);
					return new NoSuchElementException("Département non trouvé");
				});

		etudiant.setDepartement(departement);
		etudiantRepository.save(etudiant);
		log.info("Affectation réussie");
	}

	@Override
	@Transactional
	public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe) {
		if (e == null || idContrat == null || idEquipe == null) {
			log.error("Tentative d'affectation avec des paramètres null");
			throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
		}
		log.info("Ajout et affectation de l'étudiant au contrat {} et à l'équipe {}", idContrat, idEquipe);

		Contrat contrat = contratRepository.findById(idContrat)
				.orElseThrow(() -> {
					log.error("Contrat non trouvé avec l'ID : {}", idContrat);
					return new NoSuchElementException("Contrat non trouvé");
				});

		Equipe equipe = equipeRepository.findById(idEquipe)
				.orElseThrow(() -> {
					log.error("Équipe non trouvée avec l'ID : {}", idEquipe);
					return new NoSuchElementException("Équipe non trouvée");
				});

		contrat.setEtudiant(e);
		equipe.getEtudiants().add(e);
		
		log.info("Affectation réussie");
		return e;
	}

	@Override
	public List<Etudiant> getEtudiantsByDepartement(Integer idDepartement) {
		if (idDepartement == null) {
			log.error("Tentative de récupération des étudiants avec un ID de département null");
			throw new IllegalArgumentException("L'ID du département ne peut pas être null");
		}
		log.info("Récupération des étudiants du département ID : {}", idDepartement);
		List<Etudiant> etudiants = etudiantRepository.findEtudiantsByDepartement_IdDepart(idDepartement);
		log.info("Nombre d'étudiants trouvés : {}", etudiants.size());
		return etudiants;
	}
}
//etet
