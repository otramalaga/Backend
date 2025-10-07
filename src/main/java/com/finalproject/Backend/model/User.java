package com.finalproject.Backend.model;
 

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", 
      uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotBlank
   @Size(max = 50)
   private String name;

   @NotBlank
   @Size(max = 120)
   @Email
   private String email;

   @NotBlank
   private String password;

  
   @Column(name = "created_at")
   private LocalDateTime createdAt;
   
   @Column(name = "updated_at")
   private LocalDateTime updatedAt;

   @PrePersist
   protected void onCreate() {
       this.createdAt = LocalDateTime.now();
       this.updatedAt = LocalDateTime.now();
   }
   
   @PreUpdate
   protected void onUpdate() {
       this.updatedAt = LocalDateTime.now();
   }
}