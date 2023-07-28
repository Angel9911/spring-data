package com.example.model_mapper.repositories;

import com.example.model_mapper.entities.Person;
import com.example.model_mapper.entities.dtos.PersonDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findPersonByBirthdayBefore(LocalDate birthday);
    //test if the spring will return a result by passing a dto class
    List<PersonDto> findPersonByBirthdayAfter(LocalDate birthday);
}
