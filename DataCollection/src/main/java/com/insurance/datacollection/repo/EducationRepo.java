package com.insurance.datacollection.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurance.datacollection.entity.Education;

public interface EducationRepo extends JpaRepository<Education,Integer> {
	 Boolean existsByCaseNumber(Long caseNumber);
	    Education findByCaseNumber(Long caseNumber);

}