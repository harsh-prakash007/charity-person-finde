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
public class PersonSearchService {
    private final PersonRepository personRepository;

    // Basic filtered search
    public List<Person> searchByAttributes(String eyeColor, String hairColor,
                                           Integer minHeight, Integer maxHeight) {
        return personRepository.findAll().stream()
                .filter(p -> eyeColor == null || p.getEyeColor().equalsIgnoreCase(eyeColor))
                .filter(p -> hairColor == null || p.getHairColor().equalsIgnoreCase(hairColor))
                .filter(p -> minHeight == null || p.getHeight() >= minHeight)
                .filter(p -> maxHeight == null || p.getHeight() <= maxHeight)
                .collect(Collectors.toList());
    }

    // More efficient search using JPA repository method
    public List<Person> searchByAttributesOptimized(String eyeColor, String hairColor,
                                                    Integer minHeight, Integer maxHeight) {
        return personRepository.findByEyeColorIgnoreCaseAndHairColorIgnoreCaseAndHeightBetween(
                eyeColor, hairColor, minHeight, maxHeight);
    }

    // Skin color search (now properly supported by repository)
    public List<Person> findBySkinColor(String skinColor) {
        return personRepository.findBySkinColorIgnoreCase(skinColor);
    }

    // Height range search
    public List<Person> findByHeightRange(Integer minHeight, Integer maxHeight) {
        return personRepository.findByHeightBetween(minHeight, maxHeight);
    }
}