package com.ly.views.implement;

import java.util.List;
import java.util.Scanner;

import com.ly.entity.Article;
import com.ly.entity.Client;
import com.ly.entity.Dette;
import com.ly.entity.Paiement;
import com.ly.views.Interfaces.ArticleViews;
import com.ly.views.Interfaces.DetteViews;
import com.ly.views.Interfaces.PaiementViews;

public class DetteViewsImpl implements DetteViews {

    private final Scanner sc;
    private final ArticleViews articleViews;
    private final PaiementViews paiementViews;
  

    public DetteViewsImpl(Scanner sc,ArticleViews articleViews,PaiementViews paiementViews) {
        this.sc = sc;
        this.articleViews=articleViews;
        this.paiementViews=paiementViews;
    }


   @Override
 public Dette saisitDette(Client client,int id){

            
        System.out.println("Entrer montant");
        float montant=sc.nextInt();
        System.out.println("Entrer montantVerser");
        float montantVerser=sc.nextInt();
        System.out.println("Entrer Date");
        String date=sc.next();
        Dette dette=new Dette(id,date, montantVerser, client);
        System.out.println("Combien d'article voulez vous entrer");
        int choix=sc.nextInt();
        for (int i = 0; i < choix; i++) {
            Article article=articleViews.saisie();
            dette.addArticle(article);
        }
        System.out.println(" Voulez vous entrez un paiement");
        System.out.println("1 oui/2 non");
        int choix2=sc.nextInt();
        if (choix2==1) {
            Paiement paiement=paiementViews.saisitPaiement(dette);

            dette.setPaiement(paiement);
            dette.setMontantVerser(dette.getMontantVerser()+paiement.getMontant());
            client.addDette(dette);
            return dette;
        }else{
            client.addDette(dette);
            return dette;
        }
    }
    @Override
    public void listerDetteNonSolde(List<Dette> dettes){
        for (int i = 0; i <dettes.size() ; i++) {
            System.out.println(dettes.get(i).toString());
        }
    }


    @Override
    public void lister(List<Dette> dettes){
        for (int i =0;i < dettes.size(); i++){
            System.out.println(dettes.get(i).toString());
        }  
        }

    }



































