package com.example.model_mapper.entities.dtos;

import java.math.BigDecimal;

public class EmployeeDto {

    private String firstName;
    private String lastName;
    private BigDecimal salary;
  //  private String addressCity;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    /*public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }*/

    @Override
    public String toString() {
        return String.format(" %s %s %.2f",this.getFirstName(),this.getLastName(),this.getSalary());
    }
}
