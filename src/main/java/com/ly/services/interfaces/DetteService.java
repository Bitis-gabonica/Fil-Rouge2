package com.ly.services.interfaces;

import java.util.List;

import com.ly.entity.Client;
import com.ly.entity.Dette;
import com.ly.services.ServicesI;

public interface DetteService extends ServicesI<Dette> {

        public List<Dette> getAllDetteNonSolde();
        public Dette findDetteId(int id);
        public List<Dette> getAllDetteNonSolde(Client client);
        public void ArchiverAllDetteNonSolde(List<Dette> dettes);
        public List<Dette> getDetteSolde();


}
