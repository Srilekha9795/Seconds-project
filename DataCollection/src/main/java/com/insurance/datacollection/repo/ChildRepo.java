package com.insurance.datacollection.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurance.datacollection.entity.Children;

public interface ChildRepo extends JpaRepository<Children,Integer> {
	 Boolean existsByCaseNumber(Long caseNumber);

	    List<Children> findByCaseNumber(Long caseNumber);

}
