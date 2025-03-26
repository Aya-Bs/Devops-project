package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EquipeServiceImpl implements IEquipeService{

	private final EquipeRepository equipeRepository;

	@Autowired
	public EquipeServiceImpl(EquipeRepository equipeRepository) {
		this.equipeRepository = equipeRepository;
	}



	public List<Equipe> retrieveAllEquipes(){
	return  (List<Equipe>) equipeRepository.findAll();
	}
	public Equipe addEquipe(Equipe e){
		return (equipeRepository.save(e));
	}

	public  void deleteEquipe(Integer idEquipe){
		Equipe e=retrieveEquipe(idEquipe);
		equipeRepository.delete(e);
	}

	public Equipe retrieveEquipe(Integer equipeId){
		return equipeRepository.findById(equipeId)
				.isPresent() ? equipeRepository.findById(equipeId).get() : null;
	}

	public Equipe updateEquipe(Equipe e){
	return (	equipeRepository.save(e));
	}


	public void evoluerEquipes() {
		List<Equipe> equipes = (List<Equipe>) equipeRepository.findAll();
		for (Equipe equipe : equipes) {
			if (isEligibleForEvolution(equipe)) {
				evolveEquipe(equipe);
			}
		}
	}

	private boolean isEligibleForEvolution(Equipe equipe) {
		return equipe.getNiveau().equals(Niveau.JUNIOR) || equipe.getNiveau().equals(Niveau.SENIOR);
	}

	private void evolveEquipe(Equipe equipe) {
		int nbEtudiantsAvecContratsActifs = countEtudiantsWithActiveContracts(equipe);
		if (nbEtudiantsAvecContratsActifs >= 3) {
			equipe.setNiveau(getNextNiveau(equipe.getNiveau()));
			equipeRepository.save(equipe);
		}
	}

	private int countEtudiantsWithActiveContracts(Equipe equipe) {
		int count = 0;
		for (Etudiant etudiant : equipe.getEtudiants()) {
			if (hasActiveContract(etudiant)) {
				count++;
				if (count >= 3) break;
			}
		}
		return count;
	}

	private boolean hasActiveContract(Etudiant etudiant) {
		for (Contrat contrat : etudiant.getContrats()) {
			if (!contrat.getArchive() && isContractOlderThanOneYear(contrat)) {
				return true;
			}
		}
		return false;
	}

	private boolean isContractOlderThanOneYear(Contrat contrat) {
		Date dateSysteme = new Date();
		long differenceTime = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();
		long differenceYears = (differenceTime / (1000L * 60 * 60 * 24 * 365));
		return differenceYears > 1;
	}

	private Niveau getNextNiveau(Niveau niveau) {
		switch (niveau) {
			case JUNIOR:
				return Niveau.SENIOR;
			case SENIOR:
				return Niveau.EXPERT;
			default:
				return niveau;
		}


	}
}