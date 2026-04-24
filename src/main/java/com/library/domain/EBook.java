package com.library.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EBook extends Book {
    private int fileSize;
    private String downloadUrl;

}