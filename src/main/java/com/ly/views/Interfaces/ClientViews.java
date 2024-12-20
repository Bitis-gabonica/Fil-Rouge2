package com.ly.views.Interfaces;

import com.ly.entity.Client;
import com.ly.views.Views;

public interface ClientViews extends Views<Client> {


    public void getClientByPhone(Client client);
    public String saisitTel();

    
}
