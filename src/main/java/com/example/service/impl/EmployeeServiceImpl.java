package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeService;

//@Transactional no longer have to use 
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;

	@Autowired //  only have to use Autowire if there are more than one constructors 
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
		}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
      //the method could be written like this if not using lambdas    
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			return employee.get();
		}
		else {
			throw new ResourceNotFoundException("Employee", "Id", id);
		}
			
		// Using lambdas expression
//		return employeeRepository.findById(id).orElseThrow(() -> 
//		           new ResourceNotFoundException("Employee","Id",id ));
	}

	
	  @Override
	 public Employee updateEmployee(Employee employee, long id) {
     // we need to check weather the employee is with the given id exist in DB or not
	 	Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
	 			() -> new ResourceNotFoundException("Employee", "Id", id));
	 	
	 	// get current employees information
	 	existingEmployee.setFirstName(employee.getFirstName());
	 	existingEmployee.setLastName(employee.getLastName());
	 	existingEmployee.setEmail(employee.getEmail());
	 	
	 	// save existing employees information to database
	 	employeeRepository.save(existingEmployee);
      	
	 	return existingEmployee;
	 }

	@Override
	public void deleteEmployee (long id) {
		
		// check whether an employee exist in a DB
		employeeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee", "Id", id));
		employeeRepository.deleteById(id);
	
	}
	

}
