package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectInfoDTO {
    private String subjectCode;
    private String semester;
    private String campus;
    private String department;
    private String grade;
    private Integer credit;
    private String subjectName;
    private String professor;
    private String classroom;
    private String classTime;
}