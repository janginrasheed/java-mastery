package com.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.domain.Book;
import com.library.domain.BookCatalog;
import com.library.domain.BookCopy;
import com.library.domain.CopyCatalog;
import com.library.domain.Member;
import com.library.domain.MemberCatalog;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchService {
    private final BookCatalog bookCatalog;
    private final CopyCatalog copyCatalog;
    private final MemberCatalog memberCatalog;

    public SearchService(BookCatalog bookCatalog, CopyCatalog copyCatalog, MemberCatalog memberCatalog) {
        this.bookCatalog = bookCatalog;
        this.copyCatalog = copyCatalog;
        this.memberCatalog = memberCatalog;
    }

    public List<Book> getAllBooks() {
        List<Book> booksList = bookCatalog.getAllBooks();
//        List<BookCopy> bookCopies = copyCatalog;
        log.info("Fetched all books successfully!");
        return bookCatalog.getAllBooks();
    }

    public Book findBookByIsbn(String isbn) {
        Book foundBook = bookCatalog.getBookByIsbn(isbn);
        if (foundBook == null) {
            log.info("Book with isbn: {} not found in the library!", isbn);
            throw new BookNotFoundException("Book with isbn: " + isbn + " not found in the library!");
        }
        log.info("Book with isbn: {} found in the library!", isbn);
        return foundBook;
    }

    public List<Book> bookMatchesSearchText(String searchText) {
        List<Book> foundBook = new ArrayList<>();
        foundBook = bookCatalog.searchBooks(searchText);
        return foundBook;
    }

    public List<Member> getAllMembers() {
        log.info("Fetched all members successfully!");
        return memberCatalog.getAllMembers();
    }

    public Member findMemberById(int id) {
        Member foundMember = memberCatalog.getMemberById(id);
        if (foundMember == null) {
            log.info("Member with id: {} not found in the library!", id);
            throw new MemberNotFoundException("Member with id: " + id + " not found!");
        }
        log.info("Member with id: {} found in the library!", id);
        return foundMember;
    }

    // TODO
    public List<Member> memberMatchesSearchText(String searchText) {
        List<Member> foundMembers = new ArrayList<>();
        return foundMembers;
    }
}