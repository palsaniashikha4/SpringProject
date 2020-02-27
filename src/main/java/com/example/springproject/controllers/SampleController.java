package com.example.springproject.controllers;

import com.example.springproject.model.Employee;
import com.example.springproject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class SampleController {
    @RequestMapping("/")
    public String sayHello(){
        return "Hello!";
    }

    @RequestMapping("/redirect")
    public void redirectHello(HttpServletResponse httpResponse) throws IOException {
        //httpResponse.sendRedirect("/hello");
        httpResponse.setHeader("Location","/hello");
        httpResponse.setStatus(302);
    }

    @RequestMapping("/hello")
    public String helloAgain(){
        return "Hello Again!";
    }

    @Autowired
    EmployeeService empService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Employee> getallEmployees(){
        System.out.println(this.getClass().getName()+" - Get all employees method");
        return empService.getEmployees();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Employee getEmployeeById(@PathVariable int id) throws Exception{
        System.out.println(this.getClass().getName()+" - Get employee by id");
        Optional<Employee> obj = empService.getEmployeeById(id);
        if(!obj.isPresent()){
            throw new Exception("Id "+id+" not found");
        }
        return obj.get();
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Employee addEmployee(@RequestBody Employee emp){
        System.out.println(this.getClass().getName()+" - Add employee");
        return empService.addEmployee(emp);
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    public Employee updateEmployee(@RequestBody Employee emp,@PathVariable int id) throws Exception{
        System.out.println(this.getClass().getName()+" - Update employee");
        Optional<Employee> oldData = empService.getEmployeeById(id);
        if(!oldData.isPresent()){
            throw new Exception("not found with id "+id);
        }
        if(emp.getName()==null || emp.getName().isEmpty()) {
            emp.setName(oldData.get().getName());
        }if(emp.getDepartment()==null || emp.getDepartment().isEmpty()){
            emp.setDepartment(oldData.get().getDepartment());
        }if(emp.getSalary()<=0){
            emp.setSalary(oldData.get().getSalary());
        }
        emp.setId(id);
        return empService.updateEmployee(emp);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public void deleteEmployeeById(@PathVariable int id) throws Exception{
        System.out.println(this.getClass().getName()+" - Delete employee by id");
        Optional<Employee> empData = empService.getEmployeeById(id);
        if(!empData.isPresent()){
            throw new Exception("not found with id "+id);
        }
        empService.deleteEmployeeById(id);
    }

    @RequestMapping(value = "deleteAll",method = RequestMethod.DELETE)
    public void deleteAllEmployees(){
        System.out.println(this.getClass().getName()+" - Delete all employees");
        empService.deleteAllEmployees();
    }
}
