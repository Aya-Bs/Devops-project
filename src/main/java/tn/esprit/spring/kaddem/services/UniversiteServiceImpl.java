package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.DepartementDTO;
import tn.esprit.spring.kaddem.entities.UniversiteDTO;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.List;
import java.util.Set;

@Service
public class UniversiteServiceImpl implements IUniversiteService{
@Autowired
    UniversiteRepository universiteRepository;
@Autowired
    DepartementRepository departementRepository;
    public UniversiteServiceImpl() {
        // TODO Auto-generated constructor stub
    }
  public   List<UniversiteDTO> retrieveAllUniversites(){
return (List<UniversiteDTO>) universiteRepository.findAll();
    }

 public UniversiteDTO addUniversite (UniversiteDTO u){
return  (universiteRepository.save(u));
    }

 public UniversiteDTO updateUniversite (UniversiteDTO u){
     return  (universiteRepository.save(u));
    }

  public UniversiteDTO retrieveUniversite (Integer idUniversite){
UniversiteDTO u = universiteRepository.findById(idUniversite).get();
return  u;
    }
    public  void deleteUniversite(Integer idUniversite){
        universiteRepository.delete(retrieveUniversite(idUniversite));
    }

    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement){
        UniversiteDTO u= universiteRepository.findById(idUniversite).orElse(null);
        DepartementDTO d= departementRepository.findById(idDepartement).orElse(null);
        u.getDepartements().add(d);
        universiteRepository.save(u);
    }

    public Set<DepartementDTO> retrieveDepartementsByUniversite(Integer idUniversite){
UniversiteDTO u=universiteRepository.findById(idUniversite).orElse(null);
return u.getDepartements();
    }
}
