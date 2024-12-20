package com.ly.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode
@ToString

public class Paiement {

    private String date;
    private float montant;
    private Dette dette;

    public Paiement(String date, Dette dette, float montant) {
        this.date = date;
        this.dette = dette;
        this.montant = montant;
    }

}
