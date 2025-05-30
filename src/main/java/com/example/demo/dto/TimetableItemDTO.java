package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimetableItemDTO {
    private String subjectCode;
    private String subject;
    private String day;
    private String period;
    private String professor;
    private String room;
}
