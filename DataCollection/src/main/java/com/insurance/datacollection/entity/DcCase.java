package com.insurance.datacollection.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Case_Details")
@Data
public class DcCase {


	  @Id
	    private Long caseNumber;

	    private Integer appId;

	    private Long planId;

}