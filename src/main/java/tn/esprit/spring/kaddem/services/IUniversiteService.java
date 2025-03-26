package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;

import java.util.List;
import java.util.Set;

public interface IUniversiteService {
    List<Universite> retrieveAllUniversites();

    Universite addUniversite (Universite  u);

    Universite updateUniversite (Universite  u);

    Universite retrieveUniversite (Integer idUniversite);

      void deleteUniversite(Integer idUniversite);

     void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement);

     Set<Departement> retrieveDepartementsByUniversite(Integer idUniversite);


}
