package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Departement;

import java.util.List;

 public interface IDepartementService {
     List<Departement> retrieveAllDepartements();

     Departement addDepartement (Departement d);

       Departement updateDepartement (Departement d);

      Departement retrieveDepartement (Integer idDepart);

      void deleteDepartement(Integer idDepartement);

}
