package com.ly.repository.implement;

import java.util.List;
import java.util.stream.Collectors;

import com.ly.entity.DemandeDette;
import com.ly.repository.interfaces.DemandeDetteRepository;

public class DemandeDetteRepositoryListImpl extends RepositoryListImpl<DemandeDette> implements DemandeDetteRepository {

    @Override
    public List<DemandeDette> listerDemandeDetteParStatus(boolean status){
        return list.stream().filter(DemandeDette->DemandeDette.isStatus()==status).collect(Collectors.toList());
    }

}
