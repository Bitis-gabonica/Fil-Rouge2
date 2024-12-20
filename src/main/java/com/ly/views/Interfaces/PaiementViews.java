package com.ly.views.Interfaces;

import java.util.List;

import com.ly.entity.Dette;
import com.ly.entity.Paiement;

public interface PaiementViews {

      public Paiement saisitPaiement(Dette dette);
        public void lister(List<Paiement>paiements);
}
