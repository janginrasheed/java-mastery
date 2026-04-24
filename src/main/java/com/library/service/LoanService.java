package com.library.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.library.domain.Book;
import com.library.domain.Member;
import com.library.exception.BookNotAvailableException;
import com.library.exception.LoanNotFoundException;

@Service
public class LoanService {

    public void performLoan(Book book, Member member) {
        if (!book.checkout(LocalDate.now())) {
            throw new BookNotAvailableException("The book is already out!");
        }
        member.getActiveLoans().add(book);
    }

    public void returnItem(Book book, Member member) {
        if (!member.getActiveLoans().contains(book)) {
            throw new LoanNotFoundException("This member does not have this book checked out.");
        }
        book.returnItem();
        member.getActiveLoans().remove(book);
    }

}