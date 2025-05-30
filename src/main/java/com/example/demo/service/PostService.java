package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.domain.Submenu;
import com.example.demo.dto.SubmenuPostDto;
import com.example.demo.dto.SubmenuWithPostsDto;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.SubmenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubmenuRepository submenuRepository;

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
}