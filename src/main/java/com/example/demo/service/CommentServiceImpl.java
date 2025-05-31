package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now()); // 현재 시간 자동 입력
        Comment savedComment = commentRepository.save(comment);

        // 댓글이 등록된 게시글의 commentCount 1 증가
        postRepository.findById(Long.valueOf(comment.getPostId())).ifPresent(post -> {
            int currentCount = post.getCommentCount() != null ? post.getCommentCount() : 0;
            post.setCommentCount(currentCount + 1);
            postRepository.save(post);
        });

        return savedComment;
    }

    //게시글(Post)의 commentCount 값을 자동으로 1 증가
    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

}