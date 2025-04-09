package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j

@Service
public class DepartementServiceImpl implements IDepartementService{
	@Autowired
	DepartementRepository departementRepository;
	public List<Departement> retrieveAllDepartements(){
		return (List<Departement>) departementRepository.findAll();
	}

	public Departement addDepartement (Departement d){
		return departementRepository.save(d);
	}

	public   Departement updateDepartement (Departement d){
		return departementRepository.save(d);
	}

	public Departement retrieveDepartement(Integer idDepart) {
		Optional<Departement> departementOptional = departementRepository.findById(idDepart);
		if (departementOptional.isPresent()) {
			return departementOptional.get();
		} else {
			throw new EntityNotFoundException("Departement not found with id: " + idDepart);
		}
	}

	public void deleteDepartement(Integer idDepartement) {
		Optional<Departement> departementOptional = departementRepository.findById(idDepartement);
		if (departementOptional.isPresent()) {
			departementRepository.delete(departementOptional.get());
		} else {
			throw new EntityNotFoundException("Departement not found with id: " + idDepartement);
		}
	}



}
