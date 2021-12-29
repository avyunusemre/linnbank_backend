package com.linnbankbackend.demo.payload.response;

import com.linnbankbackend.demo.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private UserDAO user;
    private String jwt;
    private String message;
}
