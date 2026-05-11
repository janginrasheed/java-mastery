package com.library.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.library.domain.Book;
import com.library.domain.Member;
import com.library.exception.BookNotAvailableException;
import com.library.exception.LoanNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoanService {

    public void performLoan(Book book, Member member) {
        if (!book.checkout(LocalDate.now())) {
            log.info("Book is already out!");
            throw new BookNotAvailableException("Book is already out!");
        }
        member.getActiveLoans().add(book);
        log.info("Book loaned successfully!");
    }

    public void returnItem(Book book, Member member) {
        if (!member.getActiveLoans().contains(book)) {
            log.info("This member does not have this book checked out!");
            throw new LoanNotFoundException("This member does not have this book checked out!");
        }
        book.returnItem();
        member.getActiveLoans().remove(book);
        log.info("Book returned successfully!");
    }

}