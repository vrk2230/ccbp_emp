package com.example.employee.repository;

import java.util.Map;

import com.example.employee.model.Employee;

public interface EmployeeRepository {
    Map<Integer, Employee> getEmpMap();

    Employee addEmp(Employee employee);

    Employee getEmpById(int employeeId);

    Employee updateEmp(int employeeId, Employee employee);

    void deleteEmployee(int employeeId);
}