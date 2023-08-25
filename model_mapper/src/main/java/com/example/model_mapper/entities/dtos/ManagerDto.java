package com.example.model_mapper.entities.dtos;

import com.example.model_mapper.entities.Employee;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ManagerDto {

    @XmlElement(name = "firstName")
    private String firstName;
    @XmlElement(name = "lastName")
    private String lastName;
    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee")
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }
}
