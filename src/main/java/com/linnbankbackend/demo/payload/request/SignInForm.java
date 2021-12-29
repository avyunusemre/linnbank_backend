package com.linnbankbackend.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInForm {
    @NotBlank
    private String ssn;

    @NotBlank
    @Size(min =8)
    private String password;
}
