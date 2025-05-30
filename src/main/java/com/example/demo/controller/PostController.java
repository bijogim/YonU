package com.example.demo.controller;

import com.example.demo.dto.SubmenuWithPostsDto;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/by-menu/{menuId}")
    public ResponseEntity<List<SubmenuWithPostsDto>> getRecentPosts(@PathVariable int menuId) {
        List<SubmenuWithPostsDto> result = postService.getRecentPostsByMenuId(menuId);
        return ResponseEntity.ok(result);
    }
}