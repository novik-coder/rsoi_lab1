package com.example.ds.data;

import com.example.ds.dto.PersonDTO;
import com.example.ds.model.Person;
import com.example.ds.repository.PersonRepository;
import com.example.ds.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private ArrayList<Person> persons;

    private Person person;
    private PersonDTO personDTO;


    @BeforeAll
    public void setUp() {
        this.persons = new ArrayList<>();
        Person person1 = new Person(1, "Elena", "Leninova 10", "prepodavatel", 30);
        Person person2 = new Person(2, "Ivan", "Gagarinova 27", "programist", 35);
        Person person3 = new Person(3, "Pavel", "Pervogo maya 77", "doktor", 50);
        Person person4 = new Person(4, "Olga", "Baumanskaya 109", "ucitel", 40);
        this.persons.add(person1);
        this.persons.add(person2);
        this.persons.add(person3);
        this.persons.add(person4);
        this.person = new Person(5, "Milica", "Gogoleva 22", "programis", 24);
        this.personDTO = new PersonDTO(0, "Milica", "Gogoleva 22", "programis", 24);
    }

    @Test()
    @Order(1)
    public void testGetAllPersons() {
        when(personRepository.findAll()).thenReturn(persons);

        List<Person> result = personService.getAllPersons();
        assertEquals(4, result.size());
        assertEquals("Elena", result.get(0).getName());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testGetPersonById() {
        when(personRepository.getPersonById(2L)).thenReturn((persons.get(1)));

        Optional<Person> result = Optional.of(personService.getPersonById(2L));
        assertTrue(result.isPresent());
        verify(personRepository, times(1)).getPersonById(2L);
    }

    @Test
    @Order(3)
    public void testGetPersonByIdNotFound() {
        when(personRepository.getPersonById(50L)).thenReturn(null);
        Optional<Person> result = Optional.ofNullable(personService.getPersonById(50L));
        assertFalse(result.isPresent());
        verify(personRepository, times(1)).getPersonById(50L);
    }

    @Test
    @Order(4)
    public void testCreatePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(this.person);

        Person createdPerson = personService.createNewPerson(this.personDTO);
        assertNotNull(createdPerson);
        assertEquals("Milica", createdPerson.getName());
    }


    @Test
    @Order(5)
    public void testUpdatePerson() {
        PersonDTO newPersonDetails = new PersonDTO(2, "Ivan", "Ladozskaya 27", "programist Java", 37);
        Person updated = new Person(2, "Ivan", "Ladozskaya 27", "programist Java", 37);


        when(personRepository.getPersonById(2L)).thenReturn(persons.get(1));
        when(personRepository.save(any(Person.class))).thenReturn(updated);

        Person updatedPerson = personService.updatePerson(2L, newPersonDetails);
        assertNotNull(updatedPerson);
        assertEquals("Ivan", updatedPerson.getName());
        assertEquals(37, updatedPerson.getAge());
        assertEquals("Ladozskaya 27", updatedPerson.getAddress());
    }

    @Test
    @Order(6)
    public void testDeletePerson() {
        doNothing().when(personRepository).deleteById(1L);
        personService.deletePerson(1L);
        verify(personRepository, times(1)).deleteById(1L);
    }
}
