package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.service.EmailService;
import com.example.demo.domain.User;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"}, allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // ✅ 회원가입
    @PostMapping("/sign_up")
    public ResponseEntity<String> register(@RequestBody UserDto dto) {
        userService.register(dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    // ✅ 로그인 + 세션 저장 + Spring Security 인증 설정
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto, HttpSession session) {
        boolean result = userService.login(dto);

        if (result) {
            User user = userService.findByEmail(dto.getEmail());

            // ✅ 세션에 사용자 정보 저장
            session.setAttribute("email", user.getEmail());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("name", user.getName());
            session.setAttribute("studentId", user.getStudentId());
            session.setAttribute("department", user.getDepartment());
            session.setAttribute("nickname", user.getNickname());
            session.setAttribute("Language", user.getLanguage());
            session.setAttribute("role", user.getRole());

            // ✅ Spring Security 인증 상태 수동 설정
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(), null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // ✅ [추가] 세션에 SecurityContext 저장
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
    @GetMapping("/logged_in")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        String email = (String) session.getAttribute("email");
        String nickname = (String) session.getAttribute("nickname");

        if (email != null) {
            response.put("status", "success");
            response.put("email", email);
            response.put("nickname", nickname);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "fail");
            response.put("message", "로그인되지 않았습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }



    @PostMapping("/logout")  // 로그아웃
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 모든 세션 속성 제거 및 세션 무효화
        return ResponseEntity.ok("로그아웃 성공");
    }


        @GetMapping("/mypage")
        public Map<String, Object> getMyPageInfo(HttpSession session) {

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", session.getAttribute("email"));
            userInfo.put("name", session.getAttribute("name"));
            userInfo.put("studentId", session.getAttribute("studentId"));
            userInfo.put("department", session.getAttribute("department"));
            userInfo.put("nickname", session.getAttribute("nickname"));
            userInfo.put("preferredLanguage", session.getAttribute("preferredLanguage"));
            userInfo.put("role", session.getAttribute("role"));
            return userInfo;
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