package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import tn.esprit.spring.kaddem.entities.ContratDTO;
import tn.esprit.spring.kaddem.entities.DepartementDTO;
import tn.esprit.spring.kaddem.entities.EquipeDTO;
import tn.esprit.spring.kaddem.entities.EtudiantDTO;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class EtudiantServiceImpl implements IEtudiantService{
	@Autowired
	EtudiantRepository etudiantRepository ;
	@Autowired
	ContratRepository contratRepository;
	@Autowired
	EquipeRepository equipeRepository;
    @Autowired
    DepartementRepository departementRepository;
	public List<EtudiantDTO> retrieveAllEtudiants(){
	return (List<EtudiantDTO>) etudiantRepository.findAll();
	}

	public EtudiantDTO addEtudiant (EtudiantDTO e){
		return etudiantRepository.save(e);
	}

	public EtudiantDTO updateEtudiant (EtudiantDTO e){
		return etudiantRepository.save(e);
	}

	public EtudiantDTO retrieveEtudiant(Integer  idEtudiant){
		return etudiantRepository.findById(idEtudiant).get();
	}

	public void removeEtudiant(Integer idEtudiant){
	EtudiantDTO e=retrieveEtudiant(idEtudiant);
	etudiantRepository.delete(e);
	}

	public void assignEtudiantToDepartement (Integer etudiantId, Integer departementId){
        EtudiantDTO etudiantDTO = etudiantRepository.findById(etudiantId).orElse(null);
        DepartementDTO departementDTO = departementRepository.findById(departementId).orElse(null);
        etudiantDTO.setDepartementDTO(departementDTO);
        etudiantRepository.save(etudiantDTO);
	}
	@Transactional
	public EtudiantDTO addAndAssignEtudiantToEquipeAndContract(EtudiantDTO e, Integer idContrat, Integer idEquipe){
		ContratDTO c=contratRepository.findById(idContrat).orElse(null);
		EquipeDTO eq=equipeRepository.findById(idEquipe).orElse(null);
		c.setEtudiantDTO(e);
		eq.getEtudiants().add(e);
return e;
	}

	public 	List<EtudiantDTO> getEtudiantsByDepartementDTO (Integer idDepartement){
return  etudiantRepository.findEtudiantsByDepartementDTO_IdDepart((idDepartement));
	}
}
