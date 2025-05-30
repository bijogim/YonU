package com.yonu.community.entity;

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

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;
    private String thumbnailUrl;
    private String hashtags;

    private Integer commentCount;
    private Integer viewCount = 0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Column(nullable = false)
    private Integer dislikeCount = 0;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}