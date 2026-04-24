package com.library.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MemberList {
    private Map<Integer, Member> members = new HashMap<>();

    public void addBook(Member member) {
        members.put(member.getId(), member);
    }

    public Member getMemberById(int id) {
        return members.get(id);
    }

}
