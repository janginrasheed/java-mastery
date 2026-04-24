package com.library.domain;

import java.time.LocalDate;

public interface Lendable {
    boolean isAvailable();
    boolean checkout(LocalDate dueDate);
    void returnItem();
}
