package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.service.EmployeeService;

/*
 * restcontroller is a convenient annotation that combines @Controller and @ResponseBody, 
 * which eliminates the need to annotate every request handling method of the controller class with 
 * the class with the @ResponseBody annotation.
 */
@RestController 
@RequestMapping ("/api/employee")
public class EmployeeController {
	
	private EmployeeService employeeService;

	@Autowired //only need this if you do more than one constructor
	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	
	
	// build create employee REST API
	@PostMapping // this allow post http request
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){ 
		/*
		   responseEntity we can provide a complete response by using ResponseEntity
		   like add a header or other feature by using this class
	
		   @RequestBody annotation allows us to retrieve the request's 
		   body and automatically convert it to a java object
		*/
		return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}
	
	
	
	//build get all employees REST API
	@GetMapping
	public List<Employee> getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	
	// build get employee by id REST API
	// http://localhost:8080/api/employee/1 ...("{id}") this gives you a dynamic variable 
	// the {} are saying that the variable can change 
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId){
		return new ResponseEntity<Employee>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
	}
		
	
	 // build update employee REST API
	 @PutMapping("{id}")
	 public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id,
			@RequestBody Employee employee){
		// the @PathVariable("id") allows you to get the variable from mapping
		// the @RequestBody allows the program to turn Json into a java object
		return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
	 }
	 
	 // build delete employee REST API
	 @DeleteMapping("{id}")
	 public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id){
		 
		 // deletes the employee form the db
		 employeeService.deleteEmployee(id);
		
		 return new ResponseEntity<String>("Employee deleted successfully.", HttpStatus.OK);
	 }
	 
	
	
}
