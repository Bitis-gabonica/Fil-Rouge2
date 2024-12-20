package com.ly.repository.implement;

import java.util.List;
import java.util.stream.Collectors;

import com.ly.entity.Client;
import com.ly.repository.interfaces.ClientRepository;

public class ClientRepositoryListImpl extends RepositoryListImpl<Client> implements ClientRepository {

    @Override
    public Client getClientByPhone(String tel){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTel().compareTo(tel)==0) {
                return list.get(i);
            }
        }return null;
    }
    @Override
    public List<Client>  listClientsUser(){
        return list.stream().filter(client -> client.getUser() !=null).collect(Collectors.toList());
    }

    @Override
    public List<Client>  listClientsNotUser(){
        return list.stream().filter(client -> client.getUser()==null).collect(Collectors.toList());
    }

    }


