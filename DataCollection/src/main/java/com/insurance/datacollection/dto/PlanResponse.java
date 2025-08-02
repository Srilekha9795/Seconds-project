package com.insurance.datacollection.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanResponse {

	 private Long planId;
	    private String planName;
	    private LocalDate planStartDate;
	    private LocalDate planEndDate;
	    private String planCategoryName;
	    private String planStatus;
}
