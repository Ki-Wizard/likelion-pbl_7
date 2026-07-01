package com.likelion.pbl.dto;

import com.likelion.pbl.domain.role.Staff;

public record StaffResponse(
        String name,
        String major,
        int generation,
        String part,
        String roleName,
        String position
) {

    public static StaffResponse from(Staff staff) {
        return new StaffResponse(
                staff.getName(),
                staff.getMajor(),
                staff.getGeneration(),
                staff.getPart(),
                staff.getRoleName(),
                staff.getPosition()
        );
    }
}
