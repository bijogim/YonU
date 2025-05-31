package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private Integer submenuId;

    private String title;

    // ✅ 긴 글 저장용: MEDIUMTEXT 사용
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    private String author;

    private String thumbnailUrl;

    private String hashtags;

    private Integer commentCount = 0;

    private Integer viewCount = 0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ✅ 좋아요, 싫어요 추가
    @Column(nullable = false)
    private Integer likeCount = 0;

    @Column(nullable = false)
    private Integer dislikeCount = 0;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ✅ 뷰 카운트 세터
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
