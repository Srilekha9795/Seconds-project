package com.insurance.datacollection.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurance.datacollection.entity.Income;

public interface IncomeRepo extends JpaRepository<Income,Integer> {
	Boolean existsByCaseNumber(Long caseNumber);
    Income findByCaseNumber(Long caseNumber);
}