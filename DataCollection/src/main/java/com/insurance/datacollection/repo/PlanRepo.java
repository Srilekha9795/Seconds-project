package com.insurance.datacollection.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurance.datacollection.entity.Plan;

public interface PlanRepo extends JpaRepository<Plan,Integer> {
	Boolean existsByPlanName(String planName);
    Plan findByCaseNumber(Long caseNumber);
    Boolean existsByCaseNumber(Long CaseNumber);
}