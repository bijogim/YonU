package com.example.demo.repository;

import com.example.demo.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 특정 submenuId로 최근 3개 게시글 조회
    List<Post> findTop3BySubmenuIdOrderByCreatedAtDesc(int submenuId);

    // 특정 submenuId로 페이지네이션 조회
    Page<Post> findBySubmenuId(Integer submenuId, Pageable pageable);

    // 특정 submenuId로 전체 리스트 조회
    List<Post> findBySubmenuId(Integer submenuId);
}
