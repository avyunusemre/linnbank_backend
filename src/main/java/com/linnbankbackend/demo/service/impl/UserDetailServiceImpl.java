package com.linnbankbackend.demo.service.impl;

import com.linnbankbackend.demo.dao.UserDAO;
import com.linnbankbackend.demo.model.User;
import com.linnbankbackend.demo.payload.response.Response;
import com.linnbankbackend.demo.repository.RoleRepo;
import com.linnbankbackend.demo.repository.UserRepo;
import com.linnbankbackend.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;



    public ResponseEntity<Response> register(User user) {
        Response response = new Response();

        if(userRepo.existsBySsn(user.getSsn())) {
            response.setMessage("Error: Ssn is already used");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if(userRepo.existsByEmail(user.getEmail())) {
            response.setMessage("Error: Email is already used");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        user.setRole(roleRepo.findByRoleName("CUSTOMER"));

        userRepo.save(user);
        response.setMessage("User registered successfully");
        response.setSuccess(true);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public UserDAO getUserDAO(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.setUserId(user.getUserId());
        userDAO.setSsn(user.getSsn());
        userDAO.setFirstname(user.getFirstName());
        userDAO.setLastname(user.getLastName());
        userDAO.setEmail(user.getEmail());
        userDAO.setAddress(user.getAddress());
        Boolean isAdmin = (user.getRole().getRoleName().equals("ADMIN"));
        userDAO.setIsAdmin(isAdmin);
        userDAO.setCreatedDate(user.getCreatedDate());
        userDAO.setLastLoggedIn(user.getLastLoggedIn());
        userDAO.setMobilePhoneNumber(user.getMobilePhoneNumber());

        return userDAO;
    }

    @Override
    public UserDAO getUserDAOBySsn(String ssn) {

        UserDAO userDAO = new UserDAO();
        Optional<User> user = userRepo.findBySsn(ssn);
        if(user.isPresent()) {
            userDAO = getUserDAO(user.get());
        }

        return userDAO;
    }

    @Override
    public UserDAO getUserDAOById(Long userId) {
        UserDAO userDAO = new UserDAO();
        Optional<User> user =  userRepo.findById(userId);
        if(user.isPresent()) {
            userDAO = getUserDAO(user.get());

        }
        return userDAO;
    }

    public UserDAO transformUsers(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.setUserId(user.getUserId());
        userDAO.setSsn(user.getSsn());
        userDAO.setFirstname(user.getFirstName());
        userDAO.setLastname(user.getLastName());
        userDAO.setEmail(user.getEmail());
        userDAO.setAddress(user.getAddress());

        userDAO.setCreatedDate(user.getCreatedDate());
        userDAO.setLastLoggedIn(user.getLastLoggedIn());
        userDAO.setMobilePhoneNumber(user.getMobilePhoneNumber());

        return userDAO;
    }

    @Override
    public List<UserDAO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(this::transformUsers).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String ssn) throws UsernameNotFoundException {
        User user = userRepo.findBySsn(ssn)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + ssn));

        return user;
    }
}
