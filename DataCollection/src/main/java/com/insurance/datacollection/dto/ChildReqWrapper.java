package com.insurance.datacollection.dto;

import java.util.List;

//import com.example.EligibilityDeterminationService.dto.ChildrenRequest;

import lombok.Data;

@Data
public class ChildReqWrapper {

	private Long caseNumber;
	private List<ChildrenRequest> childrenRequests;

}
