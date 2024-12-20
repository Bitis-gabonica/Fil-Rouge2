package com.ly.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "paiement")
public class Dette {

    private int id;
    private String date;
    private int montant;
    private float montantVerser;
    private float montantRestant;
    private Paiement paiement;
    private Client client;
    private boolean status;


    private List<Article> articles=new ArrayList<>();


    public Dette(int id,String date, float montantVerser,Paiement paiement, Client client) {
        this.id=id+1;
        this.date = date;
        this.montantVerser = montantVerser;
        this.montantRestant = montant-montantVerser;
        this.paiement = paiement;
        this.client = client;
        this.status=true;
    }

    public Dette(int id,String date, float montantVerser,Client client) {
        this.date = date;
        this.montantVerser = montantVerser;
        this.montantRestant = montant-montantVerser;
        this.id = id+1;
        this.client = client;
        this.status=true;

    }


    public void addArticle(Article article){
        articles.add(article);
    }


    


}
