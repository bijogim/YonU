package com.example.demo.repository;

import com.example.demo.entity.SubjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectInfoRepository extends JpaRepository<SubjectInfo, String> {
    // 정확한 매칭 검색
    List<SubjectInfo> findBySemester(String semester);
    List<SubjectInfo> findByCampus(String campus);
    List<SubjectInfo> findByDepartment(String department);
    List<SubjectInfo> findByGrade(String grade);
    List<SubjectInfo> findByCredit(Integer credit);
    List<SubjectInfo> findByProfessor(String professor);
    List<SubjectInfo> findByClassroom(String classroom);
    List<SubjectInfo> findByClassTime(String classTime);
    
    // LIKE 검색 메서드들
    @Query("SELECT s FROM SubjectInfo s WHERE s.subjectName LIKE %:keyword%")
    List<SubjectInfo> findBySubjectNameContaining(@Param("keyword") String keyword);

    @Query("SELECT s FROM SubjectInfo s WHERE s.professor LIKE %:keyword%")
    List<SubjectInfo> findByProfessorContaining(@Param("keyword") String keyword);

    @Query("SELECT s FROM SubjectInfo s WHERE s.department LIKE %:keyword%")
    List<SubjectInfo> findByDepartmentContaining(@Param("keyword") String keyword);

    @Query("SELECT s FROM SubjectInfo s WHERE s.subjectCode LIKE %:keyword%")
    List<SubjectInfo> findBySubjectCodeContaining(@Param("keyword") String keyword);

    @Query("SELECT s FROM SubjectInfo s WHERE s.classTime LIKE %:keyword%")
    List<SubjectInfo> findByClassTimeContaining(@Param("keyword") String keyword);

    @Query("SELECT s FROM SubjectInfo s WHERE s.grade LIKE %:keyword%")
    List<SubjectInfo> findByGradeContaining(@Param("keyword") String keyword);

    @Query("SELECT s FROM SubjectInfo s WHERE s.campus LIKE %:keyword%")
    List<SubjectInfo> findByCampusContaining(@Param("keyword") String keyword);

    @Query("SELECT s FROM SubjectInfo s WHERE s.semester LIKE %:keyword%")
    List<SubjectInfo> findBySemesterContaining(@Param("keyword") String keyword);

    @Query("SELECT s FROM SubjectInfo s WHERE s.classroom LIKE %:keyword%")
    List<SubjectInfo> findByClassroomContaining(@Param("keyword") String keyword);
} 