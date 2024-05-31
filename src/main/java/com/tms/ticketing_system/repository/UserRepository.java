package com.tms.ticketing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.ticketing_system.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
