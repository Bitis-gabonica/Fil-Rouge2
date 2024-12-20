package com.ly.repository.implement;

import java.util.List;
import java.util.stream.Collectors;

import com.ly.entity.Client;
import com.ly.entity.Dette;
import com.ly.repository.interfaces.DetteRepository;

public class DetteRepositoryListImpl extends RepositoryListImpl<Dette> implements DetteRepository  {

    

@Override
public Dette findDette(int id) {
    
   for (int i = 0; i <list.size() ; i++) {
       if (list.get(i).getId()==id) {
        return list.get(i);
       }
   }return  null;
}

@Override
public List<Dette> listDetteNonSolde(){
    return list.stream().filter(dette->dette.getMontantRestant()==0).collect(Collectors.toList());
}
@Override
public List<Dette> listDetteNonSolde(Client client){

    return client.getDettes().stream().filter(dette->dette.getMontantRestant()==0).collect(Collectors.toList());
}

@Override
public  List<Dette> listDettesSoldes(){
    return list.stream().filter(dette->dette.getMontantRestant()!=0).collect(Collectors.toList());
    
}




}
