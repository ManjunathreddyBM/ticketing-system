package com.tms.ticketing_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.ticketing_system.model.Department;
import com.tms.ticketing_system.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	public DepartmentRepository departmentRepository;
	
	public Department addDept(String name) {
		Department d = new Department();
		d.setName(name);
		return departmentRepository.save(d);
	}

	public Department isDeptExsists(String name) {
		return departmentRepository.findByName(name);
	
	}

}
