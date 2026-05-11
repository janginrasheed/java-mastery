package com.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookCopy {
    private String copyId; // A unique ID (like a barcode)
    private String isbn;
    private boolean isAvailable;
    private Integer borrowedBy;
}