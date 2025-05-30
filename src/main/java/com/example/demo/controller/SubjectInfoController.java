package com.example.demo.controller;

import com.example.demo.dto.SubjectInfoDTO;
import com.example.demo.service.SubjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.demo.dto.TimetableItemDTO;

import javax.security.auth.Subject;


@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"}, allowCredentials = "true")
public class SubjectInfoController {
    private final SubjectInfoService subjectInfoService;

    @Autowired
    public SubjectInfoController(SubjectInfoService subjectInfoService) {
        this.subjectInfoService = subjectInfoService;
    }

    @GetMapping
    public ResponseEntity<List<SubjectInfoDTO>> getAllSubjects() {
        return ResponseEntity.ok(subjectInfoService.getAllSubjects());
    }

    @GetMapping("/{subjectCode}")
    public ResponseEntity<SubjectInfoDTO> getSubjectByCode(@PathVariable String subjectCode) {
        return ResponseEntity.ok(subjectInfoService.getSubjectByCode(subjectCode));
    }

    @GetMapping("/timetable")
    public List<TimetableItemDTO> getTimetableForFrontend() {
        List<SubjectInfoDTO> subjects = subjectInfoService.getAllSubjects();
        if (subjects == null || subjects.isEmpty()) {
            return Collections.emptyList();  // 빈 리스트라도 보내
        }
        return subjects.stream()
                .map(subjectInfoService::convertToTimetableItem)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    @GetMapping("/timetable/search")
    public List<TimetableItemDTO> searchTimetable(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String professor


    ) {
        return subjectInfoService.getAllSubjects().stream()
                .map(subjectInfoService::convertToTimetableItem)
                .filter(Objects::nonNull)
                .filter(item ->
                        (subject == null || item.getSubject().toLowerCase().contains(subject.toLowerCase())) &&
                                (professor == null || item.getProfessor().toLowerCase().contains(professor.toLowerCase()))
                )
                .collect(Collectors.toList());
    }




    @PostMapping
    public ResponseEntity<SubjectInfoDTO> createSubject(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        return ResponseEntity.ok(subjectInfoService.createSubject(subjectInfoDTO));
    }

    @PutMapping("/{subjectCode}")
    public ResponseEntity<SubjectInfoDTO> updateSubject(
            @PathVariable String subjectCode,
            @RequestBody SubjectInfoDTO subjectInfoDTO) {
        return ResponseEntity.ok(subjectInfoService.updateSubject(subjectCode, subjectInfoDTO));
    }

    @DeleteMapping("/{subjectCode}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String subjectCode) {
        subjectInfoService.deleteSubject(subjectCode);
        return ResponseEntity.ok().build();
    }

    // 필터링 엔드포인트
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<SubjectInfoDTO>> getSubjectsBySemester(@PathVariable String semester) {
        return ResponseEntity.ok(subjectInfoService.getSubjectsBySemester(semester));
    }

    @GetMapping("/campus/{campus}")
    public ResponseEntity<List<SubjectInfoDTO>> getSubjectsByCampus(@PathVariable String campus) {
        return ResponseEntity.ok(subjectInfoService.getSubjectsByCampus(campus));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<SubjectInfoDTO>> getSubjectsByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(subjectInfoService.getSubjectsByDepartment(department));
    }

    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<SubjectInfoDTO>> getSubjectsByGrade(@PathVariable String grade) {
        return ResponseEntity.ok(subjectInfoService.getSubjectsByGrade(grade));
    }

    @GetMapping("/credit/{credit}")
    public ResponseEntity<List<SubjectInfoDTO>> getSubjectsByCredit(@PathVariable Integer credit) {
        return ResponseEntity.ok(subjectInfoService.getSubjectsByCredit(credit));
    }

    @GetMapping("/professor/{professor}")
    public ResponseEntity<List<SubjectInfoDTO>> getSubjectsByProfessor(@PathVariable String professor) {
        return ResponseEntity.ok(subjectInfoService.getSubjectsByProfessor(professor));
    }

    @GetMapping("/classroom/{classroom}")
    public ResponseEntity<List<SubjectInfoDTO>> getSubjectsByClassroom(@PathVariable String classroom) {
        return ResponseEntity.ok(subjectInfoService.getSubjectsByClassroom(classroom));
    }

    @GetMapping("/class-time/{classTime}")
    public ResponseEntity<List<SubjectInfoDTO>> getSubjectsByClassTime(@PathVariable String classTime) {
        return ResponseEntity.ok(subjectInfoService.getSubjectsByClassTime(classTime));
    }

    // 검색 엔드포인트
    @GetMapping("/search/name/{subjectName}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsByName(@PathVariable String subjectName) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsByName(subjectName));
    }

    @GetMapping("/search/professor/{professor}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsByProfessor(@PathVariable String professor) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsByProfessor(professor));
    }

    @GetMapping("/search/department/{department}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsByDepartment(department));
    }

    @GetMapping("/search/code/{subjectCode}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsByCode(@PathVariable String subjectCode) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsByCode(subjectCode));
    }

    @GetMapping("/search/class-time/{classTime}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsByClassTime(@PathVariable String classTime) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsByClassTime(classTime));
    }

    @GetMapping("/search/grade/{grade}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsByGrade(@PathVariable String grade) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsByGrade(grade));
    }

    @GetMapping("/search/campus/{campus}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsByCampus(@PathVariable String campus) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsByCampus(campus));
    }

    @GetMapping("/search/semester/{semester}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsBySemester(@PathVariable String semester) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsBySemester(semester));
    }

    @GetMapping("/search/classroom/{classroom}")
    public ResponseEntity<List<SubjectInfoDTO>> searchSubjectsByClassroom(@PathVariable String classroom) {
        return ResponseEntity.ok(subjectInfoService.searchSubjectsByClassroom(classroom));
    }
} 