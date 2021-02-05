package com.qa.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.demo.persistence.domain.TaskDomain;
import com.qa.demo.persistence.dtos.TaskDTO;
import com.qa.demo.persistence.repos.TaskRepo;

@SpringBootTest
public class TaskServiceUnitTest {

	@MockBean // same as @Mock
	private TaskRepo mockedRepo;

	@MockBean // same as @Mock
	private ModelMapper mockedMapper;

	@Autowired // same as @InjectMocks
	private TaskService service;

	@Test
	public void readAll() {
		// RESOURCES
		TaskDomain testTaskDomain1 = new TaskDomain(1L, "OneTask", false, null);
		TaskDomain testTaskDomain2 = new TaskDomain(2L, "TwoTask", false, null);
		List<TaskDomain> taskDomainList = new ArrayList<>();
		taskDomainList.add(testTaskDomain1);
		taskDomainList.add(testTaskDomain2);

		TaskDTO testTaskDTO1 = new TaskDTO(1L, "OneTask", false);
		TaskDTO testTaskDTO2 = new TaskDTO(2L, "TwoTask", false);
		List<TaskDTO> taskListDTO = new ArrayList<>();
		taskListDTO.add(testTaskDTO1);
		taskListDTO.add(testTaskDTO2);

		// RULES
		Mockito.when(this.mockedMapper.map(testTaskDomain1, TaskDTO.class)).thenReturn(testTaskDTO1);
		Mockito.when(this.mockedMapper.map(testTaskDomain2, TaskDTO.class)).thenReturn(testTaskDTO2);
		Mockito.when(this.mockedRepo.findAll()).thenReturn(taskDomainList);

		// ACTIONS
		List<TaskDTO> result = this.service.readAll();

		// ASSERTIONS
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(taskListDTO);
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(taskListDTO);

		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testTaskDomain1, TaskDTO.class);
		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testTaskDomain2, TaskDTO.class);
		Mockito.verify(this.mockedRepo, Mockito.times(1)).findAll();
	}

	@Test
	public void readTask() {
		// RESOURCES
		TaskDomain testTaskDomain = new TaskDomain(1L, "OneTask", false, null);
		TaskDTO testTaskDTO = new TaskDTO(1L, "OneTask", false);

		// RULES
		Mockito.when(this.mockedMapper.map(testTaskDomain, TaskDTO.class)).thenReturn(testTaskDTO);
		Mockito.when(this.mockedRepo.findById(testTaskDomain.getId())).thenReturn(Optional.of(testTaskDomain));

		// ACTIONS
		TaskDTO result = this.service.readTask(1L);

		// ASSERTIONS
		Assertions.assertThat(result).isEqualTo(testTaskDTO);

		Mockito.verify(this.mockedRepo, Mockito.times(1)).findById(1L);
		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testTaskDomain, TaskDTO.class);

	}

	@Test
	public void create() {
		// RESOURCES
		TaskDomain testTaskDomain = new TaskDomain(1L, "OneTask", false, null);
		TaskDTO testTaskDTO = new TaskDTO(1L, "OneTask", false);

		// RULES
		Mockito.when(this.mockedRepo.save(Mockito.any(TaskDomain.class))).thenReturn(testTaskDomain);
		Mockito.when(this.mockedMapper.map(testTaskDomain, TaskDTO.class)).thenReturn(testTaskDTO);

		// ACTIONS
		TaskDTO result = this.service.create(testTaskDomain);

		// ASSERTIONS
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(testTaskDTO);
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(testTaskDTO); // compares values of fields rather than object instances

		Mockito.verify(this.mockedRepo, Mockito.times(1)).save(Mockito.any(TaskDomain.class));
		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testTaskDomain, TaskDTO.class);
	}

	@Test
	public void update() {
		// RESOURCES
		TaskDomain testTaskDomain = new TaskDomain(1L, "OneTask", false, null);
		TaskDTO testTaskDTO = new TaskDTO(1L, "OneTask", false);

		Long id = 1L;

		// RULES
		Mockito.when(this.mockedRepo.findById(testTaskDomain.getId())).thenReturn(Optional.of(testTaskDomain));
		Mockito.when(this.mockedRepo.save(Mockito.any(TaskDomain.class))).thenReturn(testTaskDomain);
		Mockito.when(this.mockedMapper.map(testTaskDomain, TaskDTO.class)).thenReturn(testTaskDTO);

		// ACTIONS
		TaskDTO result = this.service.update(id, testTaskDomain);

		// ASSERTIONS
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(testTaskDTO);
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(testTaskDTO);

		Mockito.verify(this.mockedRepo, Mockito.times(1)).findById(id);
		Mockito.verify(this.mockedRepo, Mockito.times(1)).save(testTaskDomain);
		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testTaskDomain, TaskDTO.class);

	}

	@Test
	public void delete() {
		// RESOURCES
		TaskDomain testTaskDomain = new TaskDomain(1L, "OneTask", false, null);

		// RULES
		Mockito.when(this.mockedRepo.existsById(1L)).thenReturn(false);

		// ACTIONS
		boolean result = this.service.delete(1L);

		// ASSERTIONS
		Assertions.assertThat(result).isTrue();

		Mockito.verify(this.mockedRepo, Mockito.times(1)).deleteById(1L);

	}

}
