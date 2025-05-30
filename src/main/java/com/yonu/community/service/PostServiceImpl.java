package com.yonu.community.service;

import com.yonu.community.entity.Post;
import com.yonu.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostsBySubmenuId(Integer submenuId) {
        return postRepository.findBySubmenuId(submenuId);
    }

    @Override
    public Page<Post> getPagedPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPostAndIncreaseView(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new RuntimeException("게시글을 찾을 수 없습니다: id = " + id)
        );
        post.setViewCount(post.getViewCount() + 1);
        return postRepository.save(post);
    }

    @Override
    public void incrementViewCount(Long id) {
        postRepository.findById(id).ifPresent(post -> {
            post.setViewCount(post.getViewCount() + 1);
            postRepository.save(post);
        });
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Page<Post> getPosts(Pageable pageable, Integer submenuId) {
        if (submenuId != null) {
            return postRepository.findBySubmenuId(submenuId, pageable);
        } else {
            return postRepository.findAll(pageable);
        }
    }
}