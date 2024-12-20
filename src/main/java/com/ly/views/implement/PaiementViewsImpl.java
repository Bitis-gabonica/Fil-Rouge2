package com.ly.views.implement;
import java.util.List;
import java.util.Scanner;

import com.ly.entity.Dette;
import com.ly.entity.Paiement;
import com.ly.views.Interfaces.PaiementViews;

public class PaiementViewsImpl implements PaiementViews {

    private final Scanner sc;

    public PaiementViewsImpl(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public Paiement saisitPaiement(Dette dette){
        System.out.println("Entrez daye yyyy-dd-mm");
        String date=sc.next();
        System.out.println("Entrez montant");
        int x=sc.nextInt();
        Paiement paiement=new Paiement(date, dette, x);
        return paiement;



    }

    @Override
    public void lister(List<Paiement>paiements){
    
    for(int i=0;i<paiements.size();i++){
        System.out.println(paiements.get(i).toString());
    }

    }

   
    }
  
    

