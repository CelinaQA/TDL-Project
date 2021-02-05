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
import com.qa.demo.persistence.domain.ListDomain;
import com.qa.demo.persistence.domain.TaskDomain;
import com.qa.demo.persistence.dtos.ListDTO;
import com.qa.demo.persistence.dtos.TaskDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "test")
public class ListControllerIntegrationTest {

	@Autowired
	private MockMvc mock;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private ObjectMapper jsonifier;

	private ListDTO mapToDTO(ListDomain list) {
		return this.mapper.map(list, ListDTO.class);
	}

	private final int id = 1;
	List<TaskDomain> taskListDomain = new ArrayList<>();
	List<TaskDTO> taskListDTO = new ArrayList<>();

	// GET
	@Test
	public void readAll() throws Exception {
		// RESOURCES
		ListDTO list1 = new ListDTO(1L, "ListOne", taskListDTO);
		ListDTO list2 = new ListDTO(2L, "ListTwo", taskListDTO);
		ListDTO list3 = new ListDTO(3L, "ListThree", taskListDTO);

		List<ListDTO> expectedResult = new ArrayList<>();
		expectedResult.add(list1);
		expectedResult.add(list2);
		expectedResult.add(list3);

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
				"http://localhost:8080/List/readAll");

		// EXPECTATIONS
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

		// ACTION
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);

	}

	@Test
	public void readList() throws Exception {

		// RESOURCES
		ListDTO expectedResult = new ListDTO(1L, "ListOne", taskListDTO);

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
				"http://localhost:8080/List/read/" + id);

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
		ListDomain contentBody = new ListDomain(1L, "ListOne", taskListDomain);
		ListDTO expectedResult = mapToDTO(contentBody);

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.POST, "http://localhost:8080/List/create").contentType(MediaType.APPLICATION_JSON)
				.content(jsonifier.writeValueAsString(contentBody)).accept(MediaType.APPLICATION_JSON);

		// EXPECTATIONS
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(jsonifier.writeValueAsString(expectedResult));

		// ACTION
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	// PUT
	@Test
	public void updateList() throws Exception {
		// RESOURCES
		ListDomain contentBody = new ListDomain(1L, "ListOne", taskListDomain);
		ListDTO expectedResult = mapToDTO(contentBody);

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.PUT, "http://localhost:8080/List/update/" + id)
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
	public void deleteList() throws Exception {
		// RESOURCES

		// REQUEST
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
				"http://localhost:8080/List/delete/" + id);

		// EXPECTATIONS
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isNoContent();

		// ACTION
		this.mock.perform(mockRequest).andExpect(matchStatus);

	}

}
