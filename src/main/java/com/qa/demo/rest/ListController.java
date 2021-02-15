package com.qa.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.demo.persistence.domain.ListDomain;
import com.qa.demo.persistence.dtos.ListDTO;
import com.qa.demo.services.ListService;

@CrossOrigin
@RestController
@RequestMapping("/List")
public class ListController {

	private ListService service;

	@Autowired
	public ListController(ListService service) {
		super();
		this.service = service;
	}

	// GET
	@GetMapping("/readAll")
	public ResponseEntity<List<ListDTO>> readAll() {
		return ResponseEntity.ok(this.service.readAll());
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<ListDTO> readList(@PathVariable("id") Long id) {
		return ResponseEntity.ok(this.service.readList(id));
	}

	// POST
	@PostMapping("/create")
	public ResponseEntity<ListDTO> create(@RequestBody ListDomain list) {
		return new ResponseEntity<ListDTO>(this.service.create(list), HttpStatus.CREATED);
	}

	// PUT
	@PutMapping("/update/{id}")
	public ResponseEntity<ListDTO> updateList(@PathVariable("id") Long id, @RequestBody ListDomain list) {
		return new ResponseEntity<ListDTO>(this.service.update(id, list), HttpStatus.ACCEPTED);
	}

	// DELETE
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteList(@PathVariable("id") Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
