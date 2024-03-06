package com.example.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeRowMapper;
import com.example.employee.repository.EmployeeRepository;

import java.util.*;

import javax.annotation.PostConstruct;

@Service
public class EmployeeH2Service implements EmployeeRepository {

    @Autowired
    private JdbcTemplate db;

    private Map<Integer, Employee> empMap;

    @PostConstruct
    public void init() {
        empMap = getEmpMap();
    }

    @Override
    public Map<Integer, Employee> getEmpMap() {
        Map<Integer, Employee> employeeMap = new HashMap<>();
        List<Employee> employees = db.query("SELECT * FROM EMPLOYEELIST", new EmployeeRowMapper());
        for (Employee emp : employees) {
            employeeMap.put(emp.getEmployeeId(), emp);
        }
        return employeeMap;
    }

    @Override
    public Employee addEmp(Employee employee) {
        db.update("INSERT INTO EMPLOYEELIST (employeeName, email, department) VALUES(?,?,?)", employee.getEmployeeName(),
                employee.getEmail(), employee.getDepartment());
        empMap.put(employee.getEmployeeId(), employee);
        return employee;
    }

    @Override
    public Employee getEmpById(int employeeId) {
        Employee employee = empMap.get(employeeId);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return employee;
    }

    @Override
    public Employee updateEmp(int employeeId, Employee employee) {
        if (empMap.containsKey(employeeId)) {
            Employee existingEmployee = empMap.get(employeeId);
            if (employee.getEmployeeName() != null) {
                existingEmployee.setEmployeeName(employee.getEmployeeName());
            }
            if (employee.getEmail() != null) {
                existingEmployee.setEmail(employee.getEmail());
            }
            if (employee.getDepartment() != null) {
                existingEmployee.setDepartment(employee.getDepartment());
            }
            empMap.put(employeeId, existingEmployee);
            return existingEmployee;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
    }

    @Override
    public void deleteEmployee(int empId) {
        if (empMap.containsKey(empId)) {
            db.update("DELETE FROM EMPLOYEELIST WHERE EMPLOYEEID = ?", empId);

            empMap.remove(empId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}