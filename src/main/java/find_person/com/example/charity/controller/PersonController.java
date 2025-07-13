package find_person.com.example.charity.controller;

import find_person.com.example.charity.model.Person;
import find_person.com.example.charity.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/search")
    public List<Person> searchPersons(
            @RequestParam(required = false) String eyeColor,
            @RequestParam(required = false) String hairColor,
            @RequestParam(required = false) Integer minHeight,
            @RequestParam(required = false) Integer maxHeight) {

        // Using the optimized JPA repository method
        return personService.searchByAttributes(eyeColor, hairColor, minHeight, maxHeight);

        // Alternatively, use the stream version:
        // return personService.flexibleSearch(eyeColor, hairColor, minHeight, maxHeight);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }
}