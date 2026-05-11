/*  
HashSet for activeLoans:
Why here? We want to ensure a member can't borrow the exact same book twice by accident.
How it works: It uses the hashCode() of the book to check if it's already in the set. 
If you try to add a duplicate, the HashSet simply says "No thanks" and ignores it.
*/

package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Searchable {

    private int id;
    private String firstName;
    private String lastName;
    private ContactInfo contactInfo;
    private Set<Book> activeLoans = new HashSet<>();

    @Override
    public Member matchesQuery(String searchText) {
        searchText = searchText.toUpperCase();
        if (firstName.toUpperCase().contains(searchText)
                || lastName.toUpperCase().contains(searchText)
                || contactInfo.getEmail().equals(searchText)
        ) {
            return this;
        }

        // TODO: return Exception
        return null;
    }

}