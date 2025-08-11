package com.dvdrental.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @GetMapping("/api")
    public ResponseEntity<Map<String, Object>> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to DVD Rental Management System API");
        response.put("version", "1.0.0");
        response.put("status", "Running");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("Films", "/api/films");
        endpoints.put("Actors", "/api/actors");
        endpoints.put("Countries", "/api/countries");
        endpoints.put("Cities", "/api/cities");
        endpoints.put("Addresses", "/api/addresses");
        endpoints.put("Languages", "/api/languages");
        endpoints.put("Categories", "/api/categories");
        endpoints.put("Stores", "/api/stores");
        endpoints.put("Staff", "/api/staff");
        endpoints.put("Customers", "/api/customers");
        endpoints.put("Inventory", "/api/inventory");
        endpoints.put("Rentals", "/api/rentals");
        endpoints.put("Payments", "/api/payments");
        endpoints.put("Film-Actors", "/api/film-actors");
        endpoints.put("Film-Categories", "/api/film-categories");
        
        response.put("availableEndpoints", endpoints);
        
        Map<String, String> examples = new HashMap<>();
        examples.put("Get all films", "GET /api/films");
        examples.put("Search films", "GET /api/films/search?title=Academy");
        examples.put("Get film by ID", "GET /api/films/1");
        examples.put("Get all actors", "GET /api/actors");
        examples.put("Get categories", "GET /api/categories");
        
        response.put("examples", examples);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("database", "Connected");
        health.put("service", "DVD Rental Management System");
        return ResponseEntity.ok(health);
    }
}
