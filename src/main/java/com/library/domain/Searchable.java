package com.library.domain;

public interface Searchable {
    // TODO: is object here ok? cause I use it in Book.java and Member.java
    Object matchesQuery(String searchText);
}
