package com.example.model_mapper.runner;

import com.example.model_mapper.entities.Address;
import com.example.model_mapper.entities.Employee;
import com.example.model_mapper.entities.Person;
import com.example.model_mapper.entities.dtos.*;
import com.example.model_mapper.entities.services.PersonService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // convert object in xml format
        ManagerDto managerDto = modelMapper.map(manger,ManagerDto.class);

        // marshall object to xml
        JAXBContext context = JAXBContext.newInstance(ManagerDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(managerDto,System.out);
        //this.marshallDto(managerDto);
        // end
        System.out.println(managerDto);


        // unmarshall xml data and convert it to dto class
        System.out.println("unmarshall manager dto: "+this.unmarshallObject());
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

        // marshall List of object in xml format
        List<PersonDto> collect = all.stream()
                .map(getperson -> modelMapper.map(getperson, PersonDto.class))
                .collect(Collectors.toList());

        collect.stream()
                .forEach(getpersondto -> System.out.println("person dto is "+getpersondto));

        PersonDtoList personDtoList = new PersonDtoList(collect);

        System.out.println("convert dtos in xml format");

        this.marshallDtoList(personDtoList);

        this.marshallDtoToXmlFile(personDtoList);

        System.out.println("Unmarshall list of person dto objects: "+this.unMarshallListObjects());

        System.out.println("Unmarshall manager dto from xml file: "+this.unmarshallFromXmlFile());

        // end marshall
        System.out.println("Exercise with custom model mapper");

        ModelMapper customMapper = new ModelMapper();
        TypeMap<Person,CustomDto> typeMap = customMapper.createTypeMap(Person.class,CustomDto.class);// before method addMapping we have to set the type of value in (destination,value)

        all.stream().filter(customPerson -> customPerson.getManager()!=null).forEach(customPerson -> System.out.println(customPerson.getManager().getLastName()));

        // second way to set value on custom property of dto class(with converter)
        Converter<Person,Integer> getLastNameLength = ctx -> ctx.getSource() == null ? null : ctx.getSource().getLastName().length();

        typeMap.addMappings(mapping->
                mapping.when(Objects::nonNull).using(getLastNameLength).map(Person::getManager,CustomDto::setManagerLastNameLength));

        all.stream()
                .map(typeMap::map)
                .forEach(System.out::println);
    }

    private void marshallDtoList(PersonDtoList personDtoList) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(PersonDtoList.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(personDtoList, System.out);

    }

    private void marshallDtoToXmlFile(PersonDtoList personDtoList) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(PersonDtoList.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);

        String path = "E:\\Programs\\SpringAngularProject\\model_mapper\\src\\main\\resources\\xml_files\\personList.xml";
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        marshaller.marshal(personDtoList,fileOutputStream);
    }

    private ManagerDto unmarshallObject() throws JAXBException {
        String xmlData = """
                <managerDto>
                    <firstName>manger</firstName>
                    <lastName>manger_lastName</lastName>
                    <employees>
                        <employee>
                            <firstName>employee1</firstName>
                            <lastName>employee_lastName</lastName>
                            <salary>1</salary>
                        </employee>
                        <employee>
                            <firstName>employee2</firstName>
                            <lastName>employee2_lastName</lastName>
                            <salary>10</salary>
                        </employee>
                    </employees>
                </managerDto>""";

        JAXBContext context = JAXBContext.newInstance(ManagerDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlData.getBytes());
        ManagerDto managerDto = (ManagerDto) unmarshaller.unmarshal(byteArrayInputStream);
        return managerDto;
    }

    private ManagerDto unmarshallFromXmlFile() throws FileNotFoundException {
        String path = "E:\\Programs\\SpringAngularProject\\model_mapper\\src\\main\\resources\\data.xml";
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            JAXBContext context = JAXBContext.newInstance(ManagerDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ManagerDto managerDto = (ManagerDto) unmarshaller.unmarshal(fileInputStream);
            return managerDto;
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PersonDtoList unMarshallListObjects() throws JAXBException {
        String xmlData = """
                <person-list>
                    <persons>
                        <personDtos>
                            <fname>manager</fname>
                            <lname>manager_firstName</lname>
                            <salary>1.00</salary>
                        </personDtos>
                        <personDtos>
                            <fname>manager</fname>
                            <lname>manager_firstName</lname>
                            <salary>1.00</salary>
                        </personDtos>
                        <personDtos>
                            <fname>employee1</fname>
                            <lname>employee1_lastName</lname>
                            <salary>1.00</salary>
                            <managerLastName>manager_firstName</managerLastName>
                        </personDtos>
                        <personDtos>
                            <fname>employee1</fname>
                            <lname>employee1_lastName</lname>
                            <salary>1.00</salary>
                            <managerLastName>manager_firstName</managerLastName>
                        </personDtos>
                    </persons>
                </person-list>""";

        JAXBContext context = JAXBContext.newInstance(PersonDtoList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlData.getBytes());
        PersonDtoList personDtoList = (PersonDtoList) unmarshaller.unmarshal(byteArrayInputStream);
        return personDtoList;
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
