package com.example.model_mapper.runner;

import com.example.model_mapper.entities.Address;
import com.example.model_mapper.entities.Employee;
import com.example.model_mapper.entities.Person;
import com.example.model_mapper.entities.dtos.CustomDto;
import com.example.model_mapper.entities.dtos.EmployeeDto;
import com.example.model_mapper.entities.dtos.ManagerDto;
import com.example.model_mapper.entities.dtos.PersonDto;
import com.example.model_mapper.entities.services.PersonService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ConsoleRunner implements CommandLineRunner {
    private final PersonService personService;

    @Autowired
    public ConsoleRunner(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void run(String... args) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        Employee employeeSource = new Employee("angel",
                "dimitrov",
                BigDecimal.TEN,
                LocalDate.now(),
                new Address("mir 3",3,"Varna","Bulgaria")
                ,true);

        EmployeeDto employeeDto = modelMapper.map(employeeSource,EmployeeDto.class);

        System.out.println(employeeDto);

        Employee manger = new Employee("manger",
                "manger_lastName",
                BigDecimal.ZERO,
                LocalDate.now(),
                new Address("boris 3",3,"Sofia","Bulgaria")
                ,false);

        Employee employee1 = new Employee("employee1",
                "employee_lastName",
                BigDecimal.ONE,
                LocalDate.now(),
                new Address("mir 3",3,"Sofia","Bulgaria")
                ,true);
        Employee employee2 = new Employee("employee2",
                "employee2_lastName",
                BigDecimal.TEN,
                LocalDate.now(),
                new Address("mir 3",3,"Plovdiv","Bulgaria")
                ,true);

        manger.add(employee1);
        manger.add(employee2);

        ManagerDto managerDto = modelMapper.map(manger,ManagerDto.class);

        System.out.println(managerDto);

        //this.persistPerson();

        Optional<Person> getPerson = this.personService.findOneById(2L);

        Person person = getPerson.get();

        PersonDto personDto = modelMapper.map(person,PersonDto.class);

        System.out.println(" the object that is received is "+personDto);

        List<Person> getPersonBornBefore = this.personService.findPersonsBornBefore(2020);

        getPersonBornBefore.stream()
                .map(currentPerson -> modelMapper.map(currentPerson,PersonDto.class))
                .forEach(System.out::println);

        /**List<PersonDto> getPersonBornAfter = this.personService.findPersonsBornAfter(2020);

        getPersonBornAfter.stream()
                .forEach(System.out::println); - doesn't working **/
        List<Person> all = this.personService.findAll();

        System.out.println("Exercise with custom model mapper");

        ModelMapper customMapper = new ModelMapper();
        TypeMap<Person,CustomDto> typeMap = customMapper.createTypeMap(Person.class,CustomDto.class);

        // before method addMapping we have to set the type of value in (destination,value)
        typeMap.addMappings(m -> m.map(source -> source.getManager() == null
                        ? 0 : source.getManager().getLastName().length()
                    ,(CustomDto::setManagerLastNameLength))); // (CustomDto::setManagerLastNameLength)); and then we can delete <Integer>

        all.stream()
                .map(typeMap::map) // .map(customPerson -> typeMap.map(customPerson))
                .forEach(System.out::println);

    }
    private void persistPerson(){
        Person personManager = new Person("manager"
                ,"manager_firstName"
                , BigDecimal.ONE
                ,LocalDate.now()
                , null);

        Person person = new Person("employee1",
                "employee1_lastName",
                BigDecimal.ONE,
                LocalDate.now(),personManager);

        //this.personService.save(person);
        //personManager = this.personService.findOneById(person.getManager().getId()).get();

        Person person2 = new Person("employee2",
                "employee2_lastName",
                BigDecimal.TEN,
                LocalDate.now(),personManager);

        Person person3 = new Person("employee3",
                "employee3_lastName",
                BigDecimal.TEN,
                LocalDate.now(),personManager);
        this.personService.save(person);
        //this.personService.save(person2);
        //this.personService.save(person3);
    }
}
