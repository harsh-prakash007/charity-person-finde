package find_person.com.example.charity.service;

import find_person.com.example.charity.model.Person;
import find_person.com.example.charity.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    // Basic filtered search (stream version)
    public List<Person> flexibleSearch(String eyeColor, String hairColor,
                                       Integer minHeight, Integer maxHeight) {
        return personRepository.findAll().stream()
                .filter(p -> eyeColor == null || p.getEyeColor().equalsIgnoreCase(eyeColor))
                .filter(p -> hairColor == null || p.getHairColor().equalsIgnoreCase(hairColor))
                .filter(p -> minHeight == null || p.getHeight() >= minHeight)
                .filter(p -> maxHeight == null || p.getHeight() <= maxHeight)
                .collect(Collectors.toList());
    }

    // Optimized JPA repository search
    public List<Person> searchByAttributes(String eyeColor, String hairColor,
                                           Integer minHeight, Integer maxHeight) {
        return personRepository.findByEyeColorIgnoreCaseAndHairColorIgnoreCaseAndHeightBetween(
                eyeColor, hairColor, minHeight, maxHeight);
    }

    // CRUD Operations
    @Transactional
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Transactional
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    // Custom exception
    private static class PersonNotFoundException extends RuntimeException {
        public PersonNotFoundException(Long id) {
            super("Person not found with id: " + id);
        }
    }
}