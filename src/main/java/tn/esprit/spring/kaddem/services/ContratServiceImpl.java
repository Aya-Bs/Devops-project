package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.ContratDTO;
import tn.esprit.spring.kaddem.entities.EtudiantDTO;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ContratServiceImpl implements IContratService{

@Autowired  // NOSONAR
ContratRepository contratRepository;
@Autowired   // NOSONAR
	EtudiantRepository etudiantRepository;

	public List<ContratDTO> retrieveAllContrats(){
		return  contratRepository.findAll();
	}

	public ContratDTO updateContrat (ContratDTO  ce){
		return contratRepository.save(ce);
	}

	public  ContratDTO addContrat (ContratDTO ce){
		return contratRepository.save(ce);
	}

	public ContratDTO retrieveContrat (Integer  idContrat){
		return contratRepository.findById(idContrat).orElse(null);
	}

	public  void removeContrat(Integer idContrat){
		ContratDTO c=retrieveContrat(idContrat);
		contratRepository.delete(c);
	}



	public ContratDTO affectContratToEtudiant (Integer idContrat, String nomE, String prenomE){
		EtudiantDTO e=etudiantRepository.findByNomEAndPrenomE(nomE, prenomE);
		ContratDTO ce=contratRepository.findByIdContrat(idContrat);
		Set<ContratDTO> contrats= e.getContrats();
		Integer nbContratssActifs=0;   // NOSONAR
		if (contrats.size()!=0) {    // NOSONAR
			for (ContratDTO contrat : contrats) {
				if (((contrat.getArchive())!=null)&& ((contrat.getArchive())!=false))  {  // NOSONAR
					nbContratssActifs++;
				}
			}
		}
		if (nbContratssActifs<=4){
		ce.setEtudiantDTO(e);
		contratRepository.save(ce);}
		return ce;
	}

	public 	Integer nbContratsValides(Date startDate, Date endDate){
		return contratRepository.getnbContratsValides(startDate, endDate);
	}

	public void retrieveAndUpdateStatusContrat() {
		List<ContratDTO> contrats = contratRepository.findAll();
		List<ContratDTO> contrats15j = new ArrayList<>(); // Initialize the list
		List<ContratDTO> contratsAarchiver = new ArrayList<>(); // Initialize the list

		for (ContratDTO contrat : contrats) {
			Date dateSysteme = new Date();
			if (contrat.getArchive() == false) {   // NOSONAR
				long difference_In_Time = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();   // NOSONAR
				long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;   // NOSONAR

				if (difference_In_Days == 15) {
					contrats15j.add(contrat);
					log.info("Contrat : " + contrat);
				}
				if (difference_In_Days == 0) {
					contratsAarchiver.add(contrat);
					contrat.setArchive(true);
					contratRepository.save(contrat);
				}
			}
		}
	}

	public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate){
		float difference_In_Time = endDate.getTime() - startDate.getTime(); // NOSONAR
		float difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365; // NOSONAR
		float difference_In_months =difference_In_Days/30;   // NOSONAR
        List<ContratDTO> contrats=contratRepository.findAll();
		float chiffreAffaireEntreDeuxDates=0;
		for (ContratDTO contrat : contrats) {
			if (contrat.getSpecialite()== Specialite.IA){
				chiffreAffaireEntreDeuxDates+=(difference_In_months*300);
			} else if (contrat.getSpecialite()== Specialite.CLOUD) {
				chiffreAffaireEntreDeuxDates+=(difference_In_months*400);
			}
			else if (contrat.getSpecialite()== Specialite.RESEAUX) {
				chiffreAffaireEntreDeuxDates+=(difference_In_months*350);
			}
			else
			 {
				 chiffreAffaireEntreDeuxDates+=(difference_In_months*450);
			}
		}
		return chiffreAffaireEntreDeuxDates;


	}


}
