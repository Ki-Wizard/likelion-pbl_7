package com.likelion.pbl.service;

import com.likelion.pbl.domain.role.Lion;
import com.likelion.pbl.domain.role.Role;
import com.likelion.pbl.domain.role.Staff;
import com.likelion.pbl.dto.LionCreateRequest;
import com.likelion.pbl.dto.LionUpdateRequest;
import com.likelion.pbl.dto.StaffCreateRequest;
import com.likelion.pbl.dto.StaffUpdateRequest;
import com.likelion.pbl.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Lion createLion(LionCreateRequest request) {
        if (memberRepository.existsByName(request.name())) {
            return null;
        }
        Lion lion = new Lion(
                request.name(),
                request.major(),
                request.generation(),
                request.part(),
                request.studentId()
        );
        return (Lion) memberRepository.save(lion);
    }

    public Staff createStaff(StaffCreateRequest request) {
        if (memberRepository.existsByName(request.name())) {
            return null;
        }
        Staff staff = new Staff(
                request.name(),
                request.major(),
                request.generation(),
                request.part(),
                request.position()
        );
        return (Staff) memberRepository.save(staff);
    }

    public Role findMember(String name) {
        return memberRepository.findByName(name).orElse(null);
    }

    public List<Role> findMembers(String name) {
        if (name == null || name.isBlank()) {
            return memberRepository.findAll();
        }
        return memberRepository.findAllByName(name);
    }

    public Lion updateLion(String name, LionUpdateRequest request) {
        if (!memberRepository.existsByName(name)) {
            return null;
        }
        Lion lion = new Lion(
                name,
                request.major(),
                request.generation(),
                request.part(),
                request.studentId()
        );
        memberRepository.updateByName(name, lion);
        return lion;
    }

    public Staff updateStaff(String name, StaffUpdateRequest request) {
        if (!memberRepository.existsByName(name)) {
            return null;
        }
        Staff staff = new Staff(
                name,
                request.major(),
                request.generation(),
                request.part(),
                request.position()
        );
        memberRepository.updateByName(name, staff);
        return staff;
    }

    public boolean deleteMember(String name) {
        return memberRepository.deleteByName(name);
    }
}
