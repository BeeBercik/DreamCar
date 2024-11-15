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
}
