package com.example.demo.service;

import com.example.demo.dto.TimetableItemDTO;
import com.example.demo.entity.UserTimetable;
import com.example.demo.repository.UserTimetableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTimetableService {

    private final UserTimetableRepository userTimetableRepository;
    @Transactional
    // ✅ 시간표 저장: 기존 데이터 삭제 후 새로 저장
    public void saveTimetable(List<TimetableItemDTO> timetableList, String email) {
        // 기존 시간표 삭제
        userTimetableRepository.deleteByEmail(email);

        // 새로운 시간표 저장
        List<UserTimetable> userTimetables = timetableList.stream()
                .map(dto -> {
                    UserTimetable ut = new UserTimetable();
                    ut.setEmail(email);
                    ut.setSubjectCode(dto.getSubjectCode());  // ❗ 이 부분 주의: subjectCode 필드가 추가된 경우에 맞게 수정 필요
                    ut.setSubject(dto.getSubject());
                    ut.setDay(dto.getDay());
                    ut.setPeriod(dto.getPeriod());
                    ut.setProfessor(dto.getProfessor());
                    ut.setRoom(dto.getRoom());
                    return ut;
                })
                .collect(Collectors.toList());

        userTimetableRepository.saveAll(userTimetables);
    }

    // ✅ 사용자 시간표 조회
    public List<TimetableItemDTO> getTimetable(String email) {
        List<UserTimetable> userTimetables = userTimetableRepository.findByEmail(email);

        return userTimetables.stream()
                .map(ut -> {
                    TimetableItemDTO dto = new TimetableItemDTO();
                    dto.setSubject(ut.getSubject());
                    dto.setDay(ut.getDay());
                    dto.setPeriod(ut.getPeriod());
                    dto.setProfessor(ut.getProfessor());
                    dto.setRoom(ut.getRoom());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
