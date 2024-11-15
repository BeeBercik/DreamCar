package com.dreamcar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String login;
    private String password;
    private String rep_password; // additional field
    private String email;
    private String phone;
    private Date add_date;

    // constructor to create user for client (after logging in)
    public UserDTO(int id, String login, String email, String phone, Date add_date) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.add_date = add_date;
    }
}
