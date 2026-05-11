package com.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookSummary {
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;
}