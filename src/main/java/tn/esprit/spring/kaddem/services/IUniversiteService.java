package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.DepartementDTO;
import tn.esprit.spring.kaddem.entities.UniversiteDTO;

import java.util.List;
import java.util.Set;

public interface IUniversiteService {
   public List<UniversiteDTO> retrieveAllUniversites();

    UniversiteDTO addUniversite (UniversiteDTO u);

    UniversiteDTO updateUniversite (UniversiteDTO u);

    UniversiteDTO retrieveUniversite (Integer idUniversite);

    public  void deleteUniversite(Integer idUniversite);

    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement);

    public Set<DepartementDTO> retrieveDepartementsByUniversite(Integer idUniversite);


}
