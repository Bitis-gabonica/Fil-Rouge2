package com.ly.services.implement;

import java.util.List;
import java.util.stream.Collectors;

import com.ly.entity.Client;
import com.ly.entity.Dette;
import com.ly.repository.interfaces.ClientRepository;
import com.ly.services.interfaces.ClientService;

public class ClientServiceImpl implements ClientService {
    
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void add(Client client){
        clientRepository.insert(client);
    }
    
    @Override
    public List<Client> getAll(){
       return  clientRepository.lister();
    }
    @Override
    public List<Client> getAllClientsUsers(){
        return  clientRepository.listClientsUser();
     }

     @Override
     public List<Client> getAllClientsNotUsers(){
        return  clientRepository.listClientsNotUser();
     }

     @Override
    public Client findClientbyTel(String tel){
        return clientRepository.getClientByPhone(tel);
    }

    @Override
    public List<Dette> getClientsDetteNonSolde(Client client){
        return client.getDettes().stream().filter(dette->dette.getMontantRestant()!=0).collect(Collectors.toList());
    }




}
