package com.ly.services.implement;

import java.util.List;

import com.ly.entity.DemandeDette;
import com.ly.repository.interfaces.DemandeDetteRepository;
import com.ly.services.interfaces.DemandeDetteService;

public class DemandeDetteServiceImpl implements DemandeDetteService {

    private final DemandeDetteRepository demandeDetteRepository;

    public DemandeDetteServiceImpl(DemandeDetteRepository demandeDetteRepository) {
        this.demandeDetteRepository = demandeDetteRepository;
    }

    @Override

     public void add(DemandeDette demandeDette){
        demandeDetteRepository.insert(demandeDette);
    }
    @Override

      public List<DemandeDette> getAll(){
       return  demandeDetteRepository.lister();
    }
    @Override

    public List<DemandeDette> getAllDemandeDettesByStatus(Boolean status){
        return  demandeDetteRepository.listerDemandeDetteParStatus(status);
     }
    

}
