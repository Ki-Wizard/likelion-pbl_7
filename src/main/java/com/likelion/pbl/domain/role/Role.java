package com.likelion.pbl.domain.role;

public abstract class Role {

    private final String name;
    private final String major;
    private final int generation;
    private final String part;

    protected Role(String name, String major, int generation, String part) {
        this.name = name;
        this.major = major;
        this.generation = generation;
        this.part = part;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public int getGeneration() {
        return generation;
    }

    public String getPart() {
        return part;
    }

    public abstract String getRoleName();
}
