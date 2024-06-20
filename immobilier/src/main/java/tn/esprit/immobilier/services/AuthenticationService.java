package tn.esprit.immobilier.services;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.immobilier.entities.AuthenticationResponse;
import tn.esprit.immobilier.entities.ChangePasswordRequest;
import tn.esprit.immobilier.entities.User;
import tn.esprit.immobilier.repositories.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private UserRepository repository;
     private PasswordEncoder passwordEncoder;
    private  JwtService jwtService;
    private UserService userService;

    private AuthenticationManager authenticationManager;



    public AuthenticationResponse register(User request) {

        if(repository.findByUserName(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }

        User user = new User();
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);


        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUserName(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt, "User login was successful");

    }
    public void changePassword(ChangePasswordRequest request) {
        System.out.println("Attempting to change password");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        System.out.println("Changing password for user: " + currentUsername);
        System.out.println("Old password" + request.getOldPassword());
        System.out.println("New password: " + request.getNewPassword());
        Optional<User> user = repository.findByUserName(currentUsername);
System.out.println(user.get().getPassword()+" this is the password have to matches"+passwordEncoder.encode(request.getOldPassword().toString()));

      if (!passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())) {
            throw new RuntimeException("Authentication failed: old password is incorrect");
        }
        System.out.println("We are here");

        updatePassword(currentUsername, request.getNewPassword());

        System.out.println("Password changed successfully for user: " + currentUsername);
    }
    public void updatePassword(String username, String newPassword) {
        System.out.println(username);
        User user = repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        repository.save(user);
    }



}
