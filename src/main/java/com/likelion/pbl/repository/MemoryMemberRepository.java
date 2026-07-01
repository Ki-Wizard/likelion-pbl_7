package com.likelion.pbl.repository;

import com.likelion.pbl.domain.role.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private final List<Role> members = new ArrayList<>();

    @Override
    public synchronized Role save(Role member) {
        members.add(member);
        return member;
    }

    @Override
    public synchronized Optional<Role> findByName(String name) {
        return members.stream()
                .filter(member -> member.getName().equals(name))
                .findFirst();
    }

    @Override
    public synchronized List<Role> findAll() {
        return List.copyOf(members);
    }

    @Override
    public synchronized List<Role> findAllByName(String name) {
        return members.stream()
                .filter(member -> member.getName().equals(name))
                .toList();
    }

    @Override
    public synchronized void updateByName(String name, Role member) {
        for (int index = 0; index < members.size(); index++) {
            if (members.get(index).getName().equals(name)) {
                members.set(index, member);
                return;
            }
        }
    }

    @Override
    public synchronized boolean deleteByName(String name) {
        return members.removeIf(member -> member.getName().equals(name));
    }

    @Override
    public synchronized boolean existsByName(String name) {
        return members.stream().anyMatch(member -> member.getName().equals(name));
    }
}
