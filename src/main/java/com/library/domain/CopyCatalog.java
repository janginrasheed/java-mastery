package com.library.domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CopyCatalog {
    Map<String, BookCopy> copiesById = new HashMap<>();
    Map<String, List<BookCopy>> copiesByIsbn = new HashMap<>();
    private final ObjectMapper objectMapper; // TODO: private final correct here?

    public CopyCatalog(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadCopiesFromFile() {
        try (InputStream is = getClass().getResourceAsStream("/copies.json")) {
            List<BookCopy> loadedCopies = objectMapper.readValue(is, new TypeReference<List<BookCopy>>() {
            });
            for (BookCopy m : loadedCopies) {
                copiesById.put(m.getCopyId(), m);
                copiesByIsbn.computeIfAbsent(m.getIsbn(), k -> new ArrayList<>()).add(m);
            }
        } catch (Exception e) {
            log.error("Failed to load copies from JSON", e);
        }
    }

    public void addCopy(BookCopy copy) {
        copiesById.put(copy.getCopyId(), copy);
        copiesByIsbn.computeIfAbsent(copy.getIsbn(), k -> new ArrayList<>()).add(copy);
    }

}
