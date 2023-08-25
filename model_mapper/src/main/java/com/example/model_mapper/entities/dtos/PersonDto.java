package com.example.model_mapper.entities.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDto {
    @XmlElement(name = "fname")
    private String firstName;
    @XmlElement(name = "lname")
    private String lastName;
    @XmlElement(name = "birthday")
    private LocalDate birthday;
    @XmlElement(name = "salary")
    private BigDecimal salary;
    @XmlElement(name = "managerLastName")
    private String managerLastName;

    public PersonDto() {
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    @Override
    public String toString() {
        return String.format(" %s %s %.2f %s",this.firstName,this.lastName,this.salary,this.managerLastName == null ? "no manager" : this.managerLastName);
    }
}
