package com.ly.repository.interfaces;

import java.util.List;

import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.repository.Repository;

public interface UserRepository extends Repository<User> {
    public List<User> listUserByRole(Role role);
    public List<User> listUserByStatus(Boolean status);
    public User getUserByLoginPasword(String login,String password);


}
