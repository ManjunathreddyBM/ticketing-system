package com.tms.ticketing_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tms.ticketing_system.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	@Query("Select t from Ticket t where t.user.email= :email")
	List<Ticket> findTicketsByUserEmail(@Param("email") String email);

	@Query("Select t from Ticket t where t.department.name= :department")
	List<Ticket> findTicketsByDepartment(@Param("department") String dept);

}
