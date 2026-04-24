package com.library;

import java.util.Collections;
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

    @PostMapping("loan")
    public ResponseEntity<String> performLoan(@RequestParam String isbn, @RequestParam int memberId) {
        Book book = searchService.findBookByIsbn(isbn);
        if (book == null) throw new BookNotFoundException("Book not found!");

        Member member = searchService.findMemberById(memberId);
        if (member == null) throw new MemberNotFoundException("Member not found!");
        
        loanService.performLoan(book, member);

        return new ResponseEntity<>("Loan successful!", HttpStatus.OK);
    }

    @PostMapping("returnItem")
    public ResponseEntity<String> returnItem(@RequestParam String isbn, @RequestParam int memberId) {
        Book book = searchService.findBookByIsbn(isbn);
        if (book == null) throw new BookNotFoundException("Book not found!");

        Member member = searchService.findMemberById(memberId);
        if (member == null) throw new MemberNotFoundException("Member not found!");
        
        loanService.returnItem(book, member);

        return new ResponseEntity<>("Return successful!", HttpStatus.OK);
    }

    @GetMapping("getAllBooks")
    public List<Book> getAllBooks() {
        List<Book> allBooks = searchService.getAllBooks();
        return (allBooks != null) ? allBooks : Collections.emptyList();
    }

}
