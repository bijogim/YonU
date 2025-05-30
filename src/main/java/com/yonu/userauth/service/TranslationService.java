package com.yonu.userauth.service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {

    private final Translate translate;

    public TranslationService() throws IOException {
        // ✅ JSON 키 파일 경로
        String jsonPath = "src/main/resources/keys/yonu-translate-key.json";

        // ✅ Google Translate 클라이언트 초기화
        this.translate = TranslateOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(jsonPath)))
                .build()
                .getService();
    }

    /**
     * ✅ 4개 언어(ko, en, ja, zh)로 번역한 결과를 Map으로 반환
     */
    public Map<String, String> translateAll(String original) {
        Map<String, String> result = new HashMap<>();
        result.put("ko", original); // 원문

        result.put("en", translate(original, "en"));
        result.put("ja", translate(original, "ja"));
        result.put("zh", translate(original, "zh"));
        return result;
    }

    /**
     * ✅ 4개 언어 버전을 "ko|en|ja|zh" 형태의 문자열로 반환
     */
    public String joinAsMultiLang(String original) {
        Map<String, String> translations = translateAll(original);
        return String.join("|",
                translations.get("ko"),
                translations.get("en"),
                translations.get("ja"),
                translations.get("zh")
        );
    }

    /**
     * ✅ Google Translate API 요청 (1개 언어)
     */
    public String translate(String text, String targetLang) {
        Translation translation = translate.translate(
                text,
                Translate.TranslateOption.targetLanguage(targetLang),
                Translate.TranslateOption.model("nmt")  // 신경망 번역
        );
        return translation.getTranslatedText();
    }
}
