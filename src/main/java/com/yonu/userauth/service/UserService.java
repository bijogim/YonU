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
    private final TranslationService translationService;

    // ✅ 생성자 주입 방식
    public UserService(UserRepository userRepository, TranslationService translationService) {
        this.userRepository = userRepository;
        this.translationService = translationService;
    }

    // ✅ 회원가입
    public void register(UserDto dto) {
        User user = new User();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // TODO: 추후 암호화 권장
        user.setStudentId(dto.getStudentId());

        // ✅ 번역 후 "ko|en|ja|zh" 형태로 저장
        user.setName(translationService.joinAsMultiLang(dto.getName()));
        user.setDepartment(translationService.joinAsMultiLang(dto.getDepartment()));
        user.setNickname(translationService.joinAsMultiLang(dto.getNickname()));

        user.setLanguage(dto.getLanguage());  // "ko", "en", "ja", "zh"
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