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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookCatalog {
    private Map<String, Book> books = new HashMap<>();
    private List<Book> booksAddedOrder = new ArrayList<>();
    private TreeSet<Book> booksSorted = new TreeSet<>();

    @PostConstruct
    public void loadBooksFromFile() {
        try (InputStream is = getClass().getResourceAsStream("/books.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // We use \\| because | is a special character in Regex
                Book book = new Book(parts[0], parts[1], parts[2], true, null);
                addBook(book);
            }
            log.info("Successfully loaded books into memory!");
        } catch (Exception exception) {
            log.error("Could not load books: ", exception);
        }
    }

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
        booksSorted.add(book);
        booksAddedOrder.add(book);
    }

    public List<Book> getAllBooks() {
        return booksAddedOrder;
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