package com.yonu.userauth.dto;

public class UserFieldResponseDto {
    private String name;
    private String department;
    private String nickname;

    public UserFieldResponseDto(String name, String department, String nickname) {
        this.name = name;
        this.department = department;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getNickname() {
        return nickname;
    }
}
