package tn.esprit.immobilier.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.immobilier.entities.User;
import tn.esprit.immobilier.entities.UserNotFoundException;
import tn.esprit.immobilier.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private UserRepository repository;

public UserService(UserRepository repository){
    this.repository=repository;
}

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("hello"+username);
        return repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User updateUser(User updatedUser) {
        User existingUser = repository.findById(updatedUser.getIdUser())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        existingUser.setRole(updatedUser.getRole());
        return repository.save(existingUser);
    }

    public void updateUserStatus(Integer userId, boolean activate) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEnabled(activate);

        repository.save(user);
    }
    public List<User> getAllUsers() {
        return repository.findAll();
    }

}
