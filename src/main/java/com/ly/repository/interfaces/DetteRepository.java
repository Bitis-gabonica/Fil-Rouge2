package com.ly.repository.interfaces;

import java.util.List;

import com.ly.entity.Client;
import com.ly.entity.Dette;
import com.ly.repository.Repository;

public interface DetteRepository extends Repository<Dette> {

    Dette findDette(int id);
    List<Dette> listDetteNonSolde();
    public List<Dette> listDetteNonSolde(Client client);
    List<Dette> listDettesSoldes();
}
