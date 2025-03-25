package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.ContratDTO;
import tn.esprit.spring.kaddem.entities.EquipeDTO;
import tn.esprit.spring.kaddem.entities.EtudiantDTO;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class EquipeServiceImpl implements IEquipeService{
	EquipeRepository equipeRepository;


	public List<EquipeDTO> retrieveAllEquipes(){
	return  (List<EquipeDTO>) equipeRepository.findAll();
	}
	public EquipeDTO addEquipe(EquipeDTO e){
		return (equipeRepository.save(e));
	}

	public  void deleteEquipe(Integer idEquipe){
		EquipeDTO e=retrieveEquipe(idEquipe);
		equipeRepository.delete(e);
	}

	public EquipeDTO retrieveEquipe(Integer equipeId){
		return equipeRepository.findById(equipeId).get();  // NOSONAR
	}

	public EquipeDTO updateEquipe(EquipeDTO e){
	return (	equipeRepository.save(e));
	}

	public void evoluerEquipes(){     // NOSONAR
		List<EquipeDTO> equipeDTOS = (List<EquipeDTO>) equipeRepository.findAll();
		for (EquipeDTO equipeDTO : equipeDTOS) {   // NOSONAR
			if ((equipeDTO.getNiveau().equals(Niveau.JUNIOR)) || (equipeDTO.getNiveau().equals(Niveau.SENIOR))) {
				List<EtudiantDTO> etudiantDTOS = (List<EtudiantDTO>) equipeDTO.getEtudiants();  // NOSONAR
				Integer nbEtudiantsAvecContratsActifs=0;  // NOSONAR
				for (EtudiantDTO etudiantDTO : etudiantDTOS) {
					Set<ContratDTO> contrats = etudiantDTO.getContrats();
					for (ContratDTO contrat : contrats) {  // NOSONAR
						Date dateSysteme = new Date();
						long difference_In_Time = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();   // NOSONAR
						long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));    // NOSONAR
						if ((contrat.getArchive() == false) && (difference_In_Years > 1)) {   // NOSONAR
							nbEtudiantsAvecContratsActifs++;
							break;
						}
						if (nbEtudiantsAvecContratsActifs >= 3) break;
					}
				}
					if (nbEtudiantsAvecContratsActifs >= 3){
						if (equipeDTO.getNiveau().equals(Niveau.JUNIOR)){
							equipeDTO.setNiveau(Niveau.SENIOR);
							equipeRepository.save(equipeDTO);
							break;
						}
						if (equipeDTO.getNiveau().equals(Niveau.SENIOR)){
							equipeDTO.setNiveau(Niveau.EXPERT);
							equipeRepository.save(equipeDTO);
							break;
						}
				}
			}

		}

	}
}