package com.insurance.datacollection.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.datacollection.dto.Summary;
import com.insurance.datacollection.exception.CaseNumNotFoundException;
import com.insurance.datacollection.service.DataCollectionService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/summary")
public class SummaryController {
    private DataCollectionService dataCollectionService;
    public SummaryController(DataCollectionService dataCollectionService) {
        this.dataCollectionService = dataCollectionService;
    }

    @GetMapping("/get/{caseNumber}")
    public ResponseEntity<Summary> getAllSavedData(@PathVariable Long caseNumber) throws CaseNumNotFoundException{
        Summary summary = dataCollectionService.getSavedData(caseNumber);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

}
