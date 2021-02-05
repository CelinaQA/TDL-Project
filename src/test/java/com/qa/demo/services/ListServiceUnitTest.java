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

import com.qa.demo.persistence.domain.ListDomain;
import com.qa.demo.persistence.dtos.ListDTO;
import com.qa.demo.persistence.repos.ListRepo;

@SpringBootTest
public class ListServiceUnitTest {

	@MockBean // same as @Mock
	private ListRepo mockedRepo;

	@MockBean // same as @Mock
	private ModelMapper mockedMapper;

	@Autowired // same as @InjectMocks
	private ListService service;

	@Test
	public void readAll() {
		// RESOURCES
		ListDomain testListDomain1 = new ListDomain(1L, "OneList", null);
		ListDomain testListDomain2 = new ListDomain(2L, "TwoList", null);
		List<ListDomain> listDomainList = new ArrayList<>();
		listDomainList.add(testListDomain1);
		listDomainList.add(testListDomain2);

		ListDTO testListDTO1 = new ListDTO(1L, "OneList", null);
		ListDTO testListDTO2 = new ListDTO(2L, "TwoList", null);
		List<ListDTO> listListDTO = new ArrayList<>();
		listListDTO.add(testListDTO1);
		listListDTO.add(testListDTO2);

		// RULES
		Mockito.when(this.mockedMapper.map(testListDomain1, ListDTO.class)).thenReturn(testListDTO1);
		Mockito.when(this.mockedMapper.map(testListDomain2, ListDTO.class)).thenReturn(testListDTO2);
		Mockito.when(this.mockedRepo.findAll()).thenReturn(listDomainList);

		// ACTIONS
		List<ListDTO> result = this.service.readAll();

		// ASSERTIONS
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(listListDTO);
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(listListDTO);

		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testListDomain1, ListDTO.class);
		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testListDomain2, ListDTO.class);
		Mockito.verify(this.mockedRepo, Mockito.times(1)).findAll();
	}
	
	@Test
	public void readList() {
		// RESOURCES
		ListDomain testListDomain = new ListDomain(1L, "OneList", null);
		ListDTO testListDTO = new ListDTO(1L, "OneList", null);

		// RULES
		Mockito.when(this.mockedMapper.map(testListDomain, ListDTO.class)).thenReturn(testListDTO);
		Mockito.when(this.mockedRepo.findById(testListDomain.getId())).thenReturn(Optional.of(testListDomain));

		// ACTIONS
		ListDTO result = this.service.readList(1L);

		// ASSERTIONS
		Assertions.assertThat(result).isEqualTo(testListDTO);

		Mockito.verify(this.mockedRepo, Mockito.times(1)).findById(1L);
		Mockito.verify(this.mockedMapper, Mockito.times(1)).map(testListDomain, ListDTO.class);

	}
	
	

}
