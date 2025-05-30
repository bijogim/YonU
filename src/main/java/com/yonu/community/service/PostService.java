package com.yonu.community.service;

import com.yonu.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> getAllPosts();
    Optional<Post> getPostById(Long id);
    Post createPost(Post post);
    void deletePost(Long id);
    List<Post> getPostsBySubmenuId(Integer submenuId);
    Page<Post> getPagedPosts(Pageable pageable);
    Post getPostAndIncreaseView(Long id);
    void incrementViewCount(Long id);
    Post savePost(Post post);

    Page<Post> getPosts(Pageable pageable, Integer submenuId);
}