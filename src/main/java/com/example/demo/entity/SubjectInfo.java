package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subject_info")
@Getter
@Setter
public class SubjectInfo {
    @Id
    @Column(name = "subject_code", length = 20)
    private String subjectCode;

    @Column(name = "semester", nullable = false, length = 10)
    private String semester;

    @Column(name = "campus", nullable = false, length = 20)
    private String campus;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Column(name = "grade", length = 10)
    private String grade;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "subject_name", length = 100)
    private String subjectName;

    @Column(name = "professor", length = 50)
    private String professor;

    @Column(name = "classroom", length = 50)
    private String classroom;

    @Column(name = "class_time", length = 50)
    private String classTime;
} 