package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String studentId;
    private String department;
    private String nickname;
    private String preferredLanguage;
}