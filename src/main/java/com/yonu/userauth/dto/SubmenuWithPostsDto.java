package com.yonu.userauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SubmenuWithPostsDto {
    private String submenuName;
    private List<SubmenuPostDto> posts;
}
