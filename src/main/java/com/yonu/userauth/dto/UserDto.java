package com.yonu.userauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String studentId;
    private String department;
    private String nickname;
    private String language;
}
