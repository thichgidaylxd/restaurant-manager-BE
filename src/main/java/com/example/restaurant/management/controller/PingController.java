package com.example.restaurant.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/ping")
public class PingController {
    public ResponseEntity<String> ping(){
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}
