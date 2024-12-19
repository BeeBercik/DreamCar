package com.dreamcar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private int id;
    private String login;
    private String password;
    private String rep_password;
    private String email;
    private String phone;
}
