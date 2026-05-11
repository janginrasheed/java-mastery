package com.library.domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemberCatalog {
    private Map<Integer, Member> members = new HashMap<>();
    private final ObjectMapper objectMapper; // Jackson's engine

    public MemberCatalog(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadMemberssFromFile() {
        try (InputStream is = getClass().getResourceAsStream("/members.json")) {
            List<Member> loadedMembers = objectMapper.readValue(is, new TypeReference<List<Member>>() {
            });
            for (Member m : loadedMembers) {
                members.put(m.getId(), m);
            }
        } catch (Exception e) {
            log.error("Failed to load members from JSON", e);
        }
    }

    public Member getMemberById(int id) {
        return members.get(id);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }
}
