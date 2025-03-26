	package tn.esprit.spring.kaddem.services;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	import lombok.extern.slf4j.Slf4j;

	import tn.esprit.spring.kaddem.entities.Departement;
	import tn.esprit.spring.kaddem.entities.Etudiant;
	import tn.esprit.spring.kaddem.repositories.ContratRepository;
	import tn.esprit.spring.kaddem.repositories.DepartementRepository;
	import tn.esprit.spring.kaddem.repositories.EquipeRepository;
	import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

	import javax.transaction.Transactional;
	import java.util.List;

	@Service
	@Slf4j
	public class EtudiantServiceImpl implements IEtudiantService{
		public final EtudiantRepository etudiantRepository ;
		public final ContratRepository contratRepository;
		public final EquipeRepository equipeRepository;
		public final DepartementRepository departementRepository;

		@Autowired
		public EtudiantServiceImpl(EtudiantRepository etudiantRepository,ContratRepository contratRepository,EquipeRepository equipeRepository,DepartementRepository departementRepository) {
			this.etudiantRepository = etudiantRepository;
			this.contratRepository=contratRepository;
			this.equipeRepository=equipeRepository;
			this.departementRepository=departementRepository;
		}
		public List<Etudiant> retrieveAllEtudiants(){
		return (List<Etudiant>) etudiantRepository.findAll();
		}

		public Etudiant addEtudiant (Etudiant e){
			return etudiantRepository.save(e);
		}

		public Etudiant updateEtudiant (Etudiant e){
			return etudiantRepository.save(e);
		}

		public Etudiant retrieveEtudiant(Integer idEtudiant){
			return etudiantRepository.findById(idEtudiant)
					.isPresent() ? etudiantRepository.findById(idEtudiant).get() : null;
		}

		public void removeEtudiant(Integer idEtudiant){
		Etudiant e=retrieveEtudiant(idEtudiant);
		etudiantRepository.delete(e);
		}

		public void assignEtudiantToDepartement (Integer etudiantId, Integer departementId){
			etudiantRepository.findById(etudiantId)
					.ifPresent(etudiant -> {
						Departement departement = departementRepository.findById(departementId).orElse(null);
						if (departement != null) {
							etudiant.setDepartement(departement);
							etudiantRepository.save(etudiant);
						}
					});
		}

		@Transactional
		public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe){
			contratRepository.findById(idContrat)
					.ifPresent(c -> c.setEtudiant(e));
			equipeRepository.findById(idEquipe)
					.ifPresent(eq -> eq.getEtudiants().add(e));
			return e;
		}

		public 	List<Etudiant> getEtudiantsByDepartement (Integer idDepartement){
	return  etudiantRepository.findEtudiantsByDepartement_IdDepart((idDepartement));
		}
	}
