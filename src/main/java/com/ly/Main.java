package com.ly;

import java.util.Scanner;

import com.ly.db.ArticleRepositoryDb;
import com.ly.db.ClientRepositoryDb;
import com.ly.db.DemandeDetteRepositoryDB;
import com.ly.db.DetteReppositoryDb;
import com.ly.db.UserRepositoryDb;
import com.ly.entity.Article;
import com.ly.entity.Client;
import com.ly.entity.Dette;
import com.ly.entity.Paiement;
import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.repository.implement.PaiementRepositoryImpl;
import com.ly.repository.interfaces.ArticleRepository;
import com.ly.repository.interfaces.ClientRepository;
import com.ly.repository.interfaces.DemandeDetteRepository;
import com.ly.repository.interfaces.DetteRepository;
import com.ly.repository.interfaces.UserRepository;
import com.ly.services.implement.ArticleServiceImpl;
import com.ly.services.implement.ClientServiceImpl;
import com.ly.services.implement.DemandeDetteServiceImpl;
import com.ly.services.implement.DetteServiceImpl;
import com.ly.services.implement.PaiementServiceImpl;
import com.ly.services.implement.UserServiceImpl;
import com.ly.services.interfaces.ArticleService;
import com.ly.services.interfaces.ClientService;
import com.ly.services.interfaces.DemandeDetteService;
import com.ly.services.interfaces.DetteService;
import com.ly.services.interfaces.PaiementService;
import com.ly.services.interfaces.UserService;
import com.ly.views.Interfaces.ArticleViews;
import com.ly.views.Interfaces.ClientViews;
import com.ly.views.Interfaces.DetteViews;
import com.ly.views.Interfaces.PaiementViews;
import com.ly.views.Interfaces.UserViews;
import com.ly.views.implement.ArticleViewsImpl;
import com.ly.views.implement.ClientViewsImpl;
import com.ly.views.implement.DemandeDetteViewsImpl;
import com.ly.views.implement.DetteViewsImpl;
import com.ly.views.implement.PaiementViewsImpl;
import com.ly.views.implement.UserViewsImpl;


public class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        DemandeDetteRepository demandeDetteRepository=new DemandeDetteRepositoryDB();
        DemandeDetteService demandeDetteService=new DemandeDetteServiceImpl(demandeDetteRepository);
        DemandeDetteViewsImpl demandeDetteViews=new DemandeDetteViewsImpl(sc);
        ClientRepository clientRepository=new ClientRepositoryDb();
        ClientService clientService=new ClientServiceImpl(clientRepository);
        UserRepository userRepository=new UserRepositoryDb();
        UserService userService=new UserServiceImpl(userRepository);
        DetteRepository detteRepository=new DetteReppositoryDb();
        DetteService detteService=new DetteServiceImpl(detteRepository);
        PaiementRepositoryImpl paiementRepository=new PaiementRepositoryImpl();
        PaiementService paiementService=new PaiementServiceImpl(paiementRepository);
        ArticleRepository articleRepository=new ArticleRepositoryDb();
        ArticleService articleService=new ArticleServiceImpl(articleRepository);
        //UserViewsImpl userViews=new UserViewsImpl(sc);
        UserViews userViews=new UserViewsImpl(sc);
        //ClientViewsImpl clientViews=new ClientViewsImpl(sc, userViews);
        ClientViews clientViews=new ClientViewsImpl(sc, userViews, clientService);
        //DetteViewsImpl detteViews=new DetteViewsImpl(sc);
        //PaiementViewsImpl paiementViews=new PaiementViewsImpl(sc);
        PaiementViews paiementViews=new PaiementViewsImpl(sc);

        //ArticleViewsImpl articleViews=new ArticleViewsImpl(sc);
        ArticleViews articleViews=new ArticleViewsImpl(sc);
        DetteViews detteViews=new DetteViewsImpl(sc, articleViews, paiementViews);
        //DetteViewsImpl DetteViews=new com.ly.views.implement.DetteViewsImpl(sc, articleViews, paiementViews);
        int id=0;
            
    
        Client client=new Client("jeant", "1234","Siprez");
        clientService.add(client);
        Dette dette = new Dette(0, "2024-12-20", 10000,client);
        detteService.add(dette);
       
        
        



        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Connexion");
        System.out.println("Entrez login");
        String login=sc.next();
        System.out.println("Entrez Password");
        String password=sc.next();
        User userConnect=userService.getUserByLoginPasword(login, password);

        if (userConnect!=null && userConnect.getRole()==Role.BOUTIQUIER) {
            System.out.println("Bienvenue "+userConnect.toString() );
            
            int choix0=0;
        do {
            
            
        System.out.println("1- Creer un client");
        System.out.println("2- Lister les clients");
        System.out.println("3- Lister les clients avec compte");
        System.out.println("4- Lister les clients sans compte");
        System.out.println("5- Rechercher Clients par telephone");
        System.out.println("6- Creer dette");
        System.out.println("7- Enregistrer paiement pour Dette");
        System.out.println("8- Lister Dettes non soldees d'un client");
        System.out.println("9- Lister les demandes de dettes");
        System.out.println("10- Lister les demandes de dettes par statut");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Faites votre choix");
        choix0=sc.nextInt();

        switch (choix0) {
            case 1 -> {
                Client client2=clientViews.saisie();
                clientService.add(client2);
                if (client2.getUser()!=null) {
                    userService.add(client2.getUser());
                }   }

            case 2 -> {
                clientViews.lister(clientService.getAll());
                    }
            case 3 -> {
                clientViews.lister(clientService.getAllClientsUsers());
                    }
            case 4 -> {
                clientViews.lister(clientService.getAllClientsNotUsers());
                    }
            case 5->{
              
                    String tel2=clientViews.saisitTel();
                    clientViews.getClientByPhone(clientService.findClientbyTel(tel2));
            }
            case 6->{
                System.out.println("Le client que vous voulez endettez existe t'il");
                System.out.println("1 oui/2 non");
                int choix4=sc.nextInt();
                if (choix4==1) {
                    System.out.println("Entrez le tel du client a endetter");
                    String tel2=sc.next();
                    System.out.println(clientService.findClientbyTel(tel2));
                    if (clientService.findClientbyTel(tel2)!=null) {
                       detteService.add(detteViews.saisitDette(clientService.findClientbyTel(tel2),id)); 
                    }
                 }
                else {
                    Client client2=clientViews.saisie();
                        clientService.add(client2);
                        if (client2.getUser()!=null) {
                            userService.add(client2.getUser());
                    }
                        detteService.add(detteViews.saisitDette(client2,id));
                }
                
               
        }case 7->{
            System.out.println("Entrez l'id de la dette");
            int id2=sc.nextInt();
            Dette detteX=detteService.findDetteId(id2);
            if (detteX!=null) {
                Paiement paiement=paiementViews.saisitPaiement(detteX);
                detteX.setPaiement(paiement);
                paiementService.add(paiement);
            }else{
                System.out.println("La dette n'existe pas");
            }

            

        }
         case 8->{
                
            System.out.println("Entrez le tel du client");
            String tel2=sc.next();
            System.out.println(clientService.findClientbyTel(tel2));
            Client client4=clientService.findClientbyTel(tel2);
            if (client4!=null) {
                detteViews.listerDetteNonSolde(detteService.getAllDetteNonSolde(client4));
            }else{
                System.out.println("Le client n'existe pas");
            }
            


        }
     case 9->{
                
            demandeDetteViews.lister(demandeDetteService.getAll());


    }
    case 10->{ 
        
        System.out.println("filtrer demande par etat 1 En cours/2 annule");
        int tel2=sc.nextInt();
            switch (tel2) {
                case 1 -> demandeDetteViews.lister(demandeDetteService.getAllDemandeDettesByStatus(true));
                case 2 -> demandeDetteViews.lister(demandeDetteService.getAllDemandeDettesByStatus(false));
                default -> System.out.println("Erreur");
            }



}
      
            default -> {
                    }
        }

















    } while (choix0!=10);

    }else if (userConnect!=null && userConnect.getRole()==Role.ADMIN) {
        System.out.println("Bienvenue "+userConnect.toString() );

        int choix0=0;
        do {
            System.out.println( "1- Créer un compte utilisateur à un client n’ayant pas de compte" );
            System.out.println( "2- Créer un compte utilisateur avec un rôle Boutiquier ou  Admin" );
            System.out.println( "3- Désactiver/Activer  un compte utilisateur " );
            System.out.println( "4- Afficher les comptes utilisateurs  actifs ou par rôle." );
            System.out.println( "5- Créer article " );
            System.out.println( "6- lister  des articles et filtrer par disponibilité(qteStock!=0)" );
            System.out.println( "7- Mettre à jour la qté en stock d’un article" );
            System.out.println( "8-Archiver les dettes soldées" );
        choix0=sc.nextInt();

        switch (choix0) {
            case 1 -> {
                System.out.println("Entrez le tel du client");
                String tel2=sc.next();
                System.out.println(clientService.findClientbyTel(tel2));
                Client client4=clientService.findClientbyTel(tel2);
                if (client4.getUser()==null) {
                    userService.add(userViews.saisitUserClient(client4));
                }else{
                    System.out.println("Le client a deja un compte");
                }   }

                case 2 -> userService.add(userViews.saisie());

                case 3 ->{ System.out.println("Connexion");
                        System.out.println("Entrez login");
                    String login2=sc.next();
                    System.out.println("Entrez Password");
                    String password2=sc.next();
                    User userConnect2=userService.getUserByLoginPasword(login2, password2);

                    if (userConnect2!=null){
                        
                        userService.changeStatus(userViews.getStatus(), userConnect2);
                    }else{
                        System.out.println("Ce user n'existe pas");
                    }
                
                
                
                }

                case 4 -> {
                    System.out.println("Vouz voulez afficher les user par 1/role 2/status");
                    int choix5=sc.nextInt();
                        switch (choix5) {
                            case 1 -> {
                                System.out.println("1/ Client");
                                System.out.println("2/ Boutiquier");
                                System.out.println("2/ Admin");
                                int choix6=sc.nextInt();
                                switch (choix6) {
                                    case 1 -> userViews.lister(userService.getAllUserByRole(Role.CLIENT));
                                    case 2 -> userViews.lister(userService.getAllUserByRole(Role.BOUTIQUIER));
                                    case 3 -> userViews.lister(userService.getAllUserByRole(Role.ADMIN));
                                    default -> throw new AssertionError();
                                }
                    }
                            case 2 -> {
                                System.out.println("1/ actif");
                                System.out.println("2/ inactif");
                                int choix7=sc.nextInt();
                                switch (choix7) {
                                    case 1 -> userViews.lister(userService.getAllUserByStaus(true));
                                    case 2 -> userViews.lister(userService.getAllUserByStaus(false));
                                    
                                    default -> throw new AssertionError();
                                }
                    }

                        
                            default -> {
                    }
                        }



                }
                case 5 -> articleService.add(articleViews.saisie());
                case 6 -> articleViews.lister(articleService.listArticlesDispo());
                case 7 -> {
                    System.out.println("Entrez libelle de l'article");
                    String libelle=sc.next();
                    Article article2=articleService.getArticleByLibelle(libelle);
                    if (article2!=null) {
                    System.out.println("Entrez Le montant de la qte stock");
                    int montant=sc.nextInt();
                    articleService.updateQteStock(montant, article2);
                        
                    }else{
                        System.out.println("L'article n'existe pas");
                    }


                }
                case 8 -> detteService.ArchiverAllDetteNonSolde(detteService.getDetteSolde());

                

               

                



            default -> throw new AssertionError();
        }

    } while (choix0!=10);

}else if (userConnect!=null && userConnect.getRole()==Role.CLIENT) {
    System.out.println("Bienvenue "+userConnect.toString() );
    int choix0=0;
    do {
        System.out.println( "1- Lister dettes non solde" );
        System.out.println( "2- faire une demande de dette" );
        System.out.println( "3- Lister ses demandes de dette avec l’option de filtrer par état(En Cours, ou Annuler) " );
        System.out.println( "4- Envoyer une relance pour une  demande de dette annuler" );
        System.out.println( "5- Quitter " );
    choix0=sc.nextInt();

    switch (choix0) {
        case 1 -> {
            System.out.println("Entrez votre tel");
            String tel2=sc.next();
            System.out.println(clientService.findClientbyTel(tel2));
            Client client4=clientService.findClientbyTel(tel2);
            if (client4!=null) {
                detteViews.listerDetteNonSolde(detteService.getAllDetteNonSolde(client4));
            }
              }

            case 2 -> demandeDetteService.add(demandeDetteViews.saisie());

            case 3 -> {

                System.err.println("Choisir le statut a filter 1/en cours /2 annuler");
                String choix3=sc.next();
                switch (choix3) {
                    case "1" -> demandeDetteViews.lister(demandeDetteService.getAllDemandeDettesByStatus(true));
                    case "2" -> demandeDetteViews.lister(demandeDetteService.getAllDemandeDettesByStatus(false));

            }}
           

        
           
            


            case 4 -> {
                
                



            }
           

           

            



        default -> throw new AssertionError();
    }

} while (choix0!=5);




























sc.close();

}
}
}