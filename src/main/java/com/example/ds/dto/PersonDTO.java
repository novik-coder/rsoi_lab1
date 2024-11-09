package com.example.ds.dto;

import com.example.ds.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private Integer id;
    private String name;
    private String address;
    private String work;
    private Integer age;

    public PersonDTO(Person person){
        this.id = person.getId();
        this.name = person.getName();
        this.address = person.getAddress();
        this.work = person.getWork();
        this.age = person.getAge();
    }
}
