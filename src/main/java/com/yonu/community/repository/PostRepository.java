package com.yonu.community.repository;

import com.yonu.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // submenuId로 페이지네이션
    Page<Post> findBySubmenuId(Integer submenuId, Pageable pageable);

    // submenuId로 전체 리스트 조회
    List<Post> findBySubmenuId(Integer submenuId);
}