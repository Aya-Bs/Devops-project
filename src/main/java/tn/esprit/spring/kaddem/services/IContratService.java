package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.ContratDTO;

import java.util.Date;
import java.util.List;

public interface IContratService {
    public List<ContratDTO> retrieveAllContrats();

    public ContratDTO updateContrat (ContratDTO  ce);

    public  ContratDTO addContrat (ContratDTO ce);

    public ContratDTO retrieveContrat (Integer  idContrat);

    public  void removeContrat(Integer idContrat);

    public ContratDTO affectContratToEtudiant (Integer idContrat, String nomE, String prenomE);

        public 	Integer nbContratsValides(Date startDate, Date endDate);


    public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate);

    public void retrieveAndUpdateStatusContrat();
}

