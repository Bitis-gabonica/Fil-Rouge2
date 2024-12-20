package com.ly.services.implement;

import java.util.List;

import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.repository.interfaces.UserRepository;
import com.ly.services.interfaces.UserService;

public class UserServiceImpl implements UserService {

private final UserRepository userRepository;

public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
}

@Override
public void add(User user){
        userRepository.insert(user);
    }

    @Override
    public List<User> getAll(){
       return  userRepository.lister();
    }


    @Override

    public List<User> getAllUserByRole(Role role){
        return  userRepository.listUserByRole(role);
     }

    @Override

     public List<User> getAllUserByStaus(Boolean status){
        return  userRepository.listUserByStatus(status);
     }
     
     @Override
     public User getUserByLoginPasword(String login,String password){
        return userRepository.getUserByLoginPasword(login, password);
     }
     @Override
     public void changeStatus(boolean status,User user){
      user.setStatus(status);
     }

     






}
