package com.qa.demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.persistence.domain.TaskDomain;
import com.qa.demo.persistence.dtos.TaskDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "test")
public class TaskControllerIntegrationTest {

	@Autowired
	private MockMvc mock;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private ObjectMapper jsonifier;

	private TaskDTO mapToDTO(TaskDomain task) {
		return this.mapper.map(task, TaskDTO.class);
	}

	private final int id = 1;

	// GET
	@Test
	public void readAll() throws Exception {
		// RESOURCES
		TaskDTO task1 = new TaskDTO(1L, "TaskOne", false);
		TaskDTO task2 = new TaskDTO(2L, "TaskTwo", false);
		TaskDTO task3 = new TaskDTO(3L, "TaskThree", false);

		List<TaskDTO> expectedResult = new ArrayList<>();
		expectedResult.add(task1);
		expectedResult.add(task2);
		expectedResult.add(task3);

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
				"http://localhost:8080/Task/readAll");

		// EXPECTATIONS
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

		// ACTION
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);

	}

	@Test
	public void readTask() throws Exception {

		// RESOURCES
		TaskDTO expectedResult = new TaskDTO(1L, "TaskOne", false);

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
				"http://localhost:8080/Task/read/" + id);

		// EXPECTATIONS
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

		// ACTION
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);

	}

	// POST
	@Test
	public void create() throws Exception {
		// RESOURCES
		TaskDomain contentBody = new TaskDomain(1L, "TaskOne", false, null);
		TaskDTO expectedResult = mapToDTO(contentBody);

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.POST, "http://localhost:8080/Task/create").contentType(MediaType.APPLICATION_JSON)
				.content(jsonifier.writeValueAsString(contentBody)).accept(MediaType.APPLICATION_JSON);

		// EXPECTATIONS
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

		// ACTION
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	// PUT
	@Test
	public void updateTask() throws Exception {
		// RESOURCES
		TaskDomain contentBody = new TaskDomain(1L, "TaskOne", false, null);
		TaskDTO expectedResult = mapToDTO(contentBody);

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.PUT, "http://localhost:8080/Task/update/" + id)
				.contentType(MediaType.APPLICATION_JSON).content(jsonifier.writeValueAsString(contentBody))
				.accept(MediaType.APPLICATION_JSON);

		// EXPECTATIONS
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isAccepted();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

		// ACTION
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);

	}

	// DELETE
	@Test
	public void deleteTask() throws Exception {
		// RESOURCES

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
				"http://localhost:8080/Task/delete/" + id);

		// EXPECTATIONS
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isNoContent();

		// ACTION
		this.mock.perform(mockRequest).andExpect(matchStatus);

	}

}
