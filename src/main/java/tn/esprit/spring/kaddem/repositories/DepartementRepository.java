package tn.esprit.spring.kaddem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.kaddem.entities.DepartementDTO;

@Repository
public interface DepartementRepository extends CrudRepository<DepartementDTO,Integer> {



}
