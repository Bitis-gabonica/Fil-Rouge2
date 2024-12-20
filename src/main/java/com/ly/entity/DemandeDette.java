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
@ToString
public class DemandeDette {

    private String date;
    private float montant;
    private Client client;
    private boolean  status; 


    
    private List<Article> articles=new ArrayList<>();



    public DemandeDette(String date, float montant, Client client, boolean status) {
        this.date = date;
        this.montant = montant;
        this.client = client;
        this.status = status;
    }
}
