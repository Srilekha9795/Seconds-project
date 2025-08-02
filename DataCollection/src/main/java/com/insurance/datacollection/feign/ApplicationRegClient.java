package com.insurance.datacollection.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.insurance.datacollection.dto.UserRegisteredSsnResponse;

@FeignClient(name="APPLICATIONREGSERVICE",url="http://localhost:6002")
public interface ApplicationRegClient {

    @GetMapping("/api/registration/get/appid/{appId}")
    public ResponseEntity<UserRegisteredSsnResponse> getUserBySsn(@PathVariable Integer appId);
}
