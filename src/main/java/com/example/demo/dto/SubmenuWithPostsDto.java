package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;




import java.util.List;

import com.example.demo.dto.SubmenuPostDto;

@Getter
@AllArgsConstructor

public class SubmenuWithPostsDto {
    private String submenuName;
    private List<SubmenuPostDto> posts;
}