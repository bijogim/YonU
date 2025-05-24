package com.yonu.userauth.controller;

import com.yonu.userauth.dto.LoginDto;
import com.yonu.userauth.dto.UserDto;
import com.yonu.userauth.service.UserService;
import com.yonu.userauth.service.EmailService;
import com.yonu.userauth.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // ✅ 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto dto) {
        userService.register(dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    // ✅ 로그인 + 세션 저장
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto, HttpSession session) {
        boolean result = userService.login(dto);
        if (result) {
            // dto.email 대신 getter 사용 권장
            User user = userService.findByEmail(dto.getEmail());

            // 세션에 사용자 정보 저장
            session.setAttribute("email", user.getEmail());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("name", user.getName());
            session.setAttribute("studentId", user.getStudentId());
            session.setAttribute("department", user.getDepartment());
            session.setAttribute("nickname", user.getNickname());
            session.setAttribute("preferredLanguage", user.getPreferredLanguage());
            session.setAttribute("role", user.getRole());

            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    // ✅ 비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");

        try {
            String tempPassword = userService.resetPasswordByEmail(email);
            emailService.sendEmail(
                    email,
                    "🔐 임시 비밀번호 안내",
                    "당신의 임시 비밀번호는 다음과 같습니다: " + tempPassword
            );
            return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이메일은 존재하지 않습니다.");
        }
    }
}
