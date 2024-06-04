package com.tms.ticketing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.ticketing_system.model.Department;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

	Department findByName(String department);

}
