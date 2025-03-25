package tn.esprit.spring.kaddem.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.kaddem.entities.ContratDTO;

import java.util.Date;
import java.util.List;


@Repository
public interface ContratRepository extends CrudRepository<ContratDTO, Integer> {

    @Query("SELECT count(c) FROM ContratDTO c where ((c.archive=true) and  ((c.dateDebutContrat BETWEEN :startDate AND :endDate)) or(c.dateFinContrat BETWEEN :startDate AND :endDate))")
public Integer getnbContratsValides(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

public List<ContratDTO> findAll();
public ContratDTO findByIdContrat(Integer idContrat);
}
