package com.devminj.blog.service.tag.dto;

import com.devminj.blog.domain.posts.Tag;
import lombok.Getter;

@Getter
public class TagCountByNameResponseDto {
    private String name;
    private Long count;

    public TagCountByNameResponseDto(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
