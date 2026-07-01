package com.likelion.pbl.domain.role;

public class Lion extends Role {

    private final String studentId;

    public Lion(String name, String major, int generation, String part, String studentId) {
        super(name, major, generation, part);
        this.studentId = studentId;
    }

    @Override
    public String getRoleName() {
        return "LION";
    }

    public String getStudentId() {
        return studentId;
    }
}
