package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.EquipeDTO;

import java.util.List;

public interface IEquipeService {
    public List<EquipeDTO> retrieveAllEquipes();
    public EquipeDTO addEquipe(EquipeDTO e);
    public  void deleteEquipe(Integer idEquipe);
    public EquipeDTO updateEquipe(EquipeDTO e);
    public EquipeDTO retrieveEquipe(Integer equipeId);
    public void evoluerEquipes();
}
