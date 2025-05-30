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
        userTimetableRepository.deleteByEmail(email);

        List<UserTimetable> userTimetables = timetableList.stream()
                .filter(dto -> dto.getSubjectCode() != null && !dto.getSubjectCode().isEmpty())  // ❗ null, 빈 값 방어
                .map(dto -> {
                    UserTimetable ut = new UserTimetable();
                    ut.setEmail(email);
                    ut.setSubjectCode(dto.getSubjectCode());
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
                    dto.setSubjectCode(ut.getSubjectCode()); // ✅ 추가
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
