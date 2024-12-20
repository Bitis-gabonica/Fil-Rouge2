package com.ly.repository.interfaces;

import java.util.List;

import com.ly.entity.DemandeDette;
import com.ly.repository.Repository;

public interface DemandeDetteRepository extends Repository<DemandeDette> {

        public List<DemandeDette> listerDemandeDetteParStatus(boolean status);


}
