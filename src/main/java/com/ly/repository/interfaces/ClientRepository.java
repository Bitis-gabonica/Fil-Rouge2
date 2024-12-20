package com.ly.repository.interfaces;

import java.util.List;

import com.ly.entity.Client;
import com.ly.repository.Repository;



public interface ClientRepository extends Repository<Client> {

   public List<Client>  listClientsUser();
   public List<Client>  listClientsNotUser();
   public Client getClientByPhone(String tel);


}
