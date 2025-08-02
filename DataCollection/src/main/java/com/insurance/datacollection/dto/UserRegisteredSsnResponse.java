package com.insurance.datacollection.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserRegisteredSsnResponse {
	 private Long ssn;
	    private LocalDate dateOfBirth;
	    private String  userName;
}
