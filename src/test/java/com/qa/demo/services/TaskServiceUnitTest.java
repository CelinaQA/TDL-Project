package com.qa.demo.services;

import java.util.ArrayList;
import java.util.List;

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
	
	@MockBean //same as @Mock
	private TaskRepo mockedRepo;
	
	@MockBean //same as @Mock
	private ModelMapper mockedMapper;
	
	@Autowired //same as @InjectMocks
	private TaskService service;
	
	@Test
	public void readAll() {
		//RESOURCES
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
		
		//RULES
		Mockito.when(this.mockedMapper.map(testTaskDomain1, TaskDTO.class)).thenReturn(testTaskDTO1);
		Mockito.when(this.mockedMapper.map(testTaskDomain2, TaskDTO.class)).thenReturn(testTaskDTO2);
		Mockito.when(this.mockedRepo.findAll()).thenReturn(taskDomainList);
				
		//ACTIONS
		List<TaskDTO> result = this.service.readAll();
					
		//ASSERTIONS
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(taskListDTO);
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(taskListDTO);
		
		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testTaskDomain1, TaskDTO.class);
		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testTaskDomain2, TaskDTO.class);
		Mockito.verify(this.mockedRepo, Mockito.times(1)).findAll();	
	}	

}
