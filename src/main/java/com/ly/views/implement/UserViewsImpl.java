package com.ly.views.implement;

import java.util.List;
import java.util.Scanner;

import com.ly.entity.Client;
import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.views.Interfaces.UserViews;

public class UserViewsImpl implements UserViews {

private final Scanner sc;

public UserViewsImpl(Scanner sc) {
    this.sc = sc;
}

@Override
public User saisie(){
    System.out.println("Entrez login");
    String login=sc.next();
    System.out.println("Entrez password");
    String password=sc.next();
    System.out.println("Entrez le role");
    System.out.println("1- Admin");
    System.out.println("2- Boutiquier ");
    int choix=sc.nextInt();
    Role role;
    switch (choix) {
        case 1 -> role=Role.ADMIN;
        case 2 -> role=Role.BOUTIQUIER;
        
    
        default -> {
            System.out.println("Vous n'avez pas rentrez un chiffre entre 1 et 2");
            return null;
        }

        
    }

    User user=new User(login, password, role, true);
    return user;
}

@Override
public User saisitUserClient(Client client){
    System.out.println("Entrez login");
    String login=sc.next();
    System.out.println("Entrez password");
    String password=sc.next();

    User user=new User(login, password, client, Role.CLIENT, true);
    return user;
}

@Override
public  void lister(List<User> users){

   for (int i = 0; i < users.size(); i++) {
       System.out.println(users.get(i).toString());
   }

     
}
@Override
public boolean  getStatus(){

        System.out.println("vous voulez 1-activer ou 2-desactiver votre compte");
        int status = sc.nextInt();
    
        return switch (status) {
            case 1 -> true;
            case 2 -> false;
            default -> false;
        };

}








}
