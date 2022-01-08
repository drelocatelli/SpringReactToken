package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {

	@Autowired
	private DepartmentService service;

	@GetMapping("/all")
	public ResponseEntity<?> getAllDepartments() {
		List<Department> departments = service.findAll();
		return ResponseEntity.ok(departments);
	}

	@PostMapping("/register")
	public ResponseEntity<?> salvar(@RequestBody DepartmentDTO dto) {
		if (dto.getTitle().isEmpty()) {
			return new ResponseEntity<>("Fields can not be null.", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Department department = Department
				.builder()
				.title(dto.getTitle())
				.build();

		try {
			Department departmentSaved = service.saveDepartment(department);
			return new ResponseEntity<>(departmentSaved, HttpStatus.CREATED);
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
