package com.insurance.datacollection.dto;

import lombok.Data;

@Data
public class Summary {

	 private Integer appId;
	    private PlanReq planRequest;
	    private IncomeReq incomeRequest;
	    private EducationReq educationRequest;
	    private ChildReqWrapper childrenRequestWrapper;
}
