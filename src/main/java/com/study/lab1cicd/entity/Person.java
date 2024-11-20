package com.study.lab1cicd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 或 GenerationType.AUTO
    private Long Id;

    private String name;
    private int age;

    private String address;
    private String work;

    public Person() {
    }

    public Person(Long Id,String name, int age) {
        this.Id=Id;
        this.name=name;
        this.age=age;
    }

    // Getters and Setters


    public Long getId() {
        return Id;
    }

    public void setPersonId(Long Id) {
        this.Id = Id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", work='" + work + '\'' +
                '}';
    }
}
