package com.finalproject.Backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.finalproject.Backend.dto.request.BookmarkRequestDTO;
import com.finalproject.Backend.dto.response.BookmarkResponseDTO;
import com.finalproject.Backend.model.User;
import com.finalproject.Backend.service.BookmarkService;
import com.finalproject.Backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookmarks")
@CrossOrigin(origins = {"http://localhost:5173", "https://aquamarine-stardust-06e107.netlify.app", "https://otramalaga.com"})
public class BookmarkController {
    @Autowired
    UserService userService;

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping
    public List<BookmarkResponseDTO> getAllBookmarks() {
        return bookmarkService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookmarkResponseDTO> getBookmarkById(@PathVariable Long id) {
        return bookmarkService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookmarkResponseDTO>> searchBookmarks(@RequestParam String title) {
        return ResponseEntity.ok(bookmarkService.searchByTitle(title));
    }

    @PostMapping
    public ResponseEntity<BookmarkResponseDTO> createBookmark(@Valid @RequestBody BookmarkRequestDTO dto) {
        Optional<User> authenticatedUser = userService.getAuthenticatedUser();
        if (!authenticatedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(bookmarkService.create(dto, authenticatedUser.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookmarkResponseDTO> updateBookmark(@PathVariable Long id,
            @Valid @RequestBody BookmarkRequestDTO dto) {
        Optional<User> authenticatedUser = userService.getAuthenticatedUser();
        if (!authenticatedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(bookmarkService.update(id, dto, authenticatedUser.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id) {
        Optional<User> authenticatedUser = userService.getAuthenticatedUser();
        if (!authenticatedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        boolean deleted = bookmarkService.delete(id, authenticatedUser.get());
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.noContent().build();
    }
} 