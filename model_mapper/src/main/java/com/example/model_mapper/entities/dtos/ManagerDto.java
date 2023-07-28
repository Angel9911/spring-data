package com.example.model_mapper.entities.dtos;

import com.example.model_mapper.entities.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> employees;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        String employees = this.employees
                .stream()
                .map(EmployeeDto::toString)
                .map(e -> "  - " + e)
                .collect(Collectors.joining("\n"));
        return String.format(" %s %s | Employees: %d%n%s%n",this.firstName,this.lastName, this.employees.size(),employees);
    }
}
