package com.ly.entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "client")
public class User {
    private String login;
    private String password;
    private Client client;
    private Role role;
    private boolean status;

    public User(String login, String password, Client client, Role role,boolean status) {
        this.login = login;
        this.password = password;
        this.client = client;
        this.role = role;
        this.status=status;
    }

    public User(String login, String password, Role role,boolean status) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.status=status;
    }
    
    

}
