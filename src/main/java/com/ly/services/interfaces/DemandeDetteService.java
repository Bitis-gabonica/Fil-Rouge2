package com.ly.services.interfaces;

import java.util.List;

import com.ly.entity.DemandeDette;
import com.ly.services.ServicesI;

public interface DemandeDetteService extends ServicesI<com.ly.entity.DemandeDette> {

        public List<DemandeDette> getAllDemandeDettesByStatus(Boolean status);

}
