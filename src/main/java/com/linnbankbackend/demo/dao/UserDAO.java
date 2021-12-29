package com.linnbankbackend.demo.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    private Long userId;
    private String ssn;
    private String firstname;
    private String lastname;
    private String address;
    private String mobilePhoneNumber;

    private String email;

    @JsonIgnore
    private String password;
    private Date createdDate;
    private LocalDateTime lastLoggedIn;
    private Boolean isAdmin;


}
