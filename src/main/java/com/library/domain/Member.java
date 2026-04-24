/*  
HashSet for activeLoans:
Why here? We want to ensure a member can't borrow the exact same book twice by accident.
How it works: It uses the hashCode() of the book to check if it's already in the set. 
If you try to add a duplicate, the HashSet simply says "No thanks" and ignores it.
*/

package com.library.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private ContactInfo contactInfo;
    private Set<Book> activeLoans = new HashSet<>();
}