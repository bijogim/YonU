package com.example.demo.service;

import com.example.demo.dto.SubjectInfoDTO;
import com.example.demo.entity.SubjectInfo;
import com.example.demo.repository.SubjectInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.dto.TimetableItemDTO;
import java.util.Arrays;
import java.util.Collections;




@Service
public class SubjectInfoService {
    public TimetableItemDTO convertToTimetableItem(SubjectInfoDTO subject) {
        String classTime = subject.getClassTime();

        // 예외 방지: null, 너무 짧거나 이상한 형식
        if (classTime == null || classTime.length() < 2 || !classTime.matches("^[월화수목금토일][0-9,\\s]*$")) {
            return null; // null 반환 후 controller에서 필터링
        }

        try {
            String day = classTime.substring(0, 1); // "월"
            String[] parts = classTime.substring(1).replace(" ", "").split(",");
            List<Integer> periods = Arrays.stream(parts)
                    .filter(p -> !p.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            if (periods.isEmpty()) return null;

            int start = Collections.min(periods);
            int end = Collections.max(periods);
            String periodRange = start + "-" + end;

            TimetableItemDTO dto = new TimetableItemDTO();
            dto.setSubjectCode(subject.getSubjectCode());
            dto.setSubject(subject.getSubjectName());
            dto.setDay(day);
            dto.setPeriod(periodRange);
            dto.setProfessor(subject.getProfessor());
            dto.setRoom(subject.getClassroom());

            return dto;
        } catch (Exception e) {
            // 에러 발생 시 로그 찍고 null 반환
            System.err.println("⚠️ classTime 변환 중 오류: " + classTime + " → " + e.getMessage());
            return null;
        }
    }
    private final SubjectInfoRepository subjectInfoRepository;

    @Autowired
    public SubjectInfoService(SubjectInfoRepository subjectInfoRepository) {
        this.subjectInfoRepository = subjectInfoRepository;
    }

    public List<SubjectInfoDTO> getAllSubjects() {
        return subjectInfoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SubjectInfoDTO getSubjectByCode(String subjectCode) {
        return subjectInfoRepository.findById(subjectCode)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public SubjectInfoDTO createSubject(SubjectInfoDTO subjectInfoDTO) {
        SubjectInfo subjectInfo = convertToEntity(subjectInfoDTO);
        return convertToDTO(subjectInfoRepository.save(subjectInfo));
    }

    public SubjectInfoDTO updateSubject(String subjectCode, SubjectInfoDTO subjectInfoDTO) {
        if (subjectInfoRepository.existsById(subjectCode)) {
            SubjectInfo subjectInfo = convertToEntity(subjectInfoDTO);
            subjectInfo.setSubjectCode(subjectCode);
            return convertToDTO(subjectInfoRepository.save(subjectInfo));
        }
        return null;
    }

    public void deleteSubject(String subjectCode) {
        subjectInfoRepository.deleteById(subjectCode);
    }

    // 필터링 메서드들
    public List<SubjectInfoDTO> getSubjectsBySemester(String semester) {
        return subjectInfoRepository.findBySemester(semester).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> getSubjectsByCampus(String campus) {
        return subjectInfoRepository.findByCampus(campus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> getSubjectsByDepartment(String department) {
        return subjectInfoRepository.findByDepartment(department).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> getSubjectsByGrade(String grade) {
        return subjectInfoRepository.findByGrade(grade).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> getSubjectsByCredit(Integer credit) {
        return subjectInfoRepository.findByCredit(credit).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> getSubjectsByProfessor(String professor) {
        return subjectInfoRepository.findByProfessor(professor).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> getSubjectsByClassroom(String classroom) {
        return subjectInfoRepository.findByClassroom(classroom).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> getSubjectsByClassTime(String classTime) {
        return subjectInfoRepository.findByClassTime(classTime).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 검색 메서드들
    public List<SubjectInfoDTO> searchSubjectsByName(String subjectName) {
        return subjectInfoRepository.findBySubjectNameContaining(subjectName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> searchSubjectsByProfessor(String professor) {
        return subjectInfoRepository.findByProfessorContaining(professor).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> searchSubjectsByDepartment(String department) {
        return subjectInfoRepository.findByDepartmentContaining(department).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> searchSubjectsByCode(String subjectCode) {
        return subjectInfoRepository.findBySubjectCodeContaining(subjectCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> searchSubjectsByClassTime(String classTime) {
        return subjectInfoRepository.findByClassTimeContaining(classTime).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> searchSubjectsByGrade(String grade) {
        return subjectInfoRepository.findByGradeContaining(grade).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> searchSubjectsByCampus(String campus) {
        return subjectInfoRepository.findByCampusContaining(campus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> searchSubjectsBySemester(String semester) {
        return subjectInfoRepository.findBySemesterContaining(semester).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubjectInfoDTO> searchSubjectsByClassroom(String classroom) {
        return subjectInfoRepository.findByClassroomContaining(classroom).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SubjectInfoDTO convertToDTO(SubjectInfo subjectInfo) {
        SubjectInfoDTO dto = new SubjectInfoDTO();
        dto.setSubjectCode(subjectInfo.getSubjectCode());
        dto.setSemester(subjectInfo.getSemester());
        dto.setCampus(subjectInfo.getCampus());
        dto.setDepartment(subjectInfo.getDepartment());
        dto.setGrade(subjectInfo.getGrade());
        dto.setCredit(subjectInfo.getCredit());
        dto.setSubjectName(subjectInfo.getSubjectName());
        dto.setProfessor(subjectInfo.getProfessor());
        dto.setClassroom(subjectInfo.getClassroom());
        dto.setClassTime(subjectInfo.getClassTime());
        return dto;
    }

    private SubjectInfo convertToEntity(SubjectInfoDTO dto) {
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.setSubjectCode(dto.getSubjectCode());
        subjectInfo.setSemester(dto.getSemester());
        subjectInfo.setCampus(dto.getCampus());
        subjectInfo.setDepartment(dto.getDepartment());
        subjectInfo.setGrade(dto.getGrade());
        subjectInfo.setCredit(dto.getCredit());
        subjectInfo.setSubjectName(dto.getSubjectName());
        subjectInfo.setProfessor(dto.getProfessor());
        subjectInfo.setClassroom(dto.getClassroom());
        subjectInfo.setClassTime(dto.getClassTime());
        return subjectInfo;
    }
} 