package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;

	@Transactional
	public Department saveDepartment(Department department) {
		return repository.save(department);
	}

	public List<Department> findAll() {
		return repository.findAll();
	}

	public Department getById(Long id) throws Exception {
		return repository.findById(id)
				.orElseThrow(() -> new Exception("Department doenst exists"));
	}

}
