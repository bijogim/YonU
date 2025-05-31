package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.domain.Submenu;
import com.example.demo.dto.SubmenuPostDto;
import com.example.demo.dto.SubmenuWithPostsDto;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.SubmenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SubmenuRepository submenuRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, SubmenuRepository submenuRepository) {
        this.postRepository = postRepository;
        this.submenuRepository = submenuRepository;
    }

    @Override
    public List<SubmenuWithPostsDto> getRecentPostsByMenuId(int menuId) {
        List<Submenu> submenus = submenuRepository.findByMenuId(menuId);
        List<SubmenuWithPostsDto> result = new ArrayList<>();

        for (Submenu submenu : submenus) {
            List<Post> posts = postRepository.findTop3BySubmenuIdOrderByCreatedAtDesc(submenu.getSubmenuId());

            List<SubmenuPostDto> postDtos = posts.stream()
                    .map(p -> new SubmenuPostDto(p.getTitle(), p.getViewCount()))
                    .toList();

            result.add(new SubmenuWithPostsDto(submenu.getName(), postDtos));
        }

        return result;
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
