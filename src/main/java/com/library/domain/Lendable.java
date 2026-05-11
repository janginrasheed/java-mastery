package com.library.domain;

import java.time.LocalDate;

public interface Lendable {
    // TODO: deleted isAvailbale -> is it ok?
    boolean checkout(LocalDate dueDate);
    void returnItem();
}
