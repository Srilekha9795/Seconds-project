package com.insurance.datacollection.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.datacollection.dto.PlanReq;
import com.insurance.datacollection.exception.CaseNumNotFoundException;
import com.insurance.datacollection.exception.DuplicateCaseNumException;
import com.insurance.datacollection.exception.NoPlanFoundException;
import com.insurance.datacollection.service.DataCollectionService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/plan/categories")
public class PlanController {
    private DataCollectionService dataCollectionService;

    public PlanController(DataCollectionService dataCollectionService) {
        this.dataCollectionService = dataCollectionService;
    }

    @GetMapping("/allplans")
    public ResponseEntity<Map<Long,String>> getAllPlans(){
        Map<Long,String> mp = dataCollectionService.getPlanNames();
        return new ResponseEntity<>(mp, HttpStatus.OK);

    }

    @PostMapping("/addplan")
    public ResponseEntity<Long> addPlan(@RequestBody PlanReq request) throws NoPlanFoundException, CaseNumNotFoundException, DuplicateCaseNumException {


       Long isPlanSaved = dataCollectionService.
               addPlan(request);
       // String message = isPlanSaved ? "Plan Saved Successful" : "Plan Saved Failed";

        return new ResponseEntity<>(isPlanSaved,HttpStatus.OK);

    }
}
