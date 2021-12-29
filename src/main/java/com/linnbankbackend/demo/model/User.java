package com.linnbankbackend.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String ssn;


    private String firstName;
    private String lastName;
    private String address;

    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    private String email;
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "last_logged_in")
    private LocalDateTime lastLoggedIn;

    @ManyToOne
    @JoinColumn(name="role")
    private Role role;


    public User(String ssn, String firstName, String lastName, String address, String mobilePhoneNumber, String email, String password, Date createdDate) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return ssn;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
