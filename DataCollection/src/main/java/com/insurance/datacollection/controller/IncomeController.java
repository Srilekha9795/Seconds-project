package com.insurance.datacollection.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.datacollection.dto.IncomeReq;
import com.insurance.datacollection.exception.CaseNumNotFoundException;
import com.insurance.datacollection.exception.DuplicateCaseNumException;
import com.insurance.datacollection.service.DataCollectionService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/income")
public class IncomeController {
    private DataCollectionService dataCollectionService;

    public IncomeController(DataCollectionService dataCollectionService) {
        this.dataCollectionService = dataCollectionService;
    }

    @PostMapping("/addincome")
    public ResponseEntity<Long> saveIncome(@RequestBody IncomeReq request) throws DuplicateCaseNumException, CaseNumNotFoundException {
        Long caseNum = dataCollectionService.saveIncomeData(request);
        return new ResponseEntity<>(caseNum, HttpStatus.OK);
    }

}
