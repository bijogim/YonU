package com.yonu.userauth.service;

import com.yonu.userauth.domain.Post;
import com.yonu.userauth.domain.Submenu;
import com.yonu.userauth.dto.SubmenuPostDto;
import com.yonu.userauth.dto.SubmenuWithPostsDto;
import com.yonu.userauth.repository.PostRepository;
import com.yonu.userauth.repository.SubmenuRepository;
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
