package com.example.model_mapper.entities.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "person-list")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDtoList {

    @XmlElementWrapper(name = "persons")
    private List<PersonDto> personDtos = null;

    public PersonDtoList() {
    }

    public PersonDtoList(List<PersonDto> personDtos) {
        this.personDtos = personDtos;
    }
    @Override
    public String toString() {
        return personDtos.toString();
    }
}
