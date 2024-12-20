package com.ly.repository.implement;
import java.util.List;
import java.util.stream.Collectors;

import com.ly.entity.Role;
import  com.ly.entity.User;
import com.ly.repository.interfaces.UserRepository;

public class UserRepositoryListeImpl extends RepositoryListImpl<User> implements UserRepository {
    @Override    
    public List<User> listUserByRole(Role role){
            return list.stream().filter(user->user.getRole()==role).collect(Collectors.toList());
        }

    @Override
    public List<User> listUserByStatus(Boolean status){
        return list.stream().filter(user->user.isStatus()==status).collect(Collectors.toList());
    }
    @Override
    public User getUserByLoginPasword(String login,String password){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLogin().compareTo(login)==0 &&list.get(i).getPassword().compareTo(password)==0) {
                return list.get(i);
            }
        }return null;
    }

    




}
