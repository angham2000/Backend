package tn.esprit.immobilier.controllers;


import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.immobilier.entities.AuthenticationResponse;
import tn.esprit.immobilier.entities.ChangePasswordRequest;
import tn.esprit.immobilier.entities.User;
import tn.esprit.immobilier.services.AuthenticationService;
import tn.esprit.immobilier.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/authenticate")
public class AuthenticationController {

    private AuthenticationService authService;
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        User u= userService.updateUser(updatedUser);
        return ResponseEntity.ok().body(u);
    }

    @PutMapping("/updateStatus/{userId}/{activate}")
    public ResponseEntity<String> updateUserStatus(@PathVariable Integer userId, @PathVariable boolean activate) {
        userService.updateUserStatus(userId, activate);
        String message = activate ? "User activated" : "User deactivated";
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
         authService.changePassword(request);
    }

}