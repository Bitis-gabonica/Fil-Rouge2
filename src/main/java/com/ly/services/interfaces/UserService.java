package com.ly.services.interfaces;

import java.util.List;

import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.services.ServicesI;

public interface UserService extends ServicesI<User> {

        public List<User> getAllUserByRole(Role role);
     public List<User> getAllUserByStaus(Boolean status);
     public User getUserByLoginPasword(String login,String password);
     public void changeStatus(boolean status,User user);

}
