package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTimetableDTO {
    private Integer id;
    private Integer userId;
    private String subjectCode;
    private String subjectName;
    private String color;
} 