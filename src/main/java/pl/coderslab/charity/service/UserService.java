package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.RoleRepository;
import pl.coderslab.charity.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void add(User user){
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);
        user.getRoles().add(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnable(true);
        userRepository.save(user);
    }

    public List<User> users() {
        return userRepository.findAll();
    }

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void update(User user) {
        userRepository.save(user);
    }
    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String emailUser = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userRepository.findByEmail(emailUser);
        }
        return null;
    }
    public void addAdmin(User user){
        Role userRole = new Role();
        userRole.setName("ROLE_ADMIN");
        roleRepository.save(userRole);
        user.getRoles().add(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnable(true);
        userRepository.save(user);
    }
    public Set<User> allUsersAndAdmins(){
        return  new HashSet<>(userRepository.findAll());
    }
    public Set<User> allUsers(){
        return  userRepository.findAllUsers();
}
    public Set<User> allAdmins() {
        return userRepository.findAllAdmins();
    }

}