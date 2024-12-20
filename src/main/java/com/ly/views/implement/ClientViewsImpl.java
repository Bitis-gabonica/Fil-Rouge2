package com.ly.views.implement;
import java.util.List;
import java.util.Scanner;

import  com.ly.entity.Client;
import com.ly.entity.User;
import com.ly.services.interfaces.ClientService;
import com.ly.views.Interfaces.ClientViews;
import com.ly.views.Interfaces.UserViews;

public class ClientViewsImpl implements ClientViews {


    private final  Scanner sc;
    private final UserViews userViews;
    private final ClientService clientService;
  

    public ClientViewsImpl( Scanner sc, UserViews userViews,ClientService clientService) {
      
        this.sc = sc;
        this.userViews = userViews;
        this.clientService=clientService;
    }

    

    

   
    @Override
    public Client saisie(){
        System.out.println("Entrez surname");
        String surname=sc.next();
        String telephone=null;
        do {    
            System.out.println("Entrez telephone");
            telephone=sc.next();
            } while (clientService.findClientbyTel(telephone)!=null);
        System.out.println("Entrez addresse");
        String adresse=sc.next();
        System.out.println("Voulez vous creez un compte utilisateur au client?");
        System.out.println("1 oui/2 non");
        int choix=sc.nextInt();
        switch (choix) {
            case 1 -> {
                Client client=new Client(surname, telephone, adresse);
                User user=userViews.saisitUserClient(client);
                client.setUser(user);
                return client;
            }
            case 2 -> {
                Client client2=new Client(surname, telephone, adresse);
                return client2;
            }
            default -> {
            }
        }
return null;


    }

    @Override
    public void lister(List<Client> clients){
        for (int i = 0; i <clients.size(); i++) {
            Client client=clients.get(i);
            System.out.println(client);
        }
    }

    
    @Override
public void getClientByPhone(Client client){
    
    if(client!= null){
        System.out.println(client);
    }else{
        System.out.println("Le client n'existe pas");
    }

}

@Override
public String saisitTel(){
    System.out.println("Entrez le tel du client a rechercher");
    String tel2=sc.next();
    return tel2;
}
}
