package com.linnbankbackend.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {

    @NotBlank
    private String ssn;
    private String firstName;
    private String lastName;
    private String address;
    private String mobilePhoneNumber;

    @NotBlank
    @Email(message = "Please provide a valid email address")
    private String email;
    private String password;


    private Set<String> role;

}
