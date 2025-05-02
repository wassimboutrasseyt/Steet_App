package com.example.gatewayservice.service;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "student-management")
public interface UserServiceClient {
}
