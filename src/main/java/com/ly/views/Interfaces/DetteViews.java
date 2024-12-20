package com.ly.views.Interfaces;

import java.util.List;

import com.ly.entity.Client;
import com.ly.entity.Dette;

public interface DetteViews{

    public Dette saisitDette(Client client,int id);
    public void listerDetteNonSolde(List<Dette> dettes);
    public void lister(List<Dette> dettes);
    
}
