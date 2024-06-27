package com.tms.ticketing_system.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.ticketing_system.dto.CreateTicket;
import com.tms.ticketing_system.dto.ResponseEntity;
import com.tms.ticketing_system.model.Department;
import com.tms.ticketing_system.model.Ticket;
import com.tms.ticketing_system.model.User;
import com.tms.ticketing_system.repository.DepartmentRepository;
import com.tms.ticketing_system.repository.TicketRepository;
import com.tms.ticketing_system.repository.UserRepository;


@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	 
	public ResponseEntity<Ticket> createTicket(CreateTicket createTicket) {
		Department dept = departmentRepository.findByName(createTicket.getDepartment());
		if(dept == null)
			return new ResponseEntity("Department "+ createTicket.getDepartment() + " Does not exist", null);
		
		User user = userRepository.findByEmail(createTicket.getEmail());
		if(user == null)
			return new ResponseEntity("User "+ createTicket.getEmail() + " Does not exist", null);
		
		Ticket ticket = new Ticket();
		ticket.setCreationDate(new Date());
		ticket.setDepartment(dept);
		ticket.setDescription(createTicket.getDescription());
		ticket.setLastUpdated(new Date());
		ticket.setPriority(2);
		ticket.setStatus("NEW");
		ticket.setTitle(createTicket.getTitle());
		ticket.setUser(user);
		Ticket newTicket = ticketRepository.save(ticket);
		if(newTicket != null)
			return new ResponseEntity<Ticket>("Ticket Craeted Successfully", newTicket);
		else
			return new ResponseEntity("Error creating ticket", null);
			
	}
	
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	public List<Ticket> getTickets(String name) {
		return ticketRepository.findTicketsByUserEmail(name);
	}
	
	public List<Ticket> getTicketsByDept(String dept) {
		return ticketRepository.findTicketsByDepartment(dept);
	}
}
