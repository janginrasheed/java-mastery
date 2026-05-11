package com.library.domain;

import lombok.Data;

@Data
public class Address {
    private String streetName;
    private String houseNr;
    private String staircase;
    private String doorNumber;
    private String postalCode;
    private String country;
}
