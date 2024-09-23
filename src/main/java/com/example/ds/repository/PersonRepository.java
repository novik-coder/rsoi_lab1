package com.example.ds.repository;

import com.example.ds.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

    Person getPersonById(Long id);
}
