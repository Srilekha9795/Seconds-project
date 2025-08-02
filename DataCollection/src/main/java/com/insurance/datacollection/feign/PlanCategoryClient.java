package com.insurance.datacollection.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.insurance.datacollection.dto.PlanResponse;


@FeignClient(name="ADMIN",url="http://localhost:6004")
public interface PlanCategoryClient {
    @GetMapping("api/plan/categories")
    public ResponseEntity<Map<Long,String>> getAllCategories();

    @GetMapping("api/plan/getByCategoryId/{id}")
    public ResponseEntity<List<PlanResponse>> getByCatId(@PathVariable Long id);
}