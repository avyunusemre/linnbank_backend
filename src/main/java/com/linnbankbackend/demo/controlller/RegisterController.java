package com.linnbankbackend.demo.controlller;

import com.linnbankbackend.demo.dao.UserDAO;
import com.linnbankbackend.demo.model.User;
import com.linnbankbackend.demo.payload.request.RegisterForm;
import com.linnbankbackend.demo.payload.request.SignInForm;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegisterController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody RegisterForm registerForm) {
        //String ssn, String firstname, String lastname, String address,
        // String mobilePhoneNumber, String email, String password, Date createdDate
        User user = new User(
                registerForm.getSsn(),
                registerForm.getFirstName(),
                registerForm.getLastName(),
                registerForm.getAddress(),
                registerForm.getMobilePhoneNumber(),
                registerForm.getEmail(),
                encoder.encode(registerForm.getPassword()),
                new Date()
        );
        return userService.register(user);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody SignInForm signInForm) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signInForm.getSsn(),
                        signInForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        user.setLastLoggedIn(java.time.LocalDateTime.now());
        userRepo.save(user);
        String jwt = jwtUtil.generateToken(authentication);
        String message = "Login is successfull!";
        UserDAO userDAO = userService.getUserDAO(user);

        return ResponseEntity.ok(new LoginResponse(userDAO,jwt,message));
    }

    @PostMapping("/user")
    public ResponseEntity<?> getUserWithToken(@Valid @RequestBody Token token) {

        Response response = new Response();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(jwtUtil.validateToken(token.getToken(), userDetails) || token.getToken() == null) {
            response.setMessage("Token expired or null");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String ssn = jwtUtil.extractUsername(token.getToken());
        UserDAO user = userService.getUserDAOBySsn(ssn);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
