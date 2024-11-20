package com.study.lab1cicd.controller;

import com.study.lab1cicd.entity.Person;
import com.study.lab1cicd.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/persons")

public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // 获取所有人的信息
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        if (persons.isEmpty()) {
            return ResponseEntity.noContent().build();  // 返回 204 No Content 如果没有数据
        }
        return ResponseEntity.ok(persons);  // 返回 200 OK 和数据
    }

    // 获取指定ID的人员信息
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 创建新记录
    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        return ResponseEntity
                .created(URI.create("/api/v1/persons/" + createdPerson.getPersonId()))
                .build();
    }

    // 更新现有记录
    @PatchMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {
        // 调用服务层进行更新
        Optional<Person> updatedPerson = personService.updatePerson(id, person);

        // 如果找到了更新后的 Person，返回 200 状态码和更新后的 Person 对象
        if (updatedPerson.isPresent()) {
            return ResponseEntity.ok(updatedPerson.get());
        }

        // 如果没有找到对应的 Person，返回 404 NOT FOUND
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 删除指定ID的记录
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id) {
        Optional<Person> person = personService.getPersonById(id);
        if (person.isPresent()) {
            personService.deletePerson(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}