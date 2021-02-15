package com.qa.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.demo.persistence.domain.ListDomain;
import com.qa.demo.persistence.dtos.ListDTO;
import com.qa.demo.persistence.repos.ListRepo;

@Service
public class ListService {

	private ListRepo repo;
	private ModelMapper mapper;

	@Autowired
	public ListService(ListRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private ListDTO mapToDTO(ListDomain list) {
		return this.mapper.map(list, ListDTO.class);
	}

	// GET
	public List<ListDTO> readAll() {
		List<ListDomain> dbList = this.repo.findAll();
		List<ListDTO> resultList = dbList.stream().map(this::mapToDTO).collect(Collectors.toList());
		return resultList;
	}

	public ListDTO readList(Long id) {
		ListDomain result = this.repo.findById(id).orElseThrow();
		return this.mapToDTO(result);
	}

	// POST
	public ListDTO create(ListDomain list) {
		return this.mapToDTO(this.repo.save(list));
	}

	// UPDATE
	public ListDTO update(Long id, ListDomain newList) {
		ListDomain dbEntry = this.repo.findById(id).orElseThrow();
		newList.setId(id);
		ListDTO result = this.mapToDTO(this.repo.save(newList));
		return result;
	}

	// DELETE
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		boolean exists = this.repo.existsById(id);
		return !exists;
	}

}
