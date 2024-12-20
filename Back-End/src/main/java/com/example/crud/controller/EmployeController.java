package com.example.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.model.Employee;
import com.example.crud.repository.EmployeeRepo;


@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class EmployeController {
	
	
	
	@Autowired
	private EmployeeRepo empRepo;
	
	
	//Get All
	@GetMapping("/employee")
	public List<Employee> getAllEmp(){
		return empRepo.findAll();
	}
	
	@PostMapping("/employee")
	public Employee createEmp(@RequestBody Employee employee) {
		return empRepo.save(employee);
	}
	
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee>  getEmpByID(@PathVariable Long id) {
		Employee employee= empRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee "+id+" Not Found!"));
		return ResponseEntity.ok(employee);
	}
	
	
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee updateEmployee){
		Employee employee= empRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee "+id+" Not Found!"));
		employee.setFirstName(updateEmployee.getFirstName());
		employee.setLastName(updateEmployee.getLastName());
		employee.setEmail(updateEmployee.getEmail());
		
		Employee updatedEmployee= empRepo.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee= empRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee "+id+" Not Found!"));
		
		empRepo.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
