package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.DepartementDTO;

import java.util.List;

public interface IDepartementService {
    public List<DepartementDTO> retrieveAllDepartements();

    public DepartementDTO addDepartement (DepartementDTO d);

    public   DepartementDTO updateDepartement (DepartementDTO d);

    public  DepartementDTO retrieveDepartement (Integer idDepart);

    public  void deleteDepartement(Integer idDepartement);

}
