package com.example.springproject.service;

import com.example.springproject.dao.EmployeeDao;
import com.example.springproject.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeDao employeeDao;

    public List<Employee> getEmployees(){
        return employeeDao.findAll();
    }
    public Optional<Employee> getEmployeeById(int empId){
        return employeeDao.findById(empId);
    }
    public Employee addEmployee(Employee emp){
        return  employeeDao.save(emp);
    }
    public Employee updateEmployee(Employee emp){
        return employeeDao.save(emp);
    }
    public void deleteEmployeeById(int empId){
        employeeDao.deleteById(empId);
    }
    public void deleteAllEmployees(){
        employeeDao.deleteAll();
    }
}
