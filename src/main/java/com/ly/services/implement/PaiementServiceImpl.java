package com.ly.services.implement;

import java.util.List;

import com.ly.entity.Paiement;
import com.ly.repository.implement.PaiementRepositoryImpl;
import com.ly.services.interfaces.PaiementService;

public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepositoryImpl paiementRepositoryImpl;

    public PaiementServiceImpl(PaiementRepositoryImpl paiementRepositoryImpl) {
        this.paiementRepositoryImpl = paiementRepositoryImpl;
    }

    @Override
    
    public void add(Paiement paiement){
        paiementRepositoryImpl.insert(paiement);
    }
    @Override

    public List<Paiement> getAll(){
        return paiementRepositoryImpl.lister();
    }

    

}
