package com.qa.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.demo.persistence.domain.TaskDomain;
import com.qa.demo.persistence.dtos.TaskDTO;
import com.qa.demo.persistence.repos.TaskRepo;

@Service
public class TaskService {

	private TaskRepo repo;
	private ModelMapper mapper;

	@Autowired
	public TaskService(TaskRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private TaskDTO mapToDTO(TaskDomain task) {
		return this.mapper.map(task, TaskDTO.class);
	}

	// GET
	public List<TaskDTO> readAll() {
		List<TaskDomain> dbTask = this.repo.findAll();
		List<TaskDTO> resultTask = dbTask.stream().map(this::mapToDTO).collect(Collectors.toList());
		return resultTask;
	}

	public TaskDTO readTask(Long id) {
		TaskDomain result = this.repo.findById(id).orElseThrow();
		return this.mapToDTO(result);
	}

	// POST
	public TaskDTO create(TaskDomain task) {
		return this.mapToDTO(this.repo.save(task));
	}

	// UPDATE
	public TaskDTO update(Long id, TaskDomain newTask) {
		TaskDomain dbEntry = this.repo.findById(id).orElseThrow();
		newTask.setId(id);
		TaskDTO result = this.mapToDTO(this.repo.save(newTask));
		return result;
	}

	// DELETE
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		boolean exists = this.repo.existsById(id);
		return !exists;
	}

}
