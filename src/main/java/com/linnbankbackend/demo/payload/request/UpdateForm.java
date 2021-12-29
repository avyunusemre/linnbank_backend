package com.linnbankbackend.demo.payload.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateForm {

    private String ssn;

    private String firstName;
    private String lastName;
    private String address;
    private String mobilePhoneNumber;
    private String email;
}
