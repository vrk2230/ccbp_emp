package com.example.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeH2Service;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeH2Service empService;

    @GetMapping("/employees")
    public Map<Integer, Employee> getEmployees() {
        return empService.getEmpMap();
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return empService.addEmp(employee);
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmpById(@PathVariable("employeeId") int employeeId) {
        return empService.getEmpById(employeeId);
    }

    @PutMapping("/employees/{employeeId}")
    public Employee updatEmployee(@PathVariable("employeeId") int employeeId, @RequestBody Employee employee) {
        return empService.updateEmp(employeeId, employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") int employeeId) {
        empService.deleteEmployee(employeeId);
    }

}