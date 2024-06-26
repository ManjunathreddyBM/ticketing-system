package com.tms.ticketing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.ticketing_system.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
