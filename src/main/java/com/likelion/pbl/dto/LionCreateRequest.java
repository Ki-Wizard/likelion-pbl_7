package com.likelion.pbl.dto;

public record LionCreateRequest(
        String name,
        String major,
        int generation,
        String part,
        String studentId
) {
}
