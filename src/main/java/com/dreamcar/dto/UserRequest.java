package com.dreamcar.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto class represents request from frontend with user data
 */
@Getter
@Setter
@ToString
public class UserRequest {
    private int id;
    private String login;
    private String password;
    /**
     * Additional filed required during registration
     */
    private String rep_password;
    private String email;
    private String phone;
}
