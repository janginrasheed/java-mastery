package com.library.domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemberList {
    private Map<Integer, Member> members = new HashMap<>();

    @PostConstruct
    public void loadMemberssFromFile() {
        try (InputStream is = getClass().getResourceAsStream("/members.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // We use \\| because | is a special character in Regex
                Member member = new Member(Integer.parseInt(parts[0]), parts[1], parts[2], null, null);
                addMember(member);
            }
            log.info("Successfully loaded members into memory!");
        } catch (Exception exception) {
            log.error("Could not load members: ", exception);
        }
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public Member getMemberById(int id) {
        return members.get(id);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }
}
