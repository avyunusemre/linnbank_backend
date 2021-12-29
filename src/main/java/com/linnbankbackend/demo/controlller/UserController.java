package com.linnbankbackend.demo.controlller;

import com.linnbankbackend.demo.dao.UserDAO;
import com.linnbankbackend.demo.model.User;
import com.linnbankbackend.demo.payload.request.Token;
import com.linnbankbackend.demo.payload.request.UpdateForm;
import com.linnbankbackend.demo.payload.response.LoginResponse;
import com.linnbankbackend.demo.payload.response.Response;
import com.linnbankbackend.demo.repository.RoleRepo;
import com.linnbankbackend.demo.repository.UserRepo;
import com.linnbankbackend.demo.security.jwt.JwtUtil;
import com.linnbankbackend.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") Long id) {
        return userRepo.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserWithId(@PathVariable("id") Long id,@Valid @RequestBody UpdateForm updateForm) {
        LoginResponse response = new LoginResponse();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setSsn(updateForm.getSsn());
        user.setFirstName(updateForm.getFirstName());
        user.setLastName(updateForm.getLastName());
        user.setAddress(updateForm.getAddress());
        user.setMobilePhoneNumber(updateForm.getMobilePhoneNumber());
        user.setEmail(updateForm.getEmail());

        userRepo.save(user);
        String newToken = jwtUtil.generateToken(SecurityContextHolder.getContext().getAuthentication());

        UserDAO userDAO = userService.getUserDAO(user);

        response.setUser(userDAO);
        response.setJwt(newToken);
        response.setMessage("User updated successfully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
