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

@Data
@Table(name="Children_Details")
@Entity
public class Children {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer childId;

    @Column(name="Child_Name")
    private String childName;

    @Column(name="Child_Age")
    private Integer childAge;


    @Column(name="Child_Ssn")
    private Long childSsn;

    @Column(name="Case_Num")
    private Long caseNumber;

    @Column(name="Created_At",updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name="Updated_At",insertable = false)
    @UpdateTimestamp
    private LocalDate updatedAt;
}