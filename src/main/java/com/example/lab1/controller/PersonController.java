package com.example.lab1.controller;

import com.example.lab1.model.Person;
import com.example.lab1.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // 获取指定 ID 的人
    @GetMapping("/{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable Long personId) {
        Person person = personService.getPersonById(personId);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 获取所有人
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    // 创建新的人
    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/persons/" + person.getId())
                .build();
    }

    // 更新已有的人
    @PatchMapping("/{personId}")
    public ResponseEntity<Void> updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        if (personService.updatePerson(personId, person)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 删除指定 ID 的人
    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        if (personService.deletePerson(personId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
