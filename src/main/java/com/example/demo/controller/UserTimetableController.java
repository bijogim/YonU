package com.example.demo.controller;

import com.example.demo.dto.TimetableItemDTO;
import com.example.demo.service.UserTimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")  // ✅ 모든 요청 허용 (개발용)
public class UserTimetableController {

    private final UserTimetableService userTimetableService;

    // ✅ 시간표 저장 (POST)
    @PostMapping("/save")
    public ResponseEntity<String> saveTimetable(@RequestBody List<TimetableItemDTO> timetableList, HttpSession session) {
        // ✅ 하드코딩으로 세션에 강제로 email 넣기
        if (session.getAttribute("email") == null) {
            session.setAttribute("email", "testuser@yonsei.ac.kr");  // 아까 DB에 넣은 가짜 사용자
        }
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return ResponseEntity.status(401).body("Unauthorized: No session");
        }

        userTimetableService.saveTimetable(timetableList, email);
        return ResponseEntity.ok("✅ 시간표 저장 완료");
    }

    // ✅ 내 시간표 조회 (GET)
    @GetMapping("/my")
    public ResponseEntity<List<TimetableItemDTO>> getMyTimetable(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return ResponseEntity.status(401).build();
        }

        List<TimetableItemDTO> timetable = userTimetableService.getTimetable(email);
        return ResponseEntity.ok(timetable);
    }
}
