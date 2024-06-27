package com.tms.ticketing_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			return new ResponseEntity("Invalid email id. Please enter the valid email id ", null);
	    }
		return ticketService.createTicket(createTicket);
		
	}
	
	@GetMapping("getAllTickets")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity viewAllTickets() {
		return new ResponseEntity("Tickets", ticketService.getAllTickets());
	}

	private <T> ResponseEntity<List<T>> viewTicketByTicketId() {
		return null;
	}

	@GetMapping("getTickets")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getTickets() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    System.out.println("Authenticated User: " + authentication.getName());
	    return new ResponseEntity("Tickets",  ticketService.getTickets(authentication.getName()));	   
	}
	
	@GetMapping("getTickets/{username}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity getTicketsByUserName(@PathVariable String username) {
	    return new ResponseEntity("Tickets",  ticketService.getTickets(username));	   
	}
	
	@GetMapping("getTicketsByDept/{department}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity getTicketsByDepartment(@PathVariable String department) {
	    return new ResponseEntity("Tickets",  ticketService.getTicketsByDept(department));	   
	}

	private String viewTicketsByDept() {
		return null;
	}

	private String deleteTicket() {
		return null;
	}
	
}
