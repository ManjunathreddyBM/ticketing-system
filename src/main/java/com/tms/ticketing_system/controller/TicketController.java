package com.tms.ticketing_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.ticketing_system.dto.CreateTicket;
import com.tms.ticketing_system.dto.ResponseEntity;
import com.tms.ticketing_system.model.Ticket;
import com.tms.ticketing_system.service.TicketService;


@RestController
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
    private TicketService ticketService;
	
	@PostMapping("/createTicket")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Ticket> createTicket(@RequestBody CreateTicket createTicket) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    System.out.println("Authenticated User: " + authentication.getName());
	    System.out.println("Roles: " + authentication.getAuthorities());
	    if(!authentication.getName().equalsIgnoreCase(createTicket.getEmail())) {
			return new ResponseEntity<Ticket>("Invalid email id. Please enter the valid email id ", null);
	    }
		return ticketService.createTicket(createTicket);
		
	}
	
	private String viewAllTickets() {
		return null;
	}

	private String viewTicketByTicketId() {
		return null;
	}

	private String viewTicketsByUserName() {
		return null;
	}
	
	private String viewTicketsByDept() {
		return null;
	}

	private String deleteTicket() {
		return null;
	}
	
}
