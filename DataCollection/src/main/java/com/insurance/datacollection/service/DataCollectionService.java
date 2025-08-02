package com.insurance.datacollection.service;

import java.util.Map;

import com.insurance.datacollection.dto.ChildReqWrapper;
import com.insurance.datacollection.dto.EducationReq;
import com.insurance.datacollection.dto.IncomeReq;
import com.insurance.datacollection.dto.PlanReq;
import com.insurance.datacollection.dto.Summary;
import com.insurance.datacollection.exception.AppIdErrorException;
import com.insurance.datacollection.exception.CaseNumNotFoundException;
import com.insurance.datacollection.exception.DuplicateCaseNumException;
import com.insurance.datacollection.exception.NoPlanFoundException;

public interface DataCollectionService {

	 public Long appIdValidate(Integer id) throws AppIdErrorException;

	    public Map<Long,String> getPlanNames();
	    public Long addPlan(PlanReq request) throws NoPlanFoundException, CaseNumNotFoundException, DuplicateCaseNumException;

	    public Long saveIncomeData(IncomeReq incomeRequest) throws CaseNumNotFoundException, DuplicateCaseNumException;

	    public Long saveEducationData(EducationReq educationRequest) throws CaseNumNotFoundException, DuplicateCaseNumException;

	    public Long saveChildrenData(ChildReqWrapper requestWrapper) throws CaseNumNotFoundException, DuplicateCaseNumException;

	    public Summary getSavedData(Long caseNumber) throws CaseNumNotFoundException;
}