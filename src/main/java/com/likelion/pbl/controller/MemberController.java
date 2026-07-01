package com.likelion.pbl.controller;

import com.likelion.pbl.domain.role.Lion;
import com.likelion.pbl.domain.role.Role;
import com.likelion.pbl.domain.role.Staff;
import com.likelion.pbl.dto.LionCreateRequest;
import com.likelion.pbl.dto.LionResponse;
import com.likelion.pbl.dto.LionUpdateRequest;
import com.likelion.pbl.dto.StaffCreateRequest;
import com.likelion.pbl.dto.StaffResponse;
import com.likelion.pbl.dto.StaffUpdateRequest;
import com.likelion.pbl.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Members", description = "Lion and Staff CRUD API")
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "Register a Lion")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lion registered"),
            @ApiResponse(responseCode = "409", description = "Member name already exists")
    })
    @PostMapping("/lions")
    public ResponseEntity<LionResponse> createLion(@RequestBody LionCreateRequest request) {
        Lion lion = memberService.createLion(request);
        if (lion == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(LionResponse.from(lion));
    }

    @Operation(summary = "Register a Staff member")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Staff member registered"),
            @ApiResponse(responseCode = "409", description = "Member name already exists")
    })
    @PostMapping("/staffs")
    public ResponseEntity<StaffResponse> createStaff(@RequestBody StaffCreateRequest request) {
        Staff staff = memberService.createStaff(request);
        if (staff == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(StaffResponse.from(staff));
    }

    @Operation(summary = "Find members by optional name query")
    @ApiResponse(responseCode = "200", description = "Members found")
    @GetMapping
    public ResponseEntity<List<Object>> findMembers(@RequestParam(required = false) String name) {
        List<Object> responses = memberService.findMembers(name).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Find one member by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity<Object> findMember(@PathVariable String name) {
        Role member = memberService.findMember(name);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(member));
    }

    @Operation(summary = "Update Lion information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lion updated"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @PutMapping("/lions/{name}")
    public ResponseEntity<LionResponse> updateLion(
            @PathVariable String name,
            @RequestBody LionUpdateRequest request
    ) {
        Lion lion = memberService.updateLion(name, request);
        if (lion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(LionResponse.from(lion));
    }

    @Operation(summary = "Update Staff information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member updated"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @PutMapping("/staffs/{name}")
    public ResponseEntity<StaffResponse> updateStaff(
            @PathVariable String name,
            @RequestBody StaffUpdateRequest request
    ) {
        Staff staff = memberService.updateStaff(name, request);
        if (staff == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(StaffResponse.from(staff));
    }

    @Operation(summary = "Delete one member by name")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Member deleted"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteMember(@PathVariable String name) {
        boolean deleted = memberService.deleteMember(name);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private Object toResponse(Role member) {
        if (member instanceof Lion lion) {
            return LionResponse.from(lion);
        }
        if (member instanceof Staff staff) {
            return StaffResponse.from(staff);
        }
        throw new IllegalStateException("Unsupported member role: " + member.getClass().getName());
    }
}
