package find_person.com.example.charity.repository;

import find_person.com.example.charity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Add custom queries if needed, e.g.:
    User findByEmail(String email);
}