package com.likelion.pbl.domain.role;

public class Staff extends Role {

    private final String position;

    public Staff(String name, String major, int generation, String part, String position) {
        super(name, major, generation, part);
        this.position = position;
    }

    @Override
    public String getRoleName() {
        return "STAFF";
    }

    public String getPosition() {
        return position;
    }
}
