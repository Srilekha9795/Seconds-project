package com.insurance.datacollection.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.datacollection.dto.EducationReq;
import com.insurance.datacollection.exception.CaseNumNotFoundException;
import com.insurance.datacollection.exception.DuplicateCaseNumException;
import com.insurance.datacollection.service.DataCollectionService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/education")
public class EducationController {

    private DataCollectionService dataCollectionService;
    public EducationController(DataCollectionService dataCollectionService) {
        this.dataCollectionService = dataCollectionService;
    }

    @PostMapping("/addeducation")
   public ResponseEntity<Long> saveEducation(@RequestBody EducationReq request) throws DuplicateCaseNumException, CaseNumNotFoundException {
        Long caseNum = dataCollectionService.saveEducationData(request);
        return new ResponseEntity<>(caseNum, HttpStatus.OK);
    }

}