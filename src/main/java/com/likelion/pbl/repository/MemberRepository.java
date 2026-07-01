package com.likelion.pbl.repository;

import com.likelion.pbl.domain.role.Role;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Role save(Role member);

    Optional<Role> findByName(String name);

    List<Role> findAll();

    List<Role> findAllByName(String name);

    void updateByName(String name, Role member);

    boolean deleteByName(String name);

    boolean existsByName(String name);
}
