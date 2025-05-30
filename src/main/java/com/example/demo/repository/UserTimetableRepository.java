package com.example.demo.repository;

import com.example.demo.entity.UserTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTimetableRepository extends JpaRepository<UserTimetable, Long> {

    // ✅ email로 사용자별 시간표 가져오기
    List<UserTimetable> findByEmail(String email);

    // ✅ email로 사용자별 시간표 삭제
    void deleteByEmail(String email);

    // (추가적으로 필요한 경우 SubjectCode나 Color 검색도 가능)
}
