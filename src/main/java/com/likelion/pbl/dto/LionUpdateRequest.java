package com.likelion.pbl.dto;

public record LionUpdateRequest(
        String major,
        int generation,
        String part,
        String studentId
) {
}
