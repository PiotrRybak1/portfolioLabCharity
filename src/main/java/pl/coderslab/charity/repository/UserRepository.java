package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    @Query("select u from User u join fetch u.roles r  where r.name ='ROLE_USER'")
    Set<User> findAllUsers();
    @Query("select u from User u join fetch u.roles r  where r.name ='ROLE_ADMIN'")
    Set<User> findAllAdmins();
}
