package com.linnbankbackend.demo.service;

import com.linnbankbackend.demo.dao.UserDAO;
import com.linnbankbackend.demo.model.User;
import com.linnbankbackend.demo.payload.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserDAO getUserDAO(User user);
    UserDAO getUserDAOBySsn(String ssn);
    UserDAO getUserDAOById(Long userId);
    List<UserDAO> getAllUsers();
    ResponseEntity<Response> register(User user);
}
