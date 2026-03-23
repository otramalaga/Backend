package com.finalproject.Backend.repository;

import com.finalproject.Backend.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT DISTINCT b FROM Bookmark b LEFT JOIN FETCH b.tag LEFT JOIN FETCH b.category LEFT JOIN FETCH b.user")
    List<Bookmark> findAllWithAssociations();

    List<Bookmark> findByTitleContainingIgnoreCase(String title);
} 