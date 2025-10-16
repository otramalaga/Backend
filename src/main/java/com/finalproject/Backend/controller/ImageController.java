package com.finalproject.Backend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = {"http://localhost:5173", "https://frontend-hsom.onrender.com", "https://www.otramalaga.com", "https://otramalaga.com"})
public class ImageController {

    @GetMapping("/{imageUrl}")
    public ResponseEntity<Void> getImage(@PathVariable String imageUrl) {
        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, imageUrl)
                .build();
    }
}
