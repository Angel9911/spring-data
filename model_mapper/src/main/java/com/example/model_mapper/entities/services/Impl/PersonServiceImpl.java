package com.example.model_mapper.entities.services.Impl;

import com.example.model_mapper.entities.Person;
import com.example.model_mapper.entities.dtos.PersonDto;
import com.example.model_mapper.entities.services.PersonService;
import com.example.model_mapper.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findOneById(Long id) {
        return this.personRepository.findById(id);
    }

    @Override
    public void save(Person person) {
        this.personRepository.save(person);
    }

    @Override
    public List<Person> findPersonsBornBefore(int year) {
        LocalDate localDate = LocalDate.of(year,1,1);

        return this.personRepository.findPersonByBirthdayBefore(localDate);
    }

    @Override
    public List<PersonDto> findPersonsBornAfter(int year) {
        LocalDate localDate = LocalDate.of(year,1,1);

        return this.personRepository.findPersonByBirthdayAfter(localDate);
    }

    @Override
    public List<Person> findAll() {
        return this.personRepository.findAll();
    }
}
