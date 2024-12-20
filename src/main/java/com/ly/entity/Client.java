package com.ly.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Client {
    private String surname;
    private String tel;
    private String adresse;
    private float montantDus;
    private User user;

    private List<Dette> dettes=new ArrayList<>();

    public Client(String surname, String tel, String adresse, User user) {
        this.surname = surname;
        this.tel = tel;
        this.adresse = adresse;
        this.montantDus=calculMontant();
        this.user = user;
    }
    public Client(String surname, String tel, String adresse) {
        this.surname = surname;
        this.tel = tel;
        this.adresse = adresse;
        this.montantDus=calculMontant();
    }


    public void addDette(Dette dette){
        dettes.add(dette);
    }

    public float calculMontant(){
        float sum =0;
        for (int i = 0; i <dettes.size(); i++) {
            float x=dettes.get(i).getMontantRestant();
            sum=sum+x;
        } 
        return sum;
    }
    
    }


