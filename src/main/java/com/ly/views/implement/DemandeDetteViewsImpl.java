package com.ly.views.implement;

import java.util.List;
import java.util.Scanner;

import com.ly.entity.DemandeDette;
import com.ly.views.Interfaces.DemandeDetteViews;

public class DemandeDetteViewsImpl implements DemandeDetteViews {

 private final Scanner sc;

    public DemandeDetteViewsImpl(Scanner sc) {
        this.sc = sc;
    }


@Override
public DemandeDette saisie(){

System.out.println("Entrez Date");
String date=sc.next();
System.out.println("Entrez Montant");
Float montant=sc.nextFloat();

DemandeDette demandeDette=new DemandeDette(date, montant,null, true);
return demandeDette;
}


@Override
public void lister(List<DemandeDette> demandeDettes){

    for (int i = 0; i <demandeDettes.size() ; i++) {
        System.out.println(demandeDettes.get(i).toString());
    }
}


}
