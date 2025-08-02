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
@Table(name="Education_Details")
@Data
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name="Highest_Degree")
    private  String highestDegree;

    @Column(name="Graduation_Year")
    private Integer graduationYear;

    @Column(name="University_Name")
    private  String universityName;

    @Column(name="Case_Num")
    private Long caseNumber;


    @Column(name="Created_At",updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name="Updated_At",insertable = false)
    @UpdateTimestamp
    private LocalDate updatedAt;



}