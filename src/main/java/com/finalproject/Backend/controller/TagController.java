package com.finalproject.Backend.controller;
 
import com.finalproject.Backend.model.Tag; 
import com.finalproject.Backend.service.TagService; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
 
import java.util.List; 

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = {"http://localhost:5173", "https://otramalaga.com"})
public class TagController {

   TagService tagService;

   public TagController(TagService tagService) {
         this.tagService = tagService; 
   }

   @GetMapping("/all")
   public ResponseEntity<List<Tag>> getAllTags () {
       List<Tag> tags = tagService.getAllTags();  
       return ResponseEntity.ok(tags);
   }
   

}
