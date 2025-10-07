package com.finalproject.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookmarks")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Embedded
    private Location location;

    @Column(name = "address", length = 1000)
    private String address;

    @Column(name = "publication_date")
    private Timestamp publicationDate;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "bookmark_images", joinColumns = @JoinColumn(name = "bookmark_id"))
    @Column(name = "image_url", length = 1000)
    private List<String> imageUrls;

    private String infoAdicional;

    
}