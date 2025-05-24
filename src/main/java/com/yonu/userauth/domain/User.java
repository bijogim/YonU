package com.yonu.userauth.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor // ⚠️ JPA용 기본 생성자
@Entity
@Table(name = "users")
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;
    private String studentId;
    private String department;
    private String nickname;

    @Column(name = "preferred_language", columnDefinition = "VARCHAR(20) DEFAULT 'kor'")
    private String preferredLanguage;

    private int role = 1;

    @Column(name = "created_at", insertable = false, updatable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
