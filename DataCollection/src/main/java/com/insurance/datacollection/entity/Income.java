package com.insurance.datacollection.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Income_Details")
@Data
public class Income {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="MONTHLY_INCOME")
    private Double monthlyIncome;

    @Column(name="RENTAL_INCOME")
    private Double rentalIncome;

    @Column(name="PROPERTY_INCOME")
    private Double propertyIncome;

    @Column(name="CASE_NUMBER")
    private Long caseNumber;


    @Column(name="CREATED_AT",updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name="UPDATED_AT",insertable = false)
    @UpdateTimestamp
    private LocalDate updatedAt;

}