package find_person.com.example.charity.repository;

import find_person.com.example.charity.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Existing method
    List<Person> findByEyeColorIgnoreCaseAndHairColorIgnoreCaseAndHeightBetween(
            String eyeColor, String hairColor, Integer minHeight, Integer maxHeight);

    // Add these new methods
    List<Person> findBySkinColorIgnoreCase(String skinColor);
    List<Person> findByHeightBetween(Integer minHeight, Integer maxHeight);
}