package com.example.model_mapper.entities.services;

import com.example.model_mapper.entities.Person;
import com.example.model_mapper.entities.dtos.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> findOneById(Long id);

    void save(Person person);

    List<Person> findPersonsBornBefore(int year);

    List<PersonDto> findPersonsBornAfter(int year);

    List<Person> findAll();
}
