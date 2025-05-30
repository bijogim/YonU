package com.yonu.userauth.dto;

public class UserFieldRequestDto {
    private String name;
    private String department;
    private String nickname;

    // ✅ 기본 생성자
    public UserFieldRequestDto() {}

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getNickname() {
        return nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
