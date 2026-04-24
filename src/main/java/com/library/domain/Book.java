package com.library.domain;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book implements Lendable, Searchable, Comparable<Book> {

    @EqualsAndHashCode.Include // Marks this as the unique identity
    private String isbn;
    private String title;
    private String author;
    private boolean available;
    private LocalDate checkOutDate;

    @Override
    public Book matchesQuery(String searchText) {
        searchText = searchText.toUpperCase();
        if (isbn.toUpperCase().contains(searchText)
                || title.toUpperCase().contains(searchText)
                || author.toUpperCase().contains(searchText)) {
            return this;
        }

        return null;
    }

    @Override
    public boolean checkout(LocalDate dueDate) {
        if (available) {
            setCheckOutDate(dueDate);
            available = false;
            return true;
        }
        return false;
    }

    @Override
    public void returnItem() {
        available = true;
        checkOutDate = null;
    }

    @Override
    public int compareTo(Book o) {
        int result = this.getTitle().compareTo(o.title);
        if (result != 0) return result; 
        return this.getIsbn().compareTo(o.isbn);
    }

}
