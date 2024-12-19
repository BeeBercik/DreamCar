package com.dreamcar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserResponse {
    private int id;
    private String login;
    private String email;
    private String phone;
    private Date add_date;
}
