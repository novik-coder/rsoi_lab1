package com.example.ds.controller;

import com.example.ds.ErrorResponse;
import com.example.ds.ValidationErrorResponse;
import com.example.ds.dto.PersonDTO;
import com.example.ds.model.Person;
import com.example.ds.service.PersonService;
import com.sun.net.httpserver.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@RestController
@RequestMapping
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/persons")
    public ResponseEntity<ArrayList<PersonDTO>> getPersons(){
        System.out.println(" Stigao je zahtev ");
        ArrayList<Person> persons = personService.getAllPersons();
        ArrayList<PersonDTO> personsDTO = new ArrayList<>();
        for (Person p:persons) {
            personsDTO.add(new PersonDTO(p));
            System.out.println(p.getName());
        }
        Headers headers = new Headers();
        headers.add("description","All Persons");
        return ResponseEntity.status(200).body(personsDTO);
    }

    @GetMapping(value = "/persons/{personId}")
    public ResponseEntity<?> getPersonBy(@PathVariable Long personId){
        System.out.println(" Stigao je zahtev ID");
        Person person = personService.getPersonById(personId);
        if (person == null){
            return ResponseEntity.status(404).body(new ErrorResponse("Not found"));
        }
        return ResponseEntity.ok(new PersonDTO(person));
    }

    @PostMapping(value = "/persons")
    public ResponseEntity<?> createNewPerson(@RequestBody PersonDTO personDTO) throws URISyntaxException {
        System.out.println(" Stigao je zahtev POST");
        if (validatePersonData(personDTO)){
            Person person = personService.createNewPerson(personDTO);
            URI location = new URI("/api/v1/persons/" + person.getId().toString());
            return ResponseEntity.created(location).build();
        }
        else{
            return ResponseEntity.status(400).body(new ValidationErrorResponse("Invalid data", personDTO, "Invalid Data"));
        }

    }


    @PatchMapping(value = "/persons/{personId}")
    public ResponseEntity<?> updatePerson(@PathVariable Long personId, @RequestBody PersonDTO personDTO)  {
        System.out.println(" Stigao je zahtev PATCH");
        Person person = personService.getPersonById(personId);
        if (person == null){
            return ResponseEntity.status(404).body(new ErrorResponse("Not found"));
        }
        if (validatePersonData(personDTO)) {
            Person updatedPerson = personService.updatePerson(personId, personDTO);
            return ResponseEntity.ok(new PersonDTO(updatedPerson));
        }
        else{
            return ResponseEntity.status(400).body(new ValidationErrorResponse("Invalid data", personDTO, "Invalid Data"));
        }

    }

    @DeleteMapping(value = "/persons/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long personId){
        System.out.println(" Stigao je zahtev DELETE");
        personService.deletePerson(personId);
        return ResponseEntity.status(204).build();
    }


    private boolean validatePersonData(PersonDTO personDTO){
        if (personDTO.getName().isBlank() || personDTO.getName() == null)
            return false;
        return true;
//        else if (personDTO.getAddress().strip().length() < 2)
//            return false;
//        else if (personDTO.getWork().strip().length() < 2)
//            return false;
//        else if (personDTO.getAge() < 1 && personDTO.getAge() > 110)
//            return false;
        
    }
}
