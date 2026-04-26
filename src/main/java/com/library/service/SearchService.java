package com.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.domain.Book;
import com.library.domain.BookCatalog;
import com.library.domain.Member;
import com.library.domain.MemberList;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;

@Service
public class SearchService {
    private final BookCatalog catalog;
    private final MemberList memberList;

    public SearchService(BookCatalog catalog, MemberList memberList) {
        this.catalog = catalog;
        this.memberList = memberList;
    }

    public List<Book> getAllBooks() {
        return catalog.getAllBooks();
    }

    public List<Member> getAllMembers() {
        return memberList.getAllMembers();
    }

    public Book findBookByIsbn(String isbn) {
        Book foundBook = catalog.getBookByIsbn(isbn);
        if (foundBook == null) {
            throw new BookNotFoundException("Book not found in the library!");
        }
        return foundBook;
    }

    public Member findMemberById(int id) {
        Member foundMember = memberList.getMemberById(id);
        if (foundMember == null) {
            throw new MemberNotFoundException("Member not found!");
        }
        return foundMember;
    }

}