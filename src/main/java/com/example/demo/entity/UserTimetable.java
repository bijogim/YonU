package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_timetable")
@Getter
@Setter
public class UserTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String subjectCode;

    private String subject;

    private String day;

    private String period;

    private String professor;

    private String room;
}
