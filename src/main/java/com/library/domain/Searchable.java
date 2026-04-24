package com.library.domain;

public interface Searchable {
    Book matchesQuery(String searchText);
}
