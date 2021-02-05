package com.qa.demo.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String description;

	@NotNull
	private Boolean isDone = false;

	@ManyToOne
	private ListDomain myList;

	// Create task
	public TaskDomain(@NotNull String description, @NotNull Boolean isDone, ListDomain myList) {
		super();
		this.description = description;
		this.isDone = isDone;
		this.myList = myList;
	}

}
