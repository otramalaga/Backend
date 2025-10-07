package com.finalproject.Backend.modelTest;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.finalproject.Backend.model.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TagTest {

    @Test
    @DisplayName("Test 1: NoArgsConstructor and Setters - Should create country and set properties")
    void noArgsConstructorAndSetters_ShouldCreateCountryAndSetProperties() {
        // ARRANGE & ACT
        Tag country = new Tag();  
        country.setId(1L);
        country.setName("Feminism");

        // ASSERT
        assertNotNull(country);
        assertEquals(1L, country.getId());
        assertEquals("Feminism", country.getName());
    }

    @Test
    @DisplayName("Test 2: AllArgsConstructor and Getters - Should create country with all properties")
    void allArgsConstructorAndGetters_ShouldCreateCountryWithAllProperties() {
        // ARRANGE & ACT
        Tag tag = new Tag(1L, "Feminism");  

        // ASSERT
        assertNotNull(tag);
        assertEquals(1L, tag.getId());
        assertEquals("Feminism", tag.getName());
    }
}