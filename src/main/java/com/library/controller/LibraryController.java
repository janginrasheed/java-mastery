package com.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.domain.Book;
import com.library.domain.Member;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;
import com.library.service.LoanService;
import com.library.service.SearchService;

@RestController
@RequestMapping("/api")
public class LibraryController {

    private final LoanService loanService;
    private final SearchService searchService;

    public LibraryController(LoanService loanService, SearchService searchService) {
        this.loanService = loanService;
        this.searchService = searchService;
    }

    // ============================ Book functions ============================ //
    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        List<Book> allBooks = searchService.getAllBooks();
        return (allBooks != null) ? allBooks : new ArrayList<>();
    }

    @GetMapping("/getBookByIsbn")
    public Book getBookByIsbn(@RequestParam(value = "isbn") String isbn) {
        System.out.println("getBookByIsbn called *************");
        return searchService.findBookByIsbn(isbn);
    }

    @GetMapping("/searchBook")
    public List<Book> searchBook(@RequestParam(value = "query") String query) {
        return searchService.bookMatchesSearchText(query);
    }

    // ============================ Member functions ============================ //
    @GetMapping("/getAllMembers")
    public List<Member> getAllMembers() {
        List<Member> allMembers = searchService.getAllMembers();
        return (allMembers != null) ? allMembers : new ArrayList<>();
    }

    @GetMapping("/getMemberById")
    public Member getMemberById(@RequestParam(value = "id") int id) {
        return searchService.findMemberById(id);
    }

    // ============================ General functions ============================ //
    @PostMapping("/loan")
    public ResponseEntity<String> performLoan(@RequestParam(value = "isbn") String isbn, @RequestParam(value = "memberId") int memberId) {
        Book book = searchService.findBookByIsbn(isbn);
        if (book == null)
            throw new BookNotFoundException("Book with ISBN: " + isbn + " not found in the library!");

        Member member = searchService.findMemberById(memberId);
        if (member == null)
            throw new MemberNotFoundException("Member with id: " + memberId + " not found!");

        loanService.performLoan(book, member);
        return new ResponseEntity<>("Loan successful!", HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnItem(@RequestParam(value = "isbn") String isbn, @RequestParam(value = "memberId") int memberId) {
        Book book = searchService.findBookByIsbn(isbn);
        if (book == null)
            throw new BookNotFoundException("Book not found!");

        Member member = searchService.findMemberById(memberId);
        if (member == null)
            throw new MemberNotFoundException("Member not found!");

        loanService.returnItem(book, member);
        return new ResponseEntity<>("Return successful!", HttpStatus.OK);
    }

}