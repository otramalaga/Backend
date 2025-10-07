package com.finalproject.Backend.modelTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.finalproject.Backend.model.Tag;
import com.finalproject.Backend.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    @DisplayName("Test 1: NoArgsConstructor and Setters - Should create user and set properties")
    void noArgsConstructorAndSetters_ShouldCreateUserAndSetProperties() {
        // ARRANGE
        User user = new User();

        // ACT
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        // ASSERT
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    @DisplayName("Test 2: AllArgsConstructor and Getters - Should create user with all properties")
    void allArgsConstructorAndGetters_ShouldCreateUserWithAllProperties() {
        // ARRANGE
        Long id = 2L;
        String name = "Another User";
        String email = "another@example.com";
        String password = "securePassword";
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Canada");
        String interests = "Gaming, Cooking";
        String bio = "Another test user bio.";
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        // ACT
        User user = new User(id, name, email, password, createdAt, updatedAt);

        // ASSERT
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(updatedAt, user.getUpdatedAt());
    }
}