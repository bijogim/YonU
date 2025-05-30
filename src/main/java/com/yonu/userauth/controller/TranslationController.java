package com.yonu.userauth.controller;

import com.yonu.userauth.dto.UserFieldRequestDto;
import com.yonu.userauth.dto.UserFieldResponseDto;
import com.yonu.userauth.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translate")
public class TranslationController {

    private final TranslationService translationService;

    // ✅ 생성자에서 필드 초기화
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    // ✅ 리턴 타입 명시되어 있어야 함
    @PostMapping("/user-fields")
    public ResponseEntity<UserFieldResponseDto> translateUserFields(@RequestBody UserFieldRequestDto dto) {
        String name = translationService.joinAsMultiLang(dto.getName());
        String department = translationService.joinAsMultiLang(dto.getDepartment());
        String nickname = translationService.joinAsMultiLang(dto.getNickname());

        UserFieldResponseDto response = new UserFieldResponseDto(name, department, nickname);
        return ResponseEntity.ok(response);
    }
}
