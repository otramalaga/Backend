package com.finalproject.Backend.service;

import com.finalproject.Backend.model.Tag; 
import com.finalproject.Backend.repository.TagRepository; 
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

}
