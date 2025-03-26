package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
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
	private final ContratRepository contratRepository;
	private final EtudiantRepository etudiantRepository;

	@Autowired
	public ContratServiceImpl(ContratRepository contratRepository, EtudiantRepository etudiantRepository) {
		this.contratRepository = contratRepository;
		this.etudiantRepository = etudiantRepository;
	}

	public List<Contrat> retrieveAllContrats(){
		return  contratRepository.findAll();
	}

	public Contrat updateContrat (Contrat  ce){
		return contratRepository.save(ce);
	}

	public  Contrat addContrat (Contrat ce){
		return contratRepository.save(ce);
	}

	public Contrat retrieveContrat (Integer  idContrat){
		return contratRepository.findById(idContrat).orElse(null);
	}

	public  void removeContrat(Integer idContrat){
		Contrat c=retrieveContrat(idContrat);
		contratRepository.delete(c);
	}



	public Contrat affectContratToEtudiant (Integer idContrat, String nomE, String prenomE){
		Etudiant e=etudiantRepository.findByNomEAndPrenomE(nomE, prenomE);
		Contrat ce=contratRepository.findByIdContrat(idContrat);
		Set<Contrat> contrats= e.getContrats();
		long nbContratssActifs=0;
		if (!(contrats.isEmpty())) {
			for (Contrat contrat : contrats) {
				if (((contrat.getArchive())!=null)&& (contrat.getArchive())) {
					nbContratssActifs++;
				}
			}
		}
		if (nbContratssActifs<=4){
		ce.setEtudiant(e);
		contratRepository.save(ce);}
		return ce;
	}
	public 	Integer nbContratsValides(Date startDate, Date endDate){
		return contratRepository.getnbContratsValides(startDate, endDate);
	}

	public void retrieveAndUpdateStatusContrat(){
		List<Contrat>contrats=contratRepository.findAll();
		List<Contrat>contrats15j=new ArrayList<>();
		List<Contrat>contratsAarchiver=new ArrayList<>();
		for (Contrat contrat : contrats) {
			Date dateSysteme = new Date();
			if (!(contrat.getArchive())) {
				long differenceTime = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();
				long differenceDays = (differenceTime / (1000 * 60 * 60 * 24)) % 365;
				if (differenceDays==15){
					contrats15j.add(contrat);
				}
				if (differenceDays==0) {
					contratsAarchiver.add(contrat);
					contrat.setArchive(true);
					contratRepository.save(contrat);
				}
			}
		}
	}

	public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate){
		float differenceTime = (float) endDate.getTime() - startDate.getTime();
		float differenceDays = (differenceTime / (1000 * 60 * 60 * 24)) % 365;
		float differenceMonths =differenceDays/30;
        List<Contrat> contrats=contratRepository.findAll();
		float chiffreAffaireEntreDeuxDates=0;
		for (Contrat contrat : contrats) {
			if (contrat.getSpecialite()== Specialite.IA){
				chiffreAffaireEntreDeuxDates+=(differenceMonths*300);
			} else if (contrat.getSpecialite()== Specialite.CLOUD) {
				chiffreAffaireEntreDeuxDates+=(differenceMonths*400);
			}
			else if (contrat.getSpecialite()== Specialite.RESEAUX) {
				chiffreAffaireEntreDeuxDates+=(differenceMonths*350);
			}
			else
			 {
				 chiffreAffaireEntreDeuxDates+=(differenceMonths*450);
			}
		}
		return chiffreAffaireEntreDeuxDates;


	}


}
