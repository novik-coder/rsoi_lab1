package com.example.ds.service;

import com.example.ds.dto.PersonDTO;
import com.example.ds.model.Person;
import com.example.ds.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public ArrayList<Person> getAllPersons(){
        return (ArrayList<Person>) personRepository.findAll();
    }

    public Person getPersonById(Long id){
        return personRepository.getPersonById(id);
    }

    public Person createNewPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setAddress(personDTO.getAddress());
        person.setWork(personDTO.getWork());
        person.setAge(personDTO.getAge());
        return personRepository.save(person);
    }


    public Person updatePerson(Long personId, PersonDTO personDTO) {
        Person person = personRepository.getPersonById(personId);
        if (personDTO.getName() != null)
            person.setName(personDTO.getName());
        if ( personDTO.getAddress() != null)
            person.setAddress(personDTO.getAddress());
        if ( personDTO.getWork() != null)
            person.setWork(personDTO.getWork());
        if (personDTO.getAge() != null)
            person.setAge(personDTO.getAge());
        return personRepository.save(person);
    }

    public void deletePerson(Long personId) {
         personRepository.deleteById(personId);
    }

}
