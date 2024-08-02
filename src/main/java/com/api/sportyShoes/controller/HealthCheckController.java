package com.api.sportyShoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.sportyShoes.repository.ShoesRepository;

@RestController
public class HealthCheckController {

    @Autowired
    private ShoesRepository shoesRepo;

    @GetMapping("/health")
    public String healthCheck() {
        try {
            // Perform a simple query to check the connection
            long count = shoesRepo.count();
            return "UP";
        } catch (Exception e) {
            return "DOWN";
        }
    }
}
