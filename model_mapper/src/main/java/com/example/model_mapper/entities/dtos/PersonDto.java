package com.example.model_mapper.entities.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PersonDto {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private BigDecimal salary;
    private String managerLastName;

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

    @Override
    public String toString() {
        return String.format(" %s %s %.2f %s",this.firstName,this.lastName,this.salary,this.managerLastName == null ? "no manager" : this.managerLastName);
    }
}
