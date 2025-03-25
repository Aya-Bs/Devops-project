package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.DepartementDTO;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.List;

@Slf4j

@Service
public class DepartementServiceImpl implements IDepartementService{

	@Autowired    // NOSONAR
	DepartementRepository departementRepository;

	public List<DepartementDTO> retrieveAllDepartements(){
		return (List<DepartementDTO>)departementRepository.findAll();
	}

	public DepartementDTO addDepartement (DepartementDTO d){
		return departementRepository.save(d);
	}

	public   DepartementDTO updateDepartement (DepartementDTO d){
		return departementRepository.save(d);
	}

	public  DepartementDTO retrieveDepartement (Integer idDepart){
		return departementRepository.findById(idDepart).get();    // NOSONAR
	}
	public  void deleteDepartement(Integer idDepartement){
		DepartementDTO d=retrieveDepartement(idDepartement);
		departementRepository.delete(d);
	}



}
