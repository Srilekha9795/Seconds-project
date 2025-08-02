package com.insurance.datacollection.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurance.datacollection.entity.DcCase;

public interface DcCaseRepo extends JpaRepository<DcCase,Long> {
	Boolean existsByAppId(Integer id);

    DcCase findByPlanId(Long planId);

    List<DcCase> findByAppId(Integer appId);
}
