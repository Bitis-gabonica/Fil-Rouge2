package com.ly.views.Interfaces;

import com.ly.entity.Client;
import com.ly.entity.User;
import com.ly.views.Views;

public interface UserViews extends Views<User> {


    public User saisitUserClient(Client client);
    public boolean  getStatus();

}
