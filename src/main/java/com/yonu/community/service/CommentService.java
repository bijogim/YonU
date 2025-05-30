package com.yonu.community.service;

import com.yonu.community.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getCommentsByPostId(Long postId);
}
