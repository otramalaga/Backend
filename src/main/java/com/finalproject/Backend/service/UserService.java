package com.finalproject.Backend.service;
 

import com.finalproject.Backend.dto.request.UserRequest;
import com.finalproject.Backend.exception.ResourceNotFoundException;
import com.finalproject.Backend.model.User;
import com.finalproject.Backend.repository.UserRepository;  
import com.finalproject.Backend.repository.TagRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService { 

    UserRepository userRepository; 
    PasswordEncoder passwordEncoder;
    TagRepository tagRepository;
    EmailService emailService;


   public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TagRepository tagRepository, EmailService emailService) {
       this.tagRepository = tagRepository;
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
       this.emailService = emailService;
   }

   public List<User> getAllUsers() {
       return userRepository.findAll();
   }

   public User getUserById(Long id) {
       return userRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
   }

   public Optional<User> getUserByEmail(String email) {
       return userRepository.findByEmail(email);
   }

   public User createUser(UserRequest userRequest) {
       if (userRepository.existsByEmail(userRequest.getEmail())) {
           throw new RuntimeException("El email ya está en uso");
       }

       String verificationToken = UUID.randomUUID().toString();
       User user = new User(null,
                            userRequest.getName(),
                            userRequest.getEmail(),
                            passwordEncoder.encode(userRequest.getPassword()),
                            false,
                            verificationToken,
                            null,
                            null,
                            null,
                            null);
       User savedUser = userRepository.save(user);
       
       // Enviar email de verificación
       emailService.sendVerificationEmail(savedUser.getEmail(), verificationToken, savedUser.getName());
       
       return savedUser;
   }

   public void verifyEmail(String token) {
       User user = userRepository.findByVerificationToken(token)
               .orElseThrow(() -> new RuntimeException("Token de verificación inválido"));
       
       if (user.getEmailVerified()) {
           throw new RuntimeException("El email ya ha sido verificado");
       }
       
       user.setEmailVerified(true);
       user.setVerificationToken(null);
       userRepository.save(user);
   }

   public void requestPasswordReset(String email) {
       User user = userRepository.findByEmail(email)
               .orElseThrow(() -> new RuntimeException("No se encontró un usuario con ese email"));
       
       String resetToken = UUID.randomUUID().toString();
       LocalDateTime expiry = LocalDateTime.now().plusHours(1);
       
       user.setResetPasswordToken(resetToken);
       user.setResetPasswordTokenExpiry(expiry);
       userRepository.save(user);
       
       emailService.sendPasswordResetEmail(user.getEmail(), resetToken, user.getName());
   }

   public void resetPassword(String token, String newPassword) {
       User user = userRepository.findByResetPasswordToken(token)
               .orElseThrow(() -> new RuntimeException("Token de recuperación inválido"));
       
       if (user.getResetPasswordTokenExpiry() == null || 
           user.getResetPasswordTokenExpiry().isBefore(LocalDateTime.now())) {
           throw new RuntimeException("El token de recuperación ha expirado");
       }
       
       user.setPassword(passwordEncoder.encode(newPassword));
       user.setResetPasswordToken(null);
       user.setResetPasswordTokenExpiry(null);
       userRepository.save(user);
   }

   public User updateUser(Long id, UserRequest userRequest) {
       User userFound = getUserById(id);
       if (!userFound.getEmail().equals(userRequest.getEmail())) {
           if (userRepository.existsByEmail(userRequest.getEmail())) {
               throw new RuntimeException("El email ya está en uso");
           } 
       }


        User userData = new User(id, 
                            userFound.getName(),
                            userFound.getEmail(),
                            userFound.getPassword(),
                            userFound.getEmailVerified(),
                            userFound.getVerificationToken(),
                            userFound.getResetPasswordToken(),
                            userFound.getResetPasswordTokenExpiry(),
                            userFound.getCreatedAt(),
                            userFound.getUpdatedAt()); 

       return userRepository.save(userData);
   }

   public void deleteUser(Long id) {
       User user = getUserById(id);
       userRepository.delete(user);
   }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return getUserByEmail(userDetails.getUsername());
    }
   
}
