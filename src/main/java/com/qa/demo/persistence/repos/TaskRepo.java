package com.qa.demo.persistence.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.demo.persistence.domain.TaskDomain;

@Repository
public interface TaskRepo extends JpaRepository<TaskDomain, Long> {

}
