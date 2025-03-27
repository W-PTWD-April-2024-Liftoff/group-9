package com.parkrangers.parkquest_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/secret_resoruce")
    public ResponseEntity<String> secret() {
        return new ResponseEntity<>("This is a secret resource", HttpStatus.OK);
    }

    @GetMapping("/public_resource")
    public ResponseEntity<String> nosecret() {
        return new ResponseEntity<>("This is a public resource", HttpStatus.OK);
    }
}
