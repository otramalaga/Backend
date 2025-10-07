package com.finalproject.Backend.dto.response;

import lombok.*;
import java.time.Instant;
import com.finalproject.Backend.model.Location;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private String category;
    private String video;
    private String url;
    private Location location;
    private String address;
    private Instant publicationDate;
    private Long userId;
    private List<String> imageUrls;
} 