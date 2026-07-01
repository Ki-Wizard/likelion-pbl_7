package com.likelion.pbl.dto;

import com.likelion.pbl.domain.role.Lion;

public record LionResponse(
        String name,
        String major,
        int generation,
        String part,
        String roleName,
        String studentId
) {

    public static LionResponse from(Lion lion) {
        return new LionResponse(
                lion.getName(),
                lion.getMajor(),
                lion.getGeneration(),
                lion.getPart(),
                lion.getRoleName(),
                lion.getStudentId()
        );
    }
}
