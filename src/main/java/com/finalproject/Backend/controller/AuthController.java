package com.finalproject.Backend.controller;


import com.finalproject.Backend.dto.request.ForgotPasswordRequest;
import com.finalproject.Backend.dto.request.LoginRequest;
import com.finalproject.Backend.dto.request.ResetPasswordRequest;
import com.finalproject.Backend.dto.request.UserRequest;
import com.finalproject.Backend.dto.request.VerifyEmailRequest;
import com.finalproject.Backend.dto.response.JwtResponse;
import com.finalproject.Backend.service.AuthService;
import com.finalproject.Backend.service.UserService;

import jakarta.validation.Valid; 
  
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "https://frontend-hsom.onrender.com", "https://www.otramalaga.com", "https://otramalaga.com"})
public class AuthController { 
    
   AuthService authService;
 
   UserService userService;

   public AuthController(AuthService authService, UserService userService) {
       this.authService = authService;
       this.userService = userService;
   }

   @PostMapping("/login")
   public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
       System.out.println("Login request received for email: " + loginRequest.getEmail());
       try {
           JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
           System.out.println("Login successful for user: " + jwtResponse.getEmail());
           return ResponseEntity.ok(jwtResponse);
       } catch (Exception e) {
           System.out.println("Error during login process: " + e.getMessage());
           throw e;
       }
   }

   @PostMapping("/register")
   public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRequest userRequest) { 
       userService.createUser(userRequest); 
       return ResponseEntity.noContent().build();
   }

   @PostMapping("/verify-email")
   public ResponseEntity<?> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
       try {
           userService.verifyEmail(request.getToken());
           return ResponseEntity.ok().body("Email verificado exitosamente");
       } catch (RuntimeException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
   }

   @PostMapping("/forgot-password")
   public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
       try {
           userService.requestPasswordReset(request.getEmail());
           return ResponseEntity.ok().body("Se ha enviado un email con las instrucciones para recuperar tu contraseña");
       } catch (RuntimeException e) {
           // Por seguridad, siempre devolvemos el mismo mensaje
           return ResponseEntity.ok().body("Si el email existe, se ha enviado un enlace de recuperación");
       }
   }

   @PostMapping("/reset-password")
   public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
       try {
           userService.resetPassword(request.getToken(), request.getNewPassword());
           return ResponseEntity.ok().body("Contraseña restablecida exitosamente");
       } catch (RuntimeException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
   }
 
}