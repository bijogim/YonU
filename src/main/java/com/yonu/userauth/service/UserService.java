package com.yonu.userauth.service;

import com.yonu.userauth.domain.User;
import com.yonu.userauth.dto.LoginDto;
import com.yonu.userauth.dto.UserDto;
import com.yonu.userauth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    // ✅ 생성자 주입 방식
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ 회원가입
    public void register(UserDto dto) {
        User user = new User();
        user.setEmail(dto.  getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setStudentId(dto.getStudentId());
        user.setDepartment(dto.getDepartment());
        user.setNickname(dto.getNickname());
        user.setPreferredLanguage(dto.getPreferredLanguage());
        user.setRole(1);
        userRepository.save(user);
    }

    // ✅ 로그인
    public boolean login(LoginDto dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
        return userOpt.map(user -> dto.getPassword().equals(user.getPassword()))
                .orElse(false);
    }

    // ✅ 이메일로 사용자 조회
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
    }

    // ✅ 비밀번호 재설정
    public String resetPasswordByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new NoSuchElementException("해당 이메일을 가진 사용자가 존재하지 않습니다.");
        }

        User user = userOpt.get();
        String tempPassword = UUID.randomUUID().toString().substring(0, 10);
        user.setPassword(tempPassword);
        userRepository.save(user);

        return tempPassword;
    }
}