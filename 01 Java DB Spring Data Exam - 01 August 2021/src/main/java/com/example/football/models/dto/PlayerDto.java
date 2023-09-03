package com.example.football.models.dto;

import com.example.football.models.enums.PlayerPosition;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

public class PlayerDto {

    @XmlElement(name = "first-name")
    @Size(min = 2)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 2)
    private String lastName;

    @XmlElement
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement
    private PlayerPosition position;

    @XmlElement(name = "town")
    private NameDto town;

    @XmlElement(name = "team")
    private NameDto team;

    @XmlElement(name = "stat")
    private StatIdDto stat;

    public PlayerDto() {
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }


    public String getBirthDate() {
        return birthDate;
    }


    public PlayerPosition getPosition() {
        return position;
    }


    public NameDto getTown() {
        return town;
    }

    public NameDto getTeam() {
        return team;
    }


    public StatIdDto getStat() {
        return stat;
    }

}
