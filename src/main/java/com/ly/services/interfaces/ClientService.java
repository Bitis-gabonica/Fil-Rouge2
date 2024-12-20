package com.ly.services.interfaces;

import java.util.List;

import com.ly.entity.Client;
import com.ly.entity.Dette;
import com.ly.services.ServicesI;

public interface ClientService extends ServicesI<Client>{

      public List<Client> getAllClientsUsers();
      public List<Client> getAllClientsNotUsers();
      public Client findClientbyTel(String tel);
      public List<Dette> getClientsDetteNonSolde(Client client);
}
