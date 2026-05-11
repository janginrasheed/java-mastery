/*
HashMap for books:
A HashMap is the ultimate lookup tool. It stores data in pairs: a Key and a Value.
Why here? Librarians need to find a book instantly using its ISBN (the Key).
How it works: Instead of searching through a long list, the HashMap uses the ISBN to jump 
straight to the correct Book object (the Value). 
It's like having a direct GPS coordinate for every item.
------------------------------------------------
TreeSet for booksSorted:
A TreeSet is a sorted collection.
Why here? We want to be able to show a list of all books in alphabetical order by title 
without manually sorting them every time.
How it works: It uses the compareTo() method we wrote in the Book class. 
Every time a book is added, the TreeSet automatically places it in the correct alphabetical spot.
*/

package com.library.domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookCatalog {
    // TODO Check if we can keep only one variable, if not -> document why not
    private Map<String, Book> books = new HashMap<>();
    private List<Book> booksAddedOrder = new ArrayList<>();
    private TreeSet<Book> booksSorted = new TreeSet<>();
    private final ObjectMapper objectMapper; // Jackson's engine

    public BookCatalog(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadBooksFromFile() {
        try (InputStream is = getClass().getResourceAsStream("/books.json")) {
            List<Book> loadedBooks = objectMapper.readValue(is, new TypeReference<List<Book>>() {
            });
            for (Book m : loadedBooks) {
                addBook(m);
            }
        } catch (Exception e) {
            log.error("Failed to load books from JSON", e);
        }
    }

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
        booksSorted.add(book);
        booksAddedOrder.add(book);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public Book getBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    public List<Book> searchBooks(String query) {
        List<Book> foundBooks = new ArrayList<>();

        for (Book book : booksAddedOrder) {
            if (book.matchesQuery(query) != null) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public void removeBook(String isbn) {
        Book bookToRemove = books.get(isbn);
        if (bookToRemove == null)
            return;
        books.remove(isbn);
        booksAddedOrder.remove(bookToRemove);
        booksSorted.remove(bookToRemove);
    }

}