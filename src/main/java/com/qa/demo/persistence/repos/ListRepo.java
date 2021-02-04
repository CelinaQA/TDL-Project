package com.qa.demo.persistence.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.demo.persistence.domain.ListDomain;

@Repository
public interface ListRepo extends JpaRepository<ListDomain, Long> {

}
