package com.ly.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode

public class Article {

    private int qteStock;
    private String libelle;
    private int montant;
    public Article(int qteStock, String libelle, int montant) {
        this.qteStock = qteStock;
        this.libelle = libelle;
        this.montant = montant;
    }
    
}
